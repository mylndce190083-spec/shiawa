<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerInventory.jsp" %>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h6 class="mb-0">Notifications & history of book import requests</h6>
            <a class="btn btn-outline-secondary btn-sm" href="${pageContext.request.contextPath}/inventory?view=in">Quay lại nhập kho</a>
        </div>

        <c:if test="${approvedCount > 0}">
            <div class="alert alert-success">
                You have <strong>${approvedCount}</strong> The request has been approved by the Admin.
            </div>
        </c:if>

        <c:if test="${empty requestHistory}">
            <div class="alert alert-info mb-0">No warehousing requests have been received yet.</div>
        </c:if>

        <c:if test="${not empty requestHistory}">
            <div class="table-responsive">
                <table class="table table-bordered align-middle mb-0">
                    <thead>
                        <tr class="text-success">
                            <th>ID</th>
                            <th>Perform by</th>
                            <th>Status</th>
                            <th>Note</th>
                            <th>Detail</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="r" items="${requestHistory}">
                            <tr>
                                <td>${r.requestId}</td>
                                <td>${not empty r.requestedByStaffName ? r.requestedByStaffName : r.requestCode}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${r.status == 'APPROVED'}"><span class="badge bg-success">Approved</span></c:when>
                                        <c:when test="${r.status == 'REJECTED'}"><span class="badge bg-danger">Rejected</span></c:when>
                                        <c:otherwise><span class="badge bg-warning text-dark">Waiting</span></c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${r.note}</td>
                                <td>
                                    <a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/inventory?view=history-detail&id=${r.requestId}">Detail</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>
</div>
<%@include file="../include/footerInventory.jsp" %>



