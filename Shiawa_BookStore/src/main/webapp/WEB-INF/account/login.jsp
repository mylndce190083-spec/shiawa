<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Login</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link href="${pageContext.request.contextPath}/assets/css/css.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <jsp:include page="/client/layout/header.jsp"/>
        <section class="account-page">

            <h2>TÀI KHOẢN</h2>
            <!-- TABS -->
            <div class="account-tabs">
                <a href="${pageContext.request.contextPath}/login" class="tab active" data-tab="login">Đăng Nhập</a>
                <a href="${pageContext.request.contextPath}/register" class="tab" data-tab="register">Đăng Ký</a>
            </div>

            <!-- LOGIN -->
            <div class="tab-content active" id="login">
                <form action="login" method="post">
                    <label>Email</label>
                    <input type="text" name="email" placeholder="Email" required>
                    <label>Password</label>
                    <input type="password" name="password" placeholder="Mật khẩu" required>
                    <p style="color:green;">
                        ${sessionScope.success}
                    </p>
                    <%
                        session.removeAttribute("Success");
                    %>
                    <p style="color:red;">
                        ${sessionScope.error}
                    </p>
                    <%
                        session.removeAttribute("Error");
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
