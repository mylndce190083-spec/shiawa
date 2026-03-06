<%-- 
    Document   : home
    Created on : Feb 2, 2026, 5:00:04 PM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <title>Book Store</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link href="${pageContext.request.contextPath}/assets/css/css.css" rel="stylesheet" type="text/css"/>

    </head>

    <style>
        .custom-toast {
            position: fixed;
            top: -120px;
            right: 20px;
            background: linear-gradient(135deg, #ff1e1e, #b30000);
            color: white;
            width: 340px;
            border-radius: 14px;
            overflow: hidden;
            box-shadow: 0 15px 35px rgba(255,0,0,0.4);
            transition: all 0.5s cubic-bezier(.68,-0.55,.27,1.55);
            z-index: 9999;
        }

        .custom-toast.show {
            top: 20px;
        }

        .toast-content {
            display: flex;
            align-items: center;
            padding: 16px;
        }

        .toast-content .icon {
            font-size: 26px;
            margin-right: 14px;
            animation: pop 0.4s ease;
        }

        .toast-content strong {
            font-size: 16px;
        }

        .toast-content .sub {
            font-size: 13px;
            opacity: 0.9;
        }

        .progress-bar {
            height: 4px;
            background: #fff;
            width: 100%;
            animation: progress 3s linear forwards;
        }

        /* Thanh chạy */
        @keyframes progress {
            from {
                width: 100%;
            }
            to {
                width: 0%;
            }
        }

        /* Icon nhảy nhẹ */
        @keyframes pop {
            0% {
                transform: scale(0.5);
            }
            80% {
                transform: scale(1.2);
            }
            100% {
                transform: scale(1);
            }
        }
        .cart-icon {
            position: relative;
        }

        .cart-badge {
            position: absolute;
            top: -6px;
            right: 0px;
            background: red;
            color: white;
            font-size: 12px;
            padding: 3px 6px;
            border-radius: 50px;
        }
    </style>
    <body>

        <jsp:include page="/client/layout/header.jsp"/>
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
                <a href="#" data-filter="${c.categoryName}">${c.categoryName}</a>
            </c:forEach>
        </nav>


        <!-- CONTENT -->
        <section class="books">

            <c:forEach items="${listB}" var="b">
                <div class="book" data-category="${b.category.categoryName}" data-name="${b.title}" data-price="${b.price}">
                    <img src="${pageContext.request.contextPath}/${b.urlImg}">
                    <p class="title">${b.title}</p>
                    <div class="price">
                        <span class="new-price">${b.price}đ</span>
                        <span class="discount">-${b.discount}%</span>
                    </div>
                    <p class="sold">Đã bán 120</p>
                    <!-- comment <form action="${pageContext.request.contextPath}/cart" method="post">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="book_id" value="${b.bookId}">
                        <button type="submit" class="add-cart">
                            Thêm giỏ hàng
                        </button>
                    </form> -->
                    <form onsubmit="addToCart(event, ${b.bookId})">
                        <button type="submit" class="add-cart">
                            Thêm giỏ hàng
                        </button>
                    </form>
                </div>
            </c:forEach>

            <div class="book" data-category="Business" data-name="Bùi Kiến Thành – Người Mở Khóa" data-price="150000">
                <img src="https://via.placeholder.com/160x220">
                <p class="title">Bùi Kiến Thành – Người Mở Khóa</p>
                <div class="price">
                    <span class="new-price">150.000đ</span>
                    <span class="discount">-15%</span>
                </div>
                <p class="sold">Đã bán 85</p>
                <button class="add-cart">Thêm giỏ hàng</button>
            </div>

            <div class="book" data-category="Novel" data-name="Stop Overthinking" data-price="90000">
                <img src="https://via.placeholder.com/160x220">
                <p class="title">Stop Overthinking</p>
                <div class="price">
                    <span class="new-price">90.000đ</span>
                    <span class="discount">-5%</span>
                </div>
                <p class="sold">Đã bán 200</p>
                <button class="add-cart">Thêm giỏ hàng</button>
            </div>

            <div class="book" data-category="Mystery" data-name="Những Mô Hình Tư Duy Vĩ Đại" data-price="180000">
                <img src="https://via.placeholder.com/160x220">
                <p class="title">Những Mô Hình Tư Duy Vĩ Đại</p>
                <div class="price">
                    <span class="new-price">180.000đ</span>
                    <span class="discount">-20%</span>
                </div>
                <p class="sold">Đã bán 60</p>
                <button class="add-cart">Thêm giỏ hàng</button>
            </div>

        </section>

        <section class="cart-page" id="cartPage" style="display:none;">

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

            <!-- VOUCHER -->
            <div class="voucher">
                <input type="text" id="voucherInput" placeholder="Nhập mã giảm giá">
                <button id="applyVoucher">Áp dụng</button>
                <p id="voucherMessage"></p>
            </div>

            <!-- TOTAL -->
            <div class="cart-footer">
                <strong id="totalPrice">Total: $0</strong>
            </div>
            <div class="cart-actions"> <button class="pay-btn">PAY NOW</button> </div>
        </div>
    </section>

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

    <script>
        function addToCart(event, bookId) {
            event.preventDefault();

            fetch("${pageContext.request.contextPath}/cart", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                    "X-Requested-With": "XMLHttpRequest"
                },
                body: "action=add&book_id=" + bookId
            })
                    .then(res => res.json())   // 🔥 ĐỔI Ở ĐÂY
                    .then(data => {

                        updateCartBadge(data.totalCartItems);  // 🔥 giờ mới đúng

                        showToast();
                    })
                    .catch(error => console.error(error));
        }

        function showToast() {

            const toast = document.createElement("div");
            toast.className = "custom-toast";
            toast.innerHTML = `
        <div class="toast-content">
            <span class="icon">🛒</span>
            <div>
                <strong>Thêm thành công!</strong>
                <div class="sub">Sản phẩm đã vào giỏ hàng</div>
            </div>
        </div>
        <div class="progress-bar"></div>
    `;

            document.body.appendChild(toast);

            setTimeout(() => {
                toast.classList.add("show");
            }, 10);

            setTimeout(() => {
                toast.classList.remove("show");
                setTimeout(() => toast.remove(), 300);
            }, 3000);
        }
        function updateCartBadge(count) {
            const badge = document.getElementById("cartBadge");

            if (count > 0) {
                badge.style.display = "inline-block";
                badge.innerText = count > 99 ? "99+" : count;
            } else {
                badge.style.display = "none";
            }
        }
    </script>
</body>


</html>

