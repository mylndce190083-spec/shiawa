<%-- 
    Document   : admin-profile
    Created on : Mar 9, 2026, 5:51:38 PM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerAdmin.jsp" %>

<div class="content">
    <div class="container-fluid">

        <h2 class="mb-4">Admin Profile</h2>

        <div class="card" style="max-width:500px">
            <div class="card-body">

                <form action="${pageContext.request.contextPath}/admin-change-password" method="post">

                    <div class="mb-3">
                        <label class="form-label">Username</label>
                        <input type="text" class="form-control" value="${user.username}" readonly>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Email</label>
                        <input type="text" class="form-control" value="${user.email}" readonly>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">New Password</label>
                        <input type="password" name="newPassword" class="form-control">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Confirm Password</label>
                        <input type="password" name="confirmPassword" class="form-control">
                    </div>

                    <button class="btn btn-primary">
                        Change Password
                    </button>

                </form>

            </div>
        </div>

    </div>
</div>

<%@include file="../include/footerAdmin.jsp" %>
