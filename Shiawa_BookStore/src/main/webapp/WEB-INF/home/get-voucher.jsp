
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
    <style>
        /* Background */
        body {
            background: #f5f5f5;
        }

        /* Alert custom */
        .custom-alert {
            max-width: 600px;
            margin: 20px auto;
            border-radius: 10px;
            font-weight: 500;
        }

        /* Empty voucher */
        .empty-voucher {
            max-width: 600px;
            margin: 20px auto;
            text-align: center;
            border-radius: 10px;
        }

        /* Container */
        .voucher-container {
            max-width: 600px;
            margin: 0 auto;
        }

        /* Voucher card */
        .voucher-card {
            background: #fff;
            border: 2px dashed #4caf50;
            border-radius: 12px;
            padding: 15px 20px;
            margin-bottom: 15px;

            display: flex;
            justify-content: space-between;
            align-items: center;

            transition: 0.3s;
            position: relative;
        }

        /* Hover */
        .voucher-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }

        /* Left green strip (giống coupon) */
        .voucher-card::before {
            content: "";
            width: 6px;
            height: 100%;
            background: #4caf50;
            position: absolute;
            left: 0;
            top: 0;
            border-radius: 12px 0 0 12px;
        }

        /* Text */
        .voucher-info {
            font-size: 16px;
            font-weight: 500;
            color: #2e7d32;
        }

        /* Button */
        .voucher-btn {
            background: #2e7d32;
            color: #fff;
            border: none;
            border-radius: 20px;
            padding: 6px 16px;
            font-weight: 500;
            cursor: pointer;
            transition: 0.3s;
        }

        .voucher-btn:hover {
            background: #256428;
        }
        /* Alert đẹp hơn */
        .custom-alert {
            max-width: 600px;
            margin: 20px auto;
            border-radius: 12px;
            padding: 15px 20px;
            font-weight: 500;
            display: flex;
            align-items: center;
            justify-content: space-between;

            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            animation: fadeInDown 0.4s ease;
        }

        /* Icon bên trái */
        .custom-alert::before {
            content: "✔";
            font-weight: bold;
            margin-right: 10px;
        }

        /* Màu theo type */
        .alert-success.custom-alert {
            background: #e8f5e9;
            color: #2e7d32;
            border: 1px solid #4caf50;
        }

        .alert-danger.custom-alert {
            background: #ffebee;
            color: #c62828;
            border: 1px solid #e53935;
        }

        .alert-warning.custom-alert {
            background: #fff8e1;
            color: #f57c00;
            border: 1px solid #ffa000;
        }

        /* Animation */
        @keyframes fadeInDown {
            from {
                opacity: 0;
                transform: translateY(-10px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
    </style>
    <body>
        <jsp:include page="/client/layout/header.jsp"/>
        <br>

        <c:if test="${not empty sessionScope.msg}">
            <div class="alert alert-${sessionScope.msgType} alert-dismissible fade show custom-alert" role="alert">
                <span>
                    <i class="fa-solid fa-circle-check me-2"></i>
                    ${sessionScope.msg}
                </span>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>

            <c:remove var="msg" scope="session"/>
            <c:remove var="msgType" scope="session"/>
        </c:if>

        <c:choose>
            <c:when test="${empty voucherList}">
                <div class="alert alert-info empty-voucher">Không có voucher nào có sẵn.</div>
            </c:when>    
            <c:otherwise>
                <div class="voucher-container">
                    <c:forEach var="v" items="${voucherList}">
                        <div class="voucher-card">
                            <span class="voucher-info">
                                ${v.name} - ${v.discount}%
                            </span>

                            <form action="get-voucher" method="post">
                                <input type="hidden" name="voucherId" value="${v.voucher_id}">
                                <button type="submit" class="voucher-btn">Nhận voucher</button>
                            </form>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </body>
</html>
