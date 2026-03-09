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
import model.Account;
import model.BookAdmin;
import model.BookImage;
import model.Category;
import utils.FileUpload;

/**
 *
 * @author BA LIEM
 */
@WebServlet(name = "BookController", urlPatterns = {"/book-admin"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class BookAdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        // 1. Check đăng nhập + role
        if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.setAttribute("currentPage", "book-admin");
        String view = request.getParameter("view");

        if ("post".equals(view)) {
            CategoryDAO cateDAO = new CategoryDAO();
            List<Category> cateList = cateDAO.getIdNameCategory();
            request.setAttribute("categoryList", cateList);
            request.getRequestDispatcher("/WEB-INF/book/post-book.jsp").forward(request, response);
            return;
        } else if ("detail".equals(view)) {
            int id = Integer.parseInt(request.getParameter("id"));

            BookDAO dao = new BookDAO();
            BookImageDAO imgDao = new BookImageDAO();
            BookAdmin book = dao.getBookAdminById(id);

            request.setAttribute("book", book);
            request.setAttribute("bookImages", imgDao.getByBookId(id));
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
            BookImageDAO imgDao = new BookImageDAO();
            BookAdmin book = dao.getBookAdminById(id);

            request.setAttribute("book", book);
            request.setAttribute("bookImages", imgDao.getByBookId(id));
            request.getRequestDispatcher("/WEB-INF/book/delete.jsp")
                    .forward(request, response);
            return;
        } else {

            String keyword = request.getParameter("keyword");
            String categoryParam = request.getParameter("categoryId");

            BookDAO dao = new BookDAO();
            CategoryDAO cateDAO = new CategoryDAO();
            List<BookAdmin> list;

            Integer categoryId = null;
            if (categoryParam != null && !categoryParam.trim().isEmpty()) {
                categoryId = Integer.parseInt(categoryParam);
            }

            boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
            boolean hasCategory = categoryId != null;

            // ===== SEARCH ƯU TIÊN =====
            if (hasKeyword) {

                list = dao.searchByTitle(keyword);

            } // ===== FILTER =====
            else if (hasCategory) {

                list = dao.getBooksByCategory(categoryId);

            } // ===== LOAD ALL =====
            else {

                list = dao.getAllBooksInfo();
            }

            // ===== THÔNG BÁO KHI RỖNG =====
            if (list.isEmpty()) {

                if (hasKeyword) {
                    request.setAttribute("searchMsg",
                            "No book found with name \"" + keyword + "\"");
                } else if (hasCategory) {
                    request.setAttribute("searchMsg",
                            "No book found in selected category.");
                } else {
                    request.setAttribute("searchMsg",
                            "No books available.");
                }
            }

            request.setAttribute("keyword", keyword);
            request.setAttribute("selectedCategoryId", categoryId);
            request.setAttribute("categoryList", cateDAO.getIdNameCategory());
            request.setAttribute("bookList", list);

            request.getRequestDispatcher("/WEB-INF/book/list.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String view = request.getParameter("view");

        if ("post".equals(view)) {
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
                b.setCategoryId(categoryId);

                // sách mới chưa có stock
                b.setStock(0);
                // active luôn vì admin đăng
                b.setIsActive(true);
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

            response.sendRedirect(request.getContextPath() + "/book-admin");
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

                        String uploadPath = "D:/ShiawaUploads/book";//sua duong dan

                        File uploadDir = new File(uploadPath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdirs();
                        }

                        part.write(uploadPath + File.separator + fileName);

                        BookImage img = new BookImage();
                        img.setBookId(bookId);
                        img.setImageUrl("book/" + fileName);//sua duong dan
                        img.setPrimary(false);
                        img.setDisplayOrder(0);

                        bookImageDAO.create(img);
                    }
                }

                // Set Primary
                String primaryImageId = request.getParameter("primaryImageId");

//                if (primaryImageId != null && !primaryImageId.isEmpty()) {
//                    BookImageDAO imgDao = new BookImageDAO();
//
//                    // reset tất cả về 0
//                    imgDao.clearPrimaryByBookId(bookId);
//
//                    // set cái mới thành 1
//                    imgDao.setPrimaryId(Integer.parseInt(primaryImageId));
//                }
                // Admin chọn primary
                if (primaryImageId != null && !primaryImageId.isEmpty()) {
                    bookImageDAO.clearPrimaryByBookId(bookId);
                    bookImageDAO.setPrimaryId(Integer.parseInt(primaryImageId));
                } // Admin không chọn primary
                else {
                    List<BookImage> currentImages = bookImageDAO.getByBookId(bookId);

                    if (currentImages.size() == 1) {
                        BookImage onlyImg = currentImages.get(0);
                        bookImageDAO.clearPrimaryByBookId(bookId);
                        bookImageDAO.setPrimaryId(onlyImg.getImageId());
                    }
                }
                session.setAttribute("msg", "Update book successfully");
                session.setAttribute("msgType", "success");

            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("msg", "Update book failed");
                session.setAttribute("msgType", "danger");
            }
            response.sendRedirect(request.getContextPath() + "/book-admin");
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
            response.sendRedirect(request.getContextPath() + "/book-admin");
            return;
        } else {
            response.sendRedirect(request.getContextPath() + "/book-admin");
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
