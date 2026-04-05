/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AccountDAO;
import dao.CustomerDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.UUID;
import model.Account;
import model.Customer;
import utils.Email;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/home/register.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email").trim();
        String fullName = request.getParameter("fullName").trim();
        String token = UUID.randomUUID().toString();

        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        String usernameRegex = "^[a-zA-Z0-9_]{3,20}$";
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$";

        CustomerDAO dao = new CustomerDAO();
        AccountDAO adao = new AccountDAO();

        if (!username.matches(usernameRegex)) {
            request.setAttribute("error",
                    "Tên bao gồm 3 đến 20 kí tự (chữ, số và dấu _)");
            request.getRequestDispatcher("/WEB-INF/home/register.jsp").forward(request, response);
            return;
        }

        if (!email.matches(emailRegex)) {
            request.setAttribute("error", "Email không hợp lệ!");
            request.getRequestDispatcher("/WEB-INF/home/register.jsp").forward(request, response);
            return;
        }

        if (!password.matches(passwordRegex)) {
            request.setAttribute("error",
                    "Mật khẩu gồm ít nhất 8 kí tự. Bao gồm ít nhất 1 chữ thường, 1 chữ hoa, 1 chữ số và 1 kí tự đặc biệt");
            request.getRequestDispatcher("/WEB-INF/home/register.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu không khớp!");
            request.getRequestDispatcher("/WEB-INF/home/register.jsp")
                    .forward(request, response);
            return;
        }

        if (dao.checkCustomerExist(email)) {
            request.setAttribute("error", "Email đã được sử dụng!");
            request.getRequestDispatcher("/WEB-INF/home/register.jsp")
                    .forward(request, response);
            return;
        }
        if (fullName.isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập họ tên!");
            request.getRequestDispatcher("/WEB-INF/home/register.jsp")
                    .forward(request, response);
            return;
        } else {
            String hashPassword = adao.hashMD5(password);
            Customer c = new Customer();
            c.setMustChangePassword(false);
            c.setUsername(username);
            c.setPassword(hashPassword);
            c.setEmail(email);
            c.setStatus("inactive");
            c.setVerifyToken(token);
            c.setAvatar("images/avatar/macdinh.jpg");
            c.setFullname(fullName);
            dao.insert(c);
            session.setAttribute("success", "Đăng kí thành công! Vui lòng xác minh email để đăng nhập");
            try {
                Email.sendVerificationEmail(email, token);
                System.out.println("Email sent!");
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect("login");
        }
    }

}
