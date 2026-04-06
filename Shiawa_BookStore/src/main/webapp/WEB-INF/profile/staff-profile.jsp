<%-- 
    Document   : staff-profile
    Created on : Mar 9, 2026, 5:51:38 PM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>

    <c:when test="${sessionScope.user.role == 'Admin'}">
        <%@include file="../include/headerAdmin.jsp" %>
    </c:when>

    <c:when test="${sessionScope.user.role == 'Inventory'}">
        <%@include file="../include/headerInventory.jsp" %>
    </c:when>

</c:choose>

<c:if test="${not empty sessionScope.msg}">
    <div id="toast-container">
        <div class="toast">
            ${sessionScope.msg}
        </div>
    </div>
</c:if>
<div id="toast-container">
    <div class="toast">
        ${msg}
    </div>
</div>

<div class="container-fluid pt-4 px-4">
    <div class="container-fluid pt-4 px-4">
        <div class="row justify-content-center">

            <div class="col-md-4">
                <div class="card avatar-card text-center">
                    <h4 class="mb-3">Profile Picture</h4>

                    <img src="${pageContext.request.contextPath}/image?file=${sessionScope.user.avatar}" 
                         alt="Avatar"
                         class="avatar">

                    <h5 class="mt-2">${sessionScope.user.username}</h5>

                    <form action="staff-avatar" method="post" enctype="multipart/form-data">
                        <input type="file" name="avatarS" class="form-control mt-3" required>
                        <button type="submit" class="btn btn-primary mt-3 w-100">
                            Upload Image
                        </button>
                    </form>

                </div>
            </div>


            <div class="col-md-6">
                <form action="staff-profile" method="post" class="profile-form">
                    <h2>Edit Profile</h2>

                    <input type="hidden" name="action" value="updateProfile"/>

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

                    <button type="submit" class="save-btn">Save Profile</button>

                </form>

                <hr>
                <form action="staff-profile" method="post" class="profile-form mt-4">
                    <h3>Change Password</h3>

                    <input type="hidden" name="action" value="changePassword"/>

                    <div class="form-group">
                        <label>Current Password</label>
                        <input type="password" name="currentPass" required>
                    </div>

                    <div class="form-group">
                        <label>New Password</label>
                        <input type="password" name="newPass" required>
                    </div>

                    <div class="form-group">
                        <label>Confirm New Password</label>
                        <input type="password" name="confirmNewPass" required>
                    </div>

                    <button type="submit" class="save-btn">Change Password</button>
                </form>
            </div>

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

    .avatar-card{
        background:#fff;
        padding:25px;
        border-radius:10px;
        box-shadow:0 0 15px rgba(0,0,0,0.1);
    }

    .avatar{
        width:150px;
        height:150px;
        border-radius:50%;
        object-fit:cover;
        border:5px solid #f1f1f1;
        display:block;
        margin:0 auto 15px auto;
        transition:0.3s;
    }

    .avatar:hover{
        transform:scale(1.05);
        border-color:#2ecc71;
    }

    .avatar-card .btn{
        border-radius:8px;
        padding:10px;
        font-weight:500;
    }

    .error-box{
        color:red;
        margin-bottom:10px;
    }
    #toast-container{
        position: fixed;
        top: 20px;
        right: 20px;
        z-index: 9999;
    }

    .toast{
        background: #2ecc71;
        color: white;
        padding: 12px 18px;
        margin-bottom: 10px;
        border-radius: 6px;
        box-shadow: 0 0 10px rgba(0,0,0,0.2);
        animation: fadeInOut 4s forwards;
    }

    /* animation */
    @keyframes fadeInOut {
        0% {
            opacity:0;
            transform: translateX(100%);
        }
        10% {
            opacity:1;
            transform: translateX(0);
        }
        90% {
            opacity:1;
        }
        100% {
            opacity:0;
            transform: translateX(100%);
        }
    }

</style>
<c:choose>

    <c:when test="${sessionScope.user.role == 'Admin'}">
        <%@include file="../include/footerAdmin.jsp" %>
    </c:when>

    <c:when test="${sessionScope.user.role == 'Inventory'}">
        <%@include file="../include/footerInventory.jsp" %>
    </c:when>

</c:choose>



<script>
    setTimeout(() => {
        const container = document.getElementById("toast-container");
        if (container)
            container.remove();
    }, 10000);
</script>