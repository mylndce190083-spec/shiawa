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
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        if (user == null || !"Admin".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.setAttribute("pagePrimary", "account");
        String view = request.getParameter("view");
        AccountDAO dao = new AccountDAO();

        if ("activate".equals(view) || "deactivate".equals(view)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String role = request.getParameter("role");

            if (user.getId() == id && user.getRole().equalsIgnoreCase(role)) {
                session.setAttribute("message", "You cannot change your own status!");
                response.sendRedirect("account");
                return;
            }

            if ("activate".equals(view)) {
                dao.updateStatus(id, role, "active");
                session.setAttribute("message", "Account activated successfully!");
            } else {
                dao.updateStatus(id, role, "inactive");
                session.setAttribute("message", "Account deactivated successfully!");
            }

            response.sendRedirect("account");
            return;
        } else if ("detail".equals(view)) {

            int id = Integer.parseInt(request.getParameter("id"));
            String role = request.getParameter("role");
            Account acc = dao.getAccountById(id, role);

            if (acc == null) {
                System.out.println("Account not found!");
            } else {
                System.out.println("Account found: " + acc.getUsername());
            }
            request.setAttribute("account", acc);

            request.getRequestDispatcher("/WEB-INF/account/detail.jsp")
                    .forward(request, response);
            return;

        } else if ("add".equals(view)) {
            request.getRequestDispatcher("/WEB-INF/account/add.jsp")
                    .forward(request, response);
            return;
        }

        String role = request.getParameter("role");
        if (role != null && role.trim().isEmpty()) {
            role = null;
        }
        int page = 1;
        int pageSize = 10;

        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
        }

        int totalAccounts = dao.countAccounts(role);
        int totalPage = (int) Math.ceil((double) totalAccounts / pageSize);

        List<Account> list = dao.getAccountsByPage(page, pageSize, role);

        request.setAttribute("accounts", list);
        request.setAttribute("selectedRole", role);
        request.setAttribute("currentPageNum", page);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("accounts", list);
        request.setAttribute("selectedRole", role);

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

            String tempPassword = java.util.UUID.randomUUID()
                    .toString().substring(0, 8);

            dao.addUser(username, email, fullName, phone, role, tempPassword);

            utils.Email.sendTempPasswordEmail(email, username, tempPassword);

            response.sendRedirect("account");
        }
    }

}
