/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author BA LIEM
 */
@WebServlet(name = "CustomerController", urlPatterns = {"/customer"})
public class CustomerController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//       String view = request.getParameter("view");
//       
//       if (view == null || view.equals("list")) {
//           int page  = 1;
//           String pageStr = request.getParameter("page");
//           
//           if (pageStr != null && !pageStr.isEmpty()) {
//               try {
//                   page = Integer.parseInt(pageStr);
//                   if (page < 1) page = 1;
//                   
//               }catch (NumberFormatException ex) {
//                   System.err.println("Invalid page parameter");
//               }
//           }
//           
//           CustomerDAO dao = new CustomerDAO();
//           List<Customer> list = dao.getCustomerList(page);
//           int rowCount = dao.getTotalRows();
//           int totalPages = (int) Math.ceil((double) rowCount / 10);
//           
//           request.setAttribute("customers", list);
//           request.setAttribute("totalPages", totalPages);
//           request.setAttribute("currentPage", page);
//                  
//           request.getRequestDispatcher("/index.jsp").forward(request, response);
//       }
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
