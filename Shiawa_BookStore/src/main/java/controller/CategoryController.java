/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CategoryDAO;
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
import model.Category;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "CategoryController", urlPatterns = {"/category-admin"})
public class CategoryController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CategoryController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CategoryController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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

        request.setAttribute("currentPage", "category-admin");
        String view = request.getParameter("view");

        if ("add".equals(view)) {
            CategoryDAO cateDAO = new CategoryDAO();
            List<Category> cateParentList = cateDAO.getAllParentCategory();
            request.setAttribute("categoryParentList", cateParentList);
            request.getRequestDispatcher("/WEB-INF/category/add.jsp").forward(request, response);
            return;
        } else if ("addParent".equals(view)) {
            List<Category> cateParentList = null;
            request.setAttribute("categoryParentList", cateParentList);
            request.getRequestDispatcher("/WEB-INF/category/add.jsp").forward(request, response);
            return;
        } else if ("edit".equals(view)) {
            int id = Integer.parseInt(request.getParameter("id"));
            CategoryDAO cdao = new CategoryDAO();
            Category category = cdao.getCategoryById(id);

            request.setAttribute("category", category);
            request.setAttribute("categoryParentList", cdao.getAllParentCategory());

            request.getRequestDispatcher("/WEB-INF/category/edit.jsp").forward(request, response);
            return;
        } else if ("editParent".equals(view)) {
            int id = Integer.parseInt(request.getParameter("id"));
            CategoryDAO cdao = new CategoryDAO();
            Category category = cdao.getCategoryById(id);

            request.setAttribute("category", category);
            request.setAttribute("categoryParentList", null);

            request.getRequestDispatcher("/WEB-INF/category/edit.jsp").forward(request, response);
            return;
        }
        else {

            String keyword = request.getParameter("keyword");
            String categoryParentParam = request.getParameter("categoryParentId");

            CategoryDAO cateDAO = new CategoryDAO();
            List<Category> list;

            Integer categoryParentId = null;
            if (categoryParentParam != null && !categoryParentParam.trim().isEmpty()) {
                categoryParentId = Integer.parseInt(categoryParentParam);
            }

            boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
            boolean hasCategoryParent = categoryParentId != null;

            // ===== SEARCH ƯU TIÊN =====
            if (hasKeyword) {

                list = cateDAO.searchCateByTitle(keyword);

            } // ===== FILTER =====
            else if (hasCategoryParent) {

                list = cateDAO.getCateByParentId(categoryParentId);

            } // ===== LOAD ALL =====
            else {

                list = cateDAO.getAllCategory();
            }

            // ===== THÔNG BÁO KHI RỖNG =====
            if (list.isEmpty()) {

                if (hasKeyword) {
                    request.setAttribute("searchMsg",
                            "No category found with name \"" + keyword + "\"");
                } else if (hasCategoryParent) {
                    request.setAttribute("searchMsg",
                            "No category found in selected parent category.");
                } else {
                    request.setAttribute("searchMsg",
                            "No categories available.");
                }
            }

            request.setAttribute("keyword", keyword);
            request.setAttribute("selectedParentCategoryId", categoryParentId);
            request.setAttribute("categoryParentList", cateDAO.getAllParentCategory());
            request.setAttribute("categoryList", list);

            request.getRequestDispatcher("/WEB-INF/category/list.jsp")
                    .forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String view = request.getParameter("view");

        if ("add".equals(view)) {
            HttpSession session = request.getSession();
            String name = request.getParameter("name");
            int categoryParentId = Integer.parseInt(request.getParameter("categoryParentId"));
            if (categoryParentId > 0) {
                try {
                    Category c = new Category();
                    c.setCategoryName(name);
                    c.setParentId(categoryParentId);

                    CategoryDAO cdao = new CategoryDAO();
                    cdao.insertChildCategory(c);

                    // message thành công
                    session.setAttribute("msg", "Add category successfully");
                    session.setAttribute("msgType", "success");

                } catch (Exception e) {
                    // message thất bại
                    session.setAttribute("msg", "Add category failed");
                    session.setAttribute("msgType", "danger");
                }

                response.sendRedirect(request.getContextPath() + "/category-admin");
                return;
            } else {
                try {
                    Category c = new Category();
                    c.setCategoryName(name);

                    CategoryDAO cdao = new CategoryDAO();
                    cdao.insertParentCategory(c);

                    // message thành công
                    session.setAttribute("msg", "Add parent category successfully");
                    session.setAttribute("msgType", "success");

                } catch (Exception e) {
                    // message thất bại
                    session.setAttribute("msg", "Add parent category failed");
                    session.setAttribute("msgType", "danger");
                }

                response.sendRedirect(request.getContextPath() + "/category-admin");
                return;
            }

        } else if ("edit".equals(view)) {
            String name = request.getParameter("name");
            int categoryParentId = Integer.parseInt(request.getParameter("categoryParentId"));
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            HttpSession session = request.getSession();

            if (categoryParentId > 0) {
                try {
                    Category c = new Category();
                    c.setCategoryName(name);
                    c.setParentId(categoryParentId);
                    c.setCategoryId(categoryId);

                    CategoryDAO cdao = new CategoryDAO();
                    cdao.updateChildCategory(c);

                    // message thành công
                    session.setAttribute("msg", "Update category successfully");
                    session.setAttribute("msgType", "success");

                } catch (Exception e) {
                    // message thất bại
                    session.setAttribute("msg", "Update category failed");
                    session.setAttribute("msgType", "danger");
                }

                response.sendRedirect(request.getContextPath() + "/category-admin");
                return;
            } else {
                try {
                    Category c = new Category();
                    c.setCategoryName(name);
                    c.setCategoryId(categoryId);

                    CategoryDAO cdao = new CategoryDAO();
                    cdao.updateParentCategory(c);

                    // message thành công
                    session.setAttribute("msg", "Update parent category successfully");
                    session.setAttribute("msgType", "success");

                } catch (Exception e) {
                    // message thất bại
                    session.setAttribute("msg", "Update parent category failed");
                    session.setAttribute("msgType", "danger");
                }

                response.sendRedirect(request.getContextPath() + "/category-admin");
                return;
            }

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
