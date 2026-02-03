package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ImportReceipt {
    private int receiptId;
    private Integer staffId;
    private Integer supplierId;
    private LocalDateTime importDate;
    private Double totalAmount;
    private String note;

    private String supplierName; // for display
    private List<ImportReceiptDetail> details = new ArrayList<>();

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public LocalDateTime getImportDate() {
        return importDate;
    }

    public void setImportDate(LocalDateTime importDate) {
        this.importDate = importDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public List<ImportReceiptDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ImportReceiptDetail> details) {
        this.details = details;
    }
}


