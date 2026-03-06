/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Account;

/**
 *
 * @author BA LIEM
 */
@WebServlet(name = "AccountController", urlPatterns = {"/account"})
public class AccountController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //kiểm tra session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("home");
            return;
        }
        String view = request.getParameter("view");
        AccountDAO dao = new AccountDAO();

        if ("activate".equals(view)) {

            int id = Integer.parseInt(request.getParameter("id"));
            String role = request.getParameter("role");
            Account acc = dao.getAccountById(id, role);
            dao.updateStatus(id, role, "active");

            response.sendRedirect("account");
            return;

        } else if ("deactivate".equals(view)) {

            int id = Integer.parseInt(request.getParameter("id"));
            String role = request.getParameter("role");
            dao.updateStatus(id, role, "inactive");

            response.sendRedirect("account");
            return;
        } else if ("detail".equals(view)) {

            int id = Integer.parseInt(request.getParameter("id"));
            String role = request.getParameter("role");
            Account acc = dao.getAccountById(id, role);

            // DEBUG
            if (acc == null) {
                System.out.println("Account not found!");
            } else {
                System.out.println("Account found: " + acc.getUsername());
            }
            request.setAttribute("account", acc);
            request.setAttribute("currentPage", "account");

            request.getRequestDispatcher("/WEB-INF/account/detail.jsp")
                    .forward(request, response);
            return;

        } else if ("add".equals(view)) {
            request.setAttribute("currentPage", "account");
            request.getRequestDispatcher("/WEB-INF/account/add.jsp")
                    .forward(request, response);
            return;
        }
        request.setAttribute("currentPage", "account");

        String role = request.getParameter("role");

        List<Account> list;

        if (role != null && !role.isEmpty()) {
            list = dao.getUsersByRole(role);
        } else {
            list = dao.getAllUsers();
        }

        request.setAttribute("accounts", list);
        request.setAttribute("selectedRole", role);
        //chặn cache
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        request.getRequestDispatcher("/WEB-INF/account/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {

            String role = request.getParameter("role");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");

            AccountDAO dao = new AccountDAO();

            if (dao.usernameExists(username)) {
                request.setAttribute("error", "Username already exists!");
                request.getRequestDispatcher("/WEB-INF/account/add.jsp")
                        .forward(request, response);
                return;
            }
            // tạo password tạm
            String tempPassword = java.util.UUID.randomUUID()
                    .toString().substring(0, 8);

            // tạo account
            dao.addUser(username, email, fullName, phone, role, tempPassword);

            // gửi mail
            utils.Email.sendTempPasswordEmail(email, username, tempPassword);

            response.sendRedirect("account");
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
