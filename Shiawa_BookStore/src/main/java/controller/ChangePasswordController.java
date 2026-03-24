/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
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

        //kiểm tra session
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
        // hash mật khẩu cũ nhập vào
        String currentHash = dao.hashMD5(currentPass);

        // kiểm tra password cũ đúng không
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

        // hash password
        String hash = dao.hashMD5(newPass);

        // update password
        dao.updatePassword(user.getId(), user.getRole(), hash);

        // cập nhật trạng thái đổi password
        dao.updateMustChangePassword(user.getId(), user.getRole(), false);

        //mới thêm
        // update session
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
