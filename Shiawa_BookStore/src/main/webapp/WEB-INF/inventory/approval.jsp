<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerInventory.jsp" %>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h6 class="mb-0">Duyệt phiếu nhập kho</h6>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <c:if test="${empty requests}">
            <div class="alert alert-info mb-0">Chưa có yêu cầu nhập kho nào.</div>
        </c:if>

        <c:if test="${not empty requests}">
            <div class="table-responsive">
                <table class="table table-bordered align-middle mb-0">
                    <thead>
                        <tr class="text-success">
                            <th>ID</th>
                            <th>Mã yêu cầu</th>
                            <th>Trạng thái</th>
                            <th>Ghi chú</th>
                            <th>Chi tiết</th>
                            <th style="width:220px;">Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="r" items="${requests}">
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
                                    <ul class="mb-0">
                                        <c:forEach var="it" items="${r.items}">
                                            <li>
                                                <c:choose>
                                                    <c:when test="${not empty it.bookTitle}">
                                                        #${it.bookId} - ${it.bookTitle}
                                                    </c:when>
                                                    <c:otherwise>
                                                        (Sách mới) ${it.newBookTitle}
                                                    </c:otherwise>
                                                </c:choose>
                                                — SL: ${it.qty}
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </td>
                                <td>
                                    <c:if test="${r.status == 'PENDING'}">
                                        <form action="${pageContext.request.contextPath}/admin/stock-in-approval" method="post" class="d-flex gap-2">
                                            <input type="hidden" name="requestId" value="${r.requestId}"/>
                                            <button class="btn btn-success btn-sm" name="action" value="approve" type="submit">Duyệt</button>
                                            <button class="btn btn-danger btn-sm" name="action" value="reject" type="submit">Từ chối</button>
                                        </form>
                                    </c:if>
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
