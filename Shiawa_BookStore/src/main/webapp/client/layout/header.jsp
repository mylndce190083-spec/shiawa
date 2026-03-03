
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!<!doctype html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <title>Book Store</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link href="${pageContext.request.contextPath}/assets/css/css.css" rel="stylesheet" type="text/css"/>
        
        
        
         <style>
            .custom-toast {
                position: fixed;
                top: -120px;
                right: 20px;
                background: linear-gradient(135deg, #ff1e1e, #b30000);
                color: white;
                width: 340px;
                border-radius: 14px;
                overflow: hidden;
                box-shadow: 0 15px 35px rgba(255,0,0,0.4);
                transition: all 0.5s cubic-bezier(.68,-0.55,.27,1.55);
                z-index: 9999;
            }

            .custom-toast.show {
                top: 20px;
            }

            .toast-content {
                display: flex;
                align-items: center;
                padding: 16px;
            }

            .toast-content .icon {
                font-size: 26px;
                margin-right: 14px;
                animation: pop 0.4s ease;
            }

            .toast-content strong {
                font-size: 16px;
            }

            .toast-content .sub {
                font-size: 13px;
                opacity: 0.9;
            }

            .progress-bar {
                height: 4px;
                background: #fff;
                width: 100%;
                animation: progress 3s linear forwards;
            }

            /* Thanh chạy */
            @keyframes progress {
                from {
                    width: 100%;
                }
                to {
                    width: 0%;
                }
            }

            /* Icon nhảy nhẹ */
            @keyframes pop {
                0% {
                    transform: scale(0.5);
                }
                80% {
                    transform: scale(1.2);
                }
                100% {
                    transform: scale(1);
                }
            }
            .cart-icon {
                position: relative;
            }

            .cart-badge {
                position: absolute;
                top: -6px;
                right: 0px;
                background: red;
                color: white;
                font-size: 12px;
                padding: 3px 6px;
                border-radius: 50px;
            }
        </style>

    </head>

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
            <a href="${pageContext.request.contextPath}/cart" class="icon cart-icon">
                <i class="fa-solid fa-cart-shopping"></i>

                <span id="cartBadge" class="cart-badge"
                      style="${sessionScope.cartSize > 0 ? '' : 'display:none;'}">
                    ${sessionScope.cartSize}
                </span>

                <span>Giỏ hàng</span>
            </a>

            <c:if test="${not empty sessionScope.user}">
                <a href="${pageContext.request.contextPath}/OrderList" 
                   style="text-decoration:none; color:inherit;">
                    <div class="icon">
                        <i class="fa-solid fa-clipboard-list"></i>
                        <span>Danh sách mua hàng</span>
                    </div>
                </a>
            </c:if>

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
</html>