<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerAdmin.jsp" %>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h6 class="mb-0">Chat session #${sessionId}</h6>
            <a class="btn btn-secondary btn-sm" href="${pageContext.request.contextPath}/support-admin">Back</a>
        </div>

        <div style="height: 380px; overflow-y: auto; border: 1px solid #ddd; border-radius: 8px; padding: 12px; background: #fff;">
            <c:forEach var="m" items="${messages}">
                <div style="margin-bottom: 10px; text-align: ${m.sender == 'staff' ? 'right' : 'left'};">
                    <div style="display: inline-block; max-width: 75%; padding: 10px 12px; border-radius: 10px; background: ${m.sender == 'staff' ? '#d1e7dd' : '#f8d7da'};">
                        <div style="font-size: 12px; color: #666; margin-bottom: 4px;">${m.sender}</div>
                        <div>${m.content}</div>
                        <div style="font-size: 11px; color: #999; margin-top: 4px;">${m.sentAt}</div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <form class="mt-3" method="post" action="${pageContext.request.contextPath}/support-admin">
            <input type="hidden" name="view" value="reply"/>
            <input type="hidden" name="sessionId" value="${sessionId}"/>
            <div class="input-group">
                <input class="form-control" name="content" placeholder="Nhập phản hồi cho khách hàng..." required />
                <button class="btn btn-success" type="submit">Submit</button>
            </div>
        </form>
    </div>
</div>

<%@include file="../include/footerAdmin.jsp" %>
