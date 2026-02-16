/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookDAO;
import dao.BookImageDAO;
import dao.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import model.BookAdmin;
import model.BookImage;
import model.Category;
import util.FileUpload;

/**
 *
 * @author BA LIEM
 */
@WebServlet(name = "BookController", urlPatterns = {"/book"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class BookController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String view = request.getParameter("view");

        if ("add".equals(view)) {
            CategoryDAO cateDAO = new CategoryDAO();
            List<Category> cateList = cateDAO.getIdNameCategory();
            request.setAttribute("categoryList", cateList);
            request.getRequestDispatcher("/WEB-INF/book/create.jsp").forward(request, response);
            return;
        } else if ("detail".equals(view)) {
            int id = Integer.parseInt(request.getParameter("id"));

            BookDAO dao = new BookDAO();
            BookAdmin book = dao.getBookAdminById(id);

            request.setAttribute("book", book);
            request.getRequestDispatcher("/WEB-INF/book/detail.jsp").forward(request, response);
            return;
        } else if ("edit".equals(view)) {
            int id = Integer.parseInt(request.getParameter("id"));

            BookDAO bdao = new BookDAO();
            CategoryDAO cdao = new CategoryDAO();
            BookImageDAO imgDAO = new BookImageDAO();

            BookAdmin book = bdao.getBookAdminById(id);

            request.setAttribute("bookImages", imgDAO.getByBookId(id));
            request.setAttribute("book", book);
            request.setAttribute("categoryList", cdao.getIdNameCategory());

            request.getRequestDispatcher("/WEB-INF/book/edit.jsp").forward(request, response);
            return;
        } else if ("delete".equals(view)) {
            int id = Integer.parseInt(request.getParameter("id"));
            BookDAO dao = new BookDAO();
            BookAdmin book = dao.getBookAdminById(id);

            request.setAttribute("book", book);
            request.getRequestDispatcher("/WEB-INF/book/delete.jsp")
                    .forward(request, response);
            return;
        } else {
            String keyword = request.getParameter("keyword");
            BookDAO dao = new BookDAO();
            List<BookAdmin> list;

            if (keyword != null && !keyword.trim().isEmpty()) {
                list = dao.searchByTitle(keyword);
                request.setAttribute("keyword", keyword); // giữ text search

                if (list.isEmpty()) {
                    request.setAttribute("searchMsg",
                            "No book found with name \"" + keyword + "\"");
                }
            } else {
                list = dao.getAllBooksInfo();
            }
            request.setAttribute("bookList", list);
            request.getRequestDispatcher("/WEB-INF/book/list.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String view = request.getParameter("view");

        if ("add".equals(view)) {
            HttpSession session = request.getSession();

            try {
                String title = request.getParameter("title");
                String author = request.getParameter("author");
                double price = Double.parseDouble(request.getParameter("price"));
                int stock = Integer.parseInt(request.getParameter("stock"));
                int categoryId = Integer.parseInt(request.getParameter("categoryId"));

                BookAdmin b = new BookAdmin();
                b.setTitle(title);
                b.setAuthor(author);
                b.setPrice(price);
                b.setStock(stock);
                b.setCategoryId(categoryId);

                BookDAO bdao = new BookDAO();
                bdao.insertBook(b);

                // message thành công
                session.setAttribute("msg", "Add book successfully");
                session.setAttribute("msgType", "success");

            } catch (Exception e) {
                // message thất bại
                session.setAttribute("msg", "Add book failed");
                session.setAttribute("msgType", "danger");
            }

            response.sendRedirect(request.getContextPath() + "/book");
            return;
        } else if ("edit".equals(view)) {
            HttpSession session = request.getSession();
            

            try {
                //Update Book
                BookAdmin b = new BookAdmin();
                b.setBookId(Integer.parseInt(request.getParameter("bookId")));
                int bookId = b.getBookId();
                b.setTitle(request.getParameter("title"));
                b.setAuthor(request.getParameter("author"));
                b.setDescription(request.getParameter("description"));
                b.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
                b.setPrice(Double.parseDouble(request.getParameter("price")));
                b.setStock(Integer.parseInt(request.getParameter("stock")));
                b.setIsActive("true".equals(request.getParameter("isActive")));

                BookDAO dao = new BookDAO();
                dao.updateBook(b);

                BookImageDAO bookImageDAO = new BookImageDAO();
                // Delete Img
                String[] deleteIds = request.getParameterValues("deleteImageIds");

                if (deleteIds != null) {
                    for (String id : deleteIds) {
                        if (id != null && !id.trim().isEmpty()) {
                            bookImageDAO.delete(Integer.parseInt(id));
                        }
                    }
                }

                // Update Img
                Collection<Part> parts = request.getParts();

                for (Part part : parts) {
                    if ("images".equals(part.getName()) && part.getSize() > 0) {

                        String fileName = Paths.get(part.getSubmittedFileName())
                                .getFileName().toString();

                        String uploadPath = getServletContext()
                                .getRealPath("/uploads");

                        File uploadDir = new File(uploadPath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdirs();
                        }

                        part.write(uploadPath + File.separator + fileName);

                        BookImage img = new BookImage();
                        img.setBookId(bookId);
                        img.setImageUrl("uploads/" + fileName);
                        img.setPrimary(false);
                        img.setDisplayOrder(0);

                        bookImageDAO.create(img);
                    }
                }

                // Set Primary
                String primaryImageId = request.getParameter("primaryImageId");

                if (primaryImageId != null && !primaryImageId.isEmpty()) {
                    BookImageDAO imgDao = new BookImageDAO();

                    // reset tất cả về 0
                    imgDao.clearPrimaryByBookId(bookId);

                    // set cái mới thành 1
                    imgDao.setPrimaryId(Integer.parseInt(primaryImageId));
                }
                session.setAttribute("msg", "Update book successfully");
                session.setAttribute("msgType", "success");

            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("msg", "Update book failed");
                session.setAttribute("msgType", "danger");
            }
            response.sendRedirect(request.getContextPath() + "/book");
            return;
        } else if ("delete".equals(view)) {
            HttpSession session = request.getSession();
            try {
                int id = Integer.parseInt(request.getParameter("bookId"));
                BookDAO dao = new BookDAO();

                if (dao.isBookUsedInOrder(id)) {
                    // đang được sử dụng -> xóa mềm
                    dao.softDeleteBook(id);
                    session.setAttribute("msg",
                            "Book is currently used in orders. Status changed to inactive.");
                    session.setAttribute("msgType", "warning");
                } else {
                    // không bị ràng buộc -> xóa cứng
                    dao.hardDeleteBook(id);
                    session.setAttribute("msg", "Delete book successfully");
                    session.setAttribute("msgType", "success");
                }
            } catch (Exception e) {
                session.setAttribute("msg", "Delete book failed");
                session.setAttribute("msgType", "danger");
            }
            response.sendRedirect(request.getContextPath() + "/book");
            return;
        } else {
            response.sendRedirect(request.getContextPath() + "/book");
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
