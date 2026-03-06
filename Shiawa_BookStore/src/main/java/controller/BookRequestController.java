/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookRequestDAO;
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
import model.BookRequest;

/**
 *
 * @author BA LIEM
 */
@WebServlet(name = "BookRequestController", urlPatterns = {"/book-request"})
public class BookRequestController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        BookRequestDAO dao = new BookRequestDAO();

        if ("list".equals(action)) {

            List<BookRequest> list = dao.getAllRequests();
            request.setAttribute("requestList", list);

            request.getRequestDispatcher("/WEB-INF/book/request-list.jsp")
                    .forward(request, response);
        } else if ("accept".equals(action)) {

            int id = Integer.parseInt(request.getParameter("id"));
            dao.approveRequest(id, user.getId());
            response.sendRedirect(request.getContextPath() + "/book-request?action=list");
        } else if ("reject".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.rejectRequest(id);
            response.sendRedirect(request.getContextPath() + "/book-request?action=list");
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
