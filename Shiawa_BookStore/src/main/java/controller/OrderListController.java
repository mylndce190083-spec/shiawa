/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.OrderDAO;
import dao.OrderDetailDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Account;
import model.OrderItem;
import model.Orders;

/**
 *
 * @author MY
 */
@WebServlet(name = "OrderListController", urlPatterns = {"/OrderList/*"})
public class OrderListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        // 1. Check đăng nhập + role
        if (user == null || !"Customer".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // 🔥 ĐẶT ĐOẠN MỚI Ở ĐÂY
        String pathInfo = request.getPathInfo();
        String status = "ALL";

        if (pathInfo != null) {
            status = pathInfo.substring(1).toUpperCase();
        }

        OrderDAO dao = new OrderDAO();
        OrderDetailDAO detailDAO = new OrderDetailDAO();
        List<Orders> orders;

        if (status == null || status.equals("ALL")) {
            orders = dao.getOrdersByCustomer(user.getId());
        } else {
            orders = dao.getOrdersByStatus(user.getId(), status);
        }
        for (Orders o : orders) {
            List<OrderItem> items = detailDAO.getItemsByOrderId(o.getOrderId());            
            o.setItems(items); //chổ này chưa biết tác dụng nên cmt lại
        }
//test        
        for (Orders o : orders) {
            for (OrderItem oi : o.getItems()) {
            System.out.println("ORDER 11: "+oi);
            }
        }
        
        request.setAttribute(
                "orders", orders);
        request.setAttribute("currentStatus", status);   // ⭐ THÊM DÒNG NÀY

        request.getRequestDispatcher(
                "/WEB-INF/home/orderlist.jsp")
                .forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");

        if ("cancel".equals(action)) {

            int orderId = Integer.parseInt(request.getParameter("orderId"));

            OrderDAO dao = new OrderDAO();

            // Chỉ hủy nếu đơn thuộc về user đó
            dao.cancelOrderIfPending(orderId, user.getId());
        }

        // Redirect lại để load danh sách mới
        response.sendRedirect(request.getContextPath() + "/OrderList");
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
