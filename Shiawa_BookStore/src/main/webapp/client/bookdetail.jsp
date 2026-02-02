<%-- 
    Document   : bookdetail
    Created on : Feb 2, 2026, 10:09:29 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>${book.title} | Shiawa</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        .book-detail-container { border: 2px solid #6189f8; border-radius: 10px; }
        .text-orange { color: #28a745; }
        .btn-buy { background-color: #6189f8; color: white; }
        .btn-basket { background-color: #76b852; color: white; }
    </style>
</head>
<body>

    <nav class="navbar navbar-expand-lg navbar-light bg-white border-bottom p-3">
        </nav>

    <div class="container my-4">
        
        <div class="book-detail-container p-4 shadow-sm">
             <div class="row">
                <div class="col-md-3">
                    <img src="${book.urlImg}" class="img-fluid rounded" alt="${book.title}">
                </div>
                <div class="col-md-9">
                    <h2 class="fw-bold">${book.title} <span class="text-muted fw-normal">by</span> <span class="text-orange">${book.author}</span></h2>
                    <h3 class="fw-bold mt-3">$${book.price}</h3>
                    <p class="text-success"><i class="bi bi-check-lg"></i> ${book.stock} in stock</p>
                    <div class="d-flex gap-2 mt-4">
                        <button class="btn btn-buy px-5 py-2 fw-bold">Buy</button>
                        <button class="btn btn-basket px-5 py-2 fw-bold">Add to Basket</button>
                    </div>
                </div>
            </div>

            <div class="mt-5 border-top pt-4">
                <h4 class="text-orange fw-bold">${book.title} Summary</h4>
                <p>${book.description}</p>
            </div>
        </div>

        <div class="mt-5">
            <h4 class="text-orange fw-bold mb-4">You might also like</h4>
            <div class="row row-cols-2 row-cols-md-6 g-3">
                <c:forEach items="${similarList}" var="s">
                    <div class="col">
                        <div class="card border-0 shadow-sm">
                            <img src="${s.urlImg}" class="card-img-top rounded" alt="${s.title}">
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
