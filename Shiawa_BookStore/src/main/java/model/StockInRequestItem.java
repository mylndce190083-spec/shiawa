package model;

public class StockInRequestItem {
    private int itemId;
    private int requestId;
    private Integer bookId; // nullable when new book
    private String newBookTitle; // nullable
    private String newBookAuthor;
    private String newBookPublisher;
    private Integer newBookCategoryId;
    private int qty;
    private Double unitCost;

    // display helper
    private String bookTitle;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getNewBookTitle() {
        return newBookTitle;
    }

    public void setNewBookTitle(String newBookTitle) {
        this.newBookTitle = newBookTitle;
    }

    public String getNewBookAuthor() {
        return newBookAuthor;
    }

    public void setNewBookAuthor(String newBookAuthor) {
        this.newBookAuthor = newBookAuthor;
    }

    public String getNewBookPublisher() {
        return newBookPublisher;
    }

    public void setNewBookPublisher(String newBookPublisher) {
        this.newBookPublisher = newBookPublisher;
    }

    public Integer getNewBookCategoryId() {
        return newBookCategoryId;
    }

    public void setNewBookCategoryId(Integer newBookCategoryId) {
        this.newBookCategoryId = newBookCategoryId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
}
