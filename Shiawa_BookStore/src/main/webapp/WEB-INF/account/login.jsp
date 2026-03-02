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
                <a href="${pageContext.request.contextPath}/login" class="tab active" data-tab="login">Đăng nhập</a>
                <a href="${pageContext.request.contextPath}/register" class="tab" data-tab="register">Đăng ký</a>
            </div>

            <!-- LOGIN -->
            <div class="tab-content active" id="login">
                <form action="login" method="post">
                    <label>Email</label>
                    <input type="text" name="email" placeholder="Email" required>
                    <label>Mật khẩu</label>
                    <input type="password" name="password" placeholder="Mật khẩu" required>
                    <p style="color:green;">
                        ${sessionScope.success}
                    </p>
                    <%
                        session.removeAttribute("success");
                    %>
                    <p style="color:red;">
                        ${sessionScope.error}
                    </p>
                    <%
                        session.removeAttribute("error");
                    %>
                    <p style="color:green;">
                        ${message}
                    </p>
                    <a href="${pageContext.request.contextPath}/forgot-password" class="forgot">Quên mật khẩu?</a>

                    <button type="submit" class="submit-btn">Đăng nhập</button>
                </form>
            </div>

        </section>

        <script src="assets/main.js"></script>
    </body>
</html>
