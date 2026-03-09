<%-- 
    Document   : change-password
    Created on : Mar 6, 2026, 1:30:52 PM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/client/layout/header.jsp"/>

<div class="container" style="max-width:500px;margin-top:60px">

    <h3 class="mb-4 text-center">Change Password</h3>

    <form action="${pageContext.request.contextPath}/change-password" method="post">

        <div class="mb-3">
            <label>New Password</label>
            <input type="password" name="newPassword" class="form-control" required>
        </div>

        <div class="mb-3">
            <label>Confirm Password</label>
            <input type="password" name="confirmPassword" class="form-control" required>
        </div>

        <button type="submit" class="btn btn-primary w-100">
            Change Password
        </button>

    </form>

    <c:if test="${not empty error}">
        <div class="alert alert-danger mt-3">
            ${error}
        </div>
    </c:if>

</div>

<jsp:include page="/client/layout/footer.jsp"/>