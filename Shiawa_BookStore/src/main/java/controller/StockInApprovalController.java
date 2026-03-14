package controller;

import dao.StockInRequestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Account;
import model.StockInRequest;
import model.StockInRequestItem;

@WebServlet(name = "StockInApprovalController", urlPatterns = {"/admin/stock-in-approval"})
public class StockInApprovalController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object u = request.getSession().getAttribute("user");
        if (!(u instanceof Account) || !"Admin".equalsIgnoreCase(((Account) u).getRole())) {
            response.sendRedirect(request.getContextPath() + "/inventory?view=in");
            return;
        }

        StockInRequestDAO dao = new StockInRequestDAO();
        List<StockInRequest> requests = dao.getRequestsWithItems(null);
        request.setAttribute("requests", requests);
        request.getRequestDispatcher("/WEB-INF/inventory/approval.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object u = request.getSession().getAttribute("user");
        if (!(u instanceof Account) || !"Admin".equalsIgnoreCase(((Account) u).getRole())) {
            response.sendRedirect(request.getContextPath() + "/inventory?view=in");
            return;
        }

        int requestId = Integer.parseInt(request.getParameter("requestId"));
        String action = request.getParameter("action");
        Account admin = (Account) u;

        StockInRequestDAO dao = new StockInRequestDAO();
        try {
            if ("approve".equals(action)) {
                dao.approveRequest(requestId, admin.getId());
            } else if ("reject".equals(action)) {
                String reason = request.getParameter("reason");
                dao.rejectRequest(requestId, admin.getId(), reason == null ? "" : reason.trim());
            }
            response.sendRedirect(request.getContextPath() + "/admin/stock-in-approval");
        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
            doGet(request, response);
        }
    }
}
