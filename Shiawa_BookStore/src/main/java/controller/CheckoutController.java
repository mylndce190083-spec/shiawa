/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookDAO;
import dao.CartItemDAO;
import dao.OrderDAO;
import dao.VoucherDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Account;
import model.Book;
import model.CartItem;
import model.Customer;
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
        
        HttpSession session = request.getSession();
        Customer cus = (Customer) session.getAttribute("customer");

        if (cus == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        VoucherDAO vdao = new VoucherDAO();
        CartItemDAO cartDAO = new CartItemDAO();
        List<CartItem> cartList
                = cartDAO.getCartByCustomerId(cus.getId());

        if (cartList == null || cartList.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        } else {
            OrderDAO orderDAO = new OrderDAO();
            Orders lastOrder = orderDAO.getLastOrderByUserId(cus.getId());

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

                request.setAttribute("receiverName", lastOrder.getReceiverName());
                request.setAttribute("phone", lastOrder.getPhone());
                request.setAttribute("lastOrder", lastOrder);
            }
            request.setAttribute("orderItems", cartList);
            List<Voucher> list = vdao.getMyVoucherList(cus.getId());
            request.setAttribute("myVoucherList", list);
            request.getRequestDispatcher("/WEB-INF/home/placeorder.jsp")
                    .forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Voucher v = new Voucher();
        String voucherIdRaw = request.getParameter("voucherId");
        VoucherDAO vdao = new VoucherDAO();

        Customer cus = (Customer) session.getAttribute("customer");

        if (cus == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        OrderDAO orderDAO = new OrderDAO();
        Orders lastOrder = orderDAO.getLastOrderByUserId(cus.getId());

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
                    = cartDAO.getCartByCustomerId(cus.getId());

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
            List<Voucher> list;
            list = vdao.getMyVoucherList(cus.getId());

            request.setAttribute("myVoucherList", list);
            request.setAttribute("now", new java.util.Date());
            request.getRequestDispatcher(
                    "/WEB-INF/home/placeorder.jsp")
                    .forward(request, response);

            return;
        }
        boolean isBuyNow = Boolean.TRUE.equals(session.getAttribute("isBuyNow"));

        List<CartItem> selectedItems;
        CartItemDAO cartDAO = new CartItemDAO();
        List<CartItem> fullCart = cartDAO.getCartByCustomerId(cus.getId());
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
            session.setAttribute("pendingItems", selectedItems);

            if (voucherIdRaw != null && !voucherIdRaw.isEmpty()) {
                Integer voucherId = Integer.parseInt(voucherIdRaw);
                v = vdao.getVoucherById(voucherId);

                request.setAttribute("selectedVoucher", v);
            }

            double total = 0;
            for (CartItem item : selectedItems) {
                total += item.getPrice() * item.getQuantity();
            }
            double shippingFee = 20000;
            int discount = (int) v.getDiscount();
            double discountAmount = total * discount / 100.0;
            double amount = total - discountAmount + shippingFee;
            request.setAttribute("orderItems", selectedItems);
            request.setAttribute("totalAmount", total);

            try {
                String province = request.getParameter("province");
                String district = request.getParameter("district");
                String ward = request.getParameter("ward");
                String detail = request.getParameter("detailAddress");
                String phone = request.getParameter("phone");
                String receiverName = request.getParameter("receiverName");
                String isEditAddress = request.getParameter("isEditAddress");
                String paymentMethod = request.getParameter("paymentMethod");
                System.out.println("Payment Method: " + paymentMethod);
                String phoneRegex = "^(03|05|07|08|09)[0-9]{8}$";
                String addressDetailRegex = "^.{3,100}$";

                boolean hasError = false;
                String shippingAddress;

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
                        List<Voucher> list = vdao.getMyVoucherList(cus.getId());
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

                    Customer customer = (Customer) session.getAttribute("customer");

                    receiverName = customer.getFullname();
                    phone = customer.getPhone();
                    shippingAddress = customer.getAddress();
                }

                
                if ("true".equals(isEditAddress)) {
                    session.setAttribute("savedProvince", province);
                    session.setAttribute("savedDistrict", district);
                    session.setAttribute("savedWard", ward);
                    session.setAttribute("savedDetailAddress", detail);
                    session.setAttribute("savedReceiverName", receiverName);
                    session.setAttribute("savedPhone", phone);

                }

                request.setAttribute("selectedItems", selectedItems);

                Integer voucherId = null;
                if (voucherIdRaw != null && !voucherIdRaw.isEmpty()) {
                    voucherId = Integer.parseInt(voucherIdRaw);
                }
                if ("COD".equals(paymentMethod)) {

                    int orderId = orderDAO.createOrder(
                            cus.getId(),
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
                    
                    request.setAttribute("orderId", orderId);

                    request.getRequestDispatcher("/WEB-INF/home/order-success.jsp")
                            .forward(request, response);

                } else if ("ONLINE".equals(paymentMethod)) {
                    session.setAttribute("pendingItems", selectedItems);
                    session.setAttribute("pendingAddress", shippingAddress);
                    session.setAttribute("pendingReceiver", receiverName);
                    session.setAttribute("pendingPhone", phone);
                    session.setAttribute("pendingAmount", amount);
                    session.setAttribute("discount", discount);
                    session.setAttribute("voucherId", voucherId );
                    
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

            CartItem item = new CartItem();
            item.setBook(book);
            item.setBookId(bookId);
            item.setQuantity(1);
            item.setPrice(book.getPrice());

            List<CartItem> orderItems = new ArrayList<>();
            orderItems.add(item);
            session.setAttribute("buyNowItems", orderItems);
            session.setAttribute("isBuyNow", true);
            request.setAttribute("orderItems", orderItems);
            List<Voucher> list;
            list = vdao.getMyVoucherList(cus.getId());

            request.setAttribute("myVoucherList", list);
            request.setAttribute("now", new java.util.Date());
            if (voucherIdRaw != null && !voucherIdRaw.isEmpty()) {
                Integer voucherId = Integer.parseInt(voucherIdRaw);
                v = vdao.getVoucherById(voucherId);

                request.setAttribute("selectedVoucher", v);
            }
            request.setAttribute("subtotal", book.getPrice());

            request.getRequestDispatcher("/WEB-INF/home/placeorder.jsp")
                    .forward(request, response);
            return;
        }
    }

}
