/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.VoucherDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Account;
import model.Voucher;

/**
 *
 * @author BA LIEM
 */
@WebServlet(name = "VoucherAdminController", urlPatterns = {"/voucher-admin"})
public class VoucherAdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        if (user == null || !"Admin".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.setAttribute("pagePrimary", "voucher-admin");
        String view = request.getParameter("view");
        VoucherDAO dao = new VoucherDAO();

        if (view == null) {

            List<Voucher> list = dao.getAllVoucher();
            request.setAttribute("voucherList", list);
            request.getRequestDispatcher("/WEB-INF/voucher/list.jsp").forward(request, response);

        } else if (view.equals("add")) {

            request.getRequestDispatcher("/WEB-INF/voucher/create.jsp").forward(request, response);

        } else if (view.equals("edit")) {

            int id = Integer.parseInt(request.getParameter("id"));
            Voucher v = dao.getVoucherById(id);

            request.setAttribute("voucher", v);
            request.getRequestDispatcher("/WEB-INF/voucher/edit.jsp").forward(request, response);

        } else if (view.equals("delete")) {

            int id = Integer.parseInt(request.getParameter("id"));

            boolean success = dao.deleteVoucher(id);

            if (success) {
                session.setAttribute("msg", "Delete voucher successfully!");
                session.setAttribute("msgType", "success");
            } else {
                session.setAttribute("msg", "Cannot delete! Voucher is being used.");
                session.setAttribute("msgType", "danger");
            }

            response.sendRedirect("voucher-admin");

        } else {

            response.sendRedirect("voucher-admin");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String view = request.getParameter("view");
        VoucherDAO dao = new VoucherDAO();

        if (view.equals("add")) {

            String name = request.getParameter("name");
            double discount = Double.parseDouble(request.getParameter("discount"));

            if (discount < 0 || discount > 100) {
                request.setAttribute("error", "Discount must be between 0 and 100");
                request.getRequestDispatcher("/WEB-INF/voucher/create.jsp").forward(request, response);
                return;
            }
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String createdAt = request.getParameter("createdAt");
            String endedAt = request.getParameter("endedAt");

            Voucher v = new Voucher();
            v.setName(name);
            v.setDiscount(discount);
            v.setQuantity(quantity);
            v.setCreatedAt(java.sql.Date.valueOf(createdAt));
            v.setEndedAt(java.sql.Date.valueOf(endedAt));

            dao.insertVoucher(v);

            HttpSession session = request.getSession();
            session.setAttribute("msg", "Create voucher successfully!");
            session.setAttribute("msgType", "success");

            response.sendRedirect("voucher-admin");

        } else if (view.equals("update")) {

            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            double discount = Double.parseDouble(request.getParameter("discount"));

            if (!(discount < 0 || discount > 100)) {

                int quantity = Integer.parseInt(request.getParameter("quantity"));
                String createdAt = request.getParameter("createdAt");
                String endedAt = request.getParameter("endedAt");

                Voucher v = new Voucher();
                v.setVoucher_id(id);
                v.setName(name);
                v.setDiscount(discount);
                v.setQuantity(quantity);
                v.setCreatedAt(java.sql.Date.valueOf(createdAt));
                v.setEndedAt(java.sql.Date.valueOf(endedAt));

                if (createdAt != null && !createdAt.isEmpty()) {
                    v.setCreatedAt(java.sql.Date.valueOf(createdAt));
                }
                if (endedAt != null && !endedAt.isEmpty()) {
                    v.setEndedAt(java.sql.Date.valueOf(endedAt));
                }
                dao.updateVoucher(v);

                HttpSession session = request.getSession();
                session.setAttribute("msg", "Update voucher successfully!");
                session.setAttribute("msgType", "primary");

                response.sendRedirect("voucher-admin");
            }
        }
    }
}
