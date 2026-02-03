<<<<<<< HEAD
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookDAO;
import dao.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
=======

package controller;

import dao.BookAdmin;
import dao.BookDAO;
import dao.CategoryDAO;
import java.io.IOException;
>>>>>>> 6eec6e2c6e3608949a045ca087e1b084a6b72c92
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
<<<<<<< HEAD
@WebServlet(name = "BookController", urlPatterns = {"/book"})
=======
@WebServlet(name = "BookController", urlPatterns = { "/book" })
>>>>>>> 6eec6e2c6e3608949a045ca087e1b084a6b72c92
public class BookController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String view = request.getParameter("view");
<<<<<<< HEAD
        
        if ("add".equals(view)) {
            CategoryDAO cateDAO = new CategoryDAO();
            List <Category> cateList = cateDAO.getAllCategories();
=======

        if ("add".equals(view)) {
            CategoryDAO cateDAO = new CategoryDAO();
            List<Category> cateList = cateDAO.getAllCategory();
>>>>>>> 6eec6e2c6e3608949a045ca087e1b084a6b72c92
            request.setAttribute("categorys", cateList);
            request.getRequestDispatcher("/WEB-INF/book/create.jsp").forward(request, response);
            return;
        }
<<<<<<< HEAD
        
        BookDAO dao = new BookDAO();
        List<Book> list = dao.getAllBooks();

        request.setAttribute("bookList", list);
        request.getRequestDispatcher("/WEB-INF/book/list.jsp").forward(request, response);
    
        
=======

        BookDAO dao = new BookDAO();
        List<Book> list = dao.getAllBook();

        request.setAttribute("bookList", list);
        request.getRequestDispatcher("/WEB-INF/book/list.jsp").forward(request, response);

>>>>>>> 6eec6e2c6e3608949a045ca087e1b084a6b72c92
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
<<<<<<< HEAD
       String view = request.getParameter("view");
       
       if ("add".equals(view)) {
           String title = request.getParameter("title");
           String author = request.getParameter("author");
           double price = Double.parseDouble(request.getParameter("price"));
           int stock = Integer.parseInt(request.getParameter("stock"));
           int categoryId = Integer.parseInt(request.getParameter("categoryId"));
           
           Book b = new Book();
           b.setTitle(title);
           b.setAuthor(author);
           b.setPrice(price);
           b.setStock(stock);
           b.setCategoryId(categoryId);
           
           BookDAO bdao = new BookDAO();
           bdao.insertBook(b);
       }
=======
        String view = request.getParameter("view");

        if ("add".equals(view)) {
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            double price = Double.parseDouble(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));

            // Book b = new Book();
            // b.setTitle(title);
            // b.setAuthor(author);
            // b.setPrice(price);
            // b.setStock(stock);
            //
            //
            // BookDAO bdao = new BookDAO();
            // bdao.insertBook(b);

            BookAdmin ba = new BookAdmin();
            ba.setTitle(title);
            ba.setAuthor(author);
            ba.setPrice(price);
            ba.setStock(stock);

            BookDAO bdao = new BookDAO();
            bdao.insertBook(ba);

        }
>>>>>>> 6eec6e2c6e3608949a045ca087e1b084a6b72c92
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
