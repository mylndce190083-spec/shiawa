<%-- 
    Document   : booklist
    Created on : Feb 22, 2026, 9:12:43 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <jsp:include page="./header.jsp" />

        <c:if test="${not empty keyword}">
            <div class="search-results-section mb-5 p-4 bg-white rounded shadow-sm">
                <h3 class="section-title">Kết quả tìm kiếm cho: "${keyword}"</h3>
                <hr>
                <div class="row row-cols-2 row-cols-md-4 g-4">
                    <c:forEach items="${books}" var="b">
                        <div class="col">
                            <div class="card h-100 border-0 shadow-sm similar-card p-2">                                
                                <img src="${pageContext.request.contextPath}/${b.urlImg}">                                      
                                <div class="card-body">
                                    <h6 class="fw-bold text-truncate">${b.title}</h6>
                                    <p class="text-primary fw-bold">$${b.price}</p>
                                    <a href="${pageContext.request.contextPath}/bookdetail?id=${b.bookId}" 
                                       class="btn btn-sm btn-outline-success w-100">Xem chi tiết</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <c:if test="${empty books}">
                    <div class="alert alert-warning">
                        <i class="bi bi-exclamation-triangle"></i> Không tìm thấy sách.
                    </div>
                </c:if>
            </div>
        </c:if>

    </div>
    <jsp:include page="./footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>