/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookDAO;
import dao.CartItemDAO;
import dao.CustomerDAO;
import jakarta.mail.FetchProfile.Item;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        if (user == null || !"customer".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        int customerId = user.getId(); //  CHUẨN

        // 3. Lấy giỏ hàng
        CartItemDAO dao = new CartItemDAO();
        List<CartItem> cartItems = dao.getCartByCustomerId(customerId);

        request.setAttribute("cartItem", cartItems);

        request.getRequestDispatcher("/WEB-INF/home/cart.jsp")
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
        System.out.println(">>> CartController doPost CALLED");
        System.out.println("ACTION = " + request.getParameter("action"));
        System.out.println("BOOK_ID = " + request.getParameter("book_id"));
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        if (user == null || !"customer".equals(user.getRole())) {
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

        int customerId = user.getId();
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

//        } else {
//            // ĐÂY LÀ PHẦN THAY ĐỔI
//            // Lấy tham số 'redirect' từ nút bấm gửi sang
//            String destination = request.getParameter("redirect");
//
//            if ("checkout".equals(destination)) {
//                // Nếu bấm 'MUA NGAY' -> Nhảy sang trang đơn hàng
//                response.sendRedirect(request.getContextPath() + "/checkout");
//            } else {
//                // Các trường hợp khác (như nút Delete hoặc thêm vào giỏ bằng form) -> Về trang cart
//                response.sendRedirect(request.getContextPath() + "/cart");
//            }
//        }
        } else {
            String destination = request.getParameter("redirect");

//            if ("checkout".equals(destination)) {
//                // --- LOGIC MUA NGAY ---
//                // 1. Vẫn thêm vào giỏ hàng bình thường (code phía trên của bạn đã làm)
//
//                // 2. Lấy lại đúng item vừa thêm/cập nhật
//                CartItem buyNowItem = dao.findItem(customerId, bookId);
//                List<CartItem> buyNowList = new ArrayList<>();
//                if (buyNowItem != null) {
//                    buyNowList.add(buyNowItem);
//                }
//
//                // 3. Gửi danh sách CHỈ CÓ 1 MÓN này sang trang checkout
//                request.setAttribute("orderItems", buyNowList);
//                request.setAttribute("isBuyNow", true); // Đánh dấu đây là mua ngay
//
//                request.getRequestDispatcher("/WEB-INF/home/placeorder.jsp").forward(request, response);
//            } else {
//                // Thêm vào giỏ bình thường thì quay về cart
//                response.sendRedirect(request.getContextPath() + "/cart");
//            }
            if ("checkout".equals(destination)) {
                // 1. Lấy dữ liệu thật từ Database
                CartItem realItem = dao.findItem(customerId, bookId);
                List<CartItem> buyNowList = new ArrayList<>();

                if (realItem != null) {
                    // 2. TẠO ĐỐI TƯỢNG HIỂN THỊ (Vẫn là dữ liệu thật nhưng chỉnh lại số lượng)
                    CartItem displayItem = new CartItem();
                    displayItem.setBookId(realItem.getBookId());
                    displayItem.setCustomerId(realItem.getCustomerId());
                    displayItem.setBook(realItem.getBook());
                    displayItem.setPrice(realItem.getPrice());
                    displayItem.setQuantity(1); // Ép số lượng hiển thị là 1 cho Mua Ngay

                    buyNowList.add(displayItem);
                }

                // 3. Gửi sang trang checkout
                request.setAttribute("orderItems", buyNowList);
                request.setAttribute("isBuyNow", true);
                request.getRequestDispatcher("/WEB-INF/home/placeorder.jsp").forward(request, response);

            } else {
                // --- ĐOẠN CẦN THÊM VÀO ĐÂY ---
                // Nếu không phải checkout (tức là chỉ bấm "Thêm vào giỏ")
                // Ta chuyển hướng về trang Cart để chạy phương thức doGet và hiển thị lại giỏ hàng
                response.sendRedirect(request.getContextPath() + "/cart");
            }
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
