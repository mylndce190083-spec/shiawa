<%-- 
    Document   : feedback
    Created on : Feb 28, 2026, 10:55:57 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Đánh giá sản phẩm | ${book.title}</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
        <style>
            :root {
                --shiawa-green: #00a651;
                --shiawa-hover: #008f45;
                --shiawa-light: #f8faf9;
                --star-color: #ffc107;
                --border-color: #e0e6ed;
            }

            body {
                background-color: #ffffff; /* Để nền trắng cho sang trọng khi làm toàn màn hình */
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                margin: 0;
                padding: 0;
            }

            /* Khung chính tràn màn hình */
            .feedback-container {
                width: 100%;
                min-height: 100vh;
                display: flex;
                flex-direction: column;
            }

            /* Header trải dài hết chiều ngang */
            .feedback-header {
                background-color: var(--shiawa-green);
                color: white;
                padding: 30px 50px;
                font-weight: 700;
                text-transform: uppercase;
                letter-spacing: 2px;
                display: flex;
                align-items: center;
                justify-content: center;
                box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            }

            /* Nội dung chính bên dưới */
            .feedback-content {
                padding: 40px 10%; /* Căn lề hai bên 10% để nội dung không bị dính sát mép màn hình quá */
                flex-grow: 1;
            }

            /* Box sản phẩm ngang hàng, rộng rãi */
            .product-box {
                background-color: var(--shiawa-light);
                border: 1px solid var(--border-color);
                border-radius: 15px;
                padding: 25px;
                margin-bottom: 40px;
            }

            .product-table {
                width: 100%;
                border-collapse: collapse;
            }

            .product-table th {
                text-align: left;
                color: #888;
                font-size: 0.9rem;
                padding-bottom: 15px;
                border-bottom: 1px solid #eee;
            }

            .product-table td {
                padding-top: 20px;
            }

            .book-info {
                display: flex;
                align-items: center;
                gap: 20px;
            }

            .book-info img {
                width: 80px;
                height: 110px;
                object-fit: cover;
                border-radius: 8px;
                box-shadow: 0 4px 10px rgba(0,0,0,0.15);
            }

            .book-title {
                font-size: 1.3rem;
                font-weight: 600;
                color: #333;
            }

            /* Rating & Comment sát nhau cho chuyên nghiệp */
            .feedback-form-section {
                display: grid;
                grid-template-columns: 1fr 2fr; /* Chia đôi: bên trái chọn sao, bên phải nhập text */
                gap: 40px;
                align-items: start;
            }

            .rating-box {
                text-align: center;
                padding: 30px;
                background: #fff;
                border: 1px solid var(--border-color);
                border-radius: 15px;
            }

            .rating-wrapper {
                display: flex;
                flex-direction: row-reverse;
                justify-content: center;
                gap: 10px;
            }

            .rating-wrapper input {
                display: none;
            }
            .rating-wrapper label {
                font-size: 50px;
                color: #ddd;
                cursor: pointer;
                transition: 0.2s;
            }

            .rating-wrapper label:hover,
            .rating-wrapper label:hover ~ label,
            .rating-wrapper input:checked ~ label {
                color: var(--star-color);
                transform: scale(1.1);
            }

            .comment-box textarea {
                width: 100%;
                height: 200px; /* Tăng chiều cao textarea */
                padding: 20px;
                border-radius: 15px;
                border: 1px solid var(--border-color);
                font-size: 1rem;
                resize: none;
            }

            .button-group {
                margin-top: 30px;
                display: flex;
                flex-direction: column;
                align-items: flex-end; /* Nút đẩy về bên phải */
            }

            .btn-submit {
                background-color: var(--shiawa-green);
                color: white;
                border: none;
                padding: 15px 60px; /* Nút to và dài */
                border-radius: 30px;
                font-weight: 700;
                font-size: 1.1rem;
                cursor: pointer;
                transition: 0.3s;
            }

            .btn-submit:hover {
                background-color: var(--shiawa-hover);
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(0, 166, 81, 0.2);
            }

            @media (max-width: 992px) {
                .feedback-form-section {
                    grid-template-columns: 1fr; /* Màn hình nhỏ thì xếp chồng lên nhau */
                }
                .feedback-content {
                    padding: 20px 5%;
                }
            }
        </style>
    </head>
    <body>

        <div class="container">
            <div class="feedback-card">
                <div class="feedback-header text-center">
                    <i class="bi bi-chat-left-heart-fill me-2"></i>Đánh giá sản phẩm
                </div>

                <form action="feedback" method="post" class="p-4">
                    <input type="hidden" name="book_id" value="${book.bookId}">

                    <table class="product-table">
                        <thead>
                            <tr class="text-secondary" style="font-size: 0.9rem;">
                                <th width="60%">Tên sách</th>
                                <th width="20%" class="text-center">Số lượng</th>
                                <th width="20%" class="text-end">Giá</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <img src="${pageContext.request.contextPath}/${book.urlImg}" 
                                             style="width: 50px; height: 70px; object-fit: cover; border-radius: 4px; margin-right: 15px;" 
                                             alt="${book.title}">
                                        <span class="fw-bold">${book.title}</span>
                                    </div>
                                </td>
                                <td class="text-center text-secondary">x 1</td>
                                <td class="text-end price-text">${book.price} đ</td>
                            </tr>
                        </tbody>
                    </table>

                    <div class="text-center mb-4">
                        <p class="mb-1 fw-bold text-secondary">Bạn thấy sản phẩm này thế nào?</p>
                        <div class="rating-wrapper">
                            <input type="radio" name="rating" value="5" id="star5" required><label for="star5">★</label>
                            <input type="radio" name="rating" value="4" id="star4"><label for="star4">★</label>
                            <input type="radio" name="rating" value="3" id="star3"><label for="star3">★</label>
                            <input type="radio" name="rating" value="2" id="star2"><label for="star2">★</label>
                            <input type="radio" name="rating" value="1" id="star1"><label for="star1">★</label>
                        </div>
                    </div>

                    <div class="mb-4">
                        <label class="form-label fw-bold text-secondary">Nhận xét của bạn</label>
                        <textarea name="content" class="form-control" rows="4" 
                                  placeholder="Hãy chia sẻ những điều bạn thích về cuốn sách này nhé..." required></textarea>
                    </div>

                    <button type="submit" class="btn btn-submit">
                        GỬI ĐÁNH GIÁ NGAY
                    </button>

                    <div class="text-center mt-3">
                        <a href="bookdetail?id=${book.bookId}" class="btn-back">
                            <i class="bi bi-arrow-left me-1"></i> Quay lại chi tiết sản phẩm
                        </a>
                    </div>
                </form>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>