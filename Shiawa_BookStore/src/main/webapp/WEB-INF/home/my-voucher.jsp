<%-- 
    Document   : my-voucher
    Created on : Mar 23, 2026, 8:22:38 AM
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Vouchers</title>
        <style>
            .voucher-card {
                border: 1px solid #ccc;
                padding: 12px;
                margin: 10px 0;
                border-radius: 8px;
            }

            .active {
                background-color: #e8f8f5;
            }

            .expired {
                background-color: #f5f5f5;
                color: gray;
            }

            .title {
                font-size: 18px;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <h2>🎟 Voucher của bạn</h2>
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

        <!-- ================= ACTIVE VOUCHERS ================= -->
        <h3>🟢 Voucher còn hiệu lực</h3>

        <c:forEach var="v" items="${myVoucherList}">
            <c:if test="${v.endedAt >= now}">
                <div class="voucher-card active">
                    <div class="title">${v.name}</div>
                    <div>Discount: ${v.discount}%</div>
                    <div>Hết hạn: ${v.endedAt}</div>
                    <div>Status: ${v.status}</div>
                </div>
            </c:if>
        </c:forEach>

        <!-- ================= EXPIRED VOUCHERS ================= -->
        <h3>⚫ Voucher đã hết hạn (trong 3 ngày)</h3>

        <c:forEach var="v" items="${myVoucherList}">
            <c:if test="${v.endedAt < now}">
                <div class="voucher-card expired">
                    <div class="title">${v.name}</div>
                    <div>Discount: ${v.discount}%</div>
                    <div>Hết hạn: ${v.endedAt}</div>
                    <div>Status: EXPIRED</div>
                </div>
            </c:if>
        </c:forEach>

    </body>
</html>
