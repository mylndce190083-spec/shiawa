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
@WebServlet(name = "AdminProfileController", urlPatterns = {"/staff-profile"})
public class StaffProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        if (!user.getRole().equalsIgnoreCase("Admin")
                && !user.getRole().equalsIgnoreCase("Inventory")) {
            response.sendRedirect("home");
            return;
        }

        request.setAttribute("pagePrimary", "staff-profile");
        request.getRequestDispatcher("/WEB-INF/profile/staff-profile.jsp")
                .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");
        String action = request.getParameter("action");

        AccountDAO dao = new AccountDAO();

        if ("updateProfile".equals(action)) {

            String username = request.getParameter("username");
            String fullname = request.getParameter("fullname");
            String email = request.getParameter("email");

            if (!username.equals(user.getUsername()) && dao.usernameExists(username)) {
                request.setAttribute("msg", "Username already exists dddd");
                request.setAttribute("msgType", "danger");
                request.getRequestDispatcher("/WEB-INF/profile/staff-profile.jsp")
                        .forward(request, response);
                return;
            }

            user.setUsername(username);
            user.setFullName(fullname);
            user.setEmail(email);

            dao.updateProfile(user);

            session.setAttribute("msg", "Profile updated successfully!");
            session.setAttribute("msgType", "success");
            response.sendRedirect("staff-profile");

        } else if ("changePassword".equals(action)) {

            String currentPass = request.getParameter("currentPass");
            String newPass = request.getParameter("newPass");
            String confirmNewPass = request.getParameter("confirmNewPass");

            String currentHash = dao.hashMD5(currentPass);

            if (!currentHash.equals(user.getPassword())) {
                request.setAttribute("msg", "Current password incorrect");
                session.setAttribute("msgType", "danger");
                request.getRequestDispatcher("/WEB-INF/profile/staff-profile.jsp")
                        .forward(request, response);
                return;
            }

            if (!newPass.equals(confirmNewPass)) {
                request.setAttribute("msg", "New password not match");
                session.setAttribute("msgType", "primary");
                request.getRequestDispatcher("/WEB-INF/profile/staff-profile.jsp")
                        .forward(request, response);
                return;
            }

            String newHash = dao.hashMD5(newPass);

            dao.updatePassword(user.getId(), user.getRole(), newHash);
            user.setPassword(newHash);
            session.setAttribute("msg", "Password changed successfully!");
            session.setAttribute("msgType", "success");
            response.sendRedirect("staff-profile");
        }
    }
}
