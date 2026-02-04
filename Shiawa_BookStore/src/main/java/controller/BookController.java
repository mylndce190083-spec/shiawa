
package controller;

import dao.BookAdmin;
import dao.BookDAO;
import dao.CategoryDAO;
import java.io.IOException;
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
@WebServlet(name = "BookController", urlPatterns = { "/book" })
public class BookController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String view = request.getParameter("view");

        if ("add".equals(view)) {
            CategoryDAO cateDAO = new CategoryDAO();
            List<Category> cateList = cateDAO.getAllCategory();
            request.setAttribute("categorys", cateList);
            request.getRequestDispatcher("/WEB-INF/book/create.jsp").forward(request, response);
            return;
        }

        BookDAO dao = new BookDAO();
        List<Book> list = dao.getAllBook();

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
