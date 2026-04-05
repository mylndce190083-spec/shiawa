/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CustomerDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "VerifyOTPController", urlPatterns = {"/verify-otp"})
public class VerifyOTPController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String otp = request.getParameter("otp");
        CustomerDAO dao = new CustomerDAO();

        if (!dao.isValidOTP(otp)) {
            request.setAttribute("error", "Invalid or expired OTP!");
            request.getRequestDispatcher("/WEB-INF/home/verify-otp.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("/WEB-INF/home/reset-password.jsp").forward(request, response);
    }
}
