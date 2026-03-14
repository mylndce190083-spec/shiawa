package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StockTxn {
    private int txnId;
    private String txnType; // IN | OUT | ADJUST
    private String txnCode;
    private LocalDateTime txnDate;
    private Integer supplierId; // nullable
    private String note;
    private Integer createdByStaffId; // nullable

    private List<StockTxnItem> items = new ArrayList<>();

    public StockTxn() {
    }

    public int getTxnId() {
        return txnId;
    }

    public void setTxnId(int txnId) {
        this.txnId = txnId;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getTxnCode() {
        return txnCode;
    }

    public void setTxnCode(String txnCode) {
        this.txnCode = txnCode;
    }

    public LocalDateTime getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(LocalDateTime txnDate) {
        this.txnDate = txnDate;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getCreatedByStaffId() {
        return createdByStaffId;
    }

    public void setCreatedByStaffId(Integer createdByStaffId) {
        this.createdByStaffId = createdByStaffId;
    }

    public List<StockTxnItem> getItems() {
        return items;
    }

    public void setItems(List<StockTxnItem> items) {
        this.items = items;
    }
}




