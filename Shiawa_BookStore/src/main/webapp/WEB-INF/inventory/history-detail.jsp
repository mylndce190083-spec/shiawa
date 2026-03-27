<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerInventory.jsp" %>

<!-- Recent Sales Start -->
<div class="container-fluid pt-4 px-4">
                <div class="bg-light rounded p-4">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h6 class="mb-0">Receipt details</h6>
                        <a href="${pageContext.request.contextPath}/inventory?view=history" class="btn btn-secondary btn-sm">
                            <i class="fa fa-arrow-left me-1"></i> Back
                        </a>
                    </div>

                    <c:if test="${empty request}">
                        <div class="alert alert-danger">No request form found.</div>
                    </c:if>

                    <c:if test="${not empty request}">
                        <div class="row g-3 mb-3">
                            <div class="col-md-3">
                                <div class="small text-muted">Performed by</div>
                                <div class="fw-semibold">${not empty request.requestedByStaffName ? request.requestedByStaffName : request.requestCode}</div>
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
                                <div class="small text-muted">Note</div>
                                <div class="fw-semibold">${request.note}</div>
                            </div>
                        </div>

                        <div class="table-responsive mb-3">
                            <table class="table table-bordered align-middle">
                                <thead>
                                <tr class="text-success">
                                    <th>Book</th>
                                    <th>Author</th>
                                    <th>Publisher</th>
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
                                        <td>${it.unitCost}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                </div>
            </div>
            <!-- Recent Sales End -->
</div>

<%@include file="../include/footerInventory.jsp" %>
