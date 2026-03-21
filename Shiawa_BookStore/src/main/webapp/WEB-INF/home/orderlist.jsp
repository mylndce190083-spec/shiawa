<%-- 
    Document   : orderlist
    Created on : Feb 28, 2026, 2:28:57 PM
    Author     : MY
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Đơn hàng của tôi</title>

        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <style>
            body{
                background:#E8F5E9;
                font-family: Arial, sans-serif;
            }

            /* HEADER */
            .page-header{
                background:#4CAF50;
                color:white;
                padding:15px 20px;
                font-size:20px;
                font-weight:bold;
                border-radius:8px;
                margin-bottom:20px;
            }



            /* CARD */
            .order-card{
                background:white;
                padding:20px;
                margin-bottom:20px;
                border-radius:12px;
                box-shadow:0 3px 10px rgba(0,0,0,0.08);
                transition:0.2s;
            }

            .order-card:hover{
                transform:translateY(-3px);
            }

            .status{
                font-weight:bold;
            }

            .status-pending{
                color:#ff9800;
            }

            .status-shipping{
                color:#2196F3;
            }

            .status-completed{
                color:#4CAF50;
            }

            .status-cancelled{
                color:#f44336;
            }

            .btn-danger{
                background:#f44336;
                border:none;
                padding:8px 15px;
                color:white;
                border-radius:6px;
                cursor:pointer;
            }

            .btn-danger:hover{
                background:#d32f2f;
            }
            .tab-container {
                display: flex;
                gap: 25px;
                border-bottom: 2px solid #eee;
                margin-bottom: 25px;
            }

            .tab-link {
                position: relative;
                text-decoration: none;
                color: #555;
                font-weight: 500;
                padding: 10px 0;
                transition: all 0.3s ease;
            }

            /* Hover effect */
            .tab-link:hover {
                color: #e53935;
            }

            /* Active tab */
            .tab-link.active {
                color: #e53935;
                font-weight: bold;
            }
            /* CỘT TÊN */
            .book-info{
                display:flex;
                align-items:center;
                width:40%;
            }

            /* CÁC CỘT KHÁC */
            .col{
                width:20%;
                text-align:center;
                font-size:14px;
            }

            /* GIÁ */
            .price{
                color:#e53935;
                font-weight:600;
            }
            /* Animated underline */
            .tab-link::after {
                content: "";
                position: absolute;
                left: 0;
                bottom: -2px;
                width: 0%;
                height: 3px;
                background-color: #e53935;
                transition: width 0.3s ease;
            }

            .tab-link:hover::after {
                width: 100%;
            }

            .tab-link.active::after {
                width: 100%;
            }
            .order-link{
                text-decoration:none;
                color:inherit;
            }
            .order-item{
                display:flex;
                align-items:center;
                padding:15px;
                margin-bottom:10px;
                border-bottom:1px solid #eee;
            }

            .book-info{
                display:flex;
                align-items:center;
            }

            .book-img{
                width:75px;
                height:100px;
                object-fit:cover;
                border-radius:8px;
                margin-right:15px;
            }

            .book-title{
                font-weight:600;
                font-size:15px;
                margin-bottom:6px;
            }

            .book-quantity{
                font-size:13px;
                color:#666;
            }



            .label{
                color:#555;
            }

            .formula{
                color:#444;
            }

            .equal{
                font-weight:bold;
            }

            .price{
                color:#e53935;
                font-weight:600;
            }
            .order-header{
                display:flex;
                padding:10px 15px;
                background:#f1f1f1;
                font-weight:bold;
                border-radius:8px;
                margin-bottom:10px;
            }
            .pagination-custom {
                text-align: center;
                margin-top: 20px;
            }

            .pagination-custom a {
                display: inline-block;
                padding: 6px 12px;
                margin: 0 4px;
                border: 1px solid #ddd;
                text-decoration: none;
                color: #333;
                border-radius: 5px;
                transition: 0.2s;
            }

            .pagination-custom a:hover {
                background-color: #f0f0f0;
            }

            .pagination-custom a.active {
                background-color: #007bff;
                color: white;
                border-color: #007bff;
            }
            .pagination-custom a.disabled {
                pointer-events: none;
                opacity: 0.4;
            }
            .pagination-custom a:empty {
                display: none;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/client/layout/header.jsp"/>
        <br>
        <div class="container mt-5">
            <div class="page-header">
                Đơn hàng của tôi
            </div>

            <!-- Tabs lọc trạng thái -->
            <div class="tab-container">
                <a href="${pageContext.request.contextPath}/OrderList"
                   class="tab-link ${currentStatus == 'ALL' ? 'active' : ''}">
                    Tất cả
                </a>

                <a href="${pageContext.request.contextPath}/OrderList/pending"
                   class="tab-link ${currentStatus == 'PENDING' ? 'active' :'' }">
                    Chờ xác nhận
                </a>
                <a href="${pageContext.request.contextPath}/OrderList/confirmed"
                   class="tab-link ${currentStatus == 'CONFIRMED' ? 'active' :'' }">
                    Đã xác nhận
                </a>

                <a href="${pageContext.request.contextPath}/OrderList/shipping"
                   class="tab-link ${currentStatus == 'SHIPPING' ? 'active' : ''}">
                    Đang giao
                </a>

                <a href="${pageContext.request.contextPath}/OrderList/delivered"
                   class="tab-link ${currentStatus == 'DELIVERED' ? 'active' : ''}">
                    Đã giao

                </a>
                <a href="${pageContext.request.contextPath}/OrderList/cancel_requested"
                   class="tab-link ${currentStatus == 'CANCEL_REQUESTED' ? 'active' : ''}">
                    Chờ hoàn tiền
                </a>
                <a href="${pageContext.request.contextPath}/OrderList/refunded"
                   class="tab-link ${currentStatus == 'REFUNDED' ? 'active' : ''}">
                    Hoàn tiền
                </a>
                <a href="${pageContext.request.contextPath}/OrderList/failed"
                   class="tab-link ${currentStatus == 'FAILED' ? 'active' : ''}">
                    Đã hủy
                </a>
            </div>

            <c:if test="${empty orders}">
                <div class="alert alert-info">Không có đơn hàng nào.</div>
            </c:if>

            <c:forEach var="o" items="${orders}">

                <a href="${pageContext.request.contextPath}/OrderDetail?id=${o.orderId}"
                   class="order-link">
                    <div style="background:white; padding:20px; margin-bottom:20px; border-radius:10px;">

                        <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:10px;">

                            <div style="font-weight:bold;">
                                Mã đơn: ${o.orderId}
                            </div>

                            <div style="
                                 font-weight:bold;
                                 color: red;
                                 ">
                                ${o.status == 'PENDING' ? 'Chờ xác nhận' :
                                  o.status == 'CANCEL_REQUESTED' ? 'Chờ duyệt hủy' :
                                  o.status == 'REFUNDED' ? 'Đã hoàn tiền' :
                                  o.status == 'CONFIRMED' ? 'Đã xác nhận' :
                                  o.status == 'SHIPPING' ? 'Đang giao' :
                                  o.status == 'DELIVERED' ? 'Hoàn thành' :
                                  o.status == 'FAILED' ? 'Đã hủy' : o.status}
                            </div>

                        </div>

                        <div class="order-header">
                            <div style="width:40%">Tên sách</div>
                            <div class="col">SL</div>
                            <div class="col">Giá</div>
                            <div class="col">Thành tiền</div>
                        </div>
                        <c:forEach var="item" items="${o.items}">

                            <div class="order-item">

                                <!-- TÊN -->
                                <div class="book-info">
                                    <img src="${pageContext.request.contextPath}/image?file=${item.book.urlImg.replace(' ', '%20')}" class="book-img">
                                    <div class="book-title">${item.title}</div>
                                </div>

                                <!-- SỐ LƯỢNG -->
                                <div class="col">
                                    ${item.quantity}x
                                </div>

                                <!-- GIÁ -->
                                <div class="col">
                                    <fmt:formatNumber value="${item.price}" type="number"
                                                      groupingUsed="true" maxFractionDigits="0"/>
                                </div>

                                <!-- THÀNH TIỀN -->
                                <div class="col price">
                                    <fmt:formatNumber value="${item.price * item.quantity}"
                                                      type="number" groupingUsed="true"
                                                      maxFractionDigits="0"/> VND
                                </div>
                            </div>
                            <hr>

                            <c:if test="${o.status == 'DELIVERED'}">

                                <%-- TRƯỜNG HỢP 1: CHƯA ĐÁNH GIÁ (Nút xanh, bấm được) --%>
                                <c:if test="${item.isRated == 'unrated'}">     
                                    <a href="${pageContext.request.contextPath}/feedback?book_id=${item.book.bookId}&order_detail_id=${item.orderDetailId}" 
                                       style="background: #00a651; color: white; text-decoration: none; padding: 6px 15px;
                                       border-radius: 8px; display: inline-block; font-size: 13px; font-weight: 600;">
                                        Đánh giá sản phẩm
                                    </a>
                                </c:if>

                                <%-- TRƯỜNG HỢP 2: ĐÃ ĐÁNH GIÁ (Nút xám, KHÔNG bấm được) --%>
                                <c:if test="${item.isRated == 'rated'}">     
                                    <%-- Đổi từ thẻ <a> sang <span> để mất link và thêm pointer-events: none --%>
                                    <a href="#"  style="background: #888; color: white; padding: 6px 15px;
                                          border-radius: 8px; display: inline-block; font-size: 13px;
                                          font-weight: 600; cursor: not-allowed; pointer-events: none;">
                                        Đã đánh giá sản phẩm 
                                    </a>
                                </c:if>

                            </c:if>

                        </c:forEach>
                        <div style="text-align:right; font-size:18px; font-weight:bold; color:black;">
                            Tổng số tiền( ${o.quantity} sản phẩm):
                            <fmt:formatNumber value="${o.totalAmount}" type="number" groupingUsed="true" maxFractionDigits="0"/> VND
                        </div>


                        <c:if test="${o.status eq 'PENDING'}">
                            <form action="${pageContext.request.contextPath}/OrderList" method="post"
                                  onsubmit="return confirm('Bạn có chắc muốn hủy đơn hàng này?');">
                                <input type="hidden" name="action" value="cancel">
                                <input type="hidden" name="orderId" value="${o.orderId}">

                                <button type="submit" class="btn-danger">
                                    ${o.paymentMethod eq 'ONLINE' ? 'Yêu cầu hủy' : 'Hủy đơn'}
                                </button>
                            </form>
                        </c:if>

                    </div>

                </c:forEach>

        </div>
        <div class="pagination-custom">

            <!-- Previous -->
            <a class="${currentPage == 1 ? 'disabled' : ''}"
               href="${pageContext.request.contextPath}/OrderList/${currentStatus}?page=${currentPage - 1}">
                «
            </a>

            <!-- Page number -->
            <c:forEach begin="1" end="${totalPage}" var="i">
                <a class="${i == currentPage ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/OrderList/${currentStatus}?page=${i}">
                    ${i}
                </a>
            </c:forEach>

            <!-- Next -->
            <a class="${currentPage == totalPage ? 'disabled' : ''}"
               href="${pageContext.request.contextPath}/OrderList/${currentStatus}?page=${currentPage + 1}">
                »
            </a>

        </div>
    </body>
</html>