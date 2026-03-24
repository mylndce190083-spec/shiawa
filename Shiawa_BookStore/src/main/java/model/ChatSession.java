package model;

import java.sql.Timestamp;

public class ChatSession {
    private int sessionId;
    private Integer customerId;
    private Integer staffId;
    private String lastMessage;
    private Timestamp lastSentAt;

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Timestamp getLastSentAt() {
        return lastSentAt;
    }

    public void setLastSentAt(Timestamp lastSentAt) {
        this.lastSentAt = lastSentAt;
    }
}
