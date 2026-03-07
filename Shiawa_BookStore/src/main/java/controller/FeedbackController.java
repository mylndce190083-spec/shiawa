/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookDAO;
import dao.FeedbackDAO;
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

        // Tạo một biến id để lưu giá trị cuối cùng
        int id;

        try {
            if (id_raw != null && !id_raw.isEmpty()) {
                // Nếu trên URL có ID thì lấy ID đó
                id = Integer.parseInt(id_raw);
            } else {
                // Nếu chạy trực tiếp (không có ID), tạm thời để 1 ID có thật trong DB để bạn thấy kết quả
                // Sau này gộp code xong, bạn có thể xóa dòng này hoặc redirect về Home
                id = 1;
            }

            // 2. Gọi DAO để lấy dữ liệu THẬT từ database
            BookDAO dao = new BookDAO();
            Book b = dao.getBookById(id);

            if (b != null) {
                // Gửi đối tượng book sang JSP
                request.setAttribute("book", b);
                request.getRequestDispatcher("/WEB-INF/home/feedback.jsp").forward(request, response);
            } else {
                // Nếu ID không tồn tại trong DB
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
//        HttpSession session = request.getSession();
//        Account user = (Account) session.getAttribute("user");
//        
//        if(user == null) {
//            response.sendRedirect(request.getContextPath() + "/login");
//            return;
//        }
//        try {
//            int bookId = Integer.parseInt(request.getParameter("book_id"));
//            int rating = Integer.parseInt(request.getParameter("rating"));
//            String content = request.getParameter("content");
//            Feedback fb = new Feedback();
//            fb.setUserId(user.getId());
//            fb.setBookId(bookId);
//            fb.setRating(rating);
//            fb.setContent(content);
//            FeedbackDAO dao = new FeedbackDAO();
//            dao.insertFeedback(fb);
//            response.sendRedirect(request.getContextPath() + "/bookdetail?id=" + bookId);
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.sendRedirect(request.getContextPath() + "/home");
//        }

        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

//        try {
//            // 1. Lấy order_id từ form/request
//            int orderId = Integer.parseInt(request.getParameter("order_id"));
//            int rating = Integer.parseInt(request.getParameter("rating"));
//            String content = request.getParameter("content");
//
//            // 2. Gọi DAO để lấy danh sách các mặt hàng trong đơn hàng này
//            // Giả sử bạn đã có hàm getOrderItemsByOrderId trong OrderDAO
//            OrderDAO orderDao = new OrderDAO();
//            Orders order = orderDao.getOrderById(orderId);
//            List<OrderItem> items = order.getItems();
//
//            FeedbackDAO fbDao = new FeedbackDAO();
//
//            // 3. Vòng lặp để lưu đánh giá cho TẤT CẢ các sách trong đơn
//            for (OrderItem item : items) {
//                Feedback fb = new Feedback();
//                fb.setUserId(user.getId());
//                fb.setBookId(item.getBookId()); // Lấy bookId từ từng item
//                fb.setRating(rating);
//                fb.setContent(content);
//
//                fbDao.insertFeedback(fb);
//            }
//
//            // 4. Sau khi xong, chuyển hướng về trang danh sách đơn hàng đã hoàn thành
//            response.sendRedirect(request.getContextPath() + "/OrderList/delivered");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.sendRedirect(request.getContextPath() + "/home");
//        }
//    }

        try {
        int orderId = Integer.parseInt(request.getParameter("order_id"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String content = request.getParameter("content");
        
        // Cần lấy thêm book_id để biết sau khi gửi xong thì quay về trang nào
        String redirectBookId = request.getParameter("book_id"); 

        OrderDAO orderDao = new OrderDAO();
        Orders order = orderDao.getOrderById(orderId);
        List<OrderItem> items = order.getItems();
        FeedbackDAO fbDao = new FeedbackDAO();

        for (OrderItem item : items) {
            Feedback fb = new Feedback();
            fb.setUserId(user.getId());
            fb.setBookId(item.getBookId());
            fb.setRating(rating);
            fb.setContent(content);
            fbDao.insertFeedback(fb);
        }

        // QUAN TRỌNG: Redirect về trang chi tiết sách
        response.sendRedirect(request.getContextPath() + "/bookdetail?id=" + redirectBookId);

    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect(request.getContextPath() + "/home");
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
