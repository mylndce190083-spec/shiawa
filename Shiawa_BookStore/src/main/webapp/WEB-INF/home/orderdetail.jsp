<%-- 
    Document   : orderdetail
    Created on : Mar 1, 2026, 5:52:12 PM
    Author     : MY
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            .product-card{
                display:flex;
                justify-content:space-between;
                padding:15px 0;
                border-bottom:1px solid #eee;
            }

            .product-left{
                display:flex;
                gap:15px;
            }

            .product-left img{
                width:85px;
                height:110px;
                object-fit:cover;
                border-radius:8px;
            }

            .product-info{
                display:flex;
                flex-direction:column;
                justify-content:center;
            }

            .product-title{
                font-weight:600;
                margin-bottom:6px;
            }

            .product-qty{
                color:#666;
                font-size:14px;
            }

            .product-price{
                font-weight:600;
                color:#444;
                align-self:center;
            }

            .summary{
                margin-top:15px;
                padding-top:15px;
                border-top:2px solid #f0f0f0;
            }

            .summary-row{
                display:flex;
                justify-content:space-between;
                margin-bottom:8px;
                font-size:14px;
            }

            .summary-total{
                display:flex;
                justify-content:space-between;
                font-size:20px;
                font-weight:bold;
                color:#d32f2f;
                margin-top:10px;
            }
        </style>
    </head>
    <body>

        <div class="detail-container">

            <div class="detail-header">
                Đơn hàng 
                <c:choose>
                    <c:when test="${order.status == 'PENDING'}">
                        chờ xác nhận
                    </c:when>

                    <c:when test="${order.status == 'SHIPPING'}">
                        đang giao 
                    </c:when>

                    <c:when test="${order.status == 'DELIVERED'}">
                        đã giao
                    </c:when>

                    <c:when test="${order.status == 'FAILED'}">
                        đã hủy
                    </c:when>

                    <c:otherwise>
                        ${order.status}
                    </c:otherwise>
                </c:choose>
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
                    <div class="product-card">

                        <div class="product-left">
                            <img src="${pageContext.request.contextPath}/image?file=${item.url_img}" />

                            <div class="product-info">
                                <div class="product-title">
                                    ${item.title}
                                </div>
                                <div class="product-qty">
                                    Số lượng: ${item.quantity}
                                </div>
                            </div>
                        </div>

                        <div class="product-price">
                            <fmt:formatNumber value="${item.price * item.quantity}" type="number"/> VND
                        </div>

                    </div>

                </c:forEach>
                <div class="payment-method">
                    <b>Phương thức thanh toán:</b>

                    <c:choose>
                        <c:when test="${order.paymentMethod== 'ONLINE'}">
                            💳 Thanh toán qua VNPAY
                        </c:when>
                        <c:when test="${order.paymentMethod == 'COD'}">
                            🚚  Thanh toán khi nhận hàng
                        </c:when>

                    </c:choose>

                </div>
                <c:if test="${order.status == 'FAILED' && order.paymentMethod == 'ONLINE'}">
                    <div style="color:red; margin-top:10px;">
                        Đơn hàng đã hủy. Tiền sẽ được hoàn lại trong 3-5 ngày làm việc.
                    </div>
                </c:if>
                <c:set var="subtotal" value="0" />

                <c:forEach var="item" items="${order.items}">
                    <c:set var="subtotal"
                           value="${subtotal + (item.price * item.quantity)}" />
                </c:forEach>

                <div class="summary">

                    <div class="summary-row">
                        <span>Tổng tiền hàng</span>
                        <span>
                            <fmt:formatNumber value="${subtotal}" type="number"/> VND
                        </span>
                    </div>

                    <div class="summary-row">
                        <span>Phí vận chuyển</span>
                        <span>
                            + <fmt:formatNumber value="${order.shippingFee}" type="number"/> VND
                        </span>
                    </div>

                    <div class="summary-row">
                        <span>Voucher</span>
                        <span>
                            - <fmt:formatNumber value="${order.discount}" type="number"/> %
                        </span>
                    </div>

                    <div class="summary-total">
                        <span>Thành tiền</span>
                        <span>
                            <fmt:formatNumber 
                                value="${subtotal + order.shippingFee - subtotal*order.discount/100}" 
                                type="number"/> VND
                        </span>
                    </div>

                </div>
            </div>





        </div>
    </body>
</html>
