/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookDAO;
import dao.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Book;
import model.Category;

/**
 *
 * @author BA LIEM
 */
@WebServlet(name = "HomeController", urlPatterns = {"/home"})
public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // kiểm tra có phải VNPAY trả kết quả về không
        String responseCode = request.getParameter("vnp_ResponseCode");

        if (responseCode != null) {

            if ("00".equals(responseCode)) {
                request.setAttribute("paymentMessage", "Thanh toán thành công");
            } else {
                request.setAttribute("paymentMessage", "Thanh toán thất bại");
            }

            request.getRequestDispatcher("/WEB-INF/home/vnpay_return.jsp")
                    .forward(request, response);
            return;
        }
        BookDAO dao = new BookDAO();
        CategoryDAO cdao = new CategoryDAO();
        String cateIdRaw = request.getParameter("id");
        List<Book> list;

        if (cateIdRaw != null && !cateIdRaw.isEmpty()) {
            int cateId = Integer.parseInt(cateIdRaw);
            list = dao.getBooksByCategoryId(cateId);
        } else {
            list = dao.getAllBook();
        }
        List<Category> clist = cdao.getAllCategory();
        request.setAttribute("listB", list);
        request.setAttribute("listC", clist);
        request.getRequestDispatcher("/WEB-INF/home/home.jsp").forward(request, response);
        for (Book b : list) {
            System.out.println(b);
        }
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
