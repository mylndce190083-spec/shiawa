
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
import model.Customer;
import model.Voucher;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "MyVoucherController", urlPatterns = {"/my-voucher"})
public class MyVoucherController extends HttpServlet {

  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer cus = (Customer) session.getAttribute("customer");

        if (cus == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        VoucherDAO vdao = new VoucherDAO();
        List<Voucher> list;
        list = vdao.getMyVoucherList(cus.getId());

        request.setAttribute("myVoucherList", list);
        request.setAttribute("now", new java.util.Date());

        request.getRequestDispatcher("/WEB-INF/home/my-voucher.jsp").forward(request, response);
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

}
