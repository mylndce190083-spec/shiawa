package controller;

import dao.BookDAO;
import dao.CategoryDAO;
import dao.ReportDAO;
import dao.StockInRequestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Book;
import model.BookAdmin;
import model.StockInRequest;
import model.StockInRequestItem;

@WebServlet(name = "InventoryController", urlPatterns = {"/inventory"})
public class InventoryController extends HttpServlet {

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
//        request.setAttribute("currentPage", "inventory");
        String view = request.getParameter("view");

        if ("list".equals(view)) {
            request.setAttribute("pagePrimary", "inventory-list");
            List<BookAdmin> list = new BookDAO().getAllBooksInfo();
            request.setAttribute("bookList", list);
            request.setAttribute("minStock", "");
            request.setAttribute("maxStock", "");
            request.setAttribute("sort", "");
            request.setAttribute("currentPage", 1);
            request.setAttribute("totalPages", 1);
            request.getRequestDispatcher("/WEB-INF/inventory/list.jsp").forward(request, response);
            return;
        }

        if ("in".equals(view)) {
            request.setAttribute("pagePrimary", "inventory-in");
            loadBooks(request);
            loadCategories(request);
            request.getRequestDispatcher("/WEB-INF/inventory/in.jsp").forward(request, response);
            return;
        }

        if ("low".equals(view)) {
            request.setAttribute("pagePrimary", "inventory-low");
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

        if ("history".equals(view)) {
            request.setAttribute("pagePrimary", "inventory-history");
            Object userObj = request.getSession().getAttribute("user");
            Integer staffId = null;
            if (userObj instanceof Account) {
                Account acc = (Account) userObj;
                if (!"Customer".equalsIgnoreCase(acc.getRole())) {
                    staffId = acc.getId();
                }
            }

            List<StockInRequest> requests = new StockInRequestDAO().getRequestsWithItems(staffId);
            int approvedCount = 0;
            for (StockInRequest r : requests) {
                if ("APPROVED".equalsIgnoreCase(r.getStatus())) {
                    approvedCount++;
                }
            }

            request.setAttribute("requestHistory", requests);
            request.setAttribute("approvedCount", approvedCount);
            request.getRequestDispatcher("/WEB-INF/inventory/history.jsp").forward(request, response);
            return;
        }

        if ("report".equals(view)) {
            request.setAttribute("pagePrimary", "inventory-report");
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
            request.setAttribute("books", new BookDAO().getAllBook());
            request.setAttribute("selectedBookId", bookId);
            request.getRequestDispatcher("/WEB-INF/inventory/report.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/inventory?view=list");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //HttpSession session = request.getSession();
        String view = request.getParameter("view");

        if ("in".equals(view)) {
            String txnCode = request.getParameter("txnCode");
            String note = request.getParameter("note");

            String[] bookIds = request.getParameterValues("bookId");
            String[] newTitles = request.getParameterValues("newBookTitle");
            String[] newAuthors = request.getParameterValues("newBookAuthor");
            String[] newPublishers = request.getParameterValues("newBookPublisher");
            String[] newCategoryIds = request.getParameterValues("newBookCategoryId");
            String[] qtys = request.getParameterValues("qty");
            String[] unitCosts = request.getParameterValues("unitCost");

            List<StockInRequestItem> items = new ArrayList<>();
            if (qtys != null) {
                for (int i = 0; i < qtys.length; i++) {
                    if (qtys[i] == null || qtys[i].isBlank()) continue;

                    int qty;
                    try {
                        qty = Integer.parseInt(qtys[i]);
                    } catch (NumberFormatException ex) {
                        continue;
                    }
                    if (qty <= 0) continue;

                    Integer selectedBookId = null;
                    if (bookIds != null && bookIds.length > i && bookIds[i] != null && !bookIds[i].isBlank()) {
                        try {
                            selectedBookId = Integer.parseInt(bookIds[i]);
                        } catch (NumberFormatException ignored) {
                        }
                    }

                    String newTitle = null;
                    if (newTitles != null && newTitles.length > i && newTitles[i] != null) {
                        newTitle = newTitles[i].trim();
                        if (newTitle.isEmpty()) {
                            newTitle = null;
                        }
                    }

                    String newAuthor = null;
                    if (newAuthors != null && newAuthors.length > i && newAuthors[i] != null) {
                        newAuthor = newAuthors[i].trim();
                        if (newAuthor.isEmpty()) {
                            newAuthor = null;
                        }
                    }

                    String newPublisher = null;
                    if (newPublishers != null && newPublishers.length > i && newPublishers[i] != null) {
                        newPublisher = newPublishers[i].trim();
                        if (newPublisher.isEmpty()) {
                            newPublisher = null;
                        }
                    }

                    Integer newCategoryId = null;
                    if (newCategoryIds != null && newCategoryIds.length > i && newCategoryIds[i] != null && !newCategoryIds[i].isBlank()) {
                        try {
                            newCategoryId = Integer.parseInt(newCategoryIds[i]);
                        } catch (NumberFormatException ignored) {
                        }
                    }

                    if (selectedBookId == null && newTitle == null) {
                        continue;
                    }

                    StockInRequestItem it = new StockInRequestItem();
                    it.setBookId(selectedBookId);
                    it.setNewBookTitle(newTitle);
                    it.setNewBookAuthor(newAuthor);
                    it.setNewBookPublisher(newPublisher);
                    it.setNewBookCategoryId(newCategoryId);
                    it.setQty(qty);

                    if (unitCosts != null && unitCosts.length > i && unitCosts[i] != null && !unitCosts[i].isBlank()) {
                        try {
                            it.setUnitCost(Double.valueOf(unitCosts[i]));
                        } catch (NumberFormatException ignored) {
                        }
                    }
                    items.add(it);
                }
            }

            if (items.isEmpty()) {
                request.setAttribute("error", "Bạn cần nhập ít nhất 1 dòng sản phẩm (chọn sách hoặc nhập tên sách mới).\n");
                loadBooks(request);
                loadCategories(request);
                request.getRequestDispatcher("/WEB-INF/inventory/in.jsp").forward(request, response);
                return;
            }

            StockInRequest reqModel = new StockInRequest();
            reqModel.setRequestCode(txnCode == null || txnCode.isBlank() ? genRequestCode() : txnCode.trim());
            reqModel.setNote(note);
            reqModel.setStatus("PENDING");

            Object userObj = request.getSession().getAttribute("user");
            if (userObj instanceof Account) {
                Account acc = (Account) userObj;
                if (!"Customer".equalsIgnoreCase(acc.getRole())) {
                    reqModel.setRequestedByStaffId(acc.getId());
                }
            }
            reqModel.setItems(items);

            try {
                int requestId = new StockInRequestDAO().createPendingRequest(reqModel);
                response.sendRedirect(request.getContextPath() + "/inventory?view=in&msg=requested&id=" + requestId);
            } catch (Exception ex) {
                request.setAttribute("error", ex.getMessage());
                loadBooks(request);
                loadCategories(request);
                request.getRequestDispatcher("/WEB-INF/inventory/in.jsp").forward(request, response);
            }
            return;
        }

        response.sendRedirect(request.getContextPath() + "/inventory");
    }

    private void loadBooks(HttpServletRequest request) {
        List<Book> books = new BookDAO().getAllBook();
        request.setAttribute("books", books);
    }

    private void loadCategories(HttpServletRequest request) {
        request.setAttribute("categories", new CategoryDAO().getAllCategories());
    }

    private String genRequestCode() {
        return "REQ-" + System.currentTimeMillis();
    }
}
