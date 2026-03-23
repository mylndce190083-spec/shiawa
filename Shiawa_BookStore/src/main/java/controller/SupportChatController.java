package controller;

import dao.ChatDAO;
import dao.ChatKnowledgeDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.text.Normalizer;
import java.util.List;
import model.Account;
import model.ChatKnowledge;
import model.ChatMessage;

@WebServlet(name = "SupportChatController", urlPatterns = {"/chat"})
public class SupportChatController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ChatDAO dao = new ChatDAO();
        int sessionId = resolveSessionId(request, dao);
        if (sessionId > 0) {
            List<ChatMessage> messages = dao.getMessagesBySession(sessionId);
            request.setAttribute("sessionId", sessionId);
            request.setAttribute("messages", messages);
        }
        request.getRequestDispatcher("/WEB-INF/support/chat.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ChatDAO dao = new ChatDAO();
        int sessionId = resolveSessionId(request, dao);
        String message = request.getParameter("message");

        if (sessionId > 0 && message != null && !message.isBlank()) {
            HttpSession session = request.getSession();
            Account user = (Account) session.getAttribute("user");

            String senderName = "guest";
            if (user != null && "Customer".equalsIgnoreCase(user.getRole())) {
                senderName = user.getUsername();
            }

            String cleanMessage = message.trim();
            dao.addMessage(sessionId, senderName, cleanMessage);

            String autoReply = findAutoReply(cleanMessage);
            dao.addMessage(sessionId, "Support Bot", autoReply);
        }

        response.sendRedirect(request.getContextPath() + "/chat");
    }

    private int resolveSessionId(HttpServletRequest request, ChatDAO dao) {
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        Integer customerId = null;
        if (user != null && "Customer".equalsIgnoreCase(user.getRole())) {
            customerId = user.getId();
        }

        Object savedSessionId = session.getAttribute("chatSessionId");
        if (savedSessionId instanceof Integer) {
            int oldSessionId = (Integer) savedSessionId;
            if (dao.isSessionOwnedByCustomer(oldSessionId, customerId)) {
                return oldSessionId;
            }
            session.removeAttribute("chatSessionId");
        }

        int sessionId = dao.getOrCreateSession(customerId);
        if (sessionId > 0) {
            session.setAttribute("chatSessionId", sessionId);
        }
        return sessionId;
    }

    private String findAutoReply(String message) {
        String fallback = "Xin chào! Mình đã ghi nhận câu hỏi. Admin sẽ phản hồi bạn sớm nhất.";

        String normalizedMsg = normalize(message);
        if (normalizedMsg.isBlank()) {
            return fallback;
        }

        List<ChatKnowledge> knowledgeList = new ChatKnowledgeDAO().getAll();
        int bestScore = 0;
        String bestAnswer = null;

        for (ChatKnowledge k : knowledgeList) {
            if (!k.isActive()) {
                continue;
            }

            String keyword = normalize(k.getKeyword());
            int score = scoreMatch(normalizedMsg, keyword);
            if (score > bestScore) {
                bestScore = score;
                bestAnswer = k.getAnswer();
            }
        }

        if (bestAnswer == null || bestScore < 1) {
            return fallback;
        }
        return bestAnswer;
    }

    private int scoreMatch(String message, String keyword) {
        if (message.isBlank() || keyword.isBlank()) {
            return 0;
        }
        int score = 0;
        if (message.contains(keyword) || keyword.contains(message)) {
            score += 3;
        }

        String[] tokens = message.split(" ");
        for (String token : tokens) {
            if (token.length() < 2) {
                continue;
            }
            if (keyword.contains(token)) {
                score += 1;
            }
        }
        return score;
    }

    private String normalize(String value) {
        if (value == null) {
            return "";
        }
        String text = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
        return text.replaceAll("[^a-z0-9 ]", " ").replaceAll("\\s+", " ").trim();
    }
}
