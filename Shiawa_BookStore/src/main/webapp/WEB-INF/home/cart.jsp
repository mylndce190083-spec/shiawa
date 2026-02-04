<%-- 
    Document   : cart
    Created on : Feb 2, 2026
    Author     : MY
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Your Cart | Book Store</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link href="${pageContext.request.contextPath}/assets/css/css.css" rel="stylesheet" type="text/css"/>

    </head>
    <body>
        <header class="header">

            <a href="${pageContext.request.contextPath}/home">
                <div class="logo" id="backToShop">
                    <img src="${pageContext.request.contextPath}/assets/img/logo.jpg" class="rounded-img">
                </div>
            </a>

            <div class="search-box">
                <input type="text">
                <button>
                    <i class="fa-solid fa-magnifying-glass"></i>
                </button>
            </div>
            <div class="icon" id="cartIcon">
                <a href="${pageContext.request.contextPath}/cart">
                    <i class="fa-solid fa-cart-shopping"></i>
                    <span>Giỏ hàng</span>
                </a>
            </div>
            <c:if test="${not empty sessionScope.user}">
                <div class="icon" id="orderIcon">
                    <i class="fa-solid fa-clipboard-list"></i>
                    <span>Order list</span>
                </div>
            </c:if>
            <div class="icon" id="accountIcon">
                <i class="fa-regular fa-user"></i>
                <span>Tài khoản</span>
            </div>


        </header>


                    
        <!--section class="cart-page" id="cartPage"-->

            <h2>Your Cart</h2>

            <div class="cart-header">
                <span></span>
                <span>Book</span>
                <span>Price</span>
                <span>Quantity</span>
                <span>Subtotal</span>
            </div>

            <!-- CART ITEMS -->

            <c:choose>
                <c:when test="${empty cartItem}">
                    <p>Giỏ hàng của bạn đang trống.</p>
                </c:when>

                <c:otherwise>
                    <c:forEach var="item" items="${cartItem}">
                        <!-- giữ nguyên toàn bộ cart-item -->
                        <div class="cart-item">

                            <input type="checkbox"
                                   class="select-item"
                                   data-book-id="${item.bookId}"
                                   data-price="${item.price}"
                                   data-qty="${item.quantity}">

                            <div class="product">
                                <img src="${pageContext.request.contextPath}/${item.book.urlImg}"
                                     alt="${item.book.title}">
                                <p class="book-title">${item.book.title}</p>
                            </div>

                            <span class="price">$${item.price}</span>

                            <div class="quantity">
                                <form action="${pageContext.request.contextPath}/cart" method="post">
                                    <input type="hidden" name="book_id" value="${item.bookId}">

                                    <button name="action" value="decrease">−</button>
                                    <span class="qty">${item.quantity}</span>
                                    <button name="action" value="increase">+</button>
                                </form>
                            </div>

                            <span class="subtotal">
                                $${item.price * item.quantity}
                            </span>



                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            <form action="${pageContext.request.contextPath}/cart"
                  method="post"
                  class="delete-form">
                <input type="hidden" name="book_id" value="${item.bookId}">
                <button name="action" value="delete" class="delete-btn">
                    🗑 
                </button>
            </form>




            <!-- VOUCHER -->
            <div class="voucher">
                <input type="text" name="voucher" placeholder="Nhập mã giảm giá">
                <button type="button">Áp dụng</button>
                <p class="voucher-message"></p>
            </div>

            <!-- TOTAL -->
            <!--            <div class="cart-footer">
                            <strong>
                                Total: $
            <c:set var="total" value="0"/>
            <c:forEach var="item" items="${cartItem}">
                <c:set var="total"
                       value="${total + (item.price * item.quantity)}"/>
            </c:forEach>
            ${total}
        </strong>
    </div>-->
            <div class="cart-footer">
                <strong>
                    Total: $<span id="totalPrice">0</span>
                </strong>
            </div>

            <div class="cart-actions">
                <button class="pay-btn">PAY NOW</button>
            </div>

        <!--/section-->

        <script>
            function updateTotal() {
                let total = 0;

                document.querySelectorAll('.select-item:checked').forEach(cb => {
                    const price = parseFloat(cb.dataset.price);
                    const qty = parseInt(cb.dataset.qty);
                    total += price * qty;
                });

                document.getElementById('totalPrice').innerText =
                        total.toFixed(2);
            }

            // gắn sự kiện cho checkbox
            document.querySelectorAll('.select-item').forEach(cb => {
                cb.addEventListener('change', updateTotal);
            });
        </script>



    </body>
</html>
