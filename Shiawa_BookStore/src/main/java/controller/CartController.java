/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookDAO;
import dao.CartItemDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
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
        Customer cus = (Customer) session.getAttribute("customer");

        if (cus == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int customerId = cus.getId();

        CartItemDAO dao = new CartItemDAO();
        List<CartItem> cartItems = dao.getCartByCustomerId(customerId);
        int totalQuantity = 0;
        for (CartItem ci : cartItems) {
            totalQuantity += ci.getQuantity();
        }

        session.setAttribute("cartSize", totalQuantity);
        request.setAttribute("cartItem", cartItems);

        request.getRequestDispatcher("/WEB-INF/home/cart.jsp")
                .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println(">>> CartController doPost CALLED");
        System.out.println("ACTION = " + request.getParameter("action"));
        System.out.println("BOOK_ID = " + request.getParameter("book_id"));

        HttpSession session = request.getSession();
        Customer cus = (Customer) session.getAttribute("customer");

        if (cus == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        int customerId = cus.getId();
        int bookId = Integer.parseInt(request.getParameter("book_id"));
        String action = request.getParameter("action");

        CartItemDAO dao = new CartItemDAO();
        CartItem item = dao.findItem(customerId, bookId);
        boolean isAjax = "XMLHttpRequest"
                .equals(request.getHeader("X-Requested-With"));
        switch (action) {
            case "add":
            case "increase":
                BookDAO bookDAO = new BookDAO();
                Book book = bookDAO.getBookById(bookId);

                int currentQty = (item != null) ? item.getQuantity() : 0;

                if (currentQty + 1 > book.getStock()) {

                    if (isAjax) {
                        response.setContentType("application/json");
                        response.getWriter().print(
                                "{\"quantity\":" + currentQty + ","
                                + "\"totalCartItems\":0,"
                                + "\"message\":\"Đã thêm quá số lượng trong kho\"}"
                        );
                        return;
                    } else {
                        response.sendRedirect(request.getContextPath() + "/cart");
                        return;
                    }

                }

                if (item != null) {
                    dao.updateQuantity(customerId, bookId, currentQty + 1);
                } else {
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

        List<CartItem> cartItems = dao.getCartByCustomerId(customerId);

        int totalQuantity = 0;
        for (CartItem ci : cartItems) {
            totalQuantity += ci.getQuantity();
        }

        session.setAttribute("cartSize", totalQuantity);
        CartItem updatedItem = dao.findItem(customerId, bookId);
        int newQty = (updatedItem != null) ? updatedItem.getQuantity() : 0;

        if (isAjax) {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String message = "";

            if ("add".equals(action)) {
                message = "Đã thêm vào giỏ hàng thành công!";
            }

            String json = "{"
                    + "\"quantity\":" + newQty + ","
                    + "\"totalCartItems\":" + totalQuantity + ","
                    + "\"message\":\"" + message + "\""
                    + "}";

            response.getWriter().print(json);

        } else {

            response.sendRedirect(request.getContextPath() + "/cart");
        }
    }
}
