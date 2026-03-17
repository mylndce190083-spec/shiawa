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
import java.util.Comparator;
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
//        HttpSession session = request.getSession();
//        Account user = (Account) session.getAttribute("user");
//
//        // 1. Check đăng nhập + role
//        if (user == null || !"Inventory".equalsIgnoreCase(user.getRole())) {
//            response.sendRedirect(request.getContextPath() + "/login");
//            return;
//        }
        String view = request.getParameter("view");

        if ("list".equals(view)) {
            String minStockStr = request.getParameter("minStock");
            String maxStockStr = request.getParameter("maxStock");
            String sort = request.getParameter("sort");
            int page = parsePage(request.getParameter("page"));
            int pageSize = 10;

            Integer minStock = parseInteger(minStockStr);
            Integer maxStock = parseInteger(maxStockStr);

            BookDAO dao = new BookDAO();
            List<BookAdmin> allBooks = dao.getAllBooksInfo();
            List<BookAdmin> filtered = new ArrayList<>();

            for (BookAdmin b : allBooks) {
                boolean ok = true;
                if (minStock != null && b.getStock() < minStock) ok = false;
                if (maxStock != null && b.getStock() > maxStock) ok = false;
                if (ok) filtered.add(b);
            }

            if ("stock_desc".equalsIgnoreCase(sort)) {
                filtered.sort(Comparator.comparingInt(BookAdmin::getStock).reversed());
            } else if ("id".equalsIgnoreCase(sort)) {
                filtered.sort(Comparator.comparingInt(BookAdmin::getBookId));
            } else {
                filtered.sort(Comparator.comparingInt(BookAdmin::getStock));
            }

            int totalBooks = filtered.size();
            int totalPages = (int) Math.ceil((double) totalBooks / pageSize);
            if (totalPages <= 0) totalPages = 1;
            if (page > totalPages) page = totalPages;

            int fromIndex = (page - 1) * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, totalBooks);
            List<BookAdmin> list = totalBooks == 0
                    ? new java.util.ArrayList<>()
                    : filtered.subList(fromIndex, toIndex);

            request.setAttribute("bookList", list);
            request.setAttribute("minStock", minStockStr == null ? "" : minStockStr);
            request.setAttribute("maxStock", maxStockStr == null ? "" : maxStockStr);
            request.setAttribute("sort", sort == null ? "" : sort);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.getRequestDispatcher("/WEB-INF/inventory/list.jsp").forward(request, response);
            return;
        }

        if ("in".equals(view)) {
            loadBooks(request);
            loadCategories(request);
            request.getRequestDispatcher("/WEB-INF/inventory/in.jsp").forward(request, response);
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

        if ("history".equals(view)) {
            Integer staffId = getStaffId(request);

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

        if ("history-detail".equals(view)) {
            Integer staffId = getStaffId(request);
            int id = parsePage(request.getParameter("id"));

            StockInRequest found = null;
            if (id > 0) {
                List<StockInRequest> requests = new StockInRequestDAO().getRequestsWithItems(staffId);
                for (StockInRequest r : requests) {
                    if (r.getRequestId() == id) {
                        found = r;
                        break;
                    }
                }
            }

            request.setAttribute("request", found);
            request.getRequestDispatcher("/WEB-INF/inventory/history-detail.jsp").forward(request, response);
            return;
        }


        if ("report".equals(view)) {
            LocalDate to = parseDate(request.getParameter("to"), LocalDate.now());
            LocalDate from = parseDate(request.getParameter("from"), to.minusDays(7));
            Integer bookId = parseInteger(request.getParameter("bookId"));

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
        String view = request.getParameter("view");

        if ("in".equals(view)) {
            List<StockInRequestItem> items = buildStockInItems(request);

            if (items.isEmpty()) {
                request.setAttribute("error", "Bạn cần nhập ít nhất 1 dòng sản phẩm (chọn sách hoặc nhập tên sách mới).\n");
                loadBooks(request);
                loadCategories(request);
                request.getRequestDispatcher("/WEB-INF/inventory/in.jsp").forward(request, response);
                return;
            }

            StockInRequest reqModel = new StockInRequest();
            reqModel.setRequestCode(getRequestCode(request.getParameter("txnCode")));
            reqModel.setNote(request.getParameter("note"));
            reqModel.setStatus("PENDING");
            reqModel.setRequestedByStaffId(getStaffId(request));
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

    private String getRequestCode(String input) {
        if (input == null || input.isBlank()) {
            return genRequestCode();
        }
        return input.trim();
    }

    private LocalDate parseDate(String value, LocalDate fallback) {
        if (value == null || value.isBlank()) return fallback;
        try {
            return LocalDate.parse(value);
        } catch (Exception ignored) {
            return fallback;
        }
    }

    private Integer parseInteger(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    private int parsePage(String value) {
        try {
            int page = Integer.parseInt(value);
            return page > 0 ? page : 1;
        } catch (Exception ignored) {
            return 1;
        }
    }

    private Integer getStaffId(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute("user");
        if (userObj instanceof Account) {
            Account acc = (Account) userObj;
            if (!"Customer".equalsIgnoreCase(acc.getRole())) {
                return acc.getId();
            }
        }
        return null;
    }

    private List<StockInRequestItem> buildStockInItems(HttpServletRequest request) {
        String[] bookIds = request.getParameterValues("bookId");
        String[] newTitles = request.getParameterValues("newBookTitle");
        String[] newAuthors = request.getParameterValues("newBookAuthor");
        String[] newPublishers = request.getParameterValues("newBookPublisher");
        String[] newCategoryIds = request.getParameterValues("newBookCategoryId");
        String[] qtys = request.getParameterValues("qty");
        String[] unitCosts = request.getParameterValues("unitCost");

        List<StockInRequestItem> items = new ArrayList<>();
        if (qtys == null) return items;

        for (int i = 0; i < qtys.length; i++) {
            Integer qty = parseInteger(qtys[i]);
            if (qty == null || qty <= 0) continue;

            Integer selectedBookId = parseInteger(getValueAt(bookIds, i));
            String newTitle = trimToNull(getValueAt(newTitles, i));
            String newAuthor = trimToNull(getValueAt(newAuthors, i));
            String newPublisher = trimToNull(getValueAt(newPublishers, i));
            Integer newCategoryId = parseInteger(getValueAt(newCategoryIds, i));

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

            Double unitCost = parseDouble(getValueAt(unitCosts, i));
            if (unitCost != null) {
                it.setUnitCost(unitCost);
            }
            items.add(it);
        }

        return items;
    }

    private String trimToNull(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private String getValueAt(String[] arr, int index) {
        if (arr == null || index < 0 || index >= arr.length) {
            return null;
        }
        return arr[index];
    }

    private Double parseDouble(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }
}
