<%-- 
    Document   : orderlist
    Created on : Feb 28, 2026, 2:28:57 PM
    Author     : MY
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Đơn hàng của tôi</title>
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <style>
            body{
                background:#f5f5f5;
            }
            .order-card{
                background:white;
                padding:20px;
                margin-bottom:20px;
                border-radius:10px;
                box-shadow:0 2px 8px rgba(0,0,0,0.08);
            }
            .status{
                font-weight:bold;
            }
            .status-pending{
                color:orange;
            }
            .status-shipping{
                color:#007bff;
            }
            .status-completed{
                color:green;
            }
            .status-cancelled{
                color:red;
            }
            .tab-link{
                margin-right:15px;
                text-decoration:none;
                font-weight:500;
            }
            .tab-link.active{
                color:red;
                border-bottom:2px solid red;
            }
        </style>
    </head>
    <body>

        <div class="container mt-5">

            <h3 class="mb-4">Đơn hàng của tôi</h3>

            <!-- Tabs lọc trạng thái -->
            <div class="mb-4">
                <a href="order?status=ALL" class="tab-link">Tất cả</a>
                <a href="order?status=PENDING" class="tab-link">Chờ xác nhận</a>
                <a href="order?status=SHIPPING" class="tab-link">Đang giao</a>
                <a href="order?status=COMPLETED" class="tab-link">Hoàn thành</a>
                <a href="order?status=CANCELLED" class="tab-link">Đã hủy</a>
            </div>

            <c:if test="${empty orders}">
                <div class="alert alert-info">Không có đơn hàng nào.</div>
            </c:if>

            <c:forEach var="o" items="${orders}">
                <div class="order-card">

                    <div class="d-flex justify-content-between">
                        <div>
                            <strong>Mã đơn:</strong> #${o.orderId} <br>
                            <strong>Ngày đặt:</strong> ${o.orderDate}
                        </div>

                        <div class="status 
                             <c:choose>
                             <c:when test="${o.status == 'PENDING'}">status-pending</c:when>
                            <c:when test="${o.status == 'SHIPPING'}">status-shipping</c:when>
                            <c:when test="${o.status == 'COMPLETED'}">status-completed</c:when>
                            <c:when test="${o.status == 'CANCELLED'}">status-cancelled</c:when>
                            </c:choose>">

                            <c:choose>
                                <c:when test="${o.status == 'PENDING'}">Chờ xác nhận</c:when>
                                <c:when test="${o.status == 'SHIPPING'}">Đang giao</c:when>
                                <c:when test="${o.status == 'COMPLETED'}">Hoàn thành</c:when>
                                <c:when test="${o.status == 'CANCELLED'}">Đã hủy</c:when>
                            </c:choose>

                        </div>
                    </div>

                    <hr>

                    <div>
                        <strong>Tổng tiền:</strong> ${o.totalAmount} VND
                    </div>

                    <!-- Nút hủy chỉ hiện khi PENDING -->
                    <c:if test="${o.status == 'PENDING'}">
                        <div class="mt-3 text-end">
                            <form action="orderList" method="post">
                                <input type="hidden" name="action" value="cancel"/>
                                <input type="hidden" name="orderId" value="${o.orderId}"/>
                                <button type="submit" class="btn btn-danger">
                                    Hủy đơn
                                </button>
                            </form>
                        </div>
                    </c:if>

                </div>
            </c:forEach>

        </div>

    </body>
</html>
