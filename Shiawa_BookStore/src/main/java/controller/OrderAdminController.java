/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.OrderDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Account;
import model.Orders;
import model.OrderDetail;

/**
 *
 * @author BA LIEM
 */
@WebServlet(name = "OrderAdminController", urlPatterns = {"/order-admin"})
public class OrderAdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        // 1. Check đăng nhập + role
        if (user == null || !"Admin".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.setAttribute("pagePrimary", "order-admin");
        String action = request.getParameter("action");
        OrderDAO dao = new OrderDAO();
        if (action == null || action.equals("list")) {

            int page = 1;
            int pageSize = 10;

            String pageParam = request.getParameter("page");

            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
            }

            List<Orders> list = dao.getOrdersByPage(page, pageSize);

            if (list.isEmpty()) {
                request.setAttribute("msg", "No orders found.");
            }

            int totalOrders = dao.getTotalOrders();
            int totalPage = (int) Math.ceil((double) totalOrders / pageSize);

            request.setAttribute("orderList", list);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPage", totalPage);
            request.getRequestDispatcher("/WEB-INF/order/list.jsp")
                    .forward(request, response);
        } else if ("detail".equals(action)) {

            int id = Integer.parseInt(request.getParameter("id"));

            Orders order = dao.getOrderByIdAdmin(id);
            List<OrderDetail> detailList = dao.getOrderDetails(id);

            request.setAttribute("order", order);
            request.setAttribute("detailList", detailList);

            request.getRequestDispatcher("/WEB-INF/order/detail.jsp")
                    .forward(request, response);
        } else if ("updateStatus".equals(action)) {

            int id = Integer.parseInt(request.getParameter("id"));
            Orders order = dao.getOrderByIdAdmin(id);

            request.setAttribute("order", order);
            request.getRequestDispatcher("/WEB-INF/order/status.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        OrderDAO dao = new OrderDAO();

        if ("updateStatus".equals(action)) {

            int id = Integer.parseInt(request.getParameter("id"));
            String newStatus = request.getParameter("status");
            HttpSession session = request.getSession();
            Account user = (Account) session.getAttribute("user");
            int adminId = user.getId();

            Orders current = dao.getOrderByIdAdmin(id);

            if (isValidTransition(current.getStatus(), newStatus)) {
                dao.updateStatus(id, newStatus,adminId);
            }

            response.sendRedirect("order-admin?action=list");
        }
    }

    private boolean isValidTransition(String current, String next) {

        switch (current) {
            case "PENDING":
                return next.equals("CONFIRMED");

            case "CONFIRMED":
                return next.equals("SHIPPING");

            case "SHIPPING":
                return next.equals("DELIVERED")
                        || next.equals("FAILED");
            case "CANCEL_REQUESTED":
                return next.equals("REFUNDED");
            default:
                return false;
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
