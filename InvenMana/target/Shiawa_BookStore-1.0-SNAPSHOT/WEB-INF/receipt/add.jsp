<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Add Import Receipt</title>
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/WEB-INF/include/header.jsp"/>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h6 class="mb-0">Thêm phiếu nhập</h6>
            <a class="btn btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/import-receipt">Quay lại</a>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/import-receipt" method="post">
            <input type="hidden" name="view" value="add"/>

            <div class="row mb-3">
                <div class="col-md-4">
                    <label class="form-label">Supplier</label>
                    <select class="form-select" name="supplierId">
                        <option value="">-- chọn supplier --</option>
                        <c:forEach var="s" items="${suppliers}">
                            <option value="${s.supplierId}">${s.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-8">
                    <label class="form-label">Ghi chú</label>
                    <input class="form-control" name="note"/>
                </div>
            </div>

            <div class="table-responsive mb-3">
                <table class="table table-bordered align-middle">
                    <thead>
                    <tr class="text-success">
                        <th>Sách</th>
                        <th style="width:140px;">Số lượng</th>
                        <th style="width:160px;">Giá nhập</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach begin="1" end="5" var="i">
                        <tr>
                            <td>
                                <select class="form-select" name="bookId">
                                    <option value="">-- chọn sách --</option>
                                    <c:forEach var="b" items="${books}">
                                        <option value="${b.bookId}">#${b.bookId} - ${b.title}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td><input class="form-control" name="qty" type="number" min="1"/></td>
                            <td><input class="form-control" name="importPrice" type="number" step="0.01" min="0"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <button class="btn btn-success" type="submit">Lưu phiếu nhập</button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/import-receipt">Hủy</a>
        </form>
    </div>
</div>

</div>
</div>

<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
</body>
</html>


