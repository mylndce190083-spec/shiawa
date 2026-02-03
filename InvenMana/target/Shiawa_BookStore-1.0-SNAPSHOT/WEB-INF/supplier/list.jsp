<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Supplier</title>
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/WEB-INF/include/header.jsp"/>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light text-center rounded p-4">
        <div class="d-flex align-items-center justify-content-between mb-4">
            <h6 class="mb-0">Supplier List</h6>
            <a class="btn btn-sm btn-success" href="${pageContext.request.contextPath}/supplier?view=add">Add Supplier</a>
        </div>
        <div class="table-responsive">
            <table class="table text-start align-middle table-bordered table-hover mb-0">
                <thead>
                <tr class="text-success">
                    <th>ID</th>
                    <th>Name</th>
                    <th>Phone</th>
                    <th>Email</th>
                    <th>Address</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="s" items="${suppliers}">
                    <tr>
                        <td>${s.supplierId}</td>
                        <td>${s.name}</td>
                        <td>${s.phone}</td>
                        <td>${s.email}</td>
                        <td>${s.address}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

</div> <%-- content from header.jsp --%>
</div> <%-- container from header.jsp --%>

<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
</body>
</html>




