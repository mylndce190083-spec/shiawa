<%-- 
    Document   : forgot
    Created on : Mar 2, 2026, 4:55:31 AM
    Author     : Lenovo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forget password</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link href="${pageContext.request.contextPath}/assets/css/css.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <c:if test="${empty sessionScope.user}">
            <header class="header">

                <div class="logo" id="backToShop">
                    <img src="${pageContext.request.contextPath}/assets/img/logo.jpg" class="rounded-img">
                </div>

                <div class="search-box">
                    <input type="text">
                    <button>
                        <i class="fa-solid fa-magnifying-glass"></i>
                    </button>
                </div>

                <div class="icons">
                    <div class="icon" id="cartIcon">
                        <i class="fa-solid fa-cart-shopping"></i>
                        <span>Giỏ hàng</span>
                    </div>


                    <div class="icon" id="accountIcon">
                        <i class="fa-regular fa-user"></i>
                        <span>Tài khoản</span>
                    </div>
                </div>

            </header>
        </c:if>
        <c:if test="${not empty sessionScope.user}">
            <div class="profile-wrapper">

                <div class="profile-sidebar">
                    <div class="avatar-box">
                        <img src="${pageContext.request.contextPath}/image?file=${customer.avatar}" 
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
                        <li>
                            <a href="${pageContext.request.contextPath}/reset-password">
                                <span>Đổi mật khẩu</span>
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/OrderList">
                                Lịch sử mua hàng
                            </a>

                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/logout">
                                <span>Đăng xuất</span>
                            </a>
                        </li>
                    </ul>
                </div>

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
        </c:if>
        <section class="account-page">

            <h2>Đặt lại mật khẩu</h2>

            <div class="modal-overlay">
                <div class="modal-box">
                    <h2>Đặt lại mật khẩu</h2>

                    <form action="reset-password" method="post" class="modal-form">

                        <c:if test="${not empty sessionScope.user}">
                            <div class="form-group">
                                <label>Mật khẩu cũ</label>
                                <input type="password" name="oldPassword" placeholder="Nhập mật khẩu cũ" required>
                            </div>
                        </c:if>

                        <div class="form-group">
                            <label>Mật khẩu mới</label>
                            <input type="password" name="password" placeholder="Nhập mật khẩu mới" required>
                        </div>

                        <div class="form-group">
                            <label>Xác nhận mật khẩu</label>
                            <input type="password" name="confirmPassword" placeholder="Nhập lại mật khẩu" required>
                        </div>

                        <p class="error">${error}</p>
                        <p class="success">${message}</p>

                        <button type="submit" class="modal-btn">Đặt lại mật khẩu</button>
                        <c:if test="${empty sessionScope.user}">
                            <div class="modal-link">
                                <a href="login">Quay lại đăng nhập</a>
                            </div>
                        </c:if>
                        <c:if test="${not empty sessionScope.user}">
                            <div class="modal-link">
                                <a href="profile">Quay lại</a>
                            </div>
                        </c:if>
                    </form>
                </div>
            </div>

        </section>

        <script src="assets/main.js"></script>
    </body>
</html>
