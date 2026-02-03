/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookDAO;
import dao.CategoryDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import model.Book;
import model.Category;

/**
 *
 * @author BA LIEM
 */
@WebServlet(name = "BookController", urlPatterns = {"/book"})
public class BookController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String view = request.getParameter("view");

        if ("edit".equals(view)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Book b = new BookDAO().getBookById(id);
            CategoryDAO cateDAO = new CategoryDAO();
            List<Category> cateList = cateDAO.getAllCategories();
            request.setAttribute("book", b);
            request.setAttribute("categorys", cateList);
            request.getRequestDispatcher("/WEB-INF/book/edit.jsp").forward(request, response);
            return;
        }

        if ("delete".equals(view)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Book b = new BookDAO().getBookById(id);
            request.setAttribute("book", b);
            request.getRequestDispatcher("/WEB-INF/book/delete.jsp").forward(request, response);
            return;
        }

        if ("add".equals(view)) {
            CategoryDAO cateDAO = new CategoryDAO();
            List <Category> cateList = cateDAO.getAllCategories();
            request.setAttribute("categorys", cateList);
            request.getRequestDispatcher("/WEB-INF/book/create.jsp").forward(request, response);
            return;
        }

        // Inventory-style list with optional stock filter/sort (handled in Java for simplicity)
        Integer minStock = null;
        Integer maxStock = null;
        String minStr = request.getParameter("minStock");
        String maxStr = request.getParameter("maxStock");
        if (minStr != null && !minStr.isBlank()) {
            try { minStock = Integer.valueOf(minStr); } catch (NumberFormatException ignored) {}
        }
        if (maxStr != null && !maxStr.isBlank()) {
            try { maxStock = Integer.valueOf(maxStr); } catch (NumberFormatException ignored) {}
        }
        if (minStock != null && minStock < 0) {
            minStock = 0;
        }
        if (maxStock != null && maxStock < 0) {
            maxStock = 0;
        }
        if (minStock != null && maxStock != null && minStock > maxStock) {
            // swap to be forgiving
            int tmp = minStock;
            minStock = maxStock;
            maxStock = tmp;
        }
        String sort = request.getParameter("sort");

        BookDAO dao = new BookDAO();
        List<Book> list = dao.getAllBooks();

        // filter by stock without lambdas (for older compiler compatibility)
        if (minStock != null || maxStock != null) {
            Iterator<Book> it = list.iterator();
            while (it.hasNext()) {
                Book b = it.next();
                if (minStock != null && b.getStock() < minStock) {
                    it.remove();
                    continue;
                }
                if (maxStock != null && b.getStock() > maxStock) {
                    it.remove();
                }
            }
        }

        // sort (no lambdas)
        if ("stock_desc".equalsIgnoreCase(sort)) {
            Collections.sort(list, new Comparator<Book>() {
                @Override
                public int compare(Book a, Book b) {
                    return Integer.compare(b.getStock(), a.getStock());
                }
            });
        } else if ("id".equalsIgnoreCase(sort)) {
            Collections.sort(list, new Comparator<Book>() {
                @Override
                public int compare(Book a, Book b) {
                    return Integer.compare(a.getBookId(), b.getBookId());
                }
            });
        } else {
            // default: stock ascending
            Collections.sort(list, new Comparator<Book>() {
                @Override
                public int compare(Book a, Book b) {
                    return Integer.compare(a.getStock(), b.getStock());
                }
            });
        }

        request.setAttribute("bookList", list);
        request.setAttribute("minStock", minStock);
        request.setAttribute("maxStock", maxStock);
        request.setAttribute("sort", sort);
        request.getRequestDispatcher("/WEB-INF/book/list.jsp").forward(request, response);
    
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       String view = request.getParameter("view");
       
       if ("add".equals(view)) {
           String title = request.getParameter("title");
           String author = request.getParameter("author");
           double price = Double.parseDouble(request.getParameter("price"));
           int initialStock = Integer.parseInt(request.getParameter("stock"));
           int categoryId = Integer.parseInt(request.getParameter("categoryId"));
           
           Book b = new Book();
           b.setTitle(title);
           b.setAuthor(author);
           b.setPrice(price);
           // Insert with stock=0, then auto-create an "IN receipt" for nhập kho ban đầu
           b.setStock(0);
           b.setCategoryId(categoryId);
           
           try {
               BookDAO bdao = new BookDAO();
               int newBookId = bdao.insertBookReturnId(b);

               if (initialStock > 0) {
                   model.StockTxn txn = new model.StockTxn();
                   txn.setTxnType("IN");
                   txn.setTxnCode("GRN-NEWBOOK-" + System.currentTimeMillis());
                   txn.setNote("Nhập kho ban đầu khi thêm sách mới (book_id=" + newBookId + ")");

                   model.StockTxnItem it = new model.StockTxnItem();
                   it.setBookId(newBookId);
                   it.setQty(initialStock);
                   java.util.List<model.StockTxnItem> items = new java.util.ArrayList<>();
                   items.add(it);
                   txn.setItems(items);

                   new dao.StockTxnDAO().createTxnAndApplyStock(txn);
               }

               response.sendRedirect(request.getContextPath() + "/book");
               return;
           } catch (Exception ex) {
               throw new ServletException(ex);
           }
       }

       if ("edit".equals(view)) {
           int id = Integer.parseInt(request.getParameter("id"));
           String title = request.getParameter("title");
           String author = request.getParameter("author");
           double price = Double.parseDouble(request.getParameter("price"));
           int stock = Integer.parseInt(request.getParameter("stock"));
           int categoryId = Integer.parseInt(request.getParameter("categoryId"));

           Book b = new Book();
           b.setBookId(id);
           b.setTitle(title);
           b.setAuthor(author);
           b.setPrice(price);
           b.setStock(stock);
           b.setCategoryId(categoryId);

           new BookDAO().updateBook(b);
           response.sendRedirect(request.getContextPath() + "/book");
           return;
       }

       if ("delete".equals(view)) {
           int id = Integer.parseInt(request.getParameter("id"));
           new BookDAO().deleteBook(id);
           response.sendRedirect(request.getContextPath() + "/book");
           return;
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
