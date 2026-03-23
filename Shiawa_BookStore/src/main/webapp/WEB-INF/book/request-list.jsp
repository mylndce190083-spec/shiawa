<%-- 
    Document   : create
    Created on : Jan 31, 2026, 10:50:31 AM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerAdmin.jsp" %>
<!-- Recent Sales Start -->
<div class="container-fluid pt-4 px-4">

    <div class="bg-light text-center rounded p-4">

        <div class="d-flex align-items-center justify-content-between mb-4">
            <h6 class="mb-0">Book Request List</h6>

            <a href="${pageContext.request.contextPath}/book-admin"
               class="btn btn-secondary btn-sm">
                <i class="fa fa-arrow-left me-1"></i> Back
            </a>
        </div>

        <div class="table-responsive">
            <table class="table table-bordered table-hover">

                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Code</th>
                        <th>Requested By</th>
                        <th>Status</th>
                        <th>Note</th>
                        <th>Items</th>
                        <th>Action</th>
                    </tr>
                </thead>

                <tbody>

                    <c:forEach var="r" items="${requestList}">

                        <tr>
                            <td>${r.requestId}</td>
                            <td>${not empty r.requestedByStaffName ? r.requestedByStaffName : r.requestCode}</td>
                            <td>${r.requestedByStaffId}</td>
                            <td>${r.status}</td>
                            <td>${r.note}</td>
                            <td>
                                <a class="btn btn-sm btn-outline-primary"
                                   href="${pageContext.request.contextPath}/book-request?action=detail&id=${r.requestId}">
                                    Xem chi tiết
                                </a>
                            </td>

                            <td>
                                <c:choose>
                                    <c:when test="${r.status == 'PENDING'}">
                                        <span class="text-muted">Chờ duyệt</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted">Đã xử lý</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                        </tr>

                    </c:forEach>

                </tbody>

            </table>
        </div>

    </div>

</div>

<!-- Recent Sales End -->
<%@include file="../include/footerAdmin.jsp" %>

