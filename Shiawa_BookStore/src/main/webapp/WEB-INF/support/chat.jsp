<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Customer Support</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/css.css" />
    <style>
body {
    background: #f4f7f6;
    font-family: 'Segoe UI', sans-serif;
}

/* Container */
.chat-container {
    max-width: 800px;
    margin: 40px auto;
    background: #fff;
    border-radius: 15px;
    display: flex;
    flex-direction: column;
    height: 80vh;
    box-shadow: 0 8px 25px rgba(0,0,0,0.1);
    overflow: hidden;
}

/* Header */
.chat-header {
    background: #00a651;
    color: #fff;
    padding: 15px;
    font-weight: bold;
    text-align: center;
}

/* Message area */
.chat-body {
    flex: 1;
    padding: 15px;
    overflow-y: auto;
    background: #f9f9f9;
}

/* Message wrapper */
.message {
    display: flex;
    margin-bottom: 12px;
}

.message.left {
    justify-content: flex-start;
}

.message.right {
    justify-content: flex-end;
}

/* Bubble */
.bubble {
    max-width: 70%;
    padding: 10px 14px;
    border-radius: 15px;
    font-size: 14px;
    position: relative;
}

/* Different roles */
.bubble.user {
    background: #d1e7dd;
}

.bubble.staff {
    background: #f8d7da;
}

.bubble.bot {
    background: #e2e3ff;
}

/* Sender */
.sender {
    font-size: 11px;
    color: #666;
    margin-bottom: 4px;
}

/* Time */
.time {
    font-size: 10px;
    color: #999;
    margin-top: 4px;
    text-align: right;
}

/* Input */
.chat-footer {
    padding: 10px;
    border-top: 1px solid #ddd;
    display: flex;
    gap: 10px;
}

.chat-footer input {
    flex: 1;
    border-radius: 20px;
    border: 1px solid #ccc;
    padding: 10px 15px;
}

.chat-footer button {
    background: #00a651;
    color: #fff;
    border: none;
    border-radius: 20px;
    padding: 10px 20px;
}
</style>
    </head>
    <body>
        <jsp:include page="/client/layout/header.jsp"/>
        <div class="chat-container">

            <div class="chat-header">
                💬 Customer Support
            </div>

            <div class="chat-body">
                <c:if test="${empty messages}">
                    <div style="text-align:center; color:#777;">
                        No messages received yet.
                    </div>
                </c:if>

                <c:forEach var="m" items="${messages}">

                    <c:set var="isStaff" value="${m.sender == 'staff'}"/>
                    <c:set var="isBot" value="${m.sender == 'Support Bot'}"/>
                    <c:set var="isUser" value="${!isStaff && !isBot}"/>

                    <div class="message ${isUser ? 'right' : 'left'}">

                        <div class="bubble 
                             ${isUser ? 'user' : (isStaff ? 'staff' : 'bot')}">

                            <div class="sender">${m.sender}</div>

                            <div>${m.content}</div>

                            <div class="time">${m.sentAt}</div>

                        </div>
                    </div>

                </c:forEach>
            </div>

            <form method="post" action="${pageContext.request.contextPath}/chat" class="chat-footer">
                <input name="message" placeholder="Nhập tin nhắn..." required />
                <button type="submit">Send</button>
            </form>

        </div
    </body>
</html>
