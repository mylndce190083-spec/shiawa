/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CartItemDAO;
import dao.OrderDAO;
import dao.VoucherDAO;
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
import model.CartItem;

/**
 *
 * @author MY
 */
@WebServlet("/vnpay_return")
public class VnpayReturnController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");
        boolean isBuyNow = Boolean.TRUE.equals(session.getAttribute("isBuyNow"));
        String responseCode = request.getParameter("vnp_ResponseCode");

        if ("00".equals(responseCode)) {

            List<CartItem> items = (List<CartItem>) session.getAttribute("pendingItems");

            // FIX CHẮC CHẮN
            if (items == null || items.isEmpty()) {
                System.out.println("❌ pendingItems NULL");

                CartItemDAO cartDAO = new CartItemDAO();
                items = cartDAO.getCartByCustomerId(user.getId());

                if (items == null || items.isEmpty()) {
                    response.sendRedirect("cart");
                    return;
                }
            }

            String address = (String) session.getAttribute("pendingAddress");
            String receiver = (String) session.getAttribute("pendingReceiver");
            String phone = (String) session.getAttribute("pendingPhone");
            int discount = (int) session.getAttribute("discount");
            Integer voucherId = (Integer) session.getAttribute("voucherId");
            OrderDAO orderDAO = new OrderDAO();
            CartItemDAO cartDAO = new CartItemDAO();
            int orderId = 0;
            try {
                orderId = orderDAO.createOrder(
                        user.getId(),
                        items,
                        address,
                        20000,
                        receiver,
                        phone,
                        "ONLINE",
                        isBuyNow,
                        discount,
                        voucherId
                );

                if (voucherId != null) {
                    VoucherDAO vdao = new VoucherDAO();
                    vdao.markVoucherAsUsed(voucherId, orderId);
                }

            } catch (Exception e) {
                e.printStackTrace();

                request.setAttribute("error", "Lỗi tạo đơn hàng!");
                request.getRequestDispatcher("/WEB-INF/home/placeorder.jsp")
                        .forward(request, response);
                return;
            }

            // clear session
            session.removeAttribute("pendingItems");
            session.removeAttribute("pendingAddress");
            session.removeAttribute("pendingReceiver");
            session.removeAttribute("pendingPhone");
            session.removeAttribute("discount");
            session.removeAttribute("voucherId");
            session.removeAttribute("isBuyNow");
            session.removeAttribute("buyNowItems");
            request.setAttribute("orderId", orderId);
            request.getRequestDispatcher("/vnpay_return.jsp")
                    .forward(request, response);

        } else {
            request.setAttribute("error", "Thanh toán thất bại!");
            request.getRequestDispatcher("/WEB-INF/home/placeorder.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
