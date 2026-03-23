<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Customer Support</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/css.css" />
</head>
<body>
    <div style="max-width: 760px; margin: 40px auto; background: #fff; border-radius: 12px; padding: 24px; border: 1px solid #ddd;">
        <h3 style="margin-bottom: 16px;">Customer Support</h3>

        <div style="height: 380px; overflow-y: auto; border: 1px solid #ddd; border-radius: 8px; padding: 12px; background: #fafafa;">
            <c:if test="${empty messages}">
                <div style="color:#777;">Chưa có tin nhắn nào. Hãy bắt đầu cuộc trò chuyện.</div>
            </c:if>

            <c:forEach var="m" items="${messages}">
                <div style="margin-bottom: 10px; text-align: ${m.sender == 'Support Bot' || m.sender == 'staff' ? 'left' : 'right'};">
                    <div style="display: inline-block; max-width: 75%; padding: 10px 12px; border-radius: 10px; background: ${m.sender == 'Support Bot' ? '#e2e3ff' : (m.sender == 'staff' ? '#f8d7da' : '#d1e7dd')};">
                        <div style="font-size: 12px; color: #666; margin-bottom: 4px;">${m.sender}</div>
                        <div>${m.content}</div>
                        <div style="font-size: 11px; color: #999; margin-top: 4px;">${m.sentAt}</div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <form class="mt-3" method="post" action="${pageContext.request.contextPath}/chat">
            <div class="input-group">
                <input class="form-control" name="message" placeholder="Nhập tin nhắn..." required />
                <button class="btn btn-success" type="submit">Gửi</button>
            </div>
        </form>
    </div>
</body>
</html>
