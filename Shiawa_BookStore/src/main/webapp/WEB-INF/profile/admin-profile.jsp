<%-- 
    Document   : admin-profile
    Created on : Mar 9, 2026, 5:51:38 PM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerAdmin.jsp" %>

<form action="admin-profile" method="post" class="profile-form">

    <h2>Edit Profile</h2>

    <c:if test="${not empty error}">
        <div style="color:red; margin-bottom:10px;">
            ${error}
        </div>
    </c:if>
    <div class="form-group">
        <label>Username</label>
        <input type="text" value="${sessionScope.user.username}" required>
    </div>

    <div class="form-group">
        <label>Full Name</label>
        <input type="text" name="fullname" value="${sessionScope.user.fullName}" required>
    </div>

    <div class="form-group">
        <label>Email</label>
        <input type="email" name="email" value="${sessionScope.user.email}" required>
    </div>

    <hr>

    <h3>Change Password</h3>

    <div class="form-group">
        <label>Current Password</label>
        <input type="password" name="currentPass">
    </div>

    <div class="form-group">
        <label>New Password</label>
        <input type="password" name="newPass">
    </div>

    <div class="form-group">
        <label>Confirm New Password</label>
        <input type="password" name="confirmNewPass">
    </div>

    <button type="submit" class="save-btn">Save Changes</button>
</form>

<style>
    .profile-form{
        width:400px;
        margin:auto;
        background:#fff;
        padding:25px;
        border-radius:8px;
        box-shadow:0 0 10px rgba(0,0,0,0.1);
    }

    .form-group{
        margin-bottom:15px;
    }

    .form-group label{
        display:block;
        font-weight:bold;
    }

    .form-group input{
        width:100%;
        padding:8px;
        margin-top:5px;
    }

    .save-btn{
        background:#2ecc71;
        border:none;
        padding:10px 20px;
        color:white;
        cursor:pointer;
        border-radius:5px;
    }
</style>
<%@include file="../include/footerAdmin.jsp" %>
