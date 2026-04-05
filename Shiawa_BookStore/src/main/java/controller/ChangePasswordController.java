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
import model.Account;

/**
 *
 * @author BA LIEM
 */
@WebServlet(name = "ChangePasswordController", urlPatterns = {"/change-password"})
public class ChangePasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("home");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/account/change-password.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        String currentPass = request.getParameter("currentPassword");
        String newPass = request.getParameter("newPassword");
        String confirm = request.getParameter("confirmPassword");

        AccountDAO dao = new AccountDAO();
        String currentHash = dao.hashMD5(currentPass);

        if (!currentHash.equals(user.getPassword())) {
            request.setAttribute("error", "Current password is incorrect");
            request.getRequestDispatcher("/WEB-INF/account/change-password.jsp")
                    .forward(request, response);
            return;
        }

        if (!newPass.equals(confirm)) {
            request.setAttribute("error", "Password confirmation does not match");
            request.getRequestDispatcher("/WEB-INF/account/change-password.jsp")
                    .forward(request, response);
            return;
        }

        String hash = dao.hashMD5(newPass);
        dao.updatePassword(user.getId(), user.getRole(), hash);
        dao.updateMustChangePassword(user.getId(), user.getRole(), false);

        user.setMustChangePassword(false);
        session.setAttribute("user", user);
        String role = user.getRole();

        if ("Admin".equalsIgnoreCase(role)) {
            response.sendRedirect("account");
        } else if ("Inventory".equalsIgnoreCase(role)) {
            response.sendRedirect("inventory?view=list");
        } else {
            response.sendRedirect("home");
        }
    }

}
