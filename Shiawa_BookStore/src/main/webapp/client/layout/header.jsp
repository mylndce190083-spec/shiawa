
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header class="header">
    <div class="logo" id="backToShop" onclick="window.location.href = '${pageContext.request.contextPath}/home'">
        <img src="${pageContext.request.contextPath}/assets/img/logo.jpg" class="rounded-img" alt="Logo">
    </div>

    <form action="${pageContext.request.contextPath}/search" method="get" class="search-box">
        <input type="text" name="keyword" placeholder="Tìm kiếm sách bạn muốn..." value="${keyword}">
        <button type="submit">
            <i class="fa-solid fa-magnifying-glass"></i>
        </button>
    </form>

    <div class="icons">
        <a href="${pageContext.request.contextPath}/cart" class="icon">
            <i class="fa-solid fa-cart-shopping"></i>
            <span>Giỏ hàng</span>
        </a>

        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <div class="icon">
                    <i class="fa-solid fa-user-check"></i>
                    <span>Hi, ${sessionScope.user.username}</span>
                </div>
                <a href="${pageContext.request.contextPath}/logout" class="icon">
                    <i class="fa-solid fa-right-from-bracket"></i>
                    <span>Đăng xuất</span>
                </a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/login" class="icon">
                    <i class="fa-regular fa-user"></i>
                    <span>Tài khoản</span>
                </a>
            </c:otherwise>
        </c:choose>
    </div>
</header>