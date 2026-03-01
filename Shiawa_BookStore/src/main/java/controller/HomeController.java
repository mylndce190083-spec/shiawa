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
//        BookDAO dao = new BookDAO();
//        CategoryDAO cdao = new CategoryDAO();
//        List<Book> list = dao.getAllBook();
//        List<Category> clist = cdao.getAllCategory();
//        
//        request.setAttribute("listB", list);
//        request.setAttribute("listC", clist);
//        request.getRequestDispatcher("/WEB-INF/home/home.jsp").forward(request, response);
//    }

    BookDAO dao = new BookDAO();
    CategoryDAO cdao = new CategoryDAO();
    
    // 1. Lấy ID thể loại từ URL (nếu có)
    String cateIdRaw = request.getParameter("id");
    List<Book> list;

    if (cateIdRaw != null && !cateIdRaw.isEmpty()) {
        // Nếu có ID -> Gọi hàm lọc sách theo thể loại đã viết trong BookDAO
        int cateId = Integer.parseInt(cateIdRaw);
        list = dao.getBooksByCategoryId(cateId); 
    } else {
        // Nếu không có ID -> Lấy tất cả sách như cũ
        list = dao.getAllBook();
    }

    List<Category> clist = cdao.getAllCategory();

    request.setAttribute("listB", list);
    request.setAttribute("listC", clist);
    
    // 2. Chuyển hướng về trang home.jsp (hoặc booklist.jsp tùy bạn muốn hiện ở đâu)
    request.getRequestDispatcher("/WEB-INF/home/home.jsp").forward(request, response);
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
