<%-- 
    Document   : admin-profile
    Created on : Mar 9, 2026, 5:51:38 PM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerAdmin.jsp" %>

<div class="container-fluid pt-4 px-4">
    <div class="row justify-content-center">

        <!-- LEFT: Avatar -->
        <div class="col-md-4">
            <div class="card shadow-sm p-4 text-center">
                <h4 class="mb-3">Profile Picture</h4>

                <img src="https://via.placeholder.com/150"
                     class="avatar mb-3">

                <input type="file" class="form-control mb-3">

                <button class="btn btn-primary w-100">
                    Upload Image
                </button>
            </div>
        </div>


        <!-- RIGHT: Profile Info -->
        <div class="col-md-6">
            <form action="admin-profile" method="post" class="profile-form">

                <h2>Edit Profile</h2>

                <c:if test="${not empty error}">
                    <div style="color:red; margin-bottom:10px;">
                        ${error}
                    </div>
                </c:if>
                <div class="form-group">
                    <label>Username</label>
                    <input type="text" name="username" value="${sessionScope.user.username}" required>
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
        </div>

    </div>
</div>
<style>
    .profile-form{
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

    .avatar{
        width:150px;
        height:150px;
        border-radius:50%;
        object-fit:cover;
        border:4px solid #eee;
        display:block;
        margin:0 auto 15px auto; /* căn giữa */
    }

    .error-box{
        color:red;
        margin-bottom:10px;
    }
</style>
<%@include file="../include/footerAdmin.jsp" %>
