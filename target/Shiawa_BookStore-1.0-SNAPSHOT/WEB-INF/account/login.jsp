<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Đăng nhập</title>
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

            <h2>Tài khoản</h2>

            <!-- TABS -->
            <div class="account-tabs">
                <span class="tab active" data-tab="login">Đăng nhập</span>
                <span class="tab" data-tab="register">Đăng ký</span>
            </div>

            <!-- LOGIN -->
            <div class="tab-content active" id="login">
                <form action="login" method="post">
                    <label>Email / Số điện thoại</label>
                    <input type="text" name="username" placeholder="Email hoặc số điện thoại" required>
                    <label>Mật khẩu</label>
                    <input type="password" name="password" placeholder="Mật khẩu" required>

                    <a href="#" class="forgot">Quên mật khẩu?</a>

                    <button type="submit" class="submit-btn">Đăng nhập</button>
                </form>
            </div>

            <!-- REGISTER -->
            <div class="tab-content" id="register">
                <form action="register" method="post">
                    <input type="text" name="firstName" placeholder="First Name">
                    <input type="text" name="lastName" placeholder="Last Name">
                    <input type="email" name="email" placeholder="Email">
                    <input type="password" name="password" placeholder="Password">

                    <button type="submit" class="submit-btn">Create Account</button>
                </form>
            </div>

        </section>

        <script src="assets/main.js"></script>
    </body>
</html>
