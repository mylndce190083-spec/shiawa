

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                            <h6 class="mb-0">Inventory</h6>
                            <span>Staff</span>
                        </div>
                    </div>
                    <div class="navbar-nav w-100">
                        <a href="${pageContext.request.contextPath}/book" 
                           class="nav-item nav-link ${pageContext.request.requestURI.contains('/book') ? 'active' : ''}">
                            <i class="fa fa-book me-2">
                            </i>Book</a>
                        <a href="${pageContext.request.contextPath}/inventory?view=in" class="nav-item nav-link">
                            <i class="fa fa-arrow-down me-2"></i>Nhập kho
                        </a>
                        <a href="${pageContext.request.contextPath}/inventory?view=out" class="nav-item nav-link">
                            <i class="fa fa-arrow-up me-2"></i>Xuất kho
                        </a>
                        <a href="${pageContext.request.contextPath}/inventory?view=report" class="nav-item nav-link">
                            <i class="fa fa-chart-line me-2"></i>Lịch sử nhập/xuất
                        </a>
                        <a href="${pageContext.request.contextPath}/supplier" class="nav-item nav-link">
                            <i class="fa fa-truck me-2"></i>Supplier
                        </a>
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
                                <h6 class="mb-0">Kho sách (tồn kho)</h6>
                            </div>
                            <div class="d-flex gap-3 align-items-center">
                                <a class="btn btn-sm btn-success" href="${pageContext.request.contextPath}/book?view=add">Add Book</a>
                            </div>
                        </div>
                        <form class="row g-2 mb-3" method="get" action="${pageContext.request.contextPath}/book">
                            <div class="col-md-3">
                                <input class="form-control" type="number" min="0" name="minStock" placeholder="Tồn >= ..." value="${minStock}"/>
                            </div>
                            <div class="col-md-3">
                                <input class="form-control" type="number" min="0" name="maxStock" placeholder="Tồn <= ..." value="${maxStock}"/>
                            </div>
                            <div class="col-md-3">
                                <select class="form-select" name="sort">
                                    <option value="">Sắp xếp tồn tăng dần</option>
                                    <option value="stock_desc" ${sort == 'stock_desc' ? 'selected' : ''}>Tồn giảm dần</option>
                                    <option value="id" ${sort == 'id' ? 'selected' : ''}>Theo ID</option>
                                </select>
                            </div>
                            <div class="col-md-3 d-flex gap-2">
                                <button class="btn btn-success w-100" type="submit">Lọc</button>
                                <a class="btn btn-outline-secondary w-100" href="${pageContext.request.contextPath}/book">Reset</a>
                            </div>
                        </form>
                        <div class="table-responsive">
                            <table class="table text-start align-middle table-bordered table-hover mb-0">
                                <thead>
                                    <tr class="text-success">
                                        <th>ID</th>
                                        <th>Image</th>
                                        <th>Title</th>
                                        <th>Author</th>
                                        <th>Category</th>
                                        <th>Publisher</th>
                                        <th>Price</th>
                                        <th>Discount</th>
                                        <th>Stock</th>
                                        <th class="text-center">Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="b" items="${bookList}">
                                        <tr>
                                            <td>${b.bookId}</td>
                                            <td class="text-center">
                                                <c:choose>
                                                    <c:when test="${not empty b.urlImg}">
                                                        <img src="${pageContext.request.contextPath}/${b.urlImg}"
                                                             alt="${b.title}"
                                                             style="width:50px;height:70px;object-fit:cover;">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="text-muted">No image</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${b.title}</td>
                                            <td>${b.author}</td>
                                            <td>${b.categoryName}</td>
                                            <td>${b.publisher}</td>
                                            <td>${b.price}</td>
                                            <td>${b.discount}%</td>
                                            <td>${b.stock}</td>
                                            <td class="text-center">
                                                <div class="d-flex justify-content-center gap-2">
                                                    <a href="${pageContext.request.contextPath}/book?view=edit&id=${b.bookId}"
                                                       class="btn btn-sm btn-warning px-3"
                                                       style="min-width:80px;">Edit</a>
                                                    <a href="${pageContext.request.contextPath}/book?view=delete&id=${b.bookId}"
                                                       class="btn btn-sm btn-danger px-3"
                                                       style="min-width:80px;"
                                                       onclick="return confirm('Delete this book?')">Delete</a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                </tbody>

                            </table>
                        </div>

                    </div>
                </div>
                <!-- Recent Sales End -->
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

</html>