<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
            <%@page import="java.time.format.DateTimeFormatter" %>
                <!DOCTYPE html>
                <html>

                <head>
                    <title>${book.title} | Shiawa</title>

                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
                        rel="stylesheet">
                    <link rel="stylesheet"
                        href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

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

                        .similar-card img {
                            height: 180px;
                            object-fit: cover;
                            border-radius: 8px;
                        }
                    </style>
                </head>

                <body>
                    <jsp:include page="./layout/header.jsp" />

                    <div class="container my-5">

                        <!-- BOOK DETAIL -->
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
                                                <strong>Danh mục:</strong> ${book.category.cateName}
                                            </li>
                                            <li>
                                                <strong>Nhà xuất bản:</strong> ${book.publisher}
                                            </li>
                                            <li>
                                                <strong>Giảm giá:</strong> ${book.discount}%
                                            </li>
                                            <li>
                                                <strong>Ngày tạo:</strong>
                                                ${book.createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy
                                                HH:mm"))}
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
                                        <button class="btn btn-buy px-5 py-2 fw-bold">
                                            <i class="bi bi-lightning-fill"></i> Mua ngay
                                        </button>

                                        <button class="btn btn-cart px-4 py-2 fw-bold">
                                            <i class="bi bi-cart-plus"></i> Thêm vào giỏ
                                        </button>
                                    </div>
                                </div>
                            </div>

                            <!-- SUMMARY -->
                            <div class="mt-5 pt-4 border-top">
                                <h5 class="section-title mb-3">Giới thiệu sách</h5>
                                <p class="text-secondary">${book.description}</p>
                            </div>
                        </div>

                        <!-- SIMILAR -->
                        <div class="mt-5">
                            <h5 class="section-title mb-4">Có thể bạn cũng thích</h5>

                            <div class="row row-cols-2 row-cols-md-6 g-3">
                                <c:forEach items="${similarList}" var="s">
                                    <div class="col">
                                        <div class="card similar-card border-0 shadow-sm h-100">
                                            <img src="${s.urlImg}" class="card-img-top" alt="${s.title}">
                                            <div class="card-body p-2">
                                                <p class="small fw-semibold mb-1">${s.title}</p>
                                                <span class="text-success fw-bold">$${s.price}</span>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>

                    </div>
                    <jsp:include page="./layout/footer.jsp" />
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
                </body>

                </html>