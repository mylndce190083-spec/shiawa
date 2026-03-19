<%-- 
    Document   : detail
    Created on : Feb 20, 2026, 7:53:52 PM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/headerAdmin.jsp" %>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">

        <h4 class="mb-4">Order Detail</h4>

        <!-- Order Info -->
        <div class="card p-3 mb-4">
            <p><strong>Order ID:</strong> ${order.orderId}</p>
            <p><strong>Customer:</strong> ${order.customerName}</p>
            <p><strong>Phone:</strong> ${order.phone}</p>
            <p><strong>Order Date:</strong> ${order.orderDate}</p>

            <p><strong>Shipping Address:</strong> ${order.shippingAddress}</p>
            <p><strong>Shipping Fee:</strong> ${order.shippingFee}</p>
            <p><strong>Discount:</strong> ${order.discount}</p>
            <p><strong>Voucher:</strong>
                <c:choose>
                    <c:when test="${not empty order.voucherName}">
                        ${order.voucherName}
                    </c:when>
                    <c:otherwise>
                        None
                    </c:otherwise>
                </c:choose>
            </p>
            <span class="badge 
                  bg-${order.status eq 'PENDING' ? 'warning' :
                       order.status eq 'CONFIRMED' ? 'info' :
                       order.status eq 'SHIPPING' ? 'primary' :
                       order.status eq 'DELIVERED' ? 'success' :
                       order.status eq 'FAILED' ? 'danger' :
                       order.status eq 'CANCEL_REQUESTED' ? 'warning' :
                       order.status eq 'REFUNDED' ? 'dark' :
                       'secondary'}">

                <c:choose>
                    <c:when test="${order.status eq 'PENDING'}">Chờ xác nhận</c:when>
                    <c:when test="${order.status eq 'CONFIRMED'}">Đã xác nhận</c:when>
                    <c:when test="${order.status eq 'SHIPPING'}">Đang vận chuyển</c:when>
                    <c:when test="${order.status eq 'DELIVERED'}">Giao thành công</c:when>
                    <c:when test="${order.status eq 'FAILED'}">Đã hủy</c:when>
                    <c:when test="${order.status eq 'CANCEL_REQUESTED'}">Chờ duyệt hủy</c:when>
                    <c:when test="${order.status eq 'REFUNDED'}">Đã hoàn tiền</c:when>
                </c:choose>

            </span>
        </div>
        
        <div class="order-progress">

            <div class="progress-line">
                <div class="progress-fill 
                     ${order.status == 'FAILED' ? 'failed' : ''}"
                     style="width:
                     ${order.status == 'PENDING' ? '0%' :
                       order.status == 'CONFIRMED' ? '33%' :
                       order.status == 'SHIPPING' ? '66%' :
                       order.status == 'DELIVERED' ? '100%' :
                       order.status == 'FAILED' ? '66%' : '0%'}">
                </div>
            </div>

            <div class="progress-steps">

                <div class="step ${order.status != null ? 'active' : ''}">
                    <div class="circle">1</div>
                    <div class="label">Pending</div>
                </div>

                <div class="step 
                     ${order.status == 'CONFIRMED' || order.status == 'SHIPPING' || order.status == 'DELIVERED' ? 'active' : ''}">
                    <div class="circle">2</div>
                    <div class="label">Confirmed</div>
                </div>

                <div class="step 
                     ${order.status == 'SHIPPING' || order.status == 'DELIVERED' || order.status == 'FAILED' ? 'active' : ''}">
                    <div class="circle">3</div>
                    <div class="label">Shipping</div>
                </div>

                <div class="step 
                     ${order.status eq 'DELIVERED' ? 'active' : ''}">
                    <div class="circle">4</div>
                    <div class="label">Delivered</div>
                </div>

            </div>

            <c:if test="${order.status == 'FAILED'}">
                <div class="failed-text">Delivery Failed</div>
            </c:if>

        </div>     

        <!-- Order Details -->
        <h5 class="mb-3">Books in this Order</h5>

        <div class="table-responsive">
            <table class="table table-bordered text-start align-middle">
                <thead>
                    <tr class="text-success">
                        <th>Title</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Subtotal</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="d" items="${detailList}">
                        <tr>
                            <td>${d.bookTitle}</td>
                            <td>${d.quantity}</td>
                            <td>${d.price}</td>
                            <td>${d.subtotal}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <a href="${pageContext.request.contextPath}/order-admin?action=list"
           class="btn btn-secondary mt-3">
            Back to List
        </a>

    </div>
</div>

<%@include file="../include/footerAdmin.jsp" %>
