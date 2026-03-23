/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Book;

/**
 *
 * @author BA LIEM
 */
@WebServlet(name = "IncomeController", urlPatterns = {"/income"})
public class IncomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");

        // 🔒 Check login (giống BookAdminController)
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        request.setAttribute("pagePrimary", "income");
        OrderDAO dao = new OrderDAO();

        double totalIncome = dao.getTotalIncome();
        int totalSold = dao.getTotalSoldQuantity();
        List<Book> bestSeller = dao.getBestSellerBooks();

        request.setAttribute("totalIncome", totalIncome);
        request.setAttribute("totalSold", totalSold);
        request.setAttribute("bestSeller", bestSeller);

        request.getRequestDispatcher("/WEB-INF/income/total.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
