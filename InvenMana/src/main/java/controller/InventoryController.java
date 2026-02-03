package controller;

import dao.BookDAO;
import dao.ReportDAO;
import dao.StockTxnDAO;
import dao.SupplierDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Book;
import model.StockTxn;
import model.StockTxnItem;
import model.Supplier;

@WebServlet(name = "InventoryController", urlPatterns = {"/inventory"})
public class InventoryController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String view = request.getParameter("view");

        if ("in".equals(view)) {
            loadBooksAndSuppliers(request);
            request.getRequestDispatcher("/WEB-INF/inventory/in.jsp").forward(request, response);
            return;
        }

        if ("out".equals(view)) {
            loadBooksAndSuppliers(request);
            request.getRequestDispatcher("/WEB-INF/inventory/out.jsp").forward(request, response);
            return;
        }

        if ("low".equals(view)) {
            int threshold = 5;
            String t = request.getParameter("threshold");
            if (t != null && !t.isBlank()) {
                try {
                    threshold = Integer.parseInt(t);
                } catch (NumberFormatException ignored) {
                }
            }
            List<Book> list = new BookDAO().getLowStockBooks(threshold);
            request.setAttribute("threshold", threshold);
            request.setAttribute("bookList", list);
            request.getRequestDispatcher("/WEB-INF/inventory/low.jsp").forward(request, response);
            return;
        }

        if ("report".equals(view)) {
            LocalDate to = LocalDate.now();
            LocalDate from = to.minusDays(7);
            String fromStr = request.getParameter("from");
            String toStr = request.getParameter("to");
            if (fromStr != null && !fromStr.isBlank()) from = LocalDate.parse(fromStr);
            if (toStr != null && !toStr.isBlank()) to = LocalDate.parse(toStr);

            Integer bookId = null;
            String bookIdStr = request.getParameter("bookId");
            if (bookIdStr != null && !bookIdStr.isBlank()) {
                try {
                    bookId = Integer.valueOf(bookIdStr);
                } catch (NumberFormatException ignored) {
                }
            }

            ReportDAO rdao = new ReportDAO();
            request.setAttribute("from", from.toString());
            request.setAttribute("to", to.toString());
            request.setAttribute("dailyRows", rdao.getDailySummary(from, to));
            request.setAttribute("productRows", rdao.getProductSummary(from, to, bookId));
            request.setAttribute("books", new BookDAO().getAllBooks());
            request.setAttribute("selectedBookId", bookId);
            request.getRequestDispatcher("/WEB-INF/inventory/report.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/inventory?view=in");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String view = request.getParameter("view");

        if ("in".equals(view) || "out".equals(view)) {
            String txnType = "in".equals(view) ? "IN" : "OUT";
            String txnCode = request.getParameter("txnCode");
            String note = request.getParameter("note");
            Integer supplierId = null;
            String supplierIdStr = request.getParameter("supplierId");
            if (supplierIdStr != null && !supplierIdStr.isBlank()) {
                try {
                    supplierId = Integer.valueOf(supplierIdStr);
                } catch (NumberFormatException ignored) {
                }
            }

            // parse items arrays
            String[] bookIds = request.getParameterValues("bookId");
            String[] qtys = request.getParameterValues("qty");
            String[] unitCosts = request.getParameterValues("unitCost"); // only for IN

            List<StockTxnItem> items = new ArrayList<>();
            if (bookIds != null && qtys != null) {
                for (int i = 0; i < bookIds.length; i++) {
                    if (bookIds[i] == null || bookIds[i].isBlank()) continue;
                    if (qtys.length <= i || qtys[i] == null || qtys[i].isBlank()) continue;
                    int bookId = Integer.parseInt(bookIds[i]);
                    int qty = Integer.parseInt(qtys[i]);
                    if (qty <= 0) continue;
                    StockTxnItem it = new StockTxnItem();
                    it.setBookId(bookId);
                    it.setQty(qty);
                    if ("IN".equals(txnType) && unitCosts != null && unitCosts.length > i && unitCosts[i] != null && !unitCosts[i].isBlank()) {
                        try {
                            it.setUnitCost(Double.valueOf(unitCosts[i]));
                        } catch (NumberFormatException ignored) {
                        }
                    }
                    items.add(it);
                }
            }

            if (items.isEmpty()) {
                request.setAttribute("error", "Bạn cần nhập ít nhất 1 dòng sản phẩm.");
                loadBooksAndSuppliers(request);
                request.getRequestDispatcher("IN".equals(txnType) ? "/WEB-INF/inventory/in.jsp" : "/WEB-INF/inventory/out.jsp")
                        .forward(request, response);
                return;
            }

            StockTxn txn = new StockTxn();
            txn.setTxnType(txnType);
            txn.setTxnCode(txnCode == null || txnCode.isBlank() ? genCode(txnType) : txnCode.trim());
            txn.setSupplierId("IN".equals(txnType) ? supplierId : null);
            txn.setNote(note);
            txn.setItems(items);

            try {
                new StockTxnDAO().createTxnAndApplyStock(txn);
                response.sendRedirect(request.getContextPath() + "/inventory?view=" + view);
            } catch (Exception ex) {
                request.setAttribute("error", ex.getMessage());
                loadBooksAndSuppliers(request);
                request.getRequestDispatcher("IN".equals(txnType) ? "/WEB-INF/inventory/in.jsp" : "/WEB-INF/inventory/out.jsp")
                        .forward(request, response);
            }
            return;
        }

        response.sendRedirect(request.getContextPath() + "/inventory");
    }

    private void loadBooksAndSuppliers(HttpServletRequest request) {
        List<Book> books = new BookDAO().getAllBooks();
        List<Supplier> suppliers = new SupplierDAO().getAllActive();
        request.setAttribute("books", books);
        request.setAttribute("suppliers", suppliers);
    }

    private String genCode(String txnType) {
        String prefix = "IN".equalsIgnoreCase(txnType) ? "GRN" : "ISS";
        return prefix + "-" + System.currentTimeMillis();
    }
}




