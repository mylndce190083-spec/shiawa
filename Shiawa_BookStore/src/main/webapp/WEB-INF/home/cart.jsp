
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
    </style>
    <body>

        <c:if test="${not empty success}">
            <div style="color: green; font-weight: bold;">
                ${success}
            </div>
            <c:remove var="success" scope="session"/>
        </c:if>
        <header class="header">

        <c:if test="${not empty success}">
            <div style="color: green; font-weight: bold;">
                ${success}
            </div>
            <div class="icon" id="cartIcon">
               <!-- <a href="${pageContext.request.contextPath}/cart">
                    <i class="fa-solid fa-cart-shopping"></i>
                    <span>Giỏ hàng</span>
                </a> -->
                <a href="${pageContext.request.contextPath}/cart" class="icon cart-icon">
                    <i class="fa-solid fa-cart-shopping"></i>
                    <c:set var="cartCount" value="0" />
                    <c:forEach var="item" items="${cartItem}">
                        <c:set var="cartCount" value="${cartCount + item.quantity}" />
                    </c:forEach>

                    <span id="cartBadge" class="cart-badge">
                        ${cartCount}
                    </span>
                    <span>Giỏ hàng</span>
                </a>
            </div>
            <c:if test="${not empty sessionScope.user}">
                <a href="${pageContext.request.contextPath}/OrderList" 
                   style="text-decoration:none; color:inherit;">
                    <div class="icon">
                        <i class="fa-solid fa-clipboard-list"></i>
                        <span>Danh sách mua hàng</span>
                    </div>
                </a>
            </c:if>

            <div class="icon" id="accountIcon">
                <i class="fa-regular fa-user"></i>
                <span>Tài khoản</span>
            </div>
        </c:if>
        <div class="icon" id="accountIcon">
            <i class="fa-regular fa-user"></i>
            <span>Tài khoản</span>
        </div>


    </header>-->
        <jsp:include page="/client/layout/header.jsp" />



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


                                <span class="subtotal" id="subtotal-${item.bookId}">
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

                                Tổng: $<span id="totalPrice">0</span>
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


                            // ✅ 1. Cập nhật số lượng hiển thị
                            document.getElementById("qty-" + bookId).innerText = data.quantity;
// 👇 server nên trả về totalCartItems
                            updateCartBadge(data.totalCartItems);
                            // ✅ 2. Lấy checkbox của item đó
                            let checkbox = document.querySelector(
                                    "input.select-item[value='" + bookId + "']"
                                    );

                            if (checkbox) {

                                // Lấy giá
                                let price = parseFloat(checkbox.dataset.price);

                                // ✅ 3. Tính lại subtotal
                                let newSubtotal = price * data.quantity;

                                document.getElementById("subtotal-" + bookId)
                                        .innerText = "$" + newSubtotal.toFixed(2);

                                // ✅ 4. Cập nhật lại data-qty
                                checkbox.dataset.qty = data.quantity;
                            }

                            // ✅ 5. Tính lại tổng tiền
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

                            // xóa dòng khỏi giao diện
                            document.getElementById("row-" + bookId).remove();

                            updateCartBadge(data.totalCartItems);
                         
                        })
                        .catch(error => console.error("Error:", error));
            }
            // ===== CHỌN TẤT CẢ =====
            document.getElementById("selectAll").addEventListener("change", function () {

                const isChecked = this.checked;

                document.querySelectorAll(".select-item").forEach(cb => {
                    cb.checked = isChecked;
                });

                updateTotal(); // cập nhật lại tổng tiền
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