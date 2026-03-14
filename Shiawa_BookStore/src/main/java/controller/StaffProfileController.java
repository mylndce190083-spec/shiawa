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

//        if (!"Admin".equalsIgnoreCase(user.getRole())) {
//            response.sendRedirect("home");
//            return;
//        }
        if (!user.getRole().equalsIgnoreCase("Admin")
                && !user.getRole().equalsIgnoreCase("Inventory")) {
            response.sendRedirect("home");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/profile/staff-profile.jsp")
                .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        String username = request.getParameter("username");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");

        String currentPass = request.getParameter("currentPass");
        String newPass = request.getParameter("newPass");
        String confirmNewPass = request.getParameter("confirmNewPass");

        AccountDAO dao = new AccountDAO();

        /* check username duplicate */
        if (!username.equals(user.getUsername()) && dao.usernameExists(username)) {
            request.setAttribute("error", "Username already exists");
            request.getRequestDispatcher("/WEB-INF/profile/staff-profile.jsp")
                    .forward(request, response);
            return;
        }
        // update profile
        user.setUsername(username);
        user.setFullName(fullname);
        user.setEmail(email);

        dao.updateProfile(user);

        // change password
        if (currentPass != null && !currentPass.isEmpty()) {

            String currentHash = dao.hashMD5(currentPass);

            if (!currentHash.equals(user.getPassword())) {
                request.setAttribute("error", "Current password incorrect");
                request.getRequestDispatcher("/WEB-INF/profile/staff-profile.jsp")
                        .forward(request, response);
                return;
            }

            if (!newPass.equals(confirmNewPass)) {
                request.setAttribute("error", "New password not match");
                request.getRequestDispatcher("/WEB-INF/profile/staff-profile.jsp")
                        .forward(request, response);
                return;
            }

            String newHash = dao.hashMD5(newPass);

            dao.updatePassword(user.getId(), user.getRole(), newHash);

            user.setPassword(newHash);
        }

        session.setAttribute("user", user);

        response.sendRedirect("staff-profile");

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
