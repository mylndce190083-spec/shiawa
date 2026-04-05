<%-- 
    Document   : home
    Created on : Feb 2, 2026, 5:00:04 PM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <title>Book Store</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link href="${pageContext.request.contextPath}/assets/css/css.css" rel="stylesheet" type="text/css" />

        <style>
            .support-chat-widget {
                position: fixed;
                right: 24px;
                bottom: 120px;
                z-index: 9999;
                width: 56px;
                height: 56px;
                border-radius: 50%;
                background: #198754;
                color: #fff;
                display: flex;
                align-items: center;
                justify-content: center;
                box-shadow: 0 6px 16px rgba(0,0,0,0.2);
                text-decoration: none;
            }
            .support-chat-widget:hover {
                background: #157347;
                color: #fff;
            }

            .category-nav {
                background-color: #f1f8f1; 
                padding: 10px 0;
                border-bottom: 1px solid #ddd;
            }

            .menu-container {
                display: flex;
                justify-content: center;
                gap: 25px;
                list-style: none;
                margin: 0;
                padding: 0;
                flex-wrap: nowrap; 
            }

            .menu-item {
                position: relative;
            }

            .parent-link {
                font-weight: 600;
                color: #2e7d32;
                text-decoration: none;
                font-size: 15px;
                display: flex;
                align-items: center;
                gap: 5px;
                white-space: nowrap;
            }

            .parent-link:hover {
                color: #ff9800;
            }


            .child-dropdown {
                display: none;
                position: absolute;
                top: 95%;
                left: 0;
                background-color: #ffffff;
                min-width: 200px;
                box-shadow: 0 8px 16px rgba(0,0,0,0.1);
                z-index: 1000;
                border-radius: 4px;
                padding: 8px 0;
                border: 1px solid #eee;
                margin-top: 0;
            }
            .child-dropdown::before {
                content: "";
                position: absolute;
                top: -15px;
                left: 0;
                width: 100%;
                height: 20px;
                background: transparent;
            }
            .child-dropdown a {
                display: block;
                padding: 10px 20px;
                color: #333;
                text-decoration: none;
                font-size: 14px;
                transition: 0.2s;
            }

            .child-dropdown a:hover {
                background-color: #e8f5e9;
                color: #2e7d32;
                padding-left: 25px;
            }
            .menu-item:hover .child-dropdown {
                display: block;
            }

            .icon-down {
                font-size: 10px;
            }
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

            @keyframes progress {
                from {
                    width: 100%;
                }
                to {
                    width: 0%;
                }
            }

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
            .category-nav {
                background-color: #f1f8f1;
                padding: 12px 0;
                border-bottom: 1px solid #ddd;
            }
            .menu-container {
                display: flex;
                justify-content: center;
                gap: 30px;
            }
            .menu-item {
                position: relative;
            }
            .parent-link {
                font-weight: bold;
                color: #2e7d32;
                text-decoration: none;
                font-size: 14px;
                display: flex;
                align-items: center;
                gap: 5px;
            }
            .icon-down {
                font-size: 10px;
            }

            .child-dropdown {
                display: none;
                position: absolute;
                top: 100%;
                left: 0;
                background: white;
                min-width: 220px;
                box-shadow: 0 4px 12px rgba(0,0,0,0.15);
                z-index: 1000;
                border-radius: 4px;
                padding: 10px 0;
                margin-top: 5px;
            }
            .child-dropdown a {
                display: block;
                padding: 8px 20px;
                color: #333;
                text-decoration: none;
                font-size: 13px;
            }
            .child-dropdown a:hover {
                background: #e8f5e9;
                color: #2e7d32;
            }

            .menu-item:hover .child-dropdown {
                display: block;
            }
            .menu-item:hover .parent-link {
                color: #ff9800;
            }
        </style>
    </head>

    <body>

        <jsp:include page="/client/layout/header.jsp"/>
        <nav class="breadcrumb">

            <a href="home">Trang chủ</a>


        </nav>

        <hr>
        <nav class="category-nav">
            <div class="menu-container">
                <c:forEach items="${listC}" var="c">
                    <c:if test="${c.parentId == 0}">
                        <div class="menu-item">
                            <a href="#" class="parent-link">${c.categoryName} <i class="fa-solid fa-chevron-down icon-down"></i></a>
                            <div class="child-dropdown">
                                <c:forEach items="${listC}" var="child">
                                    <c:if test="${c.categoryId == child.parentId}">
                                        <%-- Trỏ về home kèm id để lọc --%>
                                        <a href="${pageContext.request.contextPath}/home?id=${child.categoryId}">${child.categoryName}</a>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </nav>

        <section class="books">
            <c:forEach items="${listB}" var="b">
                <div class="book" data-category="${b.category.categoryName}" data-name="${b.title}" data-price="${b.price}">
                    <a href="${pageContext.request.contextPath}/bookdetail?id=${b.bookId}" style="text-decoration: none; color: inherit;">
                        <img src="${pageContext.request.contextPath}/image?file=${b.urlImg}">
                        <p class="title">${b.title}</p>
                    </a>
                    <div class="price">
                        <span class="new-price">
                         <fmt:formatNumber 
                                value="${b.price}" 
                                type="number"
                                groupingUsed="true"
                                maxFractionDigits="0"/> VND</span>
                        <span class="discount">-${b.discount}%</span>
                    </div>
                    <p class="sold">Đã bán ${b.sold}</p>


                    <form onsubmit="addToCart(event, ${b.bookId}, ${b.stock})">

                        <c:choose>
                            <c:when test="${b.stock == 0}">
                                <button type="button" class="add-cart" disabled 
                                        style="background: gray; cursor: not-allowed;">
                                    Hết hàng
                                </button>
                            </c:when>

                            <c:otherwise>
                                <button type="submit" class="add-cart">
                                    Thêm giỏ hàng
                                </button>
                            </c:otherwise>
                        </c:choose>

                    </form>
                </div>
            </c:forEach>
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

            <div class="voucher">
                <input type="text" id="voucherInput" placeholder="Nhập mã giảm giá">
                <button id="applyVoucher">Áp dụng</button>
                <p id="voucherMessage"></p>
            </div>

            <div class="cart-footer">
                <strong id="totalPrice">Total: $0</strong>
            </div>
            <div class="cart-actions"> <button class="pay-btn">PAY NOW</button> </div>
        </div>
    </section>

    <section class="account-page" id="accountPage" style="display:none;">

        <h2>Tài khoản</h2>

        <!-- TABS -->
        <div class="account-tabs">
            <span class="tab active" data-tab="login">Đăng nhập</span>
            <span class="tab" data-tab="register">Đăng ký</span>
        </div>

        <div class="tab-content active" id="login">
            <label>Số điện thoại / Email</label>
            <input type="text" placeholder="Nhập số điện thoại hoặc email">

            <label>Mật khẩu</label>
            <input type="password" placeholder="Nhập mật khẩu">

            <a href="#" class="forgot">Quên mật khẩu?</a>

            <button class="submit-btn">Đăng nhập</button>
        </div>

        <div class="tab-content" id="register">
            <input type="text" placeholder="First Name">
            <input type="text" placeholder="Last Name">
            <input type="email" placeholder="Email">
            <input type="password" placeholder="Password">

            <button class="submit-btn">Create Account</button>
        </div>

    </section>

    <script>
        const isLoggedIn = ${sessionScope.user != null ? "true" : "false"};
        function addToCart(event, bookId) {
            event.preventDefault();

            if (!isLoggedIn) {
                window.location.href = "${pageContext.request.contextPath}/login";
                return;
            }

            fetch("${pageContext.request.contextPath}/cart", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                    "X-Requested-With": "XMLHttpRequest"
                },
                body: "action=add&book_id=" + bookId
            })
                    .then(res => res.json())
                    .then(data => {
                  
                        if (data && data.success === true) {
                            showToast(true, "Thêm giỏ hàng thành công!");
                            updateCartBadge(data.totalCartItems);
                        } else {
                
                            showToast(false, data.message || "Số lượng trong kho không đủ!");
                        }
                    });
        }

        function showToast(isSuccess, message) {
            const old = document.getElementById("unique-toast-id");
            if (old)
                old.remove();

            const toast = document.createElement("div");
            toast.id = "unique-toast-id";

   
            Object.assign(toast.style, {
                position: 'fixed',
                top: '30px',
                right: '20px',
                width: '320px',
                minHeight: '60px',
                backgroundColor: isSuccess ? '#28a745' : '#dc3545',
                color: '#ffffff',
                borderRadius: '8px',
                zIndex: '999999',
                display: 'flex',
                alignItems: 'center',
                padding: '15px 20px',
                boxShadow: '0 4px 12px rgba(0,0,0,0.5)',
                fontSize: '16px',
                fontWeight: 'bold',
                fontFamily: 'Arial, sans-serif'
            });

             const icon = isSuccess ? "🛒 " : "🛒 ";
            toast.innerText = icon + (message || (isSuccess ? "Thành công!" : "Thất bại!"));

            document.body.appendChild(toast);

           
            setTimeout(() => {
                toast.style.transform = 'translateY(0)';
            }, 100);
            setTimeout(() => {
                toast.style.transform = 'translateY(-150%)';
                setTimeout(() => toast.remove(), 200);
            }, 1000);
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
    <a class="support-chat-widget" href="${pageContext.request.contextPath}/chat" title="Customer Support">
        <i class="fa-solid fa-comment"></i>
    </a>
</body>


</html>