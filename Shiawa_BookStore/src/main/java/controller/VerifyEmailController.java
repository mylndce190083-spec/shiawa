/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CustomerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import utils.Email;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "VerifyEmailController", urlPatterns = {"/verify"})
public class VerifyEmailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");
String email = request.getParameter("email");
        CustomerDAO dao = new CustomerDAO();
        boolean verified = dao.verifyUser(token);

        if (verified) {
            request.setAttribute("message", "Tài khoản xác minh thành công, bạn có thể đăng nhập!");
        } else {
            token = UUID.randomUUID().toString();
            try {
                Email.sendVerificationEmail(email, token);
                System.out.println("Email sent!");
                dao.updateTokenByEmail(email, token);
            } catch (Exception e) {
                e.printStackTrace();
            }
            request.setAttribute("message", "Không xác nhận được email! Xin vui lòng thử lại");

        }

        request.getRequestDispatcher("/WEB-INF/account/login.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
