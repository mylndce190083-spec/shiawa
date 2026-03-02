<%-- 
    Document   : order-success
    Created on : Feb 25, 2026, 8:43:20 AM
    Author     : MY
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Đặt hàng thành công</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background: #E8F5E9;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            .success-box {
                background: white;
                padding: 40px 60px;
                border-radius: 12px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
                text-align: center;
            }

            .success-box h1 {
                color: #28a745;
                margin-bottom: 15px;
            }

            .success-box p {
                font-size: 16px;
                color: #555;
                margin-bottom: 25px;
            }

            .btn {
                padding: 10px 20px;
                border-radius: 6px;
                text-decoration: none;
                font-weight: bold;
                margin: 5px;
                display: inline-block;
            }

            .btn-home {
                background: #007bff;
                color: white;
            }

            .btn-order {
                background: #28a745;
                color: white;
            }

            .btn:hover {
                opacity: 0.85;
            }
        </style>
    </head>
    <body>

        <div class="success-box">
            <h1>🎉 Đặt hàng thành công!</h1>

            <p>Cảm ơn bạn đã mua hàng tại cửa hàng của chúng tôi.</p>

            <!-- Nếu có truyền orderId từ Servlet -->
            <c:if test="${not empty orderId}">
                <p>Mã đơn hàng của bạn là: <strong>${orderId}</strong></p>
            </c:if>

            <a href="${pageContext.request.contextPath}/home" class="btn btn-home">
                🏠 Về trang chủ
            </a>

            <a href="${pageContext.request.contextPath}/OrderList" class="btn btn-order">
                📦 Xem đơn hàng
            </a>
        </div>

    </body>
</html>
