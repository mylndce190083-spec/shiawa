/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookDAO;
import dao.CartItemDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Book;
import model.CartItem;
import model.Customer;

/**
 *
 * @author MY
 */
@WebServlet("/cart")
public class CartController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            customer = new Customer();
            customer.setCustomer_id(1); // ID có sẵn trong DB
            customer.setUsername("cus01");

            session.setAttribute("customer", customer);
        }

        CartItemDAO dao = new CartItemDAO();
        request.setAttribute(
                "cartItem",
                dao.getCartByCustomerId(customer.getCustomer_id())
        );

        request.getRequestDispatcher("cart.jsp").forward(request, response);
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
        HttpSession session = request.getSession();

        // 1. Check login
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 2. Get data từ view
        String bookIdRaw = request.getParameter("book_id");
        String quantityRaw = request.getParameter("quantity");

        if (bookIdRaw == null || bookIdRaw.trim().isEmpty()
                || quantityRaw == null || quantityRaw.trim().isEmpty()) {

            // quay về trang trước hoặc home
            response.sendRedirect("home");
            return;
        }

        int book_id = Integer.parseInt(bookIdRaw);
        int quantity = Integer.parseInt(quantityRaw);

        // 3. DAO
        CartItemDAO dao = new CartItemDAO();
        CartItem item = dao.findItem(customer.getCustomer_id(), book_id);

        if (item != null) {
            // 4a. Đã tồn tại → update
            item.setQuantity(item.getQuantity() + quantity);
            dao.update(item);
        } else {
            // 4b. Chưa tồn tại → insert
            BookDAO bookDAO = new BookDAO();
            Book book = bookDAO.getBookById(book_id);

            CartItem newItem = new CartItem();
            newItem.setCustomer_id(customer.getCustomer_id());
            newItem.setBook_id(book_id);
            newItem.setQuantity(quantity);
            newItem.setPrice(book.getPrice());

            dao.insert(newItem);
        }

        // 5. Quay lại trang chi tiết
        response.sendRedirect("book-detail?id=" + book_id);
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
