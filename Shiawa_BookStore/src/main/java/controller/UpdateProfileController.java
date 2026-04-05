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
import jakarta.servlet.http.HttpSession;
import model.Customer;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "UpdateProfileController", urlPatterns = {"/update-profile"})
public class UpdateProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer cus = (Customer) session.getAttribute("customer");

        if (cus == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String username = request.getParameter("username");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String fullname = request.getParameter("fullname");

        String phoneRegex = "^0[35789][0-9]{8}$";
        String addressRegex = "^[A-Za-z0-9]{3,},\\sPhường\\s.+,\\sQuận\\s.+,\\sThành\\sphố\\s.+$";

        if (!phone.matches(phoneRegex)) {
            request.setAttribute("error", "Số điện thoại không hợp lệ (phải 10 số, bắt đầu bằng 0).");
            request.getRequestDispatcher("/WEB-INF/home/profile.jsp").forward(request, response);
            return;
        }

        if (!address.matches(addressRegex)) {
            request.setAttribute("error",
                    "Địa chỉ phải theo format: 123, Phường ..., Quận ..., Thành phố ...");
            request.getRequestDispatcher("/WEB-INF/home/profile.jsp").forward(request, response);
            return;
        }

        CustomerDAO dao = new CustomerDAO();
        boolean updated = dao.updateProfile(cus.getId(), username, phone, address, fullname);

        if (updated) {

            cus.setUsername(username);
            cus.setPhone(phone);
            cus.setAddress(address);
            cus.setFullname(fullname);

            session.setAttribute("customer", cus);
            request.setAttribute("message", "Cập nhật thành công!");

        } else {
            request.setAttribute("error", "Cập nhật thất bại!");
        }

        request.getRequestDispatcher("/WEB-INF/home/profile.jsp").forward(request, response);
    }

}
