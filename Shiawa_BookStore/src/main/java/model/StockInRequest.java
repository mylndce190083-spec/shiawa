package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StockInRequest {
    private int requestId;
    private String requestCode;
    private String note;
    private String status; 
    private Integer requestedByStaffId;
    private Integer approvedByStaffId;
    private String requestedByStaffName;
    private String rejectReason;
    private LocalDateTime createdAt;
    private LocalDateTime approvedAt;
    private List<StockInRequestItem> items = new ArrayList<>();

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRequestedByStaffId() {
        return requestedByStaffId;
    }

    public void setRequestedByStaffId(Integer requestedByStaffId) {
        this.requestedByStaffId = requestedByStaffId;
    }

    public Integer getApprovedByStaffId() {
        return approvedByStaffId;
    }

    public void setApprovedByStaffId(Integer approvedByStaffId) {
        this.approvedByStaffId = approvedByStaffId;
    }

    public String getRequestedByStaffName() {
        return requestedByStaffName;
    }

    public void setRequestedByStaffName(String requestedByStaffName) {
        this.requestedByStaffName = requestedByStaffName;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }

    public List<StockInRequestItem> getItems() {
        return items;
    }

    public void setItems(List<StockInRequestItem> items) {
        this.items = items;
    }
}
