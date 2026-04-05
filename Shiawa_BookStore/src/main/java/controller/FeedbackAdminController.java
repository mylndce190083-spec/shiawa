/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.FeedbackDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Feedback;

/**
 *
 * @author MY
 */
@WebServlet(name = "FeedbackAdminController", urlPatterns = {"/feedback-admin"})
public class FeedbackAdminController extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        FeedbackDAO fbDao = new FeedbackDAO();
        List<Feedback> list = fbDao.getAllFeedback();

        request.setAttribute("feedbackList", list);
        request.setAttribute("pagePrimary", "feedback-admin");
        request.getRequestDispatcher("/WEB-INF/feedback/list.jsp").forward(request, response);
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        int status = Integer.parseInt(request.getParameter("status"));

        FeedbackDAO dao = new FeedbackDAO();
        boolean result = dao.hideFeedback(id, status);

        response.getWriter().write(result ? "success" : "fail");
    }
}
