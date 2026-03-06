/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookImageDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import model.BookImage;
import utils.FileUpload;

/**
 *
 * @author BA LIEM
 */
@WebServlet(name = "BookImgController", urlPatterns = {"/book_img"})
@MultipartConfig
public class BookImgController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        String action = request.getParameter("action");
//
//        if ("upload".equals(action)) {
//
//            int bookId = Integer.parseInt(request.getParameter("bookId"));
//            Part imgPart = request.getPart("image");
//
//            String imgName = imgPart.getSubmittedFileName();
//
//            if (imgName != null && imgName.length() > 0) {
//
//                String uploadPath = getServletContext().getRealPath("/images/book");
//                File folder = new File(uploadPath);
//                if (!folder.exists()) {
//                    folder.mkdirs();
//                }
//
//                File outFile = new File(folder, imgName);
//                FileUpload.saveFile(imgPart, outFile);
//
//                BookImage img = new BookImage();
//                img.setBookId(bookId);
//                img.setImageUrl("images/book/" + imgName);
//                img.setImageTitle(imgName);
//                img.setPrimary(false);
//                img.setDisplayOrder(0);
//                img.setActive(true);
//
//                BookImageDAO dao = new BookImageDAO();
//                dao.create(img);
//            }
//
//            response.sendRedirect(request.getContextPath()
//                    + "/book?view=edit&id=" + bookId);
//        } else if ("setPrimary".equals(action)) {
//
//            int imageId = Integer.parseInt(request.getParameter("imageId"));
//            int bookId = Integer.parseInt(request.getParameter("bookId"));
//
//            BookImageDAO dao = new BookImageDAO();
//            dao.setPrimary(imageId, bookId);
//
//            response.sendRedirect(request.getContextPath()
//                    + "/book?view=edit&id=" + bookId);
//        } else if ("delete".equals(action)) {
//
//            int imageId = Integer.parseInt(request.getParameter("imageId"));
//            int bookId = Integer.parseInt(request.getParameter("bookId"));
//
//            BookImageDAO dao = new BookImageDAO();
//            dao.delete(imageId);
//
//            response.sendRedirect(request.getContextPath()
//                    + "/book?view=edit&id=" + bookId);
//        }

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
