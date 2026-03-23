<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../include/headerAdmin.jsp" %>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h6 class="mb-0">Receipt details</h6>
            <a href="${pageContext.request.contextPath}/book-request?action=list" class="btn btn-secondary btn-sm">
                <i class="fa fa-arrow-left me-1"></i> Back
            </a>
        </div>

        <c:if test="${empty request}">
            <div class="alert alert-danger">No request form found</div>
        </c:if>

        <c:if test="${not empty request}">
            <div class="row g-3 mb-3">
                <div class="col-md-3">
                    <div class="small text-muted">Ticket code</div>
                    <div class="fw-semibold">${request.requestCode}</div>
                </div>
                <div class="col-md-3">
                    <div class="small text-muted">Status</div>
                    <div>
                        <span class="badge ${request.status == 'APPROVED' ? 'bg-success' : (request.status == 'REJECTED' ? 'bg-danger' : 'bg-warning text-dark')}">
                            ${request.status}
                        </span>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="small text-muted">Requested By</div>
                    <div class="fw-semibold">${request.requestedByStaffId}</div>
                </div>
                <div class="col-md-3">
                    <div class="small text-muted">Ghi chú</div>
                    <div class="fw-semibold">${request.note}</div>
                </div>
            </div>

            <div class="table-responsive mb-3">
                <table class="table table-bordered align-middle">
                    <thead>
                    <tr class="text-success">
                        <th>Book</th>
                        <th>Author</th>
                        <th>Publish</th>
                        <th>Category</th>
                        <th>Quantity</th>
                        <th>Import price</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="it" items="${request.items}">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty it.bookId}">#${it.bookId} - ${it.bookTitle}</c:when>
                                    <c:otherwise>${it.newBookTitle}</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty it.newBookAuthor}">${it.newBookAuthor}</c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty it.newBookPublisher}">${it.newBookPublisher}</c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty it.newBookCategoryId}">${it.newBookCategoryId}</c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                            <td>${it.qty}</td>
                            <td><fmt:formatNumber value="${it.unitCost}" type="number" groupingUsed="true" maxFractionDigits="2" /></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="d-flex gap-2">
                <c:choose>
                    <c:when test="${request.status == 'PENDING'}">
                        <a class="btn btn-success" href="${pageContext.request.contextPath}/book-request?action=accept&id=${request.requestId}">Approve</a>
                        <a class="btn btn-danger" href="${pageContext.request.contextPath}/book-request?action=reject&id=${request.requestId}">Reject</a>
                    </c:when>
                    <c:otherwise>
                        <span class="text-muted">Complete</span>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>
    </div>
</div>

<%@include file="../include/footerAdmin.jsp" %>
