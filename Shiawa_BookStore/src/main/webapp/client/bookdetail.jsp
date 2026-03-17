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
        </style>
    </head>

    <body>
        <jsp:include page="/client/layout/header.jsp" />

        <div class="container my-5">

            <div class="book-card shadow-sm">
                <div class="row g-4">
                    <div class="col-md-3 text-center">
                        <img src="${book.urlImg}" class="img-fluid rounded shadow-sm" alt="${book.title}">
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
                        <form action="${pageContext.request.contextPath}/cart" method="post">
                            <input type="hidden" name="book_id" value="${book.bookId}">
                            <input type="hidden" name="action" value="add">

                            <div class="d-flex gap-2">
                                <button type="submit" name="redirect" value="checkout" class="btn btn-buy px-5 py-3 fw-bold shadow-sm">
                                    <i class="bi bi-lightning-fill"></i> MUA NGAY
                                </button>

                                <button type="submit" name="redirect" value="cart" class="btn btn-cart px-4 py-3 fw-bold shadow-sm">
                                    <i class="bi bi-cart-plus"></i> THÊM VÀO GIỎ
                                </button>
                            </div>
                        </form>
                    </div>

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
        </div>

        <div class="mt-5">
            <h5 class="section-title mb-4">Có thể bạn cũng thích</h5>

            <div class="row row-cols-2 row-cols-md-6 g-3">
                <c:forEach items="${similarBooks}" var="b">

                    <c:if test="${b.bookId != book.bookId}">
                        <div class="col">
                            <a href="${pageContext.request.contextPath}/bookdetail?id=${b.bookId}"
                               style="text-decoration: none; color: inherit;">

                                <div class="book-item shadow-sm">
                                    <img src="${b.urlImg}" class="img-fluid mb-2" style="height: 150px; object-fit: cover;">
                                    <p class="mb-1 text-truncate fw-bold">${b.title}</p>
                                    <p class="small text-muted mb-1">${b.category.categoryName}</p>
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
</body>

</html>