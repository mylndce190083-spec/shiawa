/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookDAO;
import dao.CategoryDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.BookAdmin;
import model.Category;

/**
 *
 * @author BA LIEM
 */
@WebServlet(name = "BookController", urlPatterns = {"/book"})
public class BookController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String view = request.getParameter("view");

        if ("add".equals(view)) {
            CategoryDAO cateDAO = new CategoryDAO();
            List<Category> cateList = cateDAO.getIdNameCategory();
            request.setAttribute("categoryList", cateList);
            request.getRequestDispatcher("/WEB-INF/book/create.jsp").forward(request, response);
            return;
        }

        BookDAO dao = new BookDAO();
        List<BookAdmin> list = dao.getAllBooksInfo();

        
        request.setAttribute("bookList", list);
        request.getRequestDispatcher("/WEB-INF/book/list.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String view = request.getParameter("view");

        if ("add".equals(view)) {
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            double price = Double.parseDouble(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));

            BookAdmin b = new BookAdmin();
            b.setTitle(title);
            b.setAuthor(author);
            b.setPrice(price);
            b.setStock(stock);
            b.setCategoryId(categoryId);

            BookDAO bdao = new BookDAO();
            bdao.insertBook(b);
        }
        response.sendRedirect(request.getContextPath() + "/book");
    }

}
