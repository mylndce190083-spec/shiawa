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
        </style>
    </head>
    <body>

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
                   class="tab-link ${currentStatus == 'PENDING' ? 'active' : ''}">
                    Chờ xác nhận
                </a>

                <a href="${pageContext.request.contextPath}/OrderList/shipping"
                   class="tab-link ${currentStatus == 'SHIPPING' ? 'active' : ''}">
                    Đang giao
                </a>

                <a href="${pageContext.request.contextPath}/OrderList/completed"
                   class="tab-link ${currentStatus == 'COMPLETED' ? 'active' : ''}">
                    Hoàn thành
                </a>

                <a href="${pageContext.request.contextPath}/OrderList/cancelled"
                   class="tab-link ${currentStatus == 'CANCELLED' ? 'active' : ''}">
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
                                ${o.status == 'Pending' ? 'Chờ xác nhận' :
                                  o.status == 'Shipping' ? 'Đang giao' :
                                  o.status == 'Completed' ? 'Hoàn thành' :
                                  o.status == 'Cancelled' ? 'Đã hủy' : o.status}
                            </div>

                        </div>


                        <c:forEach var="item" items="${o.items}">
                            <div style="display:flex; justify-content:space-between; align-items:flex-start; margin-bottom:15px;">

                                <!-- BÊN TRÁI: Ảnh + thông tin -->
                                <div style="display:flex; align-items:flex-start;">

                                    <img src="${pageContext.request.contextPath}/${item.url_img}"
                                         style="width:80px; height:105px; object-fit:cover; border-radius:8px; margin-right:15px;"/>

                                    <div style="margin-top:22px;">
                                        <div style="font-weight:500; margin-bottom: 7px">${item.title}</div>
                                        <div>Số lượng: ${item.quantity}</div>
                                    </div>

                                </div>

                                <!-- BÊN PHẢI: Giá -->
                                <div style="display:flex; flex-direction:column; align-items:flex-end; margin-top:50px">

                                    <!-- Đơn giá nhỏ -->
                                    <div style="font-size:13px; color:#888;">
                                        Tổng tiền : ${item.price * item.quantity} VND
                                    </div>



                                </div>

                            </div>
                        </c:forEach>

                        <hr>

                        <div style="text-align:right; font-size:18px; font-weight:bold; color:black;">
                            Tổng số tiền( ${o.quantity} sản phẩm): ${o.totalAmount} VND
                        </div>
                        <c:if test="${o.status == 'Pending'}">
                            <form action="${pageContext.request.contextPath}/OrderList" 
                                  method="post" 
                                  style="text-align:right; margin-top:10px;"
                                  onsubmit="return confirm('Bạn có chắc muốn hủy đơn hàng này?');">

                                <input type="hidden" name="action" value="cancel">
                                <input type="hidden" name="orderId" value="${o.orderId}">

                                <button type="submit" 
                                        style="background:#ff4d4f; color:white; border:none;
                                        padding:8px 16px; border-radius:6px; cursor:pointer;">
                                    Hủy đơn
                                </button>
                            </form>
                        </c:if>
                    </div>

                </c:forEach>
        </div>
    </body>
</html>
