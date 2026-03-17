<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <meta charset="utf-8">
    <title>DASHBOARD ADMIN</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">

    <!-- Favicon -->
    <link href="${pageContext.request.contextPath}/assets/img/favicon.ico" rel="icon">

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
                    <h3 class="text-primary"><img class="rounded-circle" src="${pageContext.request.contextPath}/assets/img/logo.jpg" alt="" style="width: 40px; height: 40px;">  SHIAWA</h3>
                </a>
                <div class="d-flex align-items-center ms-4 mb-4">
                    <div class="position-relative">
                        <img class="rounded-circle" src="${not empty profile.avatar ? pageContext.request.contextPath.concat(profile.avatar) : pageContext.request.contextPath.concat('/assets/img/user.jpg')}" alt="" style="width: 40px; height: 40px; object-fit: cover;">
                        <div class="bg-success rounded-circle border border-2 border-white position-absolute end-0 bottom-0 p-1"></div>
                    </div>
                    <div class="ms-3">
                        <h6 class="mb-0">${not empty profile.fullName ? profile.fullName : 'Inventory'}</h6>
                        <span>Staff</span>
                    </div>
                </div>

                <div class="navbar-nav w-100">
                    <a href="${pageContext.request.contextPath}/inventory" 
                       class="nav-item nav-link ${pageContext.request.requestURI.contains('/inventory') ? 'active' : ''}">
                        <i class="fa fa-book me-2">
                        </i>Book</a>
                    <a href="${pageContext.request.contextPath}/inventory?view=in" class="nav-item nav-link">
                        <i class="fa fa-arrow-down me-2"></i>Nhập kho
                    </a>
                    <a href="${pageContext.request.contextPath}/inventory?view=report" class="nav-item nav-link">
                        <i class="fa fa-chart-line me-2"></i>Lịch sử nhập/xuất
                    </a>
                    <a href="${pageContext.request.contextPath}/staff/profile" class="nav-item nav-link">
                        <i class="fa fa-user me-2"></i>Hồ sơ
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

                <div class="navbar-nav align-items-center ms-auto">

                </div>
            </nav>
            <!-- Navbar End -->


            <!-- Recent Sales Start -->
            <div class="container-fluid pt-4 px-4">
                <div class="bg-light rounded p-4">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h6 class="mb-0">Chi tiết phiếu nhập</h6>
                        <a href="${pageContext.request.contextPath}/inventory?view=history" class="btn btn-secondary btn-sm">
                            <i class="fa fa-arrow-left me-1"></i> Back
                        </a>
                    </div>

                    <c:if test="${empty request}">
                        <div class="alert alert-danger">Không tìm thấy phiếu yêu cầu.</div>
                    </c:if>

                    <c:if test="${not empty request}">
                        <div class="row g-3 mb-3">
                            <div class="col-md-3">
                                <div class="small text-muted">Mã phiếu</div>
                                <div class="fw-semibold">${request.requestCode}</div>
                            </div>
                            <div class="col-md-3">
                                <div class="small text-muted">Trạng thái</div>
                                <div>
                                    <span class="badge ${request.status == 'APPROVED' ? 'bg-success' : (request.status == 'REJECTED' ? 'bg-danger' : 'bg-warning text-dark')}">
                                        ${request.status}
                                    </span>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="small text-muted">Ghi chú</div>
                                <div class="fw-semibold">${request.note}</div>
                            </div>
                        </div>

                        <div class="table-responsive mb-3">
                            <table class="table table-bordered align-middle">
                                <thead>
                                <tr class="text-success">
                                    <th>Sách</th>
                                    <th>Tác giả</th>
                                    <th>NXB</th>
                                    <th>Thể loại</th>
                                    <th>Số lượng</th>
                                    <th>Giá nhập</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="it" items="${request.items}">
                                    <tr>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty it.bookId}">#${it.bookId} - ${it.bookTitle}</c:when>
                                                <c:otherwise>${it.newBookTitle}</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty it.newBookAuthor}">${it.newBookAuthor}</c:when>
                                                <c:otherwise>-</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty it.newBookPublisher}">${it.newBookPublisher}</c:when>
                                                <c:otherwise>-</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty it.newBookCategoryId}">${it.newBookCategoryId}</c:when>
                                                <c:otherwise>-</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${it.qty}</td>
                                        <td>${it.unitCost}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
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
    <script>
        const toggleBtn = document.getElementById('btnToggleProfile');
        const panel = document.getElementById('profilePanel');
        if (toggleBtn && panel) {
            toggleBtn.addEventListener('click', function () {
                const isHidden = panel.style.display === 'none' || panel.style.display === '';
                panel.style.display = isHidden ? 'block' : 'none';
            });
            if (window.location.search.includes('success=1')) {
                panel.style.display = 'block';
            }
        }
    </script>
</body>
