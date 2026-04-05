<%-- 
    Document   : list
    Created on : Feb 20, 2026, 11:55:22 AM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerAdmin.jsp" %>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">

        <div class="d-flex align-items-center justify-content-between mb-4">
            <h6 class="mb-0">Order List</h6>
        </div>       
        <c:choose>
            <c:when test="${empty orderList}">
                <div class="alert alert-warning">
                    No orders found.
                </div>
            </c:when>

            <c:otherwise>
                <div class="table-responsive">
                    <table class="table text-start align-middle table-bordered table-hover mb-0">
                        <thead>
                            <tr class="text-success">
                                <th>ID</th>
                                <th>Customer</th>
                                <th>Order Date</th>
                                <th>Status</th>
                                <th>Total Amount</th>
                                <th>Shipping Fee</th>
                                <th>Handled By</th>
                                <th class="text-center">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="o" items="${orderList}">
                                <tr>
                                    <td>${o.orderId}</td>
                                    <td>${o.customerName}</td>
                                    <td>${o.orderDate}</td>
                                    <td>
                                        <span class="badge 
                                              bg-${o.status == 'PENDING' ? 'warning' :
                                                   o.status == 'CONFIRMED' ? 'info' :
                                                   o.status == 'SHIPPING' ? 'primary' :
                                                   o.status == 'DELIVERED' ? 'success' :
                                                   o.status == 'FAILED' ? 'danger' :
                                                   o.status == 'CANCEL_REQUESTED' ? 'dark' :
                                                   o.status == 'REFUNDED' ? 'secondary' :
                                                   'secondary'}">

                                            <c:choose>
                                                <c:when test="${o.status == 'PENDING'}">Pending</c:when>
                                                <c:when test="${o.status == 'CONFIRMED'}">Confirmed</c:when>
                                                <c:when test="${o.status == 'SHIPPING'}">Shipping</c:when>
                                                <c:when test="${o.status == 'DELIVERED'}">Delivered</c:when>
                                                <c:when test="${o.status == 'FAILED'}">Failed</c:when>
                                                <c:when test="${o.status == 'CANCEL_REQUESTED'}">Cancel requested</c:when>
                                                <c:when test="${o.status == 'REFUNDED'}">Refunded</c:when>
                                                <c:otherwise>${o.status}</c:otherwise>
                                            </c:choose>

                                        </span>
                                    </td>
                                    </span>
                                    </td>                     
                                    <td>${o.totalAmount}</td>
                                    <td>${o.shippingFee}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty o.staffName}">
                                                ${o.staffName}
                                            </c:when>
                                            <c:otherwise>
                                                Not processed
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-center">
                                        <a href="${pageContext.request.contextPath}/order-admin?action=detail&id=${o.orderId}"
                                           class="btn btn-sm" style="background-color:#6366f1; color:white;">
                                            Detail
                                        </a>
                                        <a href="${pageContext.request.contextPath}/order-admin?action=updateStatus&id=${o.orderId}"
                                           class="btn btn-sm" style="background-color:#fd7e14; color:white;">
                                            Change Status
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <div class="d-flex justify-content-center mt-3">


                        <c:if test="${currentPage > 1}">
                            <a class="btn btn-sm btn-outline-secondary me-2"
                               href="order-admin?action=list&page=${currentPage - 1}">
                                <<
                            </a>
                        </c:if>

                        <c:forEach begin="1" end="${totalPage}" var="i">
                            <a class="btn btn-sm ${i == currentPage ? 'btn-primary' : 'btn-outline-primary'} me-1"
                               href="order-admin?action=list&page=${i}">
                                ${i}
                            </a>
                        </c:forEach>

                        <c:if test="${currentPage < totalPage}">
                            <a class="btn btn-sm btn-outline-secondary ms-2"
                               href="order-admin?action=list&page=${currentPage + 1}">
                                >>
                            </a>
                        </c:if>

                    </div>
                </div>
            </c:otherwise>
        </c:choose>

    </div>
</div>
<%@include file="../include/footerAdmin.jsp" %>

