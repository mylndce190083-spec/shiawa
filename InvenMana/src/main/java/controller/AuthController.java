package controller;

import dao.AuthDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.StaffSession;

@WebServlet(name = "AuthController", urlPatterns = {"/login", "/logout"})
public class AuthController extends HttpServlet {

    public static final String SESSION_USER = "staffUser";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/logout".equals(path)) {
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        StaffSession user = new AuthDAO().loginStaff(username, password);
        if (user == null) {
            request.setAttribute("error", "Sai tài khoản hoặc mật khẩu.");
            request.getRequestDispatcher("/WEB-INF/auth/login.jsp").forward(request, response);
            return;
        }

        request.getSession().setAttribute(SESSION_USER, user);
        // Staff should land on book inventory list
        response.sendRedirect(request.getContextPath() + "/book");
    }
}




