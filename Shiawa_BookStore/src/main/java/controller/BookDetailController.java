/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Book;

/**
 *
 * @author admin
 */
@WebServlet(name = "BookDetailController", urlPatterns = {"/bookdetail"})
public class BookDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idString = request.getParameter("id");
        if (idString != null) {
            int id = Integer.parseInt(idString);
            dao.BookDAO bookDAO = new dao.BookDAO();
            var foundBook = bookDAO.getBookById(id);
            if (foundBook != null) {
//                int categoryId = foundBook.getCategory().getCategoryId();
//                List<Book> similarBooks = bookDAO.getSimilarBook(categoryId);
//
//                request.setAttribute("similarBooks", similarBooks);
//                request.setAttribute("book", foundBook);
//                request.getRequestDispatcher("/client/bookdetail.jsp").forward(request, response);
//            } else {
//                response.sendRedirect("index.jsp");
//            }
//            
//        }
//
//    }
                dao.FeedbackDAO fbDAO = new dao.FeedbackDAO();
                List<model.Feedback> feedbackList = fbDAO.getFeedbacksByBookId(id);
                request.setAttribute("feedbackList", feedbackList);
                // ----------------------------

                int categoryId = foundBook.getCategory().getCategoryId();
                List<model.Book> similarBooks = bookDAO.getSimilarBook(categoryId);

                request.setAttribute("similarBooks", similarBooks);
                request.setAttribute("book", foundBook);

                // Chú ý: đường dẫn forward phải khớp với vị trí file jsp của bạn
                request.getRequestDispatcher("/client/bookdetail.jsp").forward(request, response);
            } else {
                response.sendRedirect("index.jsp");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
