<%-- 
    Document   : add
    Created on : Mar 6, 2026, 11:51:35 AM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerAdmin.jsp" %>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">

        <h5 class="mb-4">Create New User</h5>
        <c:if test="${error != null}">
            <div class="alert alert-danger">
                ${error}
            </div>
        </c:if>
        <form action="${pageContext.request.contextPath}/account" method="post">

            <input type="hidden" name="action" value="add">

            <div class="mb-3">
                <label>Username</label>
                <input type="text" name="username" class="form-control" required>
            </div>

            <div class="mb-3">
                <label>Full Name</label>
                <input type="text" name="fullName" class="form-control">
            </div>

            <div class="mb-3">
                <label>Email</label>
                <input type="email" name="email" class="form-control" required>
            </div>

            <div class="mb-3">
                <label>Phone</label>
                <input type="text" name="phone" class="form-control">
            </div>

            <div class="mb-3">
                <label>Role</label>
                <select name="role" class="form-select">
                    <option value="Admin">Admin</option>
                    <option value="Inventory">Inventory</option>
                    <option value="Customer">Customer</option>
                </select>
            </div>

            <button type="submit" class="btn btn-success">
                Create User
            </button>

            <a href="${pageContext.request.contextPath}/account"
               class="btn btn-secondary">
                Cancel
            </a>

        </form>

    </div>
</div>

<%@include file="../include/footerAdmin.jsp" %>