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
import java.sql.Timestamp;
import utils.Email;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "ForgotPasswordController", urlPatterns = {"/forgot-password"})
public class ForgotPasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/home/forgot.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        CustomerDAO dao = new CustomerDAO();

        if (!dao.checkEmailExist(email)) {
            request.setAttribute("error", "Email not found!");
            request.getRequestDispatcher("/WEB-INF/home/forgot.jsp").forward(request, response);
            return;
        }

        // Tạo OTP 6 số
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);

        // Hết hạn sau 5 phút
        Timestamp expiry = new Timestamp(System.currentTimeMillis() + 5 * 60 * 1000);

        dao.saveOTP(email, otp, expiry);

        Email.sendOTP(email, otp);

        request.setAttribute("message", "OTP sent to your email!");
        request.getRequestDispatcher("/WEB-INF/home/verify-otp.jsp").forward(request, response);
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
