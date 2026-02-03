<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Báo cáo nhập/xuất</title>
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/WEB-INF/include/header.jsp"/>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h6 class="mb-0">Báo cáo nhập/xuất theo ngày</h6>
            <div class="d-flex gap-2">
                <a class="btn btn-sm btn-success" href="${pageContext.request.contextPath}/inventory?view=in">Nhập kho</a>
                <a class="btn btn-sm btn-success" href="${pageContext.request.contextPath}/inventory?view=out">Xuất kho</a>
            </div>
        </div>

        <form class="row g-2 align-items-end mb-4" method="get" action="${pageContext.request.contextPath}/inventory">
            <input type="hidden" name="view" value="report"/>
            <div class="col-md-3">
                <label class="form-label">From</label>
                <input class="form-control" type="date" name="from" value="${from}"/>
            </div>
            <div class="col-md-3">
                <label class="form-label">To</label>
                <input class="form-control" type="date" name="to" value="${to}"/>
            </div>
            <div class="col-md-4">
                <label class="form-label">Lọc theo sách</label>
                <select class="form-select" name="bookId">
                    <option value="">-- tất cả --</option>
                    <c:forEach var="b" items="${books}">
                        <option value="${b.bookId}" ${selectedBookId == b.bookId ? 'selected' : ''}>#${b.bookId} - ${b.title}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-2">
                <button class="btn btn-success w-100" type="submit">Xem</button>
            </div>
        </form>

        <div class="row g-4">
            <div class="col-lg-6">
                <h6 class="mb-2">Tổng hợp theo ngày</h6>
                <div class="table-responsive">
                    <table class="table table-bordered table-hover mb-0">
                        <thead>
                        <tr class="text-success">
                            <th>Day</th>
                            <th>Type</th>
                            <th>Total Qty</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="r" items="${dailyRows}">
                            <tr>
                                <td>${r.day}</td>
                                <td>${r.txnType}</td>
                                <td>${r.totalQty}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="col-lg-6">
                <h6 class="mb-2">Tổng hợp theo sản phẩm</h6>
                <div class="table-responsive">
                    <table class="table table-bordered table-hover mb-0">
                        <thead>
                        <tr class="text-success">
                            <th>Book</th>
                            <th>Type</th>
                            <th>Total Qty</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="r" items="${productRows}">
                            <tr>
                                <td>#${r.bookId} - ${r.title}</td>
                                <td>${r.txnType}</td>
                                <td>${r.totalQty}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

</div>
</div>

<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
</body>
</html>




