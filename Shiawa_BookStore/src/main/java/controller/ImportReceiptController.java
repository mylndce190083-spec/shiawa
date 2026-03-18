package controller;

import dao.BookDAO;
import dao.ImportReceiptDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Book;
import model.ImportReceipt;
import model.ImportReceiptDetail;

@WebServlet(name = "ImportReceiptController", urlPatterns = {"/import-receipt"})
public class ImportReceiptController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        // 1. Check đăng nhập + role
        if (user == null || !"Inventory".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.setAttribute("pagePrimary", "import-receipt");
        String view = request.getParameter("view");
        if (view == null || view.equals("list")) {
            List<ImportReceipt> receipts = new ImportReceiptDAO().getReceipts();
            request.setAttribute("receipts", receipts);
            request.getRequestDispatcher("/WEB-INF/receipt/list.jsp").forward(request, response);
            return;
        }

        if ("add".equals(view)) {
            request.getRequestDispatcher("/WEB-INF/receipt/add.jsp").forward(request, response);
            return;
        }

        if ("edit".equals(view)) {
            int id = Integer.parseInt(request.getParameter("id"));
            ImportReceipt r = new ImportReceiptDAO().getReceiptWithDetails(id);
            request.setAttribute("receipt", r);
            request.getRequestDispatcher("/WEB-INF/receipt/edit.jsp").forward(request, response);
            return;
        }

        if ("delete".equals(view)) {
            int id = Integer.parseInt(request.getParameter("id"));
            ImportReceipt r = new ImportReceiptDAO().getReceiptWithDetails(id);
            request.setAttribute("receipt", r);
            request.getRequestDispatcher("/WEB-INF/receipt/delete.jsp").forward(request, response);
            return;
        }

        if ("detail".equals(view)) {
            int id = Integer.parseInt(request.getParameter("id"));
            ImportReceipt r = new ImportReceiptDAO().getReceiptWithDetails(id);
            request.setAttribute("receipt", r);
            request.getRequestDispatcher("/WEB-INF/receipt/detail.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/import-receipt");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String view = request.getParameter("view");
        if ("add".equals(view)) {
            handleAdd(request, response);
            return;
        }
        if ("edit".equals(view)) {
            handleEdit(request, response);
            return;
        }
        if ("delete".equals(view)) {
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                new ImportReceiptDAO().deleteReceipt(id);
                response.sendRedirect(request.getContextPath() + "/import-receipt");
            } catch (Exception ex) {
                throw new ServletException(ex);
            }
            return;
        }
        response.sendRedirect(request.getContextPath() + "/import-receipt");
    }

    private void handleAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer supplierId = null;
        String supplierStr = request.getParameter("supplierId");
        if (supplierStr != null && !supplierStr.isBlank()) {
            supplierId = Integer.valueOf(supplierStr);
        }
        String note = request.getParameter("note");

        String[] bookIds = request.getParameterValues("bookId");
        String[] qtys = request.getParameterValues("qty");
        String[] prices = request.getParameterValues("importPrice");

        List<ImportReceiptDetail> items = parseDetails(bookIds, qtys, prices);
        if (items.isEmpty()) {
            request.setAttribute("error", "Bạn cần nhập ít nhất 1 dòng sách.");
            request.getRequestDispatcher("/WEB-INF/receipt/add.jsp").forward(request, response);
            return;
        }

        ImportReceipt r = new ImportReceipt();
        r.setNote(note);
        r.setDetails(items);

        try {
            new ImportReceiptDAO().insertReceipt(r);
            response.sendRedirect(request.getContextPath() + "/import-receipt");
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void handleEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int receiptId = Integer.parseInt(request.getParameter("id"));
        Integer supplierId = null;
        String supplierStr = request.getParameter("supplierId");
        if (supplierStr != null && !supplierStr.isBlank()) {
            supplierId = Integer.valueOf(supplierStr);
        }
        String note = request.getParameter("note");

        String[] bookIds = request.getParameterValues("bookId");
        String[] qtys = request.getParameterValues("qty");
        String[] prices = request.getParameterValues("importPrice");

        List<ImportReceiptDetail> items = parseDetails(bookIds, qtys, prices);
        if (items.isEmpty()) {
            request.setAttribute("error", "Bạn cần nhập ít nhất 1 dòng sách.");
            ImportReceipt r = new ImportReceiptDAO().getReceiptWithDetails(receiptId);
            request.setAttribute("receipt", r);
            request.getRequestDispatcher("/WEB-INF/receipt/edit.jsp").forward(request, response);
            return;
        }

        ImportReceipt r = new ImportReceipt();
        r.setReceiptId(receiptId);
        r.setNote(note);
        r.setDetails(items);

        try {
            new ImportReceiptDAO().updateReceipt(r);
            response.sendRedirect(request.getContextPath() + "/import-receipt");
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private List<ImportReceiptDetail> parseDetails(String[] bookIds, String[] qtys, String[] prices) {
        List<ImportReceiptDetail> items = new ArrayList<>();
        if (bookIds == null || qtys == null || prices == null) return items;
        int len = Math.min(bookIds.length, Math.min(qtys.length, prices.length));
        for (int i = 0; i < len; i++) {
            String b = bookIds[i];
            String q = qtys[i];
            String p = prices[i];
            if (b == null || b.isBlank()) continue;
            if (q == null || q.isBlank()) continue;
            if (p == null || p.isBlank()) continue;
            try {
                int bookId = Integer.parseInt(b);
                int qty = Integer.parseInt(q);
                double price = Double.parseDouble(p);
                if (qty <= 0 || price < 0) continue;
                ImportReceiptDetail d = new ImportReceiptDetail();
                d.setBookId(bookId);
                d.setQty(qty);
                d.setImportPrice(price);
                items.add(d);
            } catch (NumberFormatException ignored) {
            }
        }
        return items;
    }

}


