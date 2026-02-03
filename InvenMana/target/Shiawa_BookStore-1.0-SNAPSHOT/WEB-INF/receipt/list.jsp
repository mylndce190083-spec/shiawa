<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Import Receipts</title>
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/WEB-INF/include/header.jsp"/>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light text-center rounded p-4">
        <div class="d-flex align-items-center justify-content-between mb-4">
            <h6 class="mb-0">Danh sách phiếu nhập</h6>
            <a class="btn btn-sm btn-success" href="${pageContext.request.contextPath}/import-receipt?view=add">Thêm phiếu nhập</a>
        </div>

        <div class="table-responsive">
            <table class="table text-start align-middle table-bordered table-hover mb-0">
                <thead>
                <tr class="text-success">
                    <th>ID</th>
                    <th>Ngày nhập</th>
                    <th>Supplier</th>
                    <th>Tổng tiền</th>
                    <th>Ghi chú</th>
                    <th class="text-center">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="r" items="${receipts}">
                    <tr>
                        <td>${r.receiptId}</td>
                        <td>${r.importDate}</td>
                        <td>${r.supplierName}</td>
                        <td>${r.totalAmount}</td>
                        <td>${r.note}</td>
                        <td class="text-center">
                            <div class="d-flex justify-content-center gap-2">
                                <a class="btn btn-sm btn-primary px-3" style="min-width:80px;"
                                   href="${pageContext.request.contextPath}/import-receipt?view=detail&id=${r.receiptId}">Detail</a>
                                <a class="btn btn-sm btn-warning px-3" style="min-width:80px;"
                                   href="${pageContext.request.contextPath}/import-receipt?view=edit&id=${r.receiptId}">Edit</a>
                                <a class="btn btn-sm btn-danger px-3" style="min-width:80px;"
                                   href="${pageContext.request.contextPath}/import-receipt?view=delete&id=${r.receiptId}">Delete</a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

</div>
</div>

<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
</body>
</html>


