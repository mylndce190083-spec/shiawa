<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Kết quả tìm kiếm</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link href="${pageContext.request.contextPath}/assets/css/css.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>

        <jsp:include page="/client/layout/header.jsp" />

        <div class="container" style="padding: 20px;">
            <h2 class="search-title">Kết quả tìm kiếm cho: "${keyword}"</h2>
            <hr>

            <section class="books">
                <c:forEach items="${books}" var="b">
                    <div class="book">
                        <a href="${pageContext.request.contextPath}/bookdetail?id=${b.bookId}" style="text-decoration: none; color: inherit;">
                             <img src="${pageContext.request.contextPath}/image?file=${b.urlImg}">
                            <p class="title">${b.title}</p>
                        </a>
                       
                       <div class="price">
                        <span class="new-price">${b.price}đ</span>
                        <span class="discount">-${b.discount}%</span>
                    </div>
                         <p class="sold">Đã bán ${b.sold}</p>
                        <form action="${pageContext.request.contextPath}/cart" method="post">
                            <input type="hidden" name="action" value="add">

                            <input type="hidden" name="book_id" value="${b.bookId}">
                            <button type="submit" class="add-cart">Thêm giỏ hàng</button>
                        </form>
                    </div>
                </c:forEach>
            </section>

            <c:if test="${empty books}">
                <p style="text-align: center; margin-top: 50px;">Không tìm thấy sách nào phù hợp với từ khóa của bạn.</p>
            </c:if>
        </div>

    </body>
</html>