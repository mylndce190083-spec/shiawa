<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <nav class="navbar navbar-expand-lg navbar-light bg-white border-bottom shadow-sm">
            <div class="container">

                <!-- LOGO -->
                <a class="navbar-brand fw-bold text-success" href="home">
                    <i class="bi bi-book"></i> SHIAWA
                </a>

                <!-- TOGGLE MOBILE -->
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNavbar">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <!-- MENU -->
                <div class="collapse navbar-collapse" id="mainNavbar">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link fw-semibold" href="home">Trang chủ</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link fw-semibold" href="books">Sách</a>
                        </li>
                    </ul>

                    <!-- SEARCH -->
                    <form class="d-flex me-3" action="search">
                        <input class="form-control me-2" type="search" name="q" placeholder="Tìm kiếm sách...">
                        <button class="btn btn-outline-success" type="submit">
                            <i class="bi bi-search"></i>
                        </button>
                    </form>

                    <!-- USER -->
                    <c:choose>
                        <c:when test="${sessionScope.user != null}">
                            <a href="cart" class="btn btn-outline-success me-2">
                                <i class="bi bi-cart"></i>
                            </a>
                            <span class="fw-semibold text-success me-2">
                                Hi, ${sessionScope.user.name}
                            </span>
                            <a href="logout" class="btn btn-success">Logout</a>
                        </c:when>
                        <c:otherwise>
                            <a href="login" class="btn btn-outline-success me-2">Login</a>
                            <a href="register" class="btn btn-success">Register</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </nav>