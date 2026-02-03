<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Add Supplier</title>
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/WEB-INF/include/header.jsp"/>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">
        <h6 class="mb-4">Add Supplier</h6>
        <form action="${pageContext.request.contextPath}/supplier" method="post">
            <input type="hidden" name="view" value="add"/>

            <div class="mb-3">
                <label class="form-label">Name</label>
                <input class="form-control" name="name" required/>
            </div>
            <div class="mb-3">
                <label class="form-label">Phone</label>
                <input class="form-control" name="phone"/>
            </div>
            <div class="mb-3">
                <label class="form-label">Email</label>
                <input class="form-control" name="email"/>
            </div>
            <div class="mb-3">
                <label class="form-label">Address</label>
                <input class="form-control" name="address"/>
            </div>

            <button class="btn btn-success" type="submit">Save</button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/supplier">Cancel</a>
        </form>
    </div>
</div>

</div>
</div>

<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
</body>
</html>




