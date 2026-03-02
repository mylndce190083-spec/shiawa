/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookDAO;
import dao.CartItemDAO;
import dao.OrderDAO;
import dao.OrderDetailDAO;
import db.DBContext;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Account;

import model.CartItem;
import model.Customer;
import model.OrderItem;
import model.Orders;

/**
 *
 * @author MY
 */
@WebServlet("/checkout")
public class CheckoutController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CheckoutController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckoutController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        CartItemDAO cartDAO = new CartItemDAO();
        List<CartItem> cartList
                = cartDAO.getCartByCustomerId(user.getId());

        if (cartList == null || cartList.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        request.setAttribute("orderItems", cartList);

        request.getRequestDispatcher("/WEB-INF/home/placeorder.jsp")
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
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
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

            request.getRequestDispatcher(
                    "/WEB-INF/home/placeorder.jsp")
                    .forward(request, response);

            return;  // 🔥 QUAN TRỌNG
        }

        if ("confirm".equals(action)) {
            System.out.println("=== CONFIRM CALLED ===");
            String[] selectedIds = request.getParameterValues("selectedItem");

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
            double total = 0;

            for (CartItem item : selectedItems) {
                total += item.getPrice() * item.getQuantity();
            }

            request.setAttribute("orderItems", selectedItems);
            request.setAttribute("totalAmount", total);
            try {

                // Lấy từng phần địa chỉ
                String province = request.getParameter("province");
                String district = request.getParameter("district");
                String ward = request.getParameter("ward");
                String detail = request.getParameter("detailAddress");
                String phone = request.getParameter("phone");
                String receiverName = request.getParameter("receiverName");
                // ===== VALIDATE ĐỊA CHỈ + SỐ ĐIỆN THOẠI =====

                String phoneRegex = "^(03|05|07|08|09)[0-9]{8}$";
                String addressDetailRegex = "^.{3,100}$";

                boolean hasError = false;

// ===== VALIDATE PROVINCE =====
                if (province == null || province.trim().isEmpty()) {
                    request.setAttribute("provinceError", "Vui lòng chọn Tỉnh / Thành phố!");
                    hasError = true;
                }

// ===== VALIDATE DISTRICT =====
                if (district == null || district.trim().isEmpty()) {
                    request.setAttribute("districtError", "Vui lòng chọn Quận / Huyện!");
                    hasError = true;
                }

// ===== VALIDATE WARD =====
                if (ward == null || ward.trim().isEmpty()) {
                    request.setAttribute("wardError", "Vui lòng chọn Phường / Xã!");
                    hasError = true;
                }

// ===== VALIDATE DETAIL ADDRESS =====
                if (detail == null || !detail.matches(addressDetailRegex)) {
                    request.setAttribute("detailError",
                            "Địa chỉ chi tiết phải từ 3 đến 100 ký tự!");
                    hasError = true;
                }

// ===== VALIDATE PHONE =====
                if (phone == null || !phone.matches(phoneRegex)) {
                    request.setAttribute("phoneError",
                            "Số điện thoại phải đủ 10 số và bắt đầu bằng 03,05,07,08,09!");
                    hasError = true;
                }
                if (receiverName == null || receiverName.trim().isEmpty()) {
                    request.setAttribute("receiverNameError", "Vui lòng nhập tên người nhận");
                    hasError = true;
                }

// ===== NẾU CÓ LỖI =====
                if (hasError) {

                    // giữ lại dữ liệu người dùng đã nhập
                    request.setAttribute("province", province);
                    request.setAttribute("district", district);
                    request.setAttribute("ward", ward);
                    request.setAttribute("detailAddress", detail);
                    request.setAttribute("phone", phone);
                    request.setAttribute("receiverName", receiverName);
                    request.setAttribute("orderItems", selectedItems);
                    request.setAttribute("totalAmount", total);

                    request.getRequestDispatcher("/WEB-INF/home/placeorder.jsp")
                            .forward(request, response);
                    return;
                }
// Gộp lại thành 1 chuỗi
                String shippingAddress = detail + ", "
                        + ward + ", "
                        + district + ", "
                        + province;

                double shippingFee = 20000;
                OrderDAO orderDAO = new OrderDAO();

                // 🔥 Gọi 1 lần duy nhất
                int orderId = orderDAO.createOrder(
                        user.getId(),
                        selectedItems,
                        shippingAddress,
                        shippingFee,
                        receiverName, phone
                );

                // Xóa cart sau khi đặt thành công
                for (CartItem item : selectedItems) {
//                cartDAO.deleteCartItem(
//                        user.getId(),
//                        item.getBookId()
//                );
                    cartDAO.delete(user.getId(), item.getBookId());
                }

                request.setAttribute("selectedItems", selectedItems);
                request.setAttribute("orderId", orderId);

                request.getRequestDispatcher("/WEB-INF/home/order-success.jsp")
                        .forward(request, response);

            } catch (Exception e) {

                e.printStackTrace();
                request.setAttribute("orderItems", fullCart);

                request.getRequestDispatcher(
                        "/WEB-INF/home/cart.jsp")
                        .forward(request, response);
                return;
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

}
