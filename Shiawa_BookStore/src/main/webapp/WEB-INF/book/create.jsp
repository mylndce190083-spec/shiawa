<%-- 
    Document   : create
    Created on : Jan 31, 2026, 10:50:31 AM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<<<<<<< HEAD
<!DOCTYPE html>
<html>
    <head>
        <title>Add Book</title>
    </head>
    <body>
        <h2>Add New Book</h2>

        <form action="${pageContext.request.contextPath}/book" method="post">
            <input type="hidden" name="view" value="add"/>

            Title: <input type="text" name="title" required/><br/>
            Author: <input type="text" name="author" required/><br/>
            Price: <input type="number" step="0.01" name="price" required/><br/>
            Stock: <input type="number" name="stock" required/><br/>
            <label>Category:</label>
            <select name="categoryId" required>
                <option value="">-- Select Category --</option>
                <c:forEach var="c" items="${categoryList}">
                    <option value="${c.categoryId}">
                        ${c.name}
                    </option>
                </c:forEach>
            </select>
            <br/>

            <button type="submit">Save</button>
            <a href="${pageContext.request.contextPath}/book">Cancel</a>
        </form>
    </body>
=======
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <title>DASHBOARD ADMIN</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="" name="keywords">
        <meta content="" name="description">

        <!-- Favicon -->
        <link href="assets/img/favicon.ico" rel="icon">

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600;700&display=swap" rel="stylesheet">

        <!-- Icon Font Stylesheet -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="assets/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
        <link href="assets/lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

        <!-- Customized Bootstrap Stylesheet -->
        <link href="assets/css/bootstrap.min.css" rel="stylesheet">

        <!-- Template Stylesheet -->
        <link href="assets/css/style.css" rel="stylesheet">
    </head>

    <body>
        <div class="container-fluid position-relative bg-white d-flex p-0">
            <!-- Spinner Start -->
            <div id="spinner"
                 class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
                <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status">
                    <span class="sr-only">Loading...</span>
                </div>
            </div>
            <!-- Spinner End -->


            <!-- Sidebar Start -->
            <div class="sidebar pe-4 pb-3">
                <nav class="navbar bg-light navbar-light">
                    <a href="index.jsp" class="navbar-brand mx-4 mb-3">
                        <h3 class="text-primary"><img class="rounded-circle" src="assets/img/logo.jpg" alt="" style="width: 40px; height: 40px;">  SHIAWA</h3>
                    </a>
                    <div class="d-flex align-items-center ms-4 mb-4">
                        <div class="position-relative">
                            <img class="rounded-circle" src="assets/img/user.jpg" alt="" style="width: 40px; height: 40px;">
                            <div
                                class="bg-success rounded-circle border border-2 border-white position-absolute end-0 bottom-0 p-1">
                            </div>
                        </div>
                        <div class="ms-3">
                            <h6 class="mb-0">Jhon Doe</h6>
                            <span>Admin</span>
                        </div>
                    </div>
                    <div class="navbar-nav w-100">
                        <a href="${pageContext.request.contextPath}/account" 
                           class="nav-item nav-link ${pageContext.request.requestURI.contains('/account') ? 'active' : ''}">
                            <i class="fa fa-users me-2">
                            </i>Account</a>

                        <a href="${pageContext.request.contextPath}/book" 
                           class="nav-item nav-link ${pageContext.request.requestURI.contains('/book') ? 'active' : ''}">
                            <i class="fa fa-book me-2">
                            </i>Book</a>
                        <a href="category.jsp" class="nav-item nav-link"><i class="fa fa-tags me-2"></i>Category</a>
                        <a href="form.jsp" class="nav-item nav-link"><i class="fa fa-shopping-cart me-2"></i>Order</a>
                        <a href="table.jsp" class="nav-item nav-link"><i class="fa fa-headset me-2"></i>Customer
                            Support</a>
                        <a href="chart.html" class="nav-item nav-link"><i class="fa fa-user-edit me-2"></i>Edit Profile</a>
                        <a href="#" class="nav-item nav-link"><i class="fa fa-sign-out-alt me-2"></i>Logout</a>
                    </div>
                </nav>
            </div>
            <!-- Sidebar End -->


            <!-- Content Start -->
            <div class="content">
                <!-- Navbar Start -->
                <nav class="navbar navbar-expand bg-light navbar-light sticky-top px-4 py-0">
                    <a href="index.jsp" class="navbar-brand d-flex d-lg-none me-4">
                        <h2 class="text-primary mb-0"><i class="fa fa-hashtag"></i></h2>
                    </a>
                    <a href="#" class="sidebar-toggler flex-shrink-0">
                        <button type="button" class="btn btn-success rounded-pill m-2">
                            <i class="fa fa-bars"></i>
                        </button>
                    </a>
                    <form class="d-none d-md-flex ms-4">
                        <input class="form-control border-0" type="search" placeholder="Search">
                    </form>
                    <div class="navbar-nav align-items-center ms-auto">

                    </div>
                </nav>
                <!-- Navbar End -->


                <!-- Recent Sales Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light text-center rounded p-4">
                        <div class="d-flex align-items-center justify-content-between mb-4">
                            <!--<h6 class="mb-0">User List</h6>-->
                            <div class="d-flex justify-content-between align-items-center">
                                <h6 class="mb-0">Book List</h6>
                            </div>
                            <div class="d-flex gap-4">
                                <a class="btn btn-sm btn-success" href="${pageContext.request.contextPath}/book?view=add">Add Book</a>
                                <a href="" class="text-primary">Show All</a>
                            </div>
                        </div>
                        <div class="row justify-content-center">
                            <div class="col-lg-8">
                                <div class="card shadow-sm">
                                    <div class="card-body">

                                        <h4 class="mb-4 text-success">
                                            <i class="fa fa-plus-circle me-2"></i>Add New Book
                                        </h4>

                                        <form action="${pageContext.request.contextPath}/book" method="post">
                                            <input type="hidden" name="view" value="add"/>

                                            <div class="mb-3">
                                                <label class="form-label">Title</label>
                                                <input type="text" name="title" class="form-control" required>
                                            </div>

                                            <div class="mb-3">
                                                <label class="form-label">Author</label>
                                                <input type="text" name="author" class="form-control" required>
                                            </div>

                                            <div class="row">
                                                <div class="col-md-6 mb-3">
                                                    <label class="form-label">Price</label>
                                                    <input type="number" step="0.01" name="price" class="form-control" required>
                                                </div>

                                                <div class="col-md-6 mb-3">
                                                    <label class="form-label">Stock</label>
                                                    <input type="number" name="stock" class="form-control" required>
                                                </div>
                                            </div>

                                            <div class="mb-3">
                                                <label class="form-label">Category</label>
                                                <select name="categoryId" class="form-select" required>
                                                    <option value="">-- Select Category --</option>
                                                    <c:forEach var="c" items="${categoryList}">
                                                        <option value="${c.categoryId}">
                                                            ${c.categoryName}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>

                                            <div class="d-flex justify-content-between mt-4">
                                                <button type="submit" class="btn btn-success">
                                                    <i class="fa fa-save me-2"></i>Save
                                                </button>

                                                <a href="${pageContext.request.contextPath}/book" class="btn btn-secondary">
                                                    Cancel
                                                </a>
                                            </div>

                                        </form>

                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <!-- Recent Sales End -->

                <!-- Footer Start -->
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-light rounded-top p-4">
                        <div class="row">
                            <div class="col-12 col-sm-6 text-center text-sm-start">
                                &copy; <a href="#" class="text-primary">Your Site Name</a>, All Right Reserved.
                            </div>
                            <div class="col-12 col-sm-6 text-center text-sm-end">
                                <!--/*** This template is free as long as you keep the footer author’s credit link/attribution link/backlink. If you'd like to use the template without the footer author’s credit link/attribution link/backlink, you can purchase the Credit Removal License from "https://htmlcodex.com/credit-removal". Thank you for your support. ***/-->
                                Designed By <a href="https://htmlcodex.com" class="text-primary">HTML Codex</a>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Footer End -->
            </div>
            <!-- Content End -->


            <!-- Back to Top -->
            <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>
        </div>

        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="assets/lib/chart/chart.min.js"></script>
        <script src="assets/lib/easing/easing.min.js"></script>
        <script src="assets/lib/waypoints/waypoints.min.js"></script>
        <script src="assets/lib/owlcarousel/owl.carousel.min.js"></script>
        <script src="assets/lib/tempusdominus/js/moment.min.js"></script>
        <script src="assets/lib/tempusdominus/js/moment-timezone.min.js"></script>
        <script src="assets/lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

        <!-- Template Javascript -->
        <script src="assets/js/main.js"></script>
    </body>

>>>>>>> origin/huynhmy
</html>
