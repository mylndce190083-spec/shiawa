package controller;

import dao.SupplierDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Supplier;

@WebServlet(name = "SupplierController", urlPatterns = {"/supplier"})
public class SupplierController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String view = request.getParameter("view");

        if ("add".equals(view)) {
            request.getRequestDispatcher("/WEB-INF/supplier/create.jsp").forward(request, response);
            return;
        }

        SupplierDAO dao = new SupplierDAO();
        List<Supplier> list = dao.getAllActive();
        request.setAttribute("suppliers", list);
        request.getRequestDispatcher("/WEB-INF/supplier/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String view = request.getParameter("view");
        if ("add".equals(view)) {
            Supplier s = new Supplier();
            s.setName(request.getParameter("name"));
            s.setPhone(request.getParameter("phone"));
            s.setEmail(request.getParameter("email"));
            s.setAddress(request.getParameter("address"));
            new SupplierDAO().insert(s);
            response.sendRedirect(request.getContextPath() + "/supplier");
        }
    }
}




