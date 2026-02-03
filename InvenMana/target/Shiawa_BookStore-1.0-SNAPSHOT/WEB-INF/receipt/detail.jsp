<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Import Receipt Detail</title>
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/WEB-INF/include/header.jsp"/>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h6 class="mb-0">Chi tiết phiếu nhập #${receipt.receiptId}</h6>
            <a class="btn btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/import-receipt">Quay lại</a>
        </div>

        <div class="row mb-3">
            <div class="col-md-4">
                <b>Ngày nhập:</b> ${receipt.importDate}
            </div>
            <div class="col-md-4">
                <b>Supplier:</b> ${receipt.supplierName}
            </div>
            <div class="col-md-4">
                <b>Tổng tiền:</b> ${receipt.totalAmount}
            </div>
        </div>
        <div class="mb-3">
            <b>Ghi chú:</b> ${receipt.note}
        </div>

        <div class="table-responsive">
            <table class="table table-bordered align-middle">
                <thead>
                <tr class="text-success">
                    <th>Book</th>
                    <th>Qty</th>
                    <th>Import Price</th>
                    <th>Thành tiền</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="d" items="${receipt.details}">
                    <tr>
                        <td>#${d.bookId} - ${d.bookTitle}</td>
                        <td>${d.qty}</td>
                        <td>${d.importPrice}</td>
                        <td>${d.qty * d.importPrice}</td>
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


