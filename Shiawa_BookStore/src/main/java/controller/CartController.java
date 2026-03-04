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
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        System.out.println(">>> CartController doPost CALLED");
//        System.out.println("ACTION = " + request.getParameter("action"));
//        System.out.println("BOOK_ID = " + request.getParameter("book_id"));
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
//        System.out.println("CUSTOMER = " + customer);
//        // ví dụ
//        if (customer != null) {
//            System.out.println("CUSTOMER_ID = " + customer.getId());
//        }
//        if (customer == null) {
//            response.sendRedirect(request.getContextPath() + "/cart");
//            return;
//        }
//
//        int customerId = user.getId();
//        int bookId = Integer.parseInt(request.getParameter("book_id"));
//        String action = request.getParameter("action");
//
//        CartItemDAO dao = new CartItemDAO();
//        CartItem item = dao.findItem(customerId, bookId);
//
//        switch (action) {
//            case "add":
//            case "increase":
//                if (item != null) {
//                    dao.updateQuantity(customerId, bookId, item.getQuantity() + 1);
//                } else {
//                    BookDAO bookDAO = new BookDAO();
//                    Book book = bookDAO.getBookById(bookId);
//
//                    CartItem newItem = new CartItem(
//                            0, customerId, bookId, 1,
//                            book.getPrice(), LocalDateTime.now()
//                    );
//                    dao.insert(newItem);
//                }
//                break;
//
//            case "decrease":
//                if (item != null) {
//                    int newQty = item.getQuantity() - 1;
//                    if (newQty <= 0) {
//                        dao.delete(customerId, bookId);
//                    } else {
//                        dao.updateQuantity(customerId, bookId, newQty);
//                    }
//                }
//                break;
//
//            case "delete":
//                dao.delete(customerId, bookId);
//                break;
//            case "confirm":
//                // Tại đây bạn sẽ gọi DAO để lưu Order (Hóa đơn) vào Database
//                // Tạm thời mình sẽ cho nó nhảy thẳng sang trang thành công
//                request.getRequestDispatcher("/WEB-INF/home/order-success.jsp").forward(request, response);
//                return; // Dừng hàm doPost tại đây luôn, không chạy xuống dưới nữa
//        }
//        // 🔥 Cập nhật lại tổng số sản phẩm trong giỏ
//        List<CartItem> cartItems = dao.getCartByCustomerId(customerId);
//
//        int totalQuantity = 0;
//        for (CartItem ci : cartItems) {
//            totalQuantity += ci.getQuantity();
//        }
//
//        session.setAttribute("cartSize", totalQuantity);
//        CartItem updatedItem = dao.findItem(customerId, bookId);
//        int newQty = (updatedItem != null) ? updatedItem.getQuantity() : 0;
//
    //// Kiểm tra có phải AJAX không
