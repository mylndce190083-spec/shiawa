
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AccountDAO;
import dao.CustomerDAO;
import dao.CartItemDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Account;
import model.Customer;
import model.CartItem;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/account/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        AccountDAO dao = new AccountDAO();
        CustomerDAO cdao = new CustomerDAO();
        String hashPassword = dao.hashMD5(password);
        Account user = dao.login(email, hashPassword);
        HttpSession session = request.getSession();

        if (user.getId() == -1) {
            session.setAttribute("error", "Sai email hoặc mật khẩu!");
            response.sendRedirect("login");
        } else {
            if (!"active".equals(user.getStatus())) {
                session.setAttribute("error", "Email chưa xác thực");
                response.sendRedirect("login");
                return;
            }
            session.setAttribute("user", user);
            if (user.isMustChangePassword()) {
                response.sendRedirect("change-password");
                return;
            }

            if ("Customer".equalsIgnoreCase(user.getRole())) {
                Customer customer = cdao.getCustomerByAccountIdUpgraded(user.getId());
                if (customer != null) {
                    session.setAttribute("customer", customer);

                    CartItemDAO cartDAO = new CartItemDAO();

                    List<CartItem> cartItems
                            = cartDAO.getCartByCustomerId(customer.getId());
                    // 👈 dùng user.getId() nếu id = customerId

                    int totalQuantity = 0;
                    for (CartItem ci : cartItems) {
                        totalQuantity += ci.getQuantity();
                    }

                    session.setAttribute("cartSize", totalQuantity);
                }
                request.setCharacterEncoding("UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html; charset=UTF-8");
                response.sendRedirect("home");
            } else if ("Admin".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/account");
            } else if ("Staff".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/inventory?view=list");
            } else {
                response.sendRedirect(request.getContextPath() + "/inventory?view=list");
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
