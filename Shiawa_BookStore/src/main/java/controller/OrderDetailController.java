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
import model.Customer;
import model.Orders;

/**
 *
 * @author MY
 */
@WebServlet(name = "OrderDetailController", urlPatterns = {"/OrderDetail"})
public class OrderDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer cus = (Customer) session.getAttribute("customer");

        if (cus == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        int orderId = Integer.parseInt(request.getParameter("id"));

        OrderDAO dao = new OrderDAO();
        Orders order = dao.getOrderById(orderId);

        request.setAttribute("order", order);
        request.getRequestDispatcher("/WEB-INF/home/orderdetail.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
