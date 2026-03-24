<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
    <head>
        <title>${book.title} | Shiawa</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
        <link href="${pageContext.request.contextPath}/assets/css/css.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

        <style>
            body {
                background-color: #f5f5f5;
            }

            :root {
                --green-main: #2e7d32;
                --green-light: #4caf50;
            }

            .book-card {
                background: #fff;
                border-radius: 12px;
                padding: 24px;
            }

            .price {
                color: var(--green-main);
                font-size: 32px;
                font-weight: bold;
            }

            .btn-buy {
                background: var(--green-main);
                color: #fff;
                border-radius: 25px;
            }

            .btn-buy:hover {
                background: #256428;
                color: #fff;
            }

            .btn-cart {
                border: 2px solid var(--green-main);
                color: var(--green-main);
                border-radius: 25px;
                display: inline-block;
                text-decoration: none;
                transition: 0.3s;
            }

            .btn-cart:hover {
                background: var(--green-main);
                color: #fff;
            }

            .stock {
                color: #388e3c;
                font-weight: 500;
            }

            .section-title {
                color: var(--green-main);
                font-weight: bold;
            }

            .book-item {
                text-align: center;
                padding: 10px;
                background: #fff;
                border-radius: 8px;
                transition: transform 0.2s;
            }

            .book-item:hover {
                transform: translateY(-5px);
            }


            .notification-overlay {
                visibility: hidden;
                opacity: 0;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.5); /* Nền mờ phía sau */
                display: flex;
                justify-content: center;
                align-items: center;
                z-index: 9999;
                transition: opacity 0.3s ease;
            }

            /* Khi bấm nút, URL có đuôi #success-pop sẽ kích hoạt mục này */
            .notification-overlay:target {
                visibility: visible;
                opacity: 1;
            }

            .notification-box {
                background: white;
                padding: 40px;
                border-radius: 15px;
                text-align: center;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
                max-width: 400px;
                width: 90%;
            }

            .notification-box i {
                font-size: 60px;
                color: var(--green-main);
                display: block;
                margin-bottom: 20px;
            }

            .btn-ok {
                display: inline-block;
                margin-top: 25px;
                padding: 10px 40px;
                background: var(--green-main);
                color: white;
                text-decoration: none;
                border-radius: 25px;
                font-weight: bold;
            }
            .btn-ok:hover {
                background: #1b5e20;
                color: white;
            }
            /* FORM CHỈNH SỬA */
            .form-container {
                position: relative; /* QUAN TRỌNG */
            }

            .edit-btn {
                position: absolute;
                top: 10px;
                right: 10px;

                color: #e53935;   /* 🔴 chữ đỏ */
                background: none; /* ❌ không nền */
                border: none;
                border-radius: 6px;
                cursor: pointer;
                font-size: 13px;
            }

            .edit-btn:hover {
                background: #0056b3;
            }
            button:disabled {
                background: gray !important;
                border: none !important;
                color: white !important;
                cursor: not-allowed;
                opacity: 0.7;
            }
        </style>
    </head>

    <body>
        <jsp:include page="/client/layout/header.jsp" />
        <nav class="breadcrumb">

            <a href="home">Trang chủ</a>

            <span>›</span>

            <a href="home?id=${book.category.categoryId}">
                ${book.category.categoryName}
            </a>

            <span>›</span>

            <span class="current">
                ${book.title}
            </span>

        </nav>
        <div class="container my-5">

            <div class="book-card shadow-sm">
                <div class="row g-4">
                    <div class="col-md-3 text-center">
                        <img src="${pageContext.request.contextPath}/image?file=${book.urlImg}" class="img-fluid rounded shadow-sm" alt="${book.title}">
                    </div>

                    <div class="col-md-9">
                        <h3 class="fw-bold">${book.title}</h3>
                        <p class="text-muted mb-2">by <span class="fw-semibold">${book.author}</span></p>

                        <div class="price mb-2">$${book.price}</div>

                        <div class="mt-4">
                            <ul class="list-unstyled text-secondary">
                                <li>
                                    <strong>Danh mục:</strong> ${book.category.categoryName}
                                </li>
                                <li>
                                    <strong>Nhà xuất bản:</strong> ${book.publisher}
                                </li>
                                <li>
                                    <strong>Giảm giá:</strong> ${book.discount}%
                                </li>
                                <li>
                                    <strong>Ngày tạo:</strong>
                                    ${book.createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}
                                </li>
                            </ul>
                            <hr>
                            <div class="mt-3">
                                <h5 class="fw-bold">Mô tả chi tiết</h5>
                                <p class="text-muted">
                                    ${book.description}
                                </p>
                            </div>
                        </div>
                        <div class="stock mb-4">
                            <i class="bi bi-check-circle-fill"></i>
                            ${book.stock} sản phẩm có sẵn
                        </div>
                        <div class="d-flex gap-3">

                            <!-- MUA NGAY -->
                            <form action="${pageContext.request.contextPath}/checkout" method="post">
                                <input type="hidden" name="book_id" value="${book.bookId}">
                                <input type="hidden" name="action" value="buy_now">

                                <button type="submit"
                                        class="btn btn-buy px-5 py-2 fw-bold"
                                        ${book.stock == 0 ? "disabled" : ""}>
                                    <i class="bi bi-lightning-fill"></i> Mua ngay
                                </button>
                            </form>

                            <!-- THÊM GIỎ -->
                            <form onsubmit="addToCart(event, ${book.bookId})">
                                <button type="submit"
                                        class="btn btn-cart px-5 py-2 fw-bold"
                                        ${book.stock == 0 ? "disabled" : ""}>
                                    <i class="bi bi-cart-plus"></i> Thêm giỏ hàng
                                </button>
                            </form>

                        </div>
                    </div>

                    <div class="mt-5 pt-4 border-top">
                        <h5 class="section-title mb-3">Giới thiệu sách</h5>
                        <p class="text-secondary">${book.description}</p>
                    </div>
                </div>

                <div class="mt-5">
                    <h4 class="mb-4">Đánh giá từ khách hàng</h4>

                    <c:if test="${empty feedbackList}">
                        <p class="text-muted">Chưa có đánh giá nào cho cuốn sách này.</p>
                    </c:if>

                    <c:forEach items="${feedbackList}" var="fb">
                        <div class="card mb-3 border-0 border-bottom">
                            <div class="form-container">

                                <c:if test="${fb.userId == sessionScope.user.id}">

                                    <%-- Tính thời gian hết hạn --%>
                                    <%
                                        java.time.LocalDateTime createdAt = ((model.Feedback) pageContext.getAttribute("fb")).getCreatedAt();
                                        java.time.LocalDateTime now = (java.time.LocalDateTime) request.getAttribute("currentTime");

                                        boolean isExpired = false;
                                        if (createdAt != null && now != null) {
                                            long hours = java.time.Duration.between(createdAt, now).toHours();
                                            isExpired = (hours >= 24);
                                        }
                                        pageContext.setAttribute("isExpired", isExpired);
                                    %>

                                    <div class="edit-btn"
                                         style="cursor: pointer; color: #e53935; font-weight: bold;"
                                         onclick="checkEditStatus(${isExpired ? 'true' : 'false'}, '${pageContext.request.contextPath}/feedback?book_id=${fb.bookId}&order_detail_id=${fb.orderdetailId}')">

                                        <i class="fa-solid fa-pen"></i> Sửa
                                    </div>

                                </c:if>

                            </div>

                            <div class="card-body">
                                <div class="d-flex align-items-center mb-2">
                                    <div class="text-warning me-2">
                                        <c:forEach begin="1" end="${fb.rating}">★</c:forEach>
                                        </div>
                                        <small class="text-muted">| Người dùng: ${fb.username}</small>
                                </div>
                                <p class="card-text">${fb.content}</p>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <script>
                    function checkEditStatus(isExpired, url) {
                        if (isExpired === true) {
                            alert("Thông báo: Đã quá 24 giờ kể từ khi gửi đánh giá. Bạn không thể chỉnh sửa được nữa!");
                        } else {
                            window.location.href = url;
                        }
                    }
                </script>

                <div class="mt-5">
                    <h5 class="section-title mb-4">Có thể bạn cũng thích</h5>

                    <div class="row row-cols-2 row-cols-md-6 g-3">
                        <c:forEach items="${similarBooks}" var="b">

                            <c:if test="${b.bookId != book.bookId}">
                                <div class="col">
                                    <a href="${pageContext.request.contextPath}/bookdetail?id=${b.bookId}"
                                       style="text-decoration: none; color: inherit;">

                                        <div class="book-item shadow-sm">
                                            <img src="${pageContext.request.contextPath}/image?file=${b.urlImg}" class="img-fluid mb-2" style="height: 150px; object-fit: cover;">
                                            <p class="mb-1 text-truncate fw-bold">${b.title}</p>
                                            <p class="small text-muted mb-1">${b.category.categoryName}</p>
                                            <p class="sold">Đã bán ${b.sold}</p>
                                            <p class="text-success fw-bold">$${b.price}</p>
                                        </div>
                                    </a>
                                </div>
                            </c:if>

                        </c:forEach>
                    </div>
                </div>
            </div>

            <div id="success-pop" class="notification-overlay">
                <div class="notification-box">
                    <i class="bi bi-check-circle-fill"></i>
                    <h3 class="fw-bold">Đã thêm vào giỏ hàng</h3>
                    <p class="text-muted">Sách <strong>${book.title}</strong> đã nằm trong giỏ hàng của bạn.</p>

                    <a href="#" class="btn-ok">OK</a>
                </div>
            </div>
            <jsp:include page="./layout/footer.jsp" />
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
            <script>
            const isLoggedIn = ${sessionScope.user != null ? 'true' : 'false'};

            function addToCart(event, bookId) 
            {
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
            updateCartBadge(data.totalCartItems);
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