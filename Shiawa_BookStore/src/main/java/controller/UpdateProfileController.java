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
import jakarta.servlet.http.HttpSession;
import model.Customer;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "UpdateProfileController", urlPatterns = {"/update-profile"})
public class UpdateProfileController extends HttpServlet {

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
            out.println("<title>Servlet UpdateProfileController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateProfileController at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");

        String username = request.getParameter("username");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String fullname = request.getParameter("fullname");

        // ================= VALIDATION =================
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

        // ================= UPDATE =================
        CustomerDAO dao = new CustomerDAO();
        boolean updated = dao.updateProfile(customer.getId(), username, phone, address, fullname);

        if (updated) {

            customer.setUsername(username);
            customer.setPhone(phone);
            customer.setAddress(address);
            customer.setFullName(fullname);

            session.setAttribute("customer", customer);
            request.setAttribute("message", "Cập nhật thành công!");

        } else {
            request.setAttribute("error", "Cập nhật thất bại!");
        }

        request.getRequestDispatcher("/WEB-INF/home/profile.jsp").forward(request, response);
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
