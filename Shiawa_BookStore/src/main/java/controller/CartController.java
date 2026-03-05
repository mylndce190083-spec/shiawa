/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookDAO;
import dao.CartItemDAO;
import dao.CustomerDAO;
import dao.OrderDAO;
import jakarta.mail.FetchProfile.Item;
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
        if ("confirm".equals(action)) {
            // 1. Lấy thông tin khách hàng
            String receiverName = request.getParameter("receiverName");
            String phone = request.getParameter("phone");
            String fullAddress = request.getParameter("detailAddress") + ", "
                    + request.getParameter("ward") + ", "
                    + request.getParameter("district") + ", "
                    + request.getParameter("province");

            // 2. LẤY PHÍ SHIP TỪ JSP GỬI SANG (Đoạn này quan trọng nè)
            double shippingFee = 0;
            String shipRaw = request.getParameter("shippingFee"); // Tên phải khớp với name="shippingFee" trong <input> bên JSP
            if (shipRaw != null && !shipRaw.isEmpty()) {
                shippingFee = Double.parseDouble(shipRaw);
            }

            List<CartItem> itemsToBuy = dao.getCartByCustomerId(customerId);

            if (itemsToBuy != null && !itemsToBuy.isEmpty()) {
                try (Connection conn = new db.DBContext().getConnection()) {
                    OrderDAO orderDao = new OrderDAO();

                    // Truyền biến shippingFee vừa lấy được vào đây
                    int newOrderId = orderDao.insertOrder(
                            conn,
                            customerId,
                            fullAddress,
                            shippingFee, // Sử dụng giá trị từ JSP gửi sang
                            receiverName,
                            phone,
                            itemsToBuy
                    );

                    if (newOrderId > 0) {
                        // Phải xóa giỏ hàng trong DB sau khi đặt xong
                        dao.getCartByCustomerId(customerId);

                        // Cập nhật lại cartSize trên session về 0
                        session.setAttribute("cartSize", 0);

                        request.setAttribute("orderId", newOrderId);
                        request.getRequestDispatcher("/WEB-INF/home/order-success.jsp").forward(request, response);
                        return; // NGẮT HÀM Ở ĐÂY, không cho chạy xuống switch-case bên dưới
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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

        // 5. CẬP NHẬT LẠI GIỎ HÀNG (Thay thế updateCartSession)
        int totalQty = dao.getTotalQuantityByCustomerId(customerId);
        session.setAttribute("cartSize", totalQty);


// 6. TRẢ VỀ KẾT QUẢ (Thay thế sendJsonResponse và handleBuyNow)
        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));

        if (isAjax) {
            // Render JSON trực tiếp
            CartItem updatedItem = dao.findItem(customerId, bookId);
            int newQty = (updatedItem != null) ? updatedItem.getQuantity() : 0;

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String json = String.format("{\"quantity\":%d, \"totalCartItems\":%d, \"message\":\"%s\"}",
                    newQty, totalQty, "add".equals(action) ? "Added to cart successfully!" : "");
            response.getWriter().print(json);
        } else {
            // Xử lý chuyển hướng
            String destination = request.getParameter("redirect");
            if ("checkout".equals(destination)) {
                // Xử lý Mua ngay: Gọi DAO lấy data và forward luôn
                request.setAttribute("orderItems", dao.getBuyNowList(bookId, customerId));
                request.setAttribute("isBuyNow", true);
                request.getRequestDispatcher("/WEB-INF/home/placeorder.jsp").forward(request, response);
            } else {
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
