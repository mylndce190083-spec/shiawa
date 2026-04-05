<!-- Header -->
<!DOCTYPE html>
<html lang="en">

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
        <link href="${pageContext.request.contextPath}/assets/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

        <!-- Customized Bootstrap Stylesheet -->
        <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">

        <!-- Template Stylesheet -->
        <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
        <style>
            .order-progress {
                margin: 40px 0;
                position: relative;
            }

            .progress-line {
                position: absolute;
                top: 20px;
                left: 0;
                width: 100%;
                height: 4px;
                background: #ddd;
                z-index: 1;
            }

            .progress-fill {
                height: 4px;
                background: #28a745;
                transition: 0.4s ease;
            }

            .progress-fill.failed {
                background: #dc3545;
            }

            .progress-steps {
                display: flex;
                justify-content: space-between;
                position: relative;
                z-index: 2;
            }

            .step {
                text-align: center;
                width: 25%;
            }

            .circle {
                width: 40px;
                height: 40px;
                border-radius: 50%;
                background: #ddd;
                color: white;
                line-height: 40px;
                margin: 0 auto;
                font-weight: bold;
            }

            .step.active .circle {
                background: #28a745;
            }

            .progress-fill.failed ~ .progress-steps .step.active .circle {
                background: #dc3545;
            }

            .label {
                margin-top: 8px;
                font-size: 14px;
            }
            .failed-text {
                margin-top: 20px;
                text-align: center;
                color: #dc3545;
                font-weight: bold;
            }
            /* user name */
            .custom-sidebar h6 {
                color: #ffffff !important;
                font-weight: 600;
                font-size: 16px;
            }

            /* Role */
            .custom-sidebar span {
                color: #d1fae5 !important;
                font-size: 14px;
            }

            .custom-topbar {
                background: linear-gradient(90deg, #1e7e34, #28a745, #20c997);
            }
            .custom-sidebar .nav-link i {
                background: none !important;
                width: auto !important;
                height: auto !important;
                border-radius: 0 !important;
            }
            .custom-sidebar {
                background: linear-gradient(180deg, #1e7e34, #28a745, #20c997);
                min-height: 100vh;
            }

            .custom-sidebar .navbar {
                background: transparent !important;
            }

            .custom-sidebar .nav-link {
                color: #000000 !important;
                padding: 12px 20px;
                border-radius: 12px;
                margin: 6px 10px;
                transition: all 0.3s ease;
            }

            .custom-sidebar.staff-theme {
                background: #f8f9fa;
            }

            .custom-sidebar.staff-theme .nav-link,
            .custom-sidebar.staff-theme .nav-link i,
            .custom-sidebar.staff-theme h6,
            .custom-sidebar.staff-theme span,
            .custom-sidebar.staff-theme h3 {
                color: #198754 !important;
            }

            .custom-sidebar.staff-theme .nav-link:hover,
            .custom-sidebar.staff-theme .nav-link.active {
                background: #ffffff;
                color: #198754 !important;
                box-shadow: 0 4px 12px rgba(25, 135, 84, 0.15);
            }

            .custom-sidebar.staff-theme .nav-link:hover i,
            .custom-sidebar.staff-theme .nav-link.active i {
                color: #198754 !important;
            }

            .custom-sidebar .nav-link:hover {
                background: rgba(0, 0, 0, 0.2);
                color: #000000 !important;
                transform: translateX(5px);
            }

            .custom-sidebar .nav-link:hover i {
                color: #000000 !important;
            }

            .custom-sidebar .navbar-nav .nav-link.active {
                background: rgba(0, 0, 0, 0.2) !important;
                color: #000000 !important;
                font-weight: 600;
            }

            .custom-sidebar .navbar-nav .nav-link.active i {
                color: #000000 !important;
            }

            .custom-sidebar .nav-link.active i {
                color: #000000 !important;
            }
            .custom-sidebar .nav-link.active:hover {
                background: rgba(0, 0, 0, 0.35);
            }
            .custom-sidebar h3 {
                color: #ffffff !important;
            }
            /* Avatar sidebar */
            .sidebar-avatar{
                width: 45px;
                height: 45px;
                border-radius: 50%;
                object-fit: cover;
                border: 2px solid #ffffff;
                transition: all 0.3s ease;
                cursor: pointer;
            }

            .sidebar-avatar:hover{
                transform: scale(1.1);
                border-color: #20c997;
                box-shadow: 0 0 10px rgba(32, 201, 151, 0.7);
            }

            .sidebar-avatar:active{
                transform: scale(0.95);
            }

            .avatar-status{
                width: 12px;
                height: 12px;
                background: #2ecc71;
                border-radius: 50%;
                border: 2px solid white;
                position: absolute;
                bottom: 2px;
                right: 2px;
                box-shadow: 0 0 6px rgba(46, 204, 113, 0.8);
            }
        </style>
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
            <div class="sidebar pe-4 pb-3 custom-sidebar">
                <nav class="navbar navbar-dark">
                    <a href="${pageContext.request.contextPath}/account" class="navbar-brand mx-4 mb-3">
                        <h3 class="text-primary"><img class="rounded-circle" src="assets/img/logo.jpg" alt="" style="width: 40px; height: 40px;">  SHIAWA</h3>
                    </a>
                    <div class="d-flex align-items-center ms-4 mb-4">
                        <div class="position-relative">
                            <img class="sidebar-avatar" 
                                 src="${pageContext.request.contextPath}/image?file=${sessionScope.user.avatar}">
                            <div
                                class="avatar-status">
                            </div>
                        </div>
                        <div class="ms-3">
                            <h6 class="mb-0">${sessionScope.user.username}</h6>
                            <span>${sessionScope.user.role}</span>
                        </div>
                    </div>
                    <div class="navbar-nav w-100">
                        <a href="${pageContext.request.contextPath}/account" 
                           class="nav-item nav-link ${'account'.equals(pagePrimary) ? 'active' : ''}">
                            <i class="fa fa-users me-2">
                            </i>Account</a>

                        <a href="${pageContext.request.contextPath}/book-admin" 
                           class="nav-item nav-link ${'book-admin'.equals(pagePrimary) ? 'active' : ''}">
                            <i class="fa fa-book me-2">
                            </i>Book</a>
                        <a href="${pageContext.request.contextPath}/category-admin" 
                           class="nav-item nav-link ${'category-admin'.equals(pagePrimary) ? 'active' : ''}">
                            <i class="fa fa-tags me-2"></i>Category</a>
                        <a href="${pageContext.request.contextPath}/order-admin" 
                           class="nav-item nav-link ${'order-admin'.equals(pagePrimary) ? 'active' : ''}">
                            <i class="fa fa-shopping-cart me-2">
                            </i>Order</a>
                        <a href="${pageContext.request.contextPath}/voucher-admin" 
                           class="nav-item nav-link ${'voucher-admin'.equals(pagePrimary) ? 'active' : ''}">
                            <i class="fa fa-ticket-alt me-2">
                            </i>Voucher</a>
                        <a href="${pageContext.request.contextPath}/feedback-admin" 
                           class="nav-item nav-link ${'feedback-admin'.equals(pagePrimary) ? 'active' : ''}">
                            <i class="fa fa-comments me-2"></i>Feedback
                        </a>
                        <a href="${pageContext.request.contextPath}/support-admin"
                           class="nav-item nav-link ${'support-admin'.equals(pagePrimary) ? 'active' : ''}">
                            <i class="fa fa-headset me-2"></i>Customer
                            Support</a>
                        <a href="${pageContext.request.contextPath}/income" 
                           class="nav-item nav-link ${'income'.equals(pagePrimary) ? 'active' : ''}">
                            <i class="fa fa-ticket-alt me-2">
                            </i>Income</a>
                        <a href="${pageContext.request.contextPath}/staff-profile" 
                           class="nav-item nav-link ${'staff-profile'.equals(pagePrimary) ? 'active' : ''}">
                            <i class="fa fa-user-edit me-2">
                            </i>Profile</a>
                        <a href="logout" class="nav-item nav-link"><i class="fa fa-sign-out-alt me-2"></i>Logout</a>
                    </div>
                </nav>
            </div>

            <!-- Sidebar End -->


            <!-- Content Start -->
            <div class="content">
                <!-- Navbar Start -->
                <nav class="navbar navbar-expand sticky-top px-4 py-0 custom-topbar">
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

