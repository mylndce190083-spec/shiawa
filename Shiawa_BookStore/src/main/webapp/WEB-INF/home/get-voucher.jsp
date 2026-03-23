<%-- 
    Document   : get-voucher
    Created on : Mar 19, 2026, 6:04:00 PM
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Get voucher</title>
    </head>
    <body>
        <jsp:include page="/client/layout/header.jsp"/>
        <br>
        <%
            java.util.Enumeration<String> attrs = request.getAttributeNames();

            while (attrs.hasMoreElements()) {
                String name = attrs.nextElement();
                Object value = request.getAttribute(name);

                out.println("<h3>Attribute: " + name + "</h3>");

                if (value instanceof java.util.List) {
                    java.util.List list = (java.util.List) value;

                    for (Object item : list) {
                        out.println(item + "<br>");
                    }
                } else {
                    out.println(value + "<br>");
                }
            }
        %>
        <c:if test="${not empty sessionScope.msg}">
            <div class="alert alert-${sessionScope.msgType} alert-dismissible fade show" role="alert">
                ${sessionScope.msg}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>

            <!-- Xóa message sau khi hiển thị -->
            <c:remove var="msg" scope="session"/>
            <c:remove var="msgType" scope="session"/>
        </c:if>
        <c:choose>
            <c:when test="${empty voucherList}">
                <div class="alert alert-info">Không có voucher nào có sẵn.</div>
            </c:when>    
            <c:otherwise>
                <c:forEach var="v" items="${voucherList}">
                    <div>
                        ${v.name} - ${v.discount}

                        <form action="get-voucher" method="post">
                            <input type="hidden" name="voucherId" value="${v.voucher_id}">
                            <button type="submit">Nhận voucher</button>
                        </form>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </body>
</html>
