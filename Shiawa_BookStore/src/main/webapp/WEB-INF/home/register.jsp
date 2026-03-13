<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Đăng kí</title>
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
                <a href="${pageContext.request.contextPath}/login" class="tab" data-tab="login">Đăng nhập</a>
                <a href="${pageContext.request.contextPath}/register" class="tab active" data-tab="register">Đăng ký</a>
            </div>

            <!-- REGISTER -->
            <div class="tab-content active" id="register">
                <form action="register" method="post">
                    <label>Tên tài khoản</label>
                    <input type="text" name="username" placeholder="Name">
                    <label>Email</label>
                    <input type="email" name="email" placeholder="Email">
                    <label>Mật khẩu</label>
                    <input type="password" name="password" placeholder="Password">
                    <label>Xác nhận mật khẩu</label>
                    <input type="password" name="confirmPassword" placeholder="Confirm Password">
                    <p style="color:red;">
                        ${error}
                    </p>
                    <input type="text" name="fullName">

                    <button type="submit" class="submit-btn">Create Account</button>
                </form>
            </div>

        </section>

        <script src="assets/main.js"></script>
    </body>
</html>
