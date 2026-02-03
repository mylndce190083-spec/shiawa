<%-- 
    Document   : home
    Created on : Jan 30, 2026, 2:18:34 PM
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <title>Book Store</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link rel="stylesheet" href="assets/css.css">

    </head>

    <body>

        <header class="header">

            <!-- LOGO -->
            <a href="${pageContext.request.contextPath}/home">
                <div class="logo" id="backToShop">
                    <img src="assets/log.jpg" class="rounded-img">
                </div>
            </a>


            <!-- SEARCH (GIỮA) -->
            <div class="search-box">
                <input type="text">
                <button>
                    <i class="fa-solid fa-magnifying-glass"></i>
                </button>
            </div>

            <!-- ICONS -->

            <div class="icons">
                <a href="cart" class="icon" id="cartIcon">
                    <i class="fa-solid fa-cart-shopping"></i>
                    <span>Giỏ hàng</span>
                </a>
            </div>

            <div class="icon" id="accountIcon">
                <i class="fa-regular fa-user"></i>
                <span>Tài khoản</span>
            </div>
       

    </header>
    <nav class="breadcrumb">
        <a href="#">Trang chủ</a>
        <span>›</span>
        <a href="#">Siêu ưu đãi</a>
        <span>›</span>
        <span class="current">Mua sắm</span>
    </nav>

    <hr>

    <!-- MENU -->
    <nav class="menu">
        <a href="#" data-filter="all">Tất cả sách</a>
        <c:forEach items="${listC}" var="c">
            <a href="#" data-filter="${c.cateName}">${c.cateName}</a>
        </c:forEach>
    </nav>


    <!-- CONTENT -->
    <section class="books">

        <c:forEach items="${listB}" var="b">
            <div class="book" data-category="${b.cate.cateName}" data-name="${b.title}" data-price="${b.price}">
                <img src="${b.imgUrl}">
                <p class="title">${b.title}</p>
                <div class="price">
                    <span class="new-price">${b.price}đ</span>
                    <span class="discount">-${b.discount}%</span>
                </div>
                <p class="sold">Đã bán 120</p>
                <form action="${pageContext.request.contextPath}/cart" method="post">
                    <input type="hidden" name="book_id" value="${b.book_id}">
                    <input type="hidden" name="quantity" value="1">

                    <button type="submit" class="add-cart">
                        Thêm giỏ hàng
                    </button>
                </form>

            </div>
        </c:forEach>
    </section>

    <!--    <section class="cart-page" id="cartPage" style="display:none;">
    
            <h2>Your Cart</h2>
    
            <div class="cart-header">
                <span></span>
                <span>Book</span>
                <span>Price</span>
                <span>Quantity</span>
                <span>Subtotal</span>
            </div>
    
            <div id="cartItems"></div>
            <div class="cart-item">
                <input type="checkbox" class="select-item" data-price="10" data-qty="1">
    
                <div class="product">
                    <img src="https://via.placeholder.com/80x100">
                    <span>Book name</span>
                </div>
    
                <span>$10</span>
    
                <div class="quantity">
                    <button>-</button>
                    <span>1</span>
                    <button>+</button>
                </div>
    
                <span class="subtotal">$10</span>
            </div>
    
            <div class="cart-item">
                <input type="checkbox" class="select-item" data-price="15" data-qty="1">
    
                <div class="product">
                    <img src="https://via.placeholder.com/80x100">
                    <span>Another book</span>
                </div>
    
                <span>$15</span>
    
                <div class="quantity">
                    <button>-</button>
                    <span>1</span>
                    <button>+</button>
                </div>
    
                <span class="subtotal">$15</span>
            </div>
    
             VOUCHER 
            <div class="voucher">
                <input type="text" id="voucherInput" placeholder="Nhập mã giảm giá">
                <button id="applyVoucher">Áp dụng</button>
                <p id="voucherMessage"></p>
            </div>
    
             TOTAL 
            <div class="cart-footer">
                <strong id="totalPrice">Total: $0</strong>
            </div>
            <div class="cart-actions"> <button class="pay-btn">PAY NOW</button> </div>
        </div>
    </section>-->

    <!-- ACCOUNT PAGE -->
    <section class="account-page" id="accountPage" style="display:none;">

        <h2>Tài khoản</h2>

        <!-- TABS -->
        <div class="account-tabs">
            <span class="tab active" data-tab="login">Đăng nhập</span>
            <span class="tab" data-tab="register">Đăng ký</span>
        </div>

        <!-- LOGIN -->
        <div class="tab-content active" id="login">
            <label>Số điện thoại / Email</label>
            <input type="text" placeholder="Nhập số điện thoại hoặc email">

            <label>Mật khẩu</label>
            <input type="password" placeholder="Nhập mật khẩu">

            <a href="#" class="forgot">Quên mật khẩu?</a>

            <button class="submit-btn">Đăng nhập</button>
        </div>

        <!-- REGISTER -->
        <div class="tab-content" id="register">
            <input type="text" placeholder="First Name">
            <input type="text" placeholder="Last Name">
            <input type="email" placeholder="Email">
            <input type="password" placeholder="Password">

            <button class="submit-btn">Create Account</button>
        </div>

    </section>

    <script src="assets/main.js"></script>
</body>


</html>
