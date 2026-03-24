<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerInventory.jsp" %>
<!-- Recent Sales Start -->
<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h6 class="mb-0">Thông báo & lịch sử yêu cầu nhập sách</h6>
            <a class="btn btn-outline-secondary btn-sm" href="${pageContext.request.contextPath}/inventory?view=in">Quay lại nhập kho</a>
        </div>

        <c:if test="${approvedCount > 0}">
            <div class="alert alert-success">
                Bạn có <strong>${approvedCount}</strong> yêu cầu đã được Admin đồng ý.
            </div>
        </c:if>

        <c:if test="${empty requestHistory}">
            <div class="alert alert-info mb-0">Chưa có yêu cầu nhập kho nào.</div>
        </c:if>

        <c:if test="${not empty requestHistory}">
            <div class="table-responsive">
                <table class="table table-bordered align-middle mb-0">
                    <thead>
                        <tr class="text-success">
                            <th>ID</th>
                            <th>Người thực hiện</th>
                            <th>Trạng thái</th>
                            <th>Ghi chú</th>
                            <th>Xem chi tiết</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="r" items="${requestHistory}">
                            <tr>
                                <td>${r.requestId}</td>
                                <td>${not empty r.requestedByStaffName ? r.requestedByStaffName : r.requestCode}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${r.status == 'APPROVED'}"><span class="badge bg-success">Đã duyệt</span></c:when>
                                        <c:when test="${r.status == 'REJECTED'}"><span class="badge bg-danger">Từ chối</span></c:when>
                                        <c:otherwise><span class="badge bg-warning text-dark">Chờ duyệt</span></c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${r.note}</td>
                                <td>
                                    <a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/inventory?view=history-detail&id=${r.requestId}">Xem chi tiết</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>
</div>
<!-- Recent Sales End -->
<%@include file="../include/footerInventory.jsp" %>



