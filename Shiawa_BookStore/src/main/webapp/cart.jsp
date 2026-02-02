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

    <!-- Font Awesome -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

    <!-- CSS -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css.css">
</head>

<body>

<section class="cart-page" id="cartPage">

    <h2>Your Cart</h2>

    <div class="cart-header">
        <span></span>
        <span>Book</span>
        <span>Price</span>
        <span>Quantity</span>
        <span>Subtotal</span>
    </div>

    <!-- CART ITEMS -->
    <c:if test="${empty cartItems}">
        <p>Your cart is empty.</p>
    </c:if>

    <c:forEach var="item" items="${cartItems}">
        <div class="cart-item">

            <input type="checkbox"
                   class="select-item"
                   data-price="${item.price}"
                   data-qty="${item.quantity}">

            <div class="product">
                <img src="${item.imageUrl}" alt="${item.title}">
                <span>${item.title}</span>
            </div>

            <span>$${item.price}</span>

            <div class="quantity">
                <form action="update-cart" method="post">
                    <input type="hidden" name="bookId" value="${item.bookId}">

                    <button type="submit" name="action" value="decrease">-</button>

                    <span>${item.quantity}</span>

                    <button type="submit" name="action" value="increase">+</button>
                </form>
            </div>

            <span class="subtotal">
                $${item.price * item.quantity}
            </span>
        </div>
    </c:forEach>

    <!-- VOUCHER -->
    <div class="voucher">
        <input type="text" name="voucher" placeholder="Nhập mã giảm giá">
        <button type="button">Áp dụng</button>
        <p class="voucher-message"></p>
    </div>

    <!-- TOTAL -->
    <div class="cart-footer">
        <strong>
            Total: $
            <c:set var="total" value="0"/>
            <c:forEach var="item" items="${cartItems}">
                <c:set var="total"
                       value="${total + (item.price * item.quantity)}"/>
            </c:forEach>
            ${total}
        </strong>
    </div>

    <div class="cart-actions">
        <button class="pay-btn">PAY NOW</button>
    </div>

</section>

</body>
</html>
