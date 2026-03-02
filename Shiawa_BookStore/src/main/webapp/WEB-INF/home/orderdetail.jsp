<%-- 
    Document   : orderdetail
    Created on : Mar 1, 2026, 5:52:12 PM
    Author     : MY
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thông tin đơn hàng</title>
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <style>
            body{
                background:#E8F5E9;
                font-family:Arial;
            }

            .detail-container{
                max-width:700px;
                margin:30px auto;
            }

            .detail-header{
                background:#4CAF50;
                color:white;
                padding:15px;
                border-radius:10px 10px 0 0;
                font-weight:bold;
            }

            .section{
                background:white;
                padding:15px;
                margin-bottom:15px;
                border-radius:10px;
            }

            .product-item{
                display:flex;
                gap:15px;
                align-items:center;
                margin-bottom:10px;
            }

            .total{
                font-size:18px;
                font-weight:bold;
                color:red;
            }
        </style>
    </head>
    <body>

        <div class="detail-container">

            <div class="detail-header">
                Đơn hàng ${order.status}
            </div>

            <div class="section">
                <h4>Thông tin đặt hàng</h4>
                <p><b>Thời gian:</b> ${order.orderDate}</p>
                <p><b>Mã đơn:</b> ${order.orderId}</p>
            </div>

            <div class="section">
                <h4>Địa chỉ nhận hàng</h4>
                <p><strong>Người nhận:</strong> ${order.receiverName}</p>
                <p><b>SĐT:</b> ${order.phone}</p>
                <p><b>Địa chỉ:</b> ${order.shippingAddress}</p>
            </div>

            <div class="section">
                <h4>Sản phẩm</h4>

                <c:forEach var="item" items="${order.items}">
                    <div class="product-item">
                        <img src="${item.book.urlImg}" width="80">
                        <div>
                            <div>${item.book.title}</div>
                            <div>Số lượng: ${item.quantity}</div>
                        </div>
                    </div>
                </c:forEach>

            </div>

            <div class="section total">
                Thành tiền: ${order.totalAmount} VNĐ
            </div>

        </div>
    </body>
</html>
