<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Delete Import Receipt</title>
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/WEB-INF/include/header.jsp"/>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">
        <h6 class="mb-3">Xóa phiếu nhập #${receipt.receiptId}</h6>
        <p>Bạn có chắc muốn xóa phiếu nhập này? Hệ thống sẽ <b>trừ lại tồn kho</b> theo các dòng chi tiết.</p>

        <form action="${pageContext.request.contextPath}/import-receipt" method="post">
            <input type="hidden" name="view" value="delete"/>
            <input type="hidden" name="id" value="${receipt.receiptId}"/>
            <button class="btn btn-danger" type="submit">Xóa</button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/import-receipt">Hủy</a>
        </form>
    </div>
</div>

</div>
</div>

<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
</body>
</html>


