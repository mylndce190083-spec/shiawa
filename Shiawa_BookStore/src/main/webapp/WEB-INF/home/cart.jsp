
<%-- 
    Document   : cart
    Created on : Feb 2, 2026
    Author     : MY
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Your Cart | Book Store</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link href="${pageContext.request.contextPath}/assets/css/css.css" rel="stylesheet" type="text/css"/>

    </head>
    <style>

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
        .book-title-link{
            text-decoration:none;
            color:inherit;
        }

        .book-title-link:hover{
            color:#e53935;
        }
    </style>
    <body>

        <c:if test="${not empty success}">
            <div style="color: green; font-weight: bold;">
                ${success}
            </div>
            <c:remove var="success" scope="session"/>
        </c:if>

        <jsp:include page="/client/layout/header.jsp"/>


        <section class="cart-page" id="cartPage">

            <h2>Giỏ hàng</h2>

            <div class="cart-header">
                <span>
                    <input type="checkbox" id="selectAll">Tất cả
                </span>
                <span>Sách</span>
                <span>Giá</span>
                <span>Số lượng</span>
                <span>Tổng</span>
            </div>


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
                                    <a href="${pageContext.request.contextPath}/bookdetail?id=${item.bookId}">

                                        <img src="${pageContext.request.contextPath}/image?file=${item.book.urlImg}" >
                                    </a>

                                    <a href="${pageContext.request.contextPath}/bookdetail?id=${item.bookId}" 
                                       class="book-title-link">
                                        <p class="book-title">${item.book.title}</p>
                                    </a>
                                </div>

                                <span class="price">
                                    <fmt:formatNumber 
                                        value="${item.price}" 
                                        type="number"
                                        groupingUsed="true"
                                        maxFractionDigits="0"/> VND</span>

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


                                <span class="subtotal" id="subtotal-${item.bookId}">

                                    <fmt:formatNumber 
                                        value=" ${item.price * item.quantity}" 
                                        type="number"
                                        groupingUsed="true"
                                        maxFractionDigits="0"/> VND
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

                                Tổng: $<span id="totalPrice"></span>
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
                        total.toLocaleString('vi-VN');
            }

            document.querySelectorAll('.select-item').forEach(cb => {
                cb.addEventListener('change', function () {

                    updateTotal();

                    const allItems = document.querySelectorAll(".select-item");
                    const checkedItems = document.querySelectorAll(".select-item:checked");

                    document.getElementById("selectAll").checked =
                            allItems.length === checkedItems.length;
                });
            });
            function updateQty(bookId, action) {

                fetch("${pageContext.request.contextPath}/cart", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",

                        "X-Requested-With": "XMLHttpRequest"

                    },
                    body: "action=" + action + "&book_id=" + bookId
                })
                        .then(response => response.json())
                        .then(data => {


                            document.getElementById("qty-" + bookId).innerText = data.quantity;

                            updateCartBadge(data.totalCartItems);

                            let checkbox = document.querySelector(
                                    "input.select-item[value='" + bookId + "']"
                                    );

                            if (checkbox) {


                                let price = parseFloat(checkbox.dataset.price);


                                let newSubtotal = price * data.quantity;

                                document.getElementById("subtotal-" + bookId)
                                        .innerText = "$" + newSubtotal.toFixed(2);


                                checkbox.dataset.qty = data.quantity;
                            }


                            updateTotal();

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
                            document.getElementById("row-" + bookId).remove();

                            updateCartBadge(data.totalCartItems);

                        })
                        .catch(error => console.error("Error:", error));
            }

            document.getElementById("selectAll").addEventListener("change", function () {

                const isChecked = this.checked;

                document.querySelectorAll(".select-item").forEach(cb => {
                    cb.checked = isChecked;
                });

                updateTotal();
            });
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