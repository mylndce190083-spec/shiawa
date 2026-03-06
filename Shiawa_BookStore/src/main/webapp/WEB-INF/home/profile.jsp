<%-- 
    Document   : profile
    Created on : Mar 2, 2026, 3:10:12 PM
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile</title>
        <link href="${pageContext.request.contextPath}/assets/css/css.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="profile-wrapper">

            <!-- Sidebar -->
            <div class="profile-sidebar">
                <div class="avatar-box">
                    <img src="/uploads/${customer.avatar}" 
                         alt="Avatar"
                         class="avatar-img">
                    <h3>${customer.username}</h3>

                    <form action="update-avatar" method="post" enctype="multipart/form-data">
                        <input type="file" name="avatarFile" accept="image/*" required>
                        <button type="submit" class="avatar-btn">Đổi avatar</button>
                    </form>
                </div>

                <ul class="profile-menu">
                    <li class="active">Thông tin cá nhân</li>
                    <li>Đổi mật khẩu</li>
                    <li>Lịch sử đơn hàng</li>
                    <li>Đăng xuất</li>
                </ul>
            </div>

            <!-- Main Content -->
            <div class="profile-content">
                <h2>Thông tin cá nhân</h2>

                <form action="update-profile" method="post" class="profile-form">

                    <div class="form-group">
                        <label>Họ và tên</label>
                        <input type="text" name="username" value="${customer.username}">
                    </div>

                    <div class="form-group">
                        <label>Số điện thoại</label>
                        <input type="text" name="phone" value="${customer.phone}">
                    </div>

                    <div class="form-group">
                        <label>Địa chỉ</label>
                        <input type="text" name="address" value="${customer.address}">
                    </div>

                    <div class="form-group">
                        <label>Email</label>
                        <input type="email" value="${customer.email}" disabled>
                    </div>

                    <button type="submit" class="save-btn">Lưu thay đổi</button>

                    <p class="success">${message}</p>
                    <p class="error">${error}</p>

                </form>
            </div>

        </div>
    </body>
</html>
