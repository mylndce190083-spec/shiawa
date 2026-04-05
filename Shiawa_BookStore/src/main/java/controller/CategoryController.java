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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        if (user == null || !"Admin".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.setAttribute("pagePrimary", "category-admin");
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
        } else if ("delete".equals(view)) {
            int id = Integer.parseInt(request.getParameter("id"));

            CategoryDAO cdao = new CategoryDAO();
            Category category = cdao.getCategoryById(id);
            Category categoryParent = null;
            if (category.getParentId() > 0) {
                categoryParent = cdao.getCategoryById(category.getParentId());
            }

            request.setAttribute("categoryParent", categoryParent);
            request.setAttribute("category", category);
            request.getRequestDispatcher("/WEB-INF/category/delete.jsp")
                    .forward(request, response);
            return;
        } else {

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

            if (hasKeyword) {

                list = cateDAO.searchCateByTitle(keyword);

            } 
            else if (hasCategoryParent) {

                list = cateDAO.getCateByParentId(categoryParentId);

            }
            else {

                list = cateDAO.getAllCategory();
            }

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
                    CategoryDAO cdao = new CategoryDAO();
                    if (cdao.isCategoryNameExists(name)) {
                        session.setAttribute("msg", "Category name already exists!");
                        session.setAttribute("msgType", "danger");
                        response.sendRedirect(request.getContextPath() + "/category-admin");
                        return;
                    }
                    
                    Category c = new Category();
                    c.setCategoryName(name);
                    c.setParentId(categoryParentId);

                    cdao.insertChildCategory(c);

                    session.setAttribute("msg", "Add category successfully");
                    session.setAttribute("msgType", "success");

                } catch (Exception e) {
                    session.setAttribute("msg", "Add category failed");
                    session.setAttribute("msgType", "danger");
                }

                response.sendRedirect(request.getContextPath() + "/category-admin");
                return;
            } else {
                try {
                    CategoryDAO cdao = new CategoryDAO();
                    if (cdao.isCategoryNameExists(name)) {
                        session.setAttribute("msg", "Category name already exists!");
                        session.setAttribute("msgType", "danger");
                        response.sendRedirect(request.getContextPath() + "/category-admin");
                        return;
                    }
                    
                    Category c = new Category();
                    c.setCategoryName(name);

                   
                    cdao.insertParentCategory(c);

                    session.setAttribute("msg", "Add parent category successfully");
                    session.setAttribute("msgType", "success");

                } catch (Exception e) {
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
                    CategoryDAO cdao = new CategoryDAO();
                    if (cdao.isCategoryNameExists(name)) {
                        session.setAttribute("msg", "Category name already exists!");
                        session.setAttribute("msgType", "danger");
                        response.sendRedirect(request.getContextPath() + "/category-admin");
                        return;
                    }
                    
                    Category c = new Category();
                    c.setCategoryName(name);
                    c.setParentId(categoryParentId);
                    c.setCategoryId(categoryId);

                  
                    cdao.updateChildCategory(c);

                    session.setAttribute("msg", "Update category successfully");
                    session.setAttribute("msgType", "success");

                } catch (Exception e) {
                    session.setAttribute("msg", "Update category failed");
                    session.setAttribute("msgType", "danger");
                }

                response.sendRedirect(request.getContextPath() + "/category-admin");
                return;
            } else {
                try {
                    CategoryDAO cdao = new CategoryDAO();
                    if (cdao.isCategoryNameExists(name)) {
                        session.setAttribute("msg", "Category name already exists!");
                        session.setAttribute("msgType", "danger");
                        response.sendRedirect(request.getContextPath() + "/category-admin");
                        return;
                    }
                    
                    Category c = new Category();
                    c.setCategoryName(name);
                    c.setCategoryId(categoryId);

                    cdao.updateParentCategory(c);

                    session.setAttribute("msg", "Update parent category successfully");
                    session.setAttribute("msgType", "success");

                } catch (Exception e) {
                    session.setAttribute("msg", "Update parent category failed");
                    session.setAttribute("msgType", "danger");
                }

                response.sendRedirect(request.getContextPath() + "/category-admin");
                return;
            }

        } else if ("delete".equals(view)) {
            HttpSession session = request.getSession();
            try {
                int id = Integer.parseInt(request.getParameter("categoryId"));
                CategoryDAO cdao = new CategoryDAO();
                Category category = cdao.getCategoryById(id);

                if (category.getParentId() > 0) {
                    if (cdao.isCategoryHasBook(id)) {
                        session.setAttribute("msg",
                                "Cannot delete! This category has been using");
                        session.setAttribute("msgType", "warning");
                    } else {
                        cdao.deleteCategory(id);
                        session.setAttribute("msg", "Delete category successfully");
                        session.setAttribute("msgType", "success");
                    }
                } else {
                    if (cdao.isParentCategoryHasChild(id)) {
                        session.setAttribute("msg",
                                "Cannot delete! This category has child");
                        session.setAttribute("msgType", "warning");
                    } else {
                        cdao.deleteCategory(id);
                        session.setAttribute("msg", "Delete parent category successfully");
                        session.setAttribute("msgType", "success");
                    }
                }
            } catch (Exception e) {
                session.setAttribute("msg", "Delete book failed");
                session.setAttribute("msgType", "danger");
            }
            response.sendRedirect(request.getContextPath() + "/category-admin");
            return;
        }
    }
}
