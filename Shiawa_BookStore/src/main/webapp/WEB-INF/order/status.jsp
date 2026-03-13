<%-- 
    Document   : status
    Created on : Feb 24, 2026, 4:34:30 PM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/headerAdmin.jsp" %>
<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">

        <h4 class="mb-4">Update Order Status</h4>

        <div class="row">

            <!-- LEFT: Order Info -->
            <div class="col-md-8">
                <div class="card p-4 shadow-sm h-100">
                    <h5 class="mb-3">Order Information</h5>

                    <div class="row mb-2">
                        <div class="col-4 fw-bold">Order ID:</div>
                        <div class="col-8">${order.orderId}</div>
                    </div>

                    <div class="row mb-2">
                        <div class="col-4 fw-bold">Customer:</div>
                        <div class="col-8">${order.customerName}</div>
                    </div>

                    <div class="row mb-2">
                        <div class="col-4 fw-bold">Phone:</div>
                        <div class="col-8">${order.phone}</div>
                    </div>

                    <div class="row mb-2">
                        <div class="col-4 fw-bold">Order Date:</div>
                        <div class="col-8">${order.orderDate}</div>
                    </div>

                    <div class="row mb-2">
                        <div class="col-4 fw-bold">Shipping Address:</div>
                        <div class="col-8">${order.shippingAddress}</div>
                    </div>

                </div>
            </div>

            <!-- RIGHT: Status Panel -->
            <div class="col-md-4">
                <div class="card p-4 shadow-sm text-center">

                    <h5 class="mb-3">Order Status</h5>

                    <!-- Current Status Badge -->
                    <div class="mb-3">
                        <span class="badge fs-6 
                              bg-${order.status == 'PENDING' ? 'warning' :
                                   order.status == 'CONFIRMED' ? 'info' :
                                   order.status == 'SHIPPING' ? 'primary' :
                                   order.status == 'DELIVERED' ? 'success' :
                                   order.status == 'FAILED' ? 'danger' : 'secondary'}">
                                  ${order.status}
                              </span>
                        </div>

                        <form action="${pageContext.request.contextPath}/order-admin" method="post">
                            <input type="hidden" name="action" value="updateStatus">
                            <input type="hidden" name="id" value="${order.orderId}">

                            <c:choose>


                                <c:when test="${order.status == 'PENDING'}">
                                    <input type="hidden" name="status" value="CONFIRMED">
                                    <button type="submit" class="btn btn-success w-100">
                                        Xác nhận đơn hàng
                                    </button>
                                </c:when>


                                <c:when test="${order.status == 'CONFIRMED'}">
                                    <input type="hidden" name="status" value="SHIPPING">
                                    <button type="submit" class="btn btn-primary w-100">
                                        Chuyển sang vận chuyển
                                    </button>
                                </c:when>


                                <c:when test="${order.status == 'SHIPPING'}">
                                    <div class="d-grid gap-2">
                                        <button type="submit" name="status" value="DELIVERED" 
                                                class="btn btn-success">
                                            Giao thành công
                                        </button>

                                        <button type="submit" name="status" value="FAILED" 
                                                class="btn btn-danger">
                                            Giao thất bại
                                        </button>
                                    </div>
                                </c:when>


                                <c:otherwise>
                                    <div class="alert alert-success">
                                        Đơn hàng đã hoàn tất. Không thể thay đổi trạng thái.
                                    </div>
                                </c:otherwise>

                            </c:choose>

                            <a href="${pageContext.request.contextPath}/order-admin?action=list"
                               class="btn btn-secondary w-100 mt-2">
                                Cancel
                            </a>
                        </form>

                    </div>
                </div>

            </div>

        </div>
    </div>
    <%@include file="../include/footerAdmin.jsp" %>