
<%-- 
    Document   : feedback
    Created on : Feb 28, 2026, 10:55:57 PM
    Author     : admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                --bg-light: #f4f7f6;
                --star-color: #ffc107;
                --border-color: #e9ecef;
            }

            body {
                background-color: var(--bg-light);
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                align-items: center;
                justify-content: center;
                min-height: 100vh;
                margin: 0;
                padding: 20px;
            }

            .feedback-card {
                width: 100%;
                max-width: 600px;
                background: #ffffff;
                border-radius: 15px;
                box-shadow: 0 10px 30px rgba(0,0,0,0.1);
                overflow: hidden;
                border: 1px solid var(--border-color);
            }

            .feedback-header {
                background-color: var(--shiawa-green);
                color: white;
                padding: 20px;
                font-size: 1.2rem;
                font-weight: 700;
                text-transform: uppercase;
                text-align: center;
                display: flex;
                align-items: center;
                justify-content: center;
                gap: 10px;
            }

            .product-info-section {
                padding: 20px;
                border-bottom: 1px solid var(--border-color);
                display: flex;
                align-items: center;
                gap: 15px;
            }

            .product-info-section img {
                width: 70px;
                height: 100px;
                object-fit: cover;
                border-radius: 8px;
                box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            }

            .book-detail-text h6 {
                margin: 0;
                font-weight: 700;
                color: #333;
            }

            .form-content {
                padding: 30px;
            }

            .rating-wrapper {
                display: flex;
                flex-direction: row-reverse;
                justify-content: center;
                gap: 10px;
                margin-bottom: 25px;
            }

            .rating-wrapper input {
                display: none;
            }
            .rating-wrapper label {
                font-size: 40px;
                color: #ddd;
                cursor: pointer;
                transition: 0.2s;
            }

            .rating-wrapper label:hover,
            .rating-wrapper label:hover ~ label,
            .rating-wrapper input:checked ~ label {
                color: var(--star-color);
            }

            .comment-box textarea {
                border-radius: 10px;
                border: 1px solid #ced4da;
                padding: 15px;
                resize: none;
                font-size: 0.95rem;
            }

            .comment-box textarea:focus {
                border-color: var(--shiawa-green);
                box-shadow: 0 0 0 0.25rem rgba(0, 166, 81, 0.1);
            }

            .btn-submit {
                background-color: var(--shiawa-green);
                color: white;
                border: none;
                width: 100%;
                padding: 12px;
                border-radius: 30px;
                font-weight: 700;
                text-transform: uppercase;
                margin-top: 20px;
                transition: 0.3s;
            }

            .btn-submit:hover {
                background-color: var(--shiawa-hover);
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(0, 166, 81, 0.2);
            }

            .btn-back {
                display: block;
                text-align: center;
                margin-top: 15px;
                color: #6c757d;
                text-decoration: none;
                font-size: 0.9rem;
            }
            .center-wrapper {
                display: flex;
                align-items: center;
                justify-content: center;
                min-height: 90vh;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/client/layout/header.jsp"/>

        <div class="center-wrapper">
            <div class="feedback-card">

                <div class="feedback-header">
                    <i class="bi bi-chat-left-heart-fill"></i> ĐÁNH GIÁ SẢN PHẨM
                </div>
                <div class="product-info-section">
                    <img src="${pageContext.request.contextPath}/image?file=${book.urlImg}" alt="${book.title}">
                    <div class="book-detail-text">
                        <h6>${book.title}</h6>
                          <span class="fw-bold text-success mt-1">
                                    <fmt:formatNumber 
                                        value="${book.price}" 
                                        type="number"
                                        groupingUsed="true"
                                        maxFractionDigits="0"/> VND</span>
                      
                    </div>
                </div>

                <form action="feedback" method="post" class="form-content">
                    <input type="hidden" name="book_id" value="${book.bookId}">
                    <input type="hidden" name="order_id" value="${param.order_id}">
                    <input type="hidden" name="isRated" value="${item.isRated}">
                    <input type="hidden" name="orderDetailId" value="${item.orderDetailId}">


                    <p class="text-center fw-bold text-secondary mb-2">Bạn thấy sản phẩm này thế nào?</p>
                    <div class="rating-wrapper">
                        <input type="radio" name="rating" value="5" id="star5" required><label for="star5">★</label>
                        <input type="radio" name="rating" value="4" id="star4"><label for="star4">★</label>
                        <input type="radio" name="rating" value="3" id="star3"><label for="star3">★</label>
                        <input type="radio" name="rating" value="2" id="star2"><label for="star2">★</label>
                        <input type="radio" name="rating" value="1" id="star1"><label for="star1">★</label>
                    </div>

                    <div class="comment-box mb-3">
                        <label class="form-label fw-bold text-secondary">Nhận xét của bạn</label>
                        <textarea name="content" class="form-control" rows="4" 
                                  placeholder="Hãy chia sẻ những điều bạn thích về cuốn sách này nhé..." ></textarea>
                    </div>

                    <button type="submit" class="btn btn-submit">
                        GỬI ĐÁNH GIÁ NGAY
                    </button>

                    <a href="bookdetail?id=${book.bookId}" class="btn-back">
                        <i class="bi bi-arrow-left"></i> Quay lại
                    </a>
                </form>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
