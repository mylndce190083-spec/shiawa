/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookDAO;
import dao.CartItemDAO;
import dao.CustomerDAO;
import dao.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;
import model.Account;
import model.Book;
import model.CartItem;
import model.Customer;

/**
 *
 * @author MY
 */
@WebServlet("/cart")
public class CartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        if (user == null || !"Customer".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = customerDAO.getCustomerByAccountId(user.getId());

        if (customer == null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        int customerId = customer.getId();
        CartItemDAO dao = new CartItemDAO();
        List<CartItem> cartItems = dao.getCartByCustomerId(customerId);
        
        int totalQuantity = 0;
        for (CartItem ci : cartItems) {
            totalQuantity += ci.getQuantity();
        }

        session.setAttribute("cartSize", totalQuantity);
        request.setAttribute("cartItem", cartItems);
        request.getRequestDispatcher("/WEB-INF/home/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        if (user == null || !"Customer".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = customerDAO.getCustomerByAccountId(user.getId());
        if (customer == null) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        int customerId = customer.getId();
        String action = request.getParameter("action");
        CartItemDAO dao = new CartItemDAO();

        if ("confirm".equals(action)) {
            String receiverName = request.getParameter("receiverName");
            String phone = request.getParameter("phone");
            String fullAddress = request.getParameter("detailAddress") + ", "
                    + request.getParameter("ward") + ", "
                    + request.getParameter("district") + ", "
                    + request.getParameter("province");

            double shippingFee = 0;
            String shipRaw = request.getParameter("shippingFee");
            if (shipRaw != null && !shipRaw.isEmpty()) {
                shippingFee = Double.parseDouble(shipRaw);
            }

            List<CartItem> itemsToBuy = dao.getCartByCustomerId(customerId);
           
            if (itemsToBuy != null && !itemsToBuy.isEmpty()) {
                try (Connection conn = new db.DBContext().getConnection()) {
                    OrderDAO orderDao = new OrderDAO();
              
                    String paymentMethod = request.getParameter("paymentMethod");
                    int newOrderId = orderDao.insertOrder(conn, customerId, fullAddress, shippingFee, receiverName, phone, paymentMethod);

                    if (newOrderId > 0) {
                      
                        dao.getCartByCustomerId(customerId); 
                        session.setAttribute("cartSize", 0);
                        request.setAttribute("orderId", newOrderId);
                        request.getRequestDispatcher("/WEB-INF/home/order-success.jsp").forward(request, response);
                        return; 
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

     
        String bookIdRaw = request.getParameter("book_id");
        if (bookIdRaw == null || bookIdRaw.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }
        int bookId = Integer.parseInt(bookIdRaw);
        CartItem item = dao.findItem(customerId, bookId);

        switch (action) {
            case "add":
            case "increase":
                if (item != null) {
                    dao.updateQuantity(customerId, bookId, item.getQuantity() + 1);
                } else {
                    BookDAO bookDAO = new BookDAO();
                    Book book = bookDAO.getBookById(bookId);
                    CartItem newItem = new CartItem(0, customerId, bookId, 1, book.getPrice(), LocalDateTime.now());
                    dao.insert(newItem);
                }
                break;
            case "decrease":
                if (item != null) {
                    int newQty = item.getQuantity() - 1;
                    if (newQty <= 0) dao.delete(customerId, bookId);
                    else dao.updateQuantity(customerId, bookId, newQty);
                }
                break;
            case "delete":
                dao.delete(customerId, bookId);
                break;
        }

        // Tính toán lại tổng số lượng sau khi thao tác
        int totalQuantity = dao.getTotalQuantityByCustomerId(customerId);
        session.setAttribute("cartSize", totalQuantity);

        // --- TRẢ VỀ KẾT QUẢ ---
        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));

        if (isAjax) {
            CartItem updatedItem = dao.findItem(customerId, bookId);
            int currentQty = (updatedItem != null) ? updatedItem.getQuantity() : 0;
            String message = "add".equals(action) ? "Added to cart successfully!" : "";

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String json = String.format("{\"quantity\":%d, \"totalCartItems\":%d, \"message\":\"%s\"}",
                    currentQty, totalQuantity, message);
            response.getWriter().print(json);
            return;
        } else {
            String destination = request.getParameter("redirect");
            if ("checkout".equals(destination)) {
                request.setAttribute("orderItems", dao.getBuyNowList(bookId, customerId));
                request.setAttribute("isBuyNow", true);
                request.getRequestDispatcher("/WEB-INF/home/placeorder.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/cart");
            }
        }
    }
}