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
                --star-color: #ffc107;
            }
            body { 
                background-color: #f0f2f5; 
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }

            .feedback-card {
                max-width: 550px;
                margin: 50px auto;
                background: white;
                border-radius: 20px;
                box-shadow: 0 10px 30px rgba(0,0,0,0.08);
                border: none;
            }

            .feedback-header {
                background-color: var(--shiawa-green);
                color: white;
                padding: 25px;
                font-weight: 700;
                text-transform: uppercase;
                letter-spacing: 1px;
                border-radius: 20px 20px 0 0;
            }

            .product-box {
                display: flex;
                align-items: center;
                padding: 20px;
                margin: 0 20px;
                background-color: #f8faf9;
                border-radius: 12px;
                border: 1px solid #edf2f0;
            }
            .product-box img {
                width: 65px;
                height: 85px;
                object-fit: cover;
                border-radius: 8px;
                margin-right: 15px;
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            }

            /* Hệ thống chọn sao thông minh */
            .rating-wrapper {
                display: flex;
                flex-direction: row-reverse;
                justify-content: center;
                gap: 5px;
            }
            .rating-wrapper input {
                display: none;
            }
            .rating-wrapper label {
                font-size: 45px;
                color: #e9ecef;
                cursor: pointer;
                transition: all 0.2s ease-in-out;
            }
            /* Hiệu ứng khi hover hoặc check */
            .rating-wrapper label:hover,
            .rating-wrapper label:hover ~ label,
            .rating-wrapper input:checked ~ label {
                color: var(--star-color);
                transform: scale(1.1);
                text-shadow: 0 0 10px rgba(255, 193, 7, 0.3);
            }
            /* Hiệu ứng đặc biệt khi đang click */
            .rating-wrapper label:active {
                transform: scale(0.9);
            }

            .form-control:focus {
                border-color: var(--shiawa-green);
                box-shadow: 0 0 0 0.25rem rgba(0, 166, 81, 0.1);
            }

            .btn-submit {
                background-color: var(--shiawa-green);
                color: white;
                border: none;
                width: 100%;
                padding: 14px;
                border-radius: 12px;
                font-weight: 700;
                font-size: 1rem;
                transition: all 0.3s;
                margin-top: 10px;
            }
            .btn-submit:hover {
                background-color: var(--shiawa-hover);
                color: white;
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(0, 166, 81, 0.3);
            }

            .btn-back {
                color: #6c757d;
                text-decoration: none;
                font-size: 0.9rem;
                transition: 0.2s;
            }
            .btn-back:hover {
                color: var(--shiawa-green);
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

                <div class="product-box mb-4">
                    <img src="${pageContext.request.contextPath}/${book.urlImg}" alt="${book.title}">
                    <div>
                        <h6 class="mb-1 fw-bold text-dark">${book.title}</h6>
                        <span class="badge bg-light text-success border border-success-subtle">
                            Thể loại: ${book.categoryName}
                        </span>
                    </div>
                </div>

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