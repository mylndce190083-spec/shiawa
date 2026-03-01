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

        <c:if test="${not empty success}">
            <div style="color: green; font-weight: bold;">
                ${success}
            </div>
            <c:remove var="success" scope="session"/>
        </c:if>
        <!--        <header class="header">
        
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


    </header>-->
        <jsp:include page="/client/layout/header.jsp" />



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

            <c:choose>
                <c:when test="${empty cartItem}">
                    <p>Giỏ hàng của bạn đang trống.</p>
                </c:when>

                <c:otherwise>


                    <form action="${pageContext.request.contextPath}/checkout" method="post">

                        <input type="hidden" name="action" value="preview">

                        <c:forEach var="item" items="${cartItem}">
                            <div id="row-${item.bookId}" class="cart-item">

                                <input type="checkbox"
                                       class="select-item"
                                       name="selectedItem"
                                       value="${item.bookId}"
                                       data-price="${item.price}"
                                       data-qty="${item.quantity}">

                                <div class="product">
                                    <img src="${pageContext.request.contextPath}/${item.book.urlImg}">
                                    <p class="book-title">${item.book.title}</p>
                                </div>

                                <span class="price">$${item.price}</span>

                                <div class="quantity">
                                    <div class="quantity-box">
                                        <button type="button"
                                                onclick="updateQty(${item.bookId}, 'decrease')">−</button>

                                        <span class="qty" id="qty-${item.bookId}">
                                            ${item.quantity}
                                        </span>

                                        <button type="button"
                                                onclick="updateQty(${item.bookId}, 'increase')">+</button>
                                    </div>
                                </div>

                                <span class="subtotal">
                                    $${item.price * item.quantity}
                                </span>

                                <button type="button"
                                        class="delete-btn"
                                        onclick="deleteItem(${item.bookId})">
                                    xóa
                                </button>
                            </div>
                        </c:forEach>

                        <div class="cart-footer">
                            <strong>
                                Total: $<span id="totalPrice">0</span>
                            </strong>
                        </div>
                        <!-- VOUCHER -->
                        <div class="voucher">
                            <input type="text" name="voucher" placeholder="Nhập mã giảm giá">
                            <button type="button">Áp dụng</button>
                            <p class="voucher-message"></p>
                        </div>
                        <div class="cart-actions">
                            <button type="submit" class="pay-btn">
                                Mua Ngay
                            </button>
                        </div>

                    </form>


                </c:otherwise>
            </c:choose>





            <!-- VOUCHER 
            <div class="voucher">
                <input type="text" name="voucher" placeholder="Nhập mã giảm giá">
                <button type="button">Áp dụng</button>
                <p class="voucher-message"></p>
            </div>-->

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
            <!--  <div class="cart-footer">
                  <strong>
                      Total: $<span id="totalPrice">0</span>
                  </strong>
              </div>
  
              <div class="cart-actions">
                  <form action="${pageContext.request.contextPath}/checkout" method="post">
                      <input type="hidden" name="action" value="preview">
                      <button type="submit" class="pay-btn">
                          PAY NOW
                      </button>
                  </form>
              </div>
            -->
        </section>

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
            function updateQty(bookId, action) {

                fetch("${pageContext.request.contextPath}/cart", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                        "X-Requested-With": "XMLHttpRequest"   // 🔥 QUAN TRỌNG
                    },
                    body: "action=" + action + "&book_id=" + bookId
                })
                        .then(response => response.json())
                        .then(data => {

                            document.getElementById("qty-" + bookId).innerText = data.quantity;

                            if (data.message) {
                                alert(data.message);
                            }

                        })
                        .catch(error => console.error("Error:", error));
            }
            function deleteItem(bookId) {

                fetch("${pageContext.request.contextPath}/cart", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                        "X-Requested-With": "XMLHttpRequest"
                    },
                    body: "action=delete&book_id=" + bookId
                })
                        .then(response => response.json())
                        .then(data => {

                            // xóa dòng khỏi giao diện
                            document.getElementById("row-" + bookId).remove();

                        })
                        .catch(error => console.error("Error:", error));
            }
        </script>



    </body>
</html>