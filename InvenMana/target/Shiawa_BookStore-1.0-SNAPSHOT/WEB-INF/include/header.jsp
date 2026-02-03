<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controller.AuthController"%>
<%@page import="model.StaffSession"%>
<%
    StaffSession staffUser = (StaffSession) session.getAttribute(AuthController.SESSION_USER);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>DASHBOARD ADMIN</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Favicon -->
    <link href="${pageContext.request.contextPath}/assets/img/favicon.ico" rel="icon">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600;700&display=swap" rel="stylesheet">

    <!-- Icons -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- CSS -->
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
</head>

<body>
<div class="container-fluid position-relative bg-white d-flex p-0">

    <!-- Sidebar -->
    <div class="sidebar pe-4 pb-3">
        <nav class="navbar bg-light navbar-light">
            <a href="${pageContext.request.contextPath}/index.jsp" class="navbar-brand mx-4 mb-3">
                <h3 class="text-primary">
                    <img src="${pageContext.request.contextPath}/assets/img/logo.jpg"
                         class="rounded-circle" style="width:40px;height:40px">
                    SHIAWA
                </h3>
            </a>

            <div class="navbar-nav w-100">
                <a href="${pageContext.request.contextPath}/book" class="nav-item nav-link">
                    <i class="fa fa-book me-2"></i>Book
                </a>
                <a href="${pageContext.request.contextPath}/inventory?view=low" class="nav-item nav-link">
                    <i class="fa fa-exclamation-triangle me-2"></i>Tồn thấp
                </a>
                <a href="${pageContext.request.contextPath}/import-receipt" class="nav-item nav-link">
                    <i class="fa fa-file-import me-2"></i>Import Receipt
                </a>
                <a href="${pageContext.request.contextPath}/supplier" class="nav-item nav-link">
                    <i class="fa fa-truck me-2"></i>Supplier
                </a>
                <% if (staffUser != null && staffUser.isAdmin()) { %>
                <a href="${pageContext.request.contextPath}/account" class="nav-item nav-link">
                    <i class="fa fa-users me-2"></i>Account
                </a>
                <% } %>
                <a href="${pageContext.request.contextPath}/logout" class="nav-item nav-link">
                    <i class="fa fa-sign-out-alt me-2"></i>Logout
                </a>
            </div>
        </nav>
    </div>

    <!-- Content -->
    <div class="content">
