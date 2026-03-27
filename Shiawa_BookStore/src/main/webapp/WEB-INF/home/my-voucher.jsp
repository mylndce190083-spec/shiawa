
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
            body {
                background: #f5f5f5;
                font-family: Arial;
                padding: 20px;
            }

            h2 {
                text-align: center;
                color: #2e7d32;
            }

            h3 {
                margin-top: 30px;
                color: #2e7d32;
            }

            .voucher-card {
                background: #fff;
                border: 2px dashed #4caf50;
                border-radius: 12px;
                padding: 15px 20px;
                margin: 15px auto;
                max-width: 600px;
                position: relative;
                transition: 0.3s;
            }

            .voucher-card:hover {
                transform: translateY(-3px);
                box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            }

            .voucher-card::before {
                content: "";
                width: 6px;
                height: 100%;
                background: #4caf50;
                position: absolute;
                left: 0;
                top: 0;
            }

            .active {
                background: #e8f5e9;
            }

            .expired {
                background: #f5f5f5;
                color: gray;
                border-color: #ccc;
            }

            .title {
                font-size: 18px;
                font-weight: bold;
                color: #2e7d32;
            }
            /* Card */
            .voucher-card {
                display: flex;
                align-items: center;
                background: #fff;
                border-radius: 12px;
                overflow: hidden;
                margin: 15px auto;
                max-width: 600px;

                box-shadow: 0 4px 12px rgba(0,0,0,0.08);
                transition: 0.3s;
            }

            /* Hover */
            .voucher-card:hover {
                transform: translateY(-4px);
            }

            /* LEFT (discount block) */
            .left {
                background: #4caf50;
                color: #fff;
                width: 100px;
                text-align: center;
                padding: 15px 10px;
            }

            .discount {
                font-size: 24px;
                font-weight: bold;
            }

            .label {
                font-size: 12px;
            }

            /* RIGHT */
            .right {
                flex: 1;
                padding: 12px 16px;
            }

            .title {
                font-size: 16px;
                font-weight: bold;
                color: #2e7d32;
                margin-bottom: 5px;
            }

            .expire {
                font-size: 13px;
                color: #777;
            }

            .status {
                font-size: 13px;
                color: #4caf50;
                margin-top: 5px;
            }

            /* ACTIVE */
            .active {
                border-left: 4px solid #4caf50;
            }
            /* Title chính */
            .main-title {
                text-align: center;
                font-size: 28px;
                font-weight: bold;
                color: #2e7d32;
                margin-bottom: 30px;
            }

            /* Section title */
            .section-title {
                max-width: 600px;
                margin: 30px auto 10px;
                font-size: 18px;
                font-weight: bold;
                position: relative;
                padding-bottom: 5px;
            }

            /* Line dưới */
            .section-title::after {
                content: "";
                width: 100%;
                height: 2px;
                position: absolute;
                left: 0;
                bottom: 0;
            }

            /* Active */
            .active-title {
                color: #2e7d32;
            }

            .active-title::after {
                background: #4caf50;
            }

            /* Expired */
            .expired-title {
                color: gray;
            }

            .expired-title::after {
                background: #ccc;
            }
        </style>
    </head>
    <body>
          <jsp:include page="/client/layout/header.jsp"/>
        <h2 class="main-title">🎟 Voucher của bạn</h2>

        <!-- ACTIVE VOUCHERS  -->
        <h3 class="section-title active-title">🟢 Voucher còn hiệu lực</h3>

        <c:forEach var="v" items="${myVoucherList}">
            <c:if test="${v.endedAt >= now}">
                <div class="voucher-card active">

                    <div class="left">
                        <div class="discount">${v.discount}%</div>
                        <div class="label">OFF</div>
                    </div>

                    <div class="right">
                        <div class="title">${v.name}</div>
                        <div class="expire">Hết hạn: ${v.endedAt}</div>
                        <div class="status">Trạng thái: ${v.status}</div>
                    </div>

                </div>
            </c:if>
        </c:forEach>

        <!-- EXPIRED VOUCHERS -->
        <h3 class="section-title expired-title">⚫ Voucher đã hết hạn (trong 3 ngày)</h3>

        <c:forEach var="v" items="${myVoucherList}">
            <c:if test="${v.endedAt < now}">
                <div class="voucher-card expired">

                    <div class="left expired-left">
                        <div class="discount">${v.discount}%</div>
                        <div class="label">OFF</div>
                    </div>

                    <div class="right">
                        <div class="title">${v.name}</div>
                        <div class="expire">Hết hạn: ${v.endedAt}</div>
                        <div class="status expired-text">Đã hết hạn</div>
                    </div>

                </div>
            </c:if>
        </c:forEach>

    </body>
</html>
