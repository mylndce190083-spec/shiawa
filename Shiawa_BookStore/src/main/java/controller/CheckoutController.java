/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookDAO;
import dao.CartItemDAO;
import dao.OrderDAO;
import dao.OrderDetailDAO;
import dao.VoucherDAO;
import db.DBContext;
import jakarta.persistence.criteria.Order;

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
import java.util.Arrays;
import java.util.List;
import model.Account;
import model.Address;
import model.Book;
import model.CartItem;
import model.Customer;
import model.OrderItem;
import model.Orders;
import model.Voucher;

/**
 *
 * @author MY
 */
@WebServlet("/checkout")
public class CheckoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Voucher v = new Voucher();
        String voucherIdRaw = request.getParameter("voucherId");
        VoucherDAO vdao = new VoucherDAO();
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        // 1. Check đăng nhập + role
        if (user == null || !"Customer".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        CartItemDAO cartDAO = new CartItemDAO();
        List<CartItem> cartList
                = cartDAO.getCartByCustomerId(user.getId());

        if (cartList == null || cartList.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        } else {

            // 👉 Không có session → lấy từ đơn hàng gần nhất trong DB
            OrderDAO orderDAO = new OrderDAO();
            Orders lastOrder = orderDAO.getLastOrderByUserId(user.getId());

            if (lastOrder != null) {
                session.setAttribute("lastOrder", lastOrder);
                String lastAddress = lastOrder.getShippingAddress();

                if (lastAddress != null && !lastAddress.trim().isEmpty()) {

                    String[] parts = lastAddress.split(",");

                    if (parts.length >= 4) {
                        request.setAttribute("detailAddress", parts[0].trim());
                        request.setAttribute("ward", parts[1].trim());
                        request.setAttribute("district", parts[2].trim());
                        request.setAttribute("province", parts[3].trim());
                    }
                }

                // 👇 Thêm 3 dòng này
                request.setAttribute("receiverName", lastOrder.getReceiverName());
                request.setAttribute("phone", lastOrder.getPhone());
                request.setAttribute("lastOrder", lastOrder);
            }
            request.setAttribute("orderItems", cartList);
            List<Voucher> list = vdao.getMyVoucherList(user.getId());
            request.setAttribute("myVoucherList", list);
            request.getRequestDispatcher("/WEB-INF/home/placeorder.jsp")
                    .forward(request, response);
        }
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
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");
        Voucher v = new Voucher();
        String voucherIdRaw = request.getParameter("voucherId");
        VoucherDAO vdao = new VoucherDAO();

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        OrderDAO orderDAO = new OrderDAO();
        Orders lastOrder = orderDAO.getLastOrderByUserId(user.getId());

        if (lastOrder != null) {
            session.setAttribute("lastOrder", lastOrder);
        }
        if ("preview".equals(action)) {

            String[] selectedIds = request.getParameterValues("selectedItem");
            System.out.println("SelectedIds: " + Arrays.toString(selectedIds));
            if (selectedIds == null) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            CartItemDAO cartDAO = new CartItemDAO();
            List<CartItem> fullCart
                    = cartDAO.getCartByCustomerId(user.getId());

            List<CartItem> selectedItems = new ArrayList<>();

            for (String id : selectedIds) {
                int bookId = Integer.parseInt(id);

                for (CartItem item : fullCart) {
                    if (item.getBookId() == bookId) {
                        selectedItems.add(item);
                    }
                }
            }

            if (selectedItems.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }
            /*check hàng*/
            boolean hasOutOfStock = false;

            for (CartItem item : selectedItems) {
                if (item.getBook().getStock() <= 0) {
                    hasOutOfStock = true;
                    break;
                }
            }

            if (hasOutOfStock) {
                request.setAttribute("stockError",
                        "Có sản phẩm trong danh sách đã hết hàng!");

                request.setAttribute("orderItems", selectedItems);

                request.getRequestDispatcher("/WEB-INF/home/placeorder.jsp")
                        .forward(request, response);
                return;
            }

            double total = 0;
            for (CartItem item : selectedItems) {
                total += item.getPrice() * item.getQuantity();
            }

            request.setAttribute("orderItems", selectedItems);
            request.setAttribute("totalAmount", total);
            session.setAttribute("pendingItems", selectedItems);
            //lấy voucher
//            VoucherDAO vdao = new VoucherDAO();
            List<Voucher> list;
            list = vdao.getMyVoucherList(user.getId());

            request.setAttribute("myVoucherList", list);
            request.setAttribute("now", new java.util.Date());
            request.getRequestDispatcher(
                    "/WEB-INF/home/placeorder.jsp")
                    .forward(request, response);

            return;  //  QUAN TRỌNG
        }
        boolean isBuyNow = Boolean.TRUE.equals(session.getAttribute("isBuyNow"));

        List<CartItem> selectedItems;
        CartItemDAO cartDAO = new CartItemDAO();
        List<CartItem> fullCart = cartDAO.getCartByCustomerId(user.getId());
        if ("confirm".equals(action)) {

            System.out.println("=== CONFIRM CALLED ===");
            if (isBuyNow) {

                selectedItems = (List<CartItem>) session.getAttribute("buyNowItems");

                if (selectedItems == null || selectedItems.isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/cart");
                    return;
                }

            } else {

                String[] selectedIds = request.getParameterValues("selectedItem");

                if (selectedIds == null) {
                    response.sendRedirect(request.getContextPath() + "/cart");
                    return;
                }

                selectedItems = new ArrayList<>();

                for (String id : selectedIds) {
                    int bookId = Integer.parseInt(id);
                    for (CartItem item : fullCart) {
                        if (item.getBookId() == bookId) {
                            selectedItems.add(item);
                        }
                    }
                }
            }

            if (selectedItems.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }
            // 🔥 LƯU LẠI ĐỂ KHÔNG BỊ MẤT
            session.setAttribute("pendingItems", selectedItems);
//áp dụng voucher

            if (voucherIdRaw != null && !voucherIdRaw.isEmpty()) {
                Integer voucherId = Integer.parseInt(voucherIdRaw);
                v = vdao.getVoucherById(voucherId);

                request.setAttribute("selectedVoucher", v);

                // tính discount ở đây
            }

            double total = 0;
            for (CartItem item : selectedItems) {
                total += item.getPrice() * item.getQuantity();
            }
            double shippingFee = 20000;
            int discount = (int) v.getDiscount();

//            double amount = total + shippingFee - discount; đổi công thức
            double discountAmount = total * discount / 100.0;
            double amount = total - discountAmount + shippingFee;
            request.setAttribute("orderItems", selectedItems);
            request.setAttribute("totalAmount", total);

            try {

                // ===== LẤY PARAM =====
                String province = request.getParameter("province");
                String district = request.getParameter("district");
                String ward = request.getParameter("ward");
                String detail = request.getParameter("detailAddress");
                String phone = request.getParameter("phone");
                String receiverName = request.getParameter("receiverName");
                String isEditAddress = request.getParameter("isEditAddress");
// ===== PAYMENT METHOD =====
                String paymentMethod = request.getParameter("paymentMethod");
                System.out.println("Payment Method: " + paymentMethod);
                String phoneRegex = "^(03|05|07|08|09)[0-9]{8}$";
                String addressDetailRegex = "^.{3,100}$";

                boolean hasError = false;
                String shippingAddress;

                // =====================================================
                // TRƯỜNG HỢP 1: NGƯỜI DÙNG CÓ CHỈNH SỬA ĐỊA CHỈ
                // =====================================================
                if ("true".equals(isEditAddress)) {

                    if (province == null || province.trim().isEmpty()) {
                        request.setAttribute("provinceError", "Vui lòng chọn Tỉnh / Thành phố!");
                        hasError = true;
                    }

                    if (district == null || district.trim().isEmpty()) {
                        request.setAttribute("districtError", "Vui lòng chọn Quận / Huyện!");
                        hasError = true;
                    }

                    if (ward == null || ward.trim().isEmpty()) {
                        request.setAttribute("wardError", "Vui lòng chọn Phường / Xã!");
                        hasError = true;
                    }

                    if (detail == null || !detail.matches(addressDetailRegex)) {
                        request.setAttribute("detailError",
                                "Địa chỉ chi tiết phải từ 3 đến 100 ký tự!");
                        hasError = true;
                    }

                    if (phone == null || !phone.matches(phoneRegex)) {
                        request.setAttribute("phoneError",
                                "Số điện thoại không hợp lệ!");
                        hasError = true;
                    }

                    if (receiverName == null || receiverName.trim().isEmpty()) {
                        request.setAttribute("receiverNameError",
                                "Vui lòng nhập tên người nhận!");
                        hasError = true;
                    }

                    if (hasError) {
                        List<Voucher> list = vdao.getMyVoucherList(user.getId());
                        request.setAttribute("myVoucherList", list);
                        request.setAttribute("orderItems", selectedItems);
                        request.setAttribute("totalAmount", total);
                        request.getRequestDispatcher("/WEB-INF/home/placeorder.jsp")
                                .forward(request, response);
                        return;
                    }

                    shippingAddress = detail + ", "
                            + ward + ", "
                            + district + ", "
                            + province;

                } else {

                    // =====================================================
                    // TRƯỜNG HỢP 2: KHÔNG CHỈNH SỬA → DÙNG SESSION
                    // =====================================================
                    Customer customer = (Customer) session.getAttribute("customer");

                    receiverName = customer.getFullname();
                    phone = customer.getPhone();
                    shippingAddress = customer.getAddress();
                }

                // =====================================================
                // TẠO ORDER
                // =====================================================
//                int orderId = orderDAO.createOrder(
//                        user.getId(),
//                        selectedItems,
//                        shippingAddress,
//                        shippingFee,
//                        receiverName,
//                        phone
//                );
                // =====================================================
                // LƯU SESSION (chỉ khi có chỉnh sửa địa chỉ)
                // =====================================================
                if ("true".equals(isEditAddress)) {
                    session.setAttribute("savedProvince", province);
                    session.setAttribute("savedDistrict", district);
                    session.setAttribute("savedWard", ward);
                    session.setAttribute("savedDetailAddress", detail);
                    session.setAttribute("savedReceiverName", receiverName);
                    session.setAttribute("savedPhone", phone);

                }

                // =====================================================
                // XÓA CART
                // =====================================================
//                for (CartItem item : selectedItems) {
//                    cartDAO.delete(user.getId(), item.getBookId());
//                }
                request.setAttribute("selectedItems", selectedItems);
//                request.setAttribute("orderId", orderId);

                // =====================================================
// PAYMENT FLOW
// =====================================================
                Integer voucherId = null;
                if (voucherIdRaw != null && !voucherIdRaw.isEmpty()) {
                    voucherId = Integer.parseInt(voucherIdRaw);
                }
                if ("COD".equals(paymentMethod)) {

                    int orderId = orderDAO.createOrder(
                            user.getId(),
                            selectedItems,
                            shippingAddress,
                            shippingFee,
                            receiverName,
                            phone,
                            paymentMethod,
                            isBuyNow,
                            discount,
                            voucherId
                    );
                    session.removeAttribute("isBuyNow");
                    session.removeAttribute("buyNowItems");
                    if (voucherId  != null) {
                        vdao.markVoucherAsUsed(voucherId, orderId);
                    }
                    System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                    System.out.println(voucherIdRaw);
                    // ❗ FIX Ở ĐÂY
//                    if (!isBuyNow) {
//                        for (CartItem item : selectedItems) {
//                            cartDAO.delete(user.getId(), item.getBookId());
//                        }
//                    }
                    request.setAttribute("orderId", orderId);

                    request.getRequestDispatcher("/WEB-INF/home/order-success.jsp")
                            .forward(request, response);

                } else if ("ONLINE".equals(paymentMethod)) {
                    System.out.println("DEBUG RECEIVER = " + receiverName);
                    System.out.println("DEBUG PHONE = " + phone);
                    System.out.println("DEBUG ADDRESS = " + shippingAddress);
                    System.out.println("DEBUG ITEMS = " + selectedItems.size());

                    session.setAttribute("pendingItems", selectedItems);
                    session.setAttribute("pendingAddress", shippingAddress);
                    session.setAttribute("pendingReceiver", receiverName);
                    session.setAttribute("pendingPhone", phone);
                    session.setAttribute("pendingAmount", amount);
                    session.setAttribute("discount", discount);
                    session.setAttribute("voucherId", voucherId );
                    // 🔥 FIX QUAN TRỌNG
//                    session.removeAttribute("isBuyNow");
//                    session.removeAttribute("buyNowItems");
                    response.sendRedirect("ajaxServlet?amount=" + (int) amount);
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("orderItems", fullCart);
                request.getRequestDispatcher("/WEB-INF/home/cart.jsp")
                        .forward(request, response);
            }
        }

        if ("buy_now".equals(action)) {

            int bookId = Integer.parseInt(request.getParameter("book_id"));

            BookDAO bookDAO = new BookDAO();
            Book book = bookDAO.getBookById(bookId);

            // 🔥 tạo 1 CartItem giả
            CartItem item = new CartItem();
            item.setBook(book);
            item.setBookId(bookId);
            item.setQuantity(1);
            item.setPrice(book.getPrice());

            // 🔥 tạo list giống cart
            List<CartItem> orderItems = new ArrayList<>();
            orderItems.add(item);
            session.setAttribute("buyNowItems", orderItems);
            session.setAttribute("isBuyNow", true);
            // 🔥 set giống cart luôn
            request.setAttribute("orderItems", orderItems);
            List<Voucher> list;
            list = vdao.getMyVoucherList(user.getId());

            request.setAttribute("myVoucherList", list);
            request.setAttribute("now", new java.util.Date());
            if (voucherIdRaw != null && !voucherIdRaw.isEmpty()) {
                Integer voucherId = Integer.parseInt(voucherIdRaw);
                v = vdao.getVoucherById(voucherId);

                request.setAttribute("selectedVoucher", v);

                // tính discount ở đây
            }
            // subtotal
            request.setAttribute("subtotal", book.getPrice());

            request.getRequestDispatcher("/WEB-INF/home/placeorder.jsp")
                    .forward(request, response);
            return;
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
