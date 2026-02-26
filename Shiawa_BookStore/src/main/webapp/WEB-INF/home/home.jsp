<%-- 
    Document   : home
    Created on : Feb 2, 2026, 5:00:04 PM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <title>Book Store</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link href="${pageContext.request.contextPath}/assets/css/css.css" rel="stylesheet" type="text/css" />

        <style>
            /* CSS MỚI ĐỂ GOM NHÓM MENU */
            .category-nav {
                background-color: #f1f8f1; /* Màu xanh nhạt đồng bộ */
                padding: 10px 0;
                border-bottom: 1px solid #ddd;
            }

            .menu-container {
                display: flex;
                justify-content: center;
                gap: 25px;
                list-style: none;
                margin: 0;
                padding: 0;
                flex-wrap: nowrap; /* Ép menu nằm trên 1 dòng */
            }

            .menu-item {
                position: relative;
            }

            .parent-link {
                font-weight: 600;
                color: #2e7d32;
                text-decoration: none;
                font-size: 15px;
                display: flex;
                align-items: center;
                gap: 5px;
                white-space: nowrap;
            }

            .parent-link:hover {
                color: #ff9800; /* Đổi sang màu cam khi di chuột */
            }

            /* Ẩn danh mục con mặc định */
            .child-dropdown {
                display: none;
                position: absolute;
                top: 100%;
                left: 0;
                background-color: #ffffff;
                min-width: 200px;
                box-shadow: 0 8px 16px rgba(0,0,0,0.1);
                z-index: 1000;
                border-radius: 4px;
                padding: 8px 0;
                border: 1px solid #eee;
            }

            .child-dropdown a {
                display: block;
                padding: 10px 20px;
                color: #333;
                text-decoration: none;
                font-size: 14px;
                transition: 0.2s;
            }

            .child-dropdown a:hover {
                background-color: #e8f5e9;
                color: #2e7d32;
                padding-left: 25px;
            }

            /* Hiệu ứng: Di chuột vào CHA hiện CON */
            .menu-item:hover .child-dropdown {
                display: block;
            }

            .icon-down {
                font-size: 10px;
            }
        </style>
    </head>

    <body>

        <jsp:include page="/client/layout/header.jsp" />

        <nav class="breadcrumb">
            <a href="#">Trang chủ</a>
            <span>›</span>
            <a href="#">Siêu ưu đãi</a>
            <span>›</span>
            <span class="current">Mua sắm</span>
        </nav>

        <hr>

        <nav class="category-nav">
            <div class="menu-container">
                <div class="menu-item">
                    <a href="#" class="parent-link">Nhóm văn học-truyện <i class="fa-solid fa-chevron-down icon-down"></i></a>
                    <div class="child-dropdown">
                        <c:forEach items="${listC}" var="c">
                            <c:if test="${c.categoryName == 'Sách Văn học' || c.categoryName == 'Văn học nước ngoài' || 
                                          c.categoryName == 'Văn học trong nước' || c.categoryName == 'Trinh thám / Kinh dị' || 
                                          c.categoryName == 'Manga / Truyện tranh' || c.categoryName == 'Thiếu nhi'}">
                                  <a href="search?cateId=${c.categoryId}">${c.categoryName}</a>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>

                <div class="menu-item">
                    <a href="#" class="parent-link">Nhóm sách học thuật-kiến thức <i class="fa-solid fa-chevron-down icon-down"></i></a>
                    <div class="child-dropdown">
                        <c:forEach items="${listC}" var="c">
                            <c:if test="${c.categoryName == 'Sách CNTT' || c.categoryName == 'Kinh tế' || 
                                          c.categoryName == 'Ngôn ngữ' || c.categoryName == 'Sách giáo khoa'}">
                                  <a href="search?cateId=${c.categoryId}">${c.categoryName}</a>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>

                <div class="menu-item">
                    <a href="#" class="parent-link">Nhóm kỹ năng-phát triển <i class="fa-solid fa-chevron-down icon-down"></i></a>
                    <div class="child-dropdown">
                        <c:forEach items="${listC}" var="c">
                            <c:if test="${c.categoryName == 'Kỹ năng sống'}">
                                <a href="search?cateId=${c.categoryId}">${c.categoryName}</a>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>

                <div class="menu-item">
                    <a href="#" class="parent-link">Nhóm nghệ thuật <i class="fa-solid fa-chevron-down icon-down"></i></a>
                    <div class="child-dropdown">
                        <c:forEach items="${listC}" var="c">
                            <c:if test="${c.categoryName == 'Nghệ thuật'}">
                                <a href="search?cateId=${c.categoryId}">${c.categoryName}</a>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </nav>

        <style>
            .category-nav {
                background-color: #f1f8f1;
                padding: 12px 0;
                border-bottom: 1px solid #ddd;
            }
            .menu-container {
                display: flex;
                justify-content: center;
                gap: 30px;
            }
            .menu-item {
                position: relative;
            }
            .parent-link {
                font-weight: bold;
                color: #2e7d32;
                text-decoration: none;
                font-size: 14px;
                display: flex;
                align-items: center;
                gap: 5px;
            }
            .icon-down {
                font-size: 10px;
            }

            /* Dropdown menu con */
            .child-dropdown {
                display: none;
                position: absolute;
                top: 100%;
                left: 0;
                background: white;
                min-width: 220px;
                box-shadow: 0 4px 12px rgba(0,0,0,0.15);
                z-index: 1000;
                border-radius: 4px;
                padding: 10px 0;
                margin-top: 5px;
            }
            .child-dropdown a {
                display: block;
                padding: 8px 20px;
                color: #333;
                text-decoration: none;
                font-size: 13px;
            }
            .child-dropdown a:hover {
                background: #e8f5e9;
                color: #2e7d32;
            }

            /* Hiệu ứng Hover */
            .menu-item:hover .child-dropdown {
                display: block;
            }
            .menu-item:hover .parent-link {
                color: #ff9800;
            }
        </style>



        <section class="books">
            <c:forEach items="${listB}" var="b">
                <div class="book" data-category="${b.category.categoryName}" data-name="${b.title}" data-price="${b.price}">
                    <a href="${pageContext.request.contextPath}/bookdetail?id=${b.bookId}" style="text-decoration: none; color: inherit;">
                        <img src="${pageContext.request.contextPath}/${b.urlImg}">
                        <p class="title">${b.title}</p>
                    </a>
                    <div class="price">
                        <span class="new-price">${b.price}đ</span>
                        <span class="discount">-${b.discount}%</span>
                    </div>
                    <p class="sold">Đã bán 120</p>

                    <form action="${pageContext.request.contextPath}/cart" method="post">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="book_id" value="${b.bookId}">
                        <button type="submit" class="add-cart">Thêm giỏ hàng</button>
                    </form>
                </div>
            </c:forEach>
        </section>

        <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
    </body>

</html>