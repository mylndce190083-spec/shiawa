/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AccountDAO;
import dao.CustomerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.UUID;
import model.Customer;
import utils.Email;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/home/register.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email").trim();
        String token = UUID.randomUUID().toString();

        // biểu thức chính quy để kiểm tra input
        String usernameRegex = "^[a-zA-Z0-9_]{3,20}$";
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$";

        CustomerDAO dao = new CustomerDAO();
        AccountDAO adao = new AccountDAO();

        // Validate username
        if (!username.matches(usernameRegex)) {
            request.setAttribute("error",
                    "Tên bao gồm 3 đến 20 kí tự (chữ, số và dấu _)");
            request.getRequestDispatcher("/WEB-INF/home/register.jsp").forward(request, response);
            return;
        }

        // Validate email
        if (!email.matches(emailRegex)) {
            request.setAttribute("error", "Email không hợp lệ!");
            request.getRequestDispatcher("/WEB-INF/home/register.jsp").forward(request, response);
            return;
        }

        // Validate password
        if (!password.matches(passwordRegex)) {
            request.setAttribute("error",
                    "Mật khẩu gồm ít nhất 8 kí tự. Bao gồm ít nhất 1 chữ thường, 1 chữ hoa, 1 chữ số và 1 kí tự đặc biệt");
            request.getRequestDispatcher("/WEB-INF/home/register.jsp").forward(request, response);
            return;
        }

        // 1️⃣ Kiểm tra confirm password
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu không khớp!");
            request.getRequestDispatcher("/WEB-INF/home/register.jsp")
                    .forward(request, response);
            return;
        }

        // 2️⃣ Kiểm tra username tồn tại
        if (dao.checkCustomerExist(email)) {
            request.setAttribute("error", "Email đã được sử dụng!");
            request.getRequestDispatcher("/WEB-INF/home/register.jsp")
                    .forward(request, response);
        } else {
            String hashPassword = adao.hashMD5(password);
            Customer c = new Customer(1, username, hashPassword, email, "00", "inactive", token, "00", "00", "00");
            dao.insert(c);
            HttpSession session = request.getSession();
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
