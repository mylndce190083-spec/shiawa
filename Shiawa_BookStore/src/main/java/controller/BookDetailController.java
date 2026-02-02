/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
                model.Book foundBook = bookDAO.getBookById(id);
                if(foundBook != null) {
                    request.setAttribute("book", foundBook);
                    request.getRequestDispatcher("bookdetail.jsp").forward(request, response);
                } else {
                    response.sendRedirect("index.jsp");
                }
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
