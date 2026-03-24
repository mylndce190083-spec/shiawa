
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.VoucherDAO;
import dao.VoucherDAO.ClaimVoucherResult;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Account;
import model.Customer;
import model.Voucher;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "GetVoucherController", urlPatterns = {"/get-voucher"})
public class GetVoucherController extends HttpServlet {

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
            out.println("<title>Servlet GetVoucherController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetVoucherController at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        // 1. Check đăng nhập + role
        if (user == null || !"customer".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        VoucherDAO vdao = new VoucherDAO();
        List<Voucher> list;
        list = vdao.getAllAvailableVoucher();

        request.setAttribute("voucherList", list);

        request.getRequestDispatcher("/WEB-INF/home/get-voucher.jsp").forward(request, response);

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
        int voucherId = Integer.parseInt(request.getParameter("voucherId"));
        VoucherDAO vdao = new VoucherDAO();

        if (customer == null) {
            response.sendRedirect("login");
            return;
        }

        try {

            ClaimVoucherResult result = vdao.claimVoucher(customer.getId(), voucherId);
            switch (result) {
                case SUCCESS:
                    // message thành công
                    session.setAttribute("msg", "Lấy voucher thành công!");
                    session.setAttribute("msgType", "success");
                    break;

                case ALREADY_HAVE:
                    session.setAttribute("msg", "Bạn đã có voucher này!");
                    session.setAttribute("msgType", "danger");
                    break;

                case OUT_OF_STOCK:
                    session.setAttribute("msg", "Voucher này đã hết!");
                    session.setAttribute("msgType", "danger");
                    break;

                default:
                    // message thất bại
                    session.setAttribute("msg", "Lấy voucher thất bại!");
                    session.setAttribute("msgType", "danger");
            }

        } catch (Exception e) {
            // message thất bại
            session.setAttribute("msg", "Lỗi!");
            session.setAttribute("msgType", "danger");
        }

        response.sendRedirect(request.getContextPath() + "/get-voucher");
        return;

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
