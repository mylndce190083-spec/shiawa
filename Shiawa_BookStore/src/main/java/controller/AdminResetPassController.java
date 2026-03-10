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
@WebServlet(name = "AdminResetPassController", urlPatterns = {"/admin-pass"})
public class AdminResetPassController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pass = request.getParameter("password");
        String confirm = request.getParameter("confirmPassword");

        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        if (!pass.equals(confirm)) {
            request.setAttribute("error", "Passwords do not match!");
            request.getRequestDispatcher("/WEB-INF/profile/admin-profile.jsp")
                    .forward(request, response);
            return;
        }

        AccountDAO dao = new AccountDAO();

        dao.changePassword(user.getId(), user.getRole(), pass);

        request.setAttribute("message", "Password changed successfully!");
        response.sendRedirect(request.getContextPath() + "/admin-profile");
        
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
