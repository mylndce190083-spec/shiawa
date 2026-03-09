/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookDAO;
import dao.CartItemDAO;
import dao.CustomerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

        // 1. Check đăng nhập + role
        if (user == null || !"Customer".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
//
//        // 2. Lấy customer từ account
//        CustomerDAO customerDAO = new CustomerDAO();
//        Customer customer = customerDAO.getCustomerByAccountId(user.getId());
//
//        if (customer == null) {
//            request.setAttribute("cartItem", List.of());
//            request.getRequestDispatcher("/WEB-INF/home/cart.jsp")
//                    .forward(request, response);
//            return;
//        }
//
        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = customerDAO.getCustomerByAccountId(user.getId());

        if (customer == null) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }
        int customerId = customer.getId();

//        int customerId = user.getId(); // CHUẨN
        // 3. Lấy giỏ hàng
        CartItemDAO dao = new CartItemDAO();
        List<CartItem> cartItems = dao.getCartByCustomerId(customerId);

        request.setAttribute("cartItem", cartItems);

        request.getRequestDispatcher("/WEB-INF/home/cart.jsp")
                .forward(request, response);

// System.out.println(">>> CartTestController doGet CALLED <<<");
//
//        // TEST CỨNG customerId = 1
//        int customerId = 1;
//
//        CartItemDAO dao = new CartItemDAO();
//        List<CartItem> cartItems = dao.getCartByCustomerId(customerId);
//
//        System.out.println(">>> CART SIZE = " + cartItems.size());
//
//        for (CartItem item : cartItems) {
//            System.out.println(
//                "ITEM: bookId=" + item.getBookId()
//                + ", title=" + item.getBook().getTitle()
//                + ", qty=" + item.getQuantity()
//            );
//        }
//
//        request.setAttribute("cartItem", cartItems);
//        request.getRequestDispatcher("/WEB-INF/home/cart.jsp")
//                .forward(request, response);
//    }
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

        System.out.println(">>> CartController doPost CALLED");
        System.out.println("ACTION = " + request.getParameter("action"));
        System.out.println("BOOK_ID = " + request.getParameter("book_id"));

//        HttpSession session = request.getSession();
//        Account user = (Account) session.getAttribute("user");
//
//        if (user == null || !"customer".equals(user.getRole())) {
//            response.sendRedirect(request.getContextPath() + "/login");
//            return;
//        }
//
//        CustomerDAO customerDAO = new CustomerDAO();
//        Customer customer = customerDAO.getCustomerByAccountId(user.getId());
//
//        if (customer == null) {
//            response.sendRedirect(request.getContextPath() + "/cart");
//            return;
//        }
//
//        int customerId = user.getId();
//
//        String action = request.getParameter("action");
//        String bookIdRaw = request.getParameter("book_id");
//
//        if (action == null || bookIdRaw == null) {
//            response.sendRedirect(request.getContextPath() + "/cart");
//            return;
//        }
//
//        int bookId = Integer.parseInt(bookIdRaw);
//
//        CartItemDAO dao = new CartItemDAO();
//        CartItem item = dao.findItem(customerId, bookId);
//
//        switch (action) {
//            case "add":   // ✅ ADD TO CART
//                if (item != null) {
//                    // đã có → tăng số lượng
//                    item.setQuantity(item.getQuantity() + 1);
//                    dao.update(item);
//                } else {
//                    // chưa có → INSERT
//                    BookDAO bookDAO = new BookDAO();
//                    Book book = bookDAO.getBookById(bookId);
//
//                    if (book != null) {
//                        CartItem newItem = new CartItem(
//                                0,
//                                customerId,
//                                bookId,
//                                1,
//                                book.getPrice(),
//                                java.time.LocalDateTime.now()
//                        );
//                        dao.insert(newItem);
//                    }
//                }
//                break;
//            case "increase":
//                if (item != null) {
//                    item.setQuantity(item.getQuantity() + 1);
//                    dao.update(item);
//                }
//                break;
//
//            case "decrease":
//                if (item != null) {
//                    int newQty = item.getQuantity() - 1;
//                    if (newQty <= 0) {
//                        dao.delete(customerId, bookId);
//                    } else {
//                        item.setQuantity(newQty);
//                        dao.update(item);
//                    }
//                }
//                break;
//
//            case "delete":
//                dao.delete(customerId, bookId);
//                break;
//
//            default:
//                break;
//        }
//
//        response.sendRedirect(request.getContextPath() + "/cart");
//    }
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        if (user == null || !"Customer".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = customerDAO.getCustomerByAccountId(user.getId());
        System.out.println("CUSTOMER = " + customer);
        // ví dụ
        if (customer != null) {
            System.out.println("CUSTOMER_ID = " + customer.getId());
        }
        if (customer == null) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        int customerId = customer.getId();
        int bookId = Integer.parseInt(request.getParameter("book_id"));
        String action = request.getParameter("action");

        CartItemDAO dao = new CartItemDAO();
        CartItem item = dao.findItem(customerId, bookId);

        switch (action) {
            case "add":
            case "increase":
                if (item != null) {
                    dao.updateQuantity(customerId, bookId, item.getQuantity() + 1);
                } else {
                    BookDAO bookDAO = new BookDAO();
                    Book book = bookDAO.getBookById(bookId);

                    CartItem newItem = new CartItem(
                            0, customerId, bookId, 1,
                            book.getPrice(), LocalDateTime.now()
                    );
                    dao.insert(newItem);
                }
                break;

            case "decrease":
                if (item != null) {
                    int newQty = item.getQuantity() - 1;
                    if (newQty <= 0) {
                        dao.delete(customerId, bookId);
                    } else {
                        dao.updateQuantity(customerId, bookId, newQty);
                    }
                }
                break;

            case "delete":
                dao.delete(customerId, bookId);
                break;

        }
        // 🔥 Cập nhật lại tổng số sản phẩm trong giỏ
        List<CartItem> cartItems = dao.getCartByCustomerId(customerId);

        int totalQuantity = 0;
        for (CartItem ci : cartItems) {
            totalQuantity += ci.getQuantity();
        }

        session.setAttribute("cartSize", totalQuantity);
        CartItem updatedItem = dao.findItem(customerId, bookId);
        int newQty = (updatedItem != null) ? updatedItem.getQuantity() : 0;

// Kiểm tra có phải AJAX không
        boolean isAjax = "XMLHttpRequest"
                .equals(request.getHeader("X-Requested-With"));

        if (isAjax) {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String message = "";

            if ("add".equals(action)) {
                message = "Added to cart successfully!";
            }

            String json = "{"
                    + "\"quantity\":" + newQty + ","
                    + "\"totalCartItems\":" + totalQuantity + ","
                    + "\"message\":\"" + message + "\""
                    + "}";

            response.getWriter().print(json);

        } else {
            // Nếu là submit form bình thường (delete)
            response.sendRedirect(request.getContextPath() + "/cart");
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

    public static void main(String[] args) {

    }
}