//        boolean isAjax = "XMLHttpRequest"
//                .equals(request.getHeader("X-Requested-With"));
//
//        if (isAjax) {
//
//            response.setContentType("application/json");
//            response.setCharacterEncoding("UTF-8");
//
//            String message = "";
//
//            if ("add".equals(action)) {
//                message = "Added to cart successfully!";
//            }
//
//            String json = "{"
//                    + "\"quantity\":" + newQty + ","
//                    + "\"totalCartItems\":" + totalQuantity + ","
//                    + "\"message\":\"" + message + "\""
//                    + "}";
//
//            response.getWriter().print(json);
//
////        } else {
////            // ĐÂY LÀ PHẦN THAY ĐỔI
////            // Lấy tham số 'redirect' từ nút bấm gửi sang
////            String destination = request.getParameter("redirect");
////
////            if ("checkout".equals(destination)) {
////                // Nếu bấm 'MUA NGAY' -> Nhảy sang trang đơn hàng
////                response.sendRedirect(request.getContextPath() + "/checkout");
////            } else {
////                // Các trường hợp khác (như nút Delete hoặc thêm vào giỏ bằng form) -> Về trang cart
////                response.sendRedirect(request.getContextPath() + "/cart");
////            }
////        }
//        } else {
//            String destination = request.getParameter("redirect");
//
////            if ("checkout".equals(destination)) {
////                // --- LOGIC MUA NGAY ---
////                // 1. Vẫn thêm vào giỏ hàng bình thường (code phía trên của bạn đã làm)
////
////                // 2. Lấy lại đúng item vừa thêm/cập nhật
////                CartItem buyNowItem = dao.findItem(customerId, bookId);
////                List<CartItem> buyNowList = new ArrayList<>();
////                if (buyNowItem != null) {
////                    buyNowList.add(buyNowItem);
////                }
////
////                // 3. Gửi danh sách CHỈ CÓ 1 MÓN này sang trang checkout
////                request.setAttribute("orderItems", buyNowList);
////                request.setAttribute("isBuyNow", true); // Đánh dấu đây là mua ngay
////
////                request.getRequestDispatcher("/WEB-INF/home/placeorder.jsp").forward(request, response);
////            } else {
////                // Thêm vào giỏ bình thường thì quay về cart
////                response.sendRedirect(request.getContextPath() + "/cart");
////            }
//            if ("checkout".equals(destination)) {
//                // 1. Lấy thông tin CartItem từ DB
//                CartItem realItem = dao.findItem(customerId, bookId);
//
//                // --- ĐOẠN FIX QUAN TRỌNG Ở ĐÂY ---
//                // 2. Phải lấy thêm thông tin Book đầy đủ từ BookDAO để có Stock thật
//                BookDAO bDao = new BookDAO();
//                Book currentBook = bDao.getBookById(bookId);
//
//                List<CartItem> buyNowList = new ArrayList<>();
//                if (realItem != null && currentBook != null) {
//                    CartItem displayItem = new CartItem();
//                    displayItem.setBookId(realItem.getBookId());
//                    displayItem.setCustomerId(realItem.getCustomerId());
//                    displayItem.setPrice(realItem.getPrice());
//                    displayItem.setQuantity(1);
//
//                    // Gán đối tượng Book vừa lấy từ BookDAO (chắc chắn có stock = 50)
//                    displayItem.setBook(currentBook);
//
//                    buyNowList.add(displayItem);
//                }
//
//                // 3. Gửi sang trang checkout
//                request.setAttribute("orderItems", buyNowList);
//                request.setAttribute("isBuyNow", true);
//                request.getRequestDispatcher("/WEB-INF/home/placeorder.jsp").forward(request, response);
//
//            } else {
//                // --- ĐOẠN CẦN THÊM VÀO ĐÂY ---
//                // Nếu không phải checkout (tức là chỉ bấm "Thêm vào giỏ")
//                // Ta chuyển hướng về trang Cart để chạy phương thức doGet và hiển thị lại giỏ hàng
//                response.sendRedirect(request.getContextPath() + "/cart");
//            }
//        }
//    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Kiểm tra đăng nhập & Role
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        if (user == null || !"customer".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int customerId = user.getId();
        String action = request.getParameter("action");
        CartItemDAO dao = new CartItemDAO();

        // 2. XỬ LÝ RIÊNG CHO ACTION "CONFIRM" (Đặt hàng)
        // Case này không cần book_id nên phải check trước để tránh parse lỗi
        if ("confirm".equals(action)) {
            // Sau này bạn sẽ thêm logic lưu vào bảng Orders/OrderDetails ở đây
            request.getRequestDispatcher("/WEB-INF/home/order-success.jsp").forward(request, response);
            return;
        }

        // 3. LẤY BOOK_ID CHO CÁC THAO TÁC GIỎ HÀNG
        String bookIdRaw = request.getParameter("book_id");
        if (bookIdRaw == null || bookIdRaw.isEmpty()) {
            // Nếu không có book_id mà cũng không phải confirm thì quay về giỏ hàng
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        int bookId = Integer.parseInt(bookIdRaw);
        CartItem item = dao.findItem(customerId, bookId);

        // 4. XỬ LÝ CÁC THAO TÁC GIỎ HÀNG (Switch-case)
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

        // 5. CẬP NHẬT LẠI GIỎ HÀNG TRÊN SESSION (cartSize)
        updateCartSession(session, dao, customerId);

        // 6. TRẢ VỀ KẾT QUẢ (AJAX HOẶC REDIRECT)
        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
        if (isAjax) {
            sendJsonResponse(response, dao, customerId, bookId, action);
        } else {
            String destination = request.getParameter("redirect");
            if ("checkout".equals(destination)) {
                handleBuyNow(request, response, dao, customerId, bookId);
            } else {
                response.sendRedirect(request.getContextPath() + "/cart");
            }
        }
    }

    /**
     * Cập nhật số lượng hiển thị trên icon giỏ hàng
     */
    private void updateCartSession(HttpSession session, CartItemDAO dao, int customerId) {
        List<CartItem> cartItems = dao.getCartByCustomerId(customerId);
        int totalQuantity = 0;
        for (CartItem ci : cartItems) {
            totalQuantity += ci.getQuantity();
        }
        session.setAttribute("cartSize", totalQuantity);
    }

    /**
     * Xử lý logic Mua ngay (Redirect sang trang thanh toán với 1 món duy nhất)
     */
    private void handleBuyNow(HttpServletRequest request, HttpServletResponse response,
            CartItemDAO dao, int customerId, int bookId)
            throws ServletException, IOException {
        BookDAO bDao = new BookDAO();
        Book currentBook = bDao.getBookById(bookId);

        List<CartItem> buyNowList = new ArrayList<>();
        if (currentBook != null) {
            CartItem displayItem = new CartItem();
            displayItem.setBookId(bookId);
            displayItem.setCustomerId(customerId);
            displayItem.setPrice(currentBook.getPrice());
            displayItem.setQuantity(1);
            displayItem.setBook(currentBook);
            buyNowList.add(displayItem);
        }

        request.setAttribute("orderItems", buyNowList);
        request.setAttribute("isBuyNow", true);
        request.getRequestDispatcher("/WEB-INF/home/placeorder.jsp").forward(request, response);
    }

    /**
     * Trả về JSON cho các request Ajax (Cập nhật số lượng tại chỗ)
     */
    private void sendJsonResponse(HttpServletResponse response, CartItemDAO dao,
            int customerId, int bookId, String action)
            throws IOException {
        CartItem updatedItem = dao.findItem(customerId, bookId);
        int newQty = (updatedItem != null) ? updatedItem.getQuantity() : 0;

        List<CartItem> cartItems = dao.getCartByCustomerId(customerId);
        int totalCartItems = cartItems.stream().mapToInt(CartItem::getQuantity).sum();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String message = "add".equals(action) ? "Added to cart successfully!" : "";
        String json = String.format("{\"quantity\":%d, \"totalCartItems\":%d, \"message\":\"%s\"}",
                newQty, totalCartItems, message);

        response.getWriter().print(json);
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
