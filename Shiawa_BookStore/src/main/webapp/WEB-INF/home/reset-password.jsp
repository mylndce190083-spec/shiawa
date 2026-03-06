<%-- 
    Document   : forgot
    Created on : Mar 2, 2026, 4:55:31 AM
    Author     : Lenovo
--%>

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
        <header class="header">

            <!-- LOGO -->
            <div class="logo" id="backToShop">
                <img src="${pageContext.request.contextPath}/assets/img/logo.jpg" class="rounded-img">
            </div>

            <!-- SEARCH (GIỮA) -->
            <div class="search-box">
                <input type="text">
                <button>
                    <i class="fa-solid fa-magnifying-glass"></i>
                </button>
            </div>

            <!-- ICONS -->
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
        <section class="account-page">

            <h2>Đặt lại mật khẩu</h2>

            <!-- LOGIN -->
            <div class="modal-overlay">
                <div class="modal-box">
                    <h2>Đặt lại mật khẩu</h2>

                    <form action="reset-password" method="post" class="modal-form">

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

                        <div class="modal-link">
                            <a href="login">Quay lại đăng nhập</a>
                        </div>

                    </form>
                </div>
            </div>

        </section>

        <script src="assets/main.js"></script>
    </body>
</html>
