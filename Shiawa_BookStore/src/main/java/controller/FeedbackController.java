
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookDAO;
import dao.FeedbackDAO;
import dao.OrderDAO;
import dao.OrderDetailDAO;
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
import model.Book;
import model.Feedback;
import model.OrderItem;
import model.Orders;

/**
 *
 * @author admin
 */
@WebServlet(name = "FeedbackController", urlPatterns = {"/feedback"})
public class FeedbackController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //   request.getRequestDispatcher("/WEB-INF/home/feedback.jsp").forward(request, response);
        response.setContentType("text/html;charset=UTF-8");

        String id_raw = request.getParameter("book_id");
        String order_detail_id = request.getParameter("order_detail_id");
        int id;

        try {
            if (id_raw != null && !id_raw.isEmpty()) {
                id = Integer.parseInt(id_raw);
            } else {
                id = 1;
            }
            BookDAO dao = new BookDAO();
            Book b = dao.getBookById(id);
            OrderDetailDAO oddao = new OrderDetailDAO();
            OrderItem od = oddao.getOneItemByOrderDetailId(Integer.parseInt(order_detail_id));

            if (b != null && od != null) {
                request.setAttribute("book", b);
                request.setAttribute("item", od);
                request.getRequestDispatcher("/WEB-INF/home/feedback.jsp").forward(request, response);
            } else {
                response.sendRedirect("home");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            
            int rating = Integer.parseInt(request.getParameter("rating"));
            String content = request.getParameter("content");
            int bookId = Integer.parseInt(request.getParameter("book_id"));
            String isRated = request.getParameter("isRated");
            int orderDetailId = Integer.parseInt(request.getParameter("orderDetailId"));

            Feedback fb = new Feedback();
            fb.setUserId(user.getId());
            fb.setBookId(bookId);
            fb.setRating(rating);
            fb.setContent(content);
            FeedbackDAO fbDao = new FeedbackDAO();
            OrderDetailDAO oddao = new OrderDetailDAO();
            
            if ("rated".equalsIgnoreCase(isRated)) {
                fbDao.updateFeedback(fb, orderDetailId);
            } else if ("unrated".equalsIgnoreCase(isRated)) {
                fbDao.insertFeedback(fb, orderDetailId);
                oddao.changeIsRatedById(orderDetailId);
                
            }

            // Chuyển hướng về trang chi tiết sách để xem đánh giá mới
            response.sendRedirect("bookdetail?id=" + bookId);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("home");
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
