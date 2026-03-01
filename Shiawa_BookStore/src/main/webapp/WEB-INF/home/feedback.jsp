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
    <title>Đánh giá sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        :root {
            --shiawa-green: #00a651; /* Màu xanh từ header của bạn */
        }
        body { background-color: #f5f5f5; }
        .feedback-card {
            max-width: 600px;
            margin: 30px auto;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            overflow: hidden;
        }
        .feedback-header {
            background-color: var(--shiawa-green);
            color: white;
            padding: 15px;
            font-weight: bold;
            font-size: 1.2rem;
        }
        /* Style cho phần chọn sao */
        .rating {
            display: flex;
            flex-direction: row-reverse;
            justify-content: center;
            gap: 10px;
            margin: 20px 0;
        }
        .rating input { display: none; }
        .rating label {
            font-size: 40px;
            color: #ddd;
            cursor: pointer;
            transition: color 0.2s;
        }
        .rating label:hover,
        .rating label:hover ~ label,
        .rating input:checked ~ label {
            color: #ffc107; /* Màu vàng khi chọn sao */
        }
        .product-box {
            display: flex;
            align-items: center;
            padding: 15px;
            border-bottom: 1px solid #eee;
        }
        .product-box img {
            width: 60px;
            height: 80px;
            object-fit: cover;
            margin-right: 15px;
        }
        .btn-submit {
            background-color: var(--shiawa-green);
            color: white;
            border: none;
            width: 100%;
            padding: 12px;
            font-weight: bold;
            transition: opacity 0.3s;
        }
        .btn-submit:hover { opacity: 0.9; color: white; }
        textarea { resize: none; border: 1px solid #ddd !important; }
    </style>
</head>
<body>

<div class="container">
    <div class="feedback-card">
        <div class="feedback-header text-center">ĐÁNH GIÁ SẢN PHẨM</div>
        
        <form action="feedback" method="post" class="p-4">
            <input type="hidden" name="book_id" value="${book.bookId}">

            <div class="product-box">
                <img src="${pageContext.request.contextPath}/${book.urlImg}" alt="">
                <div>
                    <h6 class="mb-0">${book.title}</h6>
                    <small class="text-muted">Thể loại: ${book.categoryName}</small>
                </div>
            </div>

            <div class="text-center mt-3">
                <p class="mb-0 fw-bold">Vui lòng chọn mức độ hài lòng</p>
                <div class="rating">
                    <input type="radio" name="rating" value="5" id="5" required><label for="5">☆</label>
                    <input type="radio" name="rating" value="4" id="4"><label for="4">☆</label>
                    <input type="radio" name="rating" value="3" id="3"><label for="3">☆</label>
                    <input type="radio" name="rating" value="2" id="2"><label for="2">☆</label>
                    <input type="radio" name="rating" value="1" id="1"><label for="1">☆</label>
                </div>
            </div>

            <div class="mb-4">
                <label class="form-label fw-bold">Nhận xét của bạn</label>
                <textarea name="content" class="form-control" rows="5" 
                          placeholder="Chia sẻ trải nghiệm của bạn về sản phẩm này nhé..." required></textarea>
            </div>

            <button type="submit" class="btn btn-submit">GỬI ĐÁNH GIÁ</button>
            <a href="bookdetail?id=${book.bookId}" class="btn btn-link w-100 mt-2 text-decoration-none text-muted">Quay lại</a>
        </form>
    </div>
</div>

</body>
</html>