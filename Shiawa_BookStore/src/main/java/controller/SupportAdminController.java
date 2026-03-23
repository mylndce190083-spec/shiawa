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
import java.util.List;
import model.Account;
import model.ChatKnowledge;
import model.ChatMessage;
import model.ChatSession;

@WebServlet(name = "SupportAdminController", urlPatterns = {"/support-admin"})
public class SupportAdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account user = getAdminUser(request);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.setAttribute("pagePrimary", "support-admin");
        String view = request.getParameter("view");
        ChatDAO chatDao = new ChatDAO();
        ChatKnowledgeDAO knowledgeDao = new ChatKnowledgeDAO();

        if ("chat".equals(view)) {
            int sessionId = parseInt(request.getParameter("id"));
            if (sessionId <= 0) {
                response.sendRedirect(request.getContextPath() + "/support-admin");
                return;
            }

            chatDao.assignStaffIfEmpty(sessionId, user.getId());

            List<ChatMessage> messages = chatDao.getMessagesBySession(sessionId);
            request.setAttribute("sessionId", sessionId);
            request.setAttribute("messages", messages);
            request.getRequestDispatcher("/WEB-INF/support/admin-chat.jsp").forward(request, response);
            return;
        }

        if ("knowledge-add".equals(view)) {
            request.getRequestDispatcher("/WEB-INF/support/admin-knowledge-create.jsp").forward(request, response);
            return;
        }

        if ("knowledge-edit".equals(view)) {
            int id = parseInt(request.getParameter("id"));
            ChatKnowledge k = knowledgeDao.getById(id);
            request.setAttribute("knowledge", k);
            request.getRequestDispatcher("/WEB-INF/support/admin-knowledge-edit.jsp").forward(request, response);
            return;
        }

        if ("knowledge-toggle".equals(view)) {
            int id = parseInt(request.getParameter("id"));
            if (id > 0) {
                knowledgeDao.toggleActive(id);
            }
            response.sendRedirect(request.getContextPath() + "/support-admin");
            return;
        }

        List<ChatSession> sessions = chatDao.getAllSessions();
        List<ChatKnowledge> knowledgeList = knowledgeDao.getAll();
        request.setAttribute("chatSessions", sessions);
        request.setAttribute("knowledgeList", knowledgeList);
        request.getRequestDispatcher("/WEB-INF/support/admin-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account user = getAdminUser(request);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String view = request.getParameter("view");
        ChatDAO chatDao = new ChatDAO();
        ChatKnowledgeDAO knowledgeDao = new ChatKnowledgeDAO();

        if ("reply".equals(view)) {
            int sessionId = parseInt(request.getParameter("sessionId"));
            String content = request.getParameter("content");
            if (sessionId > 0 && content != null && !content.isBlank()) {
                chatDao.addMessage(sessionId, user.getUsername(), content.trim());
            }
            response.sendRedirect(request.getContextPath() + "/support-admin?view=chat&id=" + sessionId);
            return;
        }

        if ("knowledge-add".equals(view)) {
            ChatKnowledge k = new ChatKnowledge();
            k.setKeyword(request.getParameter("keyword"));
            k.setAnswer(request.getParameter("answer"));
            k.setActive("on".equals(request.getParameter("isActive")));
            knowledgeDao.insert(k);
            response.sendRedirect(request.getContextPath() + "/support-admin");
            return;
        }

        if ("knowledge-update".equals(view)) {
            ChatKnowledge k = new ChatKnowledge();
            k.setId(parseInt(request.getParameter("id")));
            k.setKeyword(request.getParameter("keyword"));
            k.setAnswer(request.getParameter("answer"));
            k.setActive("on".equals(request.getParameter("isActive")));
            knowledgeDao.update(k);
            response.sendRedirect(request.getContextPath() + "/support-admin");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/support-admin");
    }

    private int parseInt(String raw) {
        try {
            return Integer.parseInt(raw);
        } catch (Exception e) {
            return -1;
        }
    }

    private Account getAdminUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");
        if (user == null || !"Admin".equalsIgnoreCase(user.getRole())) {
            return null;
        }
        return user;
    }
}
