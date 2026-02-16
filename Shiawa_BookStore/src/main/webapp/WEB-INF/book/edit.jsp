<%-- 
    Document   : edit
    Created on : Jan 31, 2026, 10:50:37 AM
    Author     : BA LIEM
--%>

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
                                <h6 class="mb-0">Edit Book</h6>
                            </div>                         
                        </div>

                        <form action="${pageContext.request.contextPath}/book" method="post" enctype="multipart/form-data">
                            <div class="row">
                                <!-- LEFT: IMAGE MANAGEMENT -->
                                <div class="col-md-4">

                                    <h6>Book Images</h6>

                                    <!-- Upload form riêng -->
    <!--                                <form action="${pageContext.request.contextPath}/book_img"
                                          method="post"
                                          enctype="multipart/form-data"
                                          class="mb-3">
    
                                        <input type="hidden" name="action" value="upload">
                                        <input type="hidden" name="bookId" value="${book.bookId}">
    
                                        <input type="file" name="image" class="form-control mb-2" required>
    
                                        <button type="submit" class="btn btn-success w-100">
                                            <i class="fa fa-upload me-2"></i>Upload Image
                                        </button>
                                    </form>-->

                                    <!-- Danh sách ảnh -->
                                    <c:forEach var="img" items="${bookImages}">
                                        <c:if test="${not empty img.imageUrl}">
                                            <div class="border rounded p-2 mb-3 text-center">

                                                <img src="${pageContext.request.contextPath}/${img.imageUrl}"
                                                     class="img-fluid rounded mb-2"
                                                     style="max-height:150px"
                                                     onerror="this.src='assets/img/no-image.png'">
                                                <input type="hidden"
                                                       name="deleteImageIds"
                                                       id="delete_${img.imageId}"
                                                       value="">

                                                <div class="d-flex justify-content-between">

                                                    <!-- Set Primary -->
                                                    <div>
                                                        <!--                                                        <input type="hidden" name="action" value="setPrimary">
                                                                                                                <input type="hidden" name="imageId" value="${img.imageId}">
                                                                                                                <input type="hidden" name="bookId" value="${book.bookId}">
                                                                                                                <button type="submit"
                                                                                                                        class="btn btn-sm ${img.primary ? 'btn-warning' : 'btn-outline-warning'}"
                                                        ${img.primary ? 'disabled' : ''}>
                                                    <i class="fa fa-star"></i>
                                                </button>-->
                                                        <button type="button"
                                                                onclick="setPrimary(${img.imageId}, this)"
                                                                class="btn btn-sm ${img.primary ? 'btn-warning' : 'btn-outline-warning'}">
                                                            <i class="fa fa-star"></i>
                                                        </button>


                                                    </div>

                                                    <!-- Delete -->
                                                    <div>
                                                        <!--                                                        <input type="hidden" name="action" value="delete">
                                                                                                                <input type="hidden" name="imageId" value="${img.imageId}">
                                                                                                                <input type="hidden" name="bookId" value="${book.bookId}">
                                                                                                                <button type="submit" class="btn btn-danger btn-sm">
                                                                                                                    <i class="fa fa-trash"></i>
                                                                                                                </button>-->
                                                        <button type="button"
                                                                onclick="markDelete(${img.imageId}, this)"
                                                                class="btn btn-danger btn-sm">
                                                            <i class="fa fa-trash"></i>
                                                        </button>


                                                    </div>

                                                </div>

                                            </div>
                                        </c:if>
                                    </c:forEach>

                                </div>
                                <!-- Book Information -->
                                <div class="col-md-8">

                                    <input type="hidden" name="view" value="edit">
                                    <input type="hidden" name="bookId" value="${book.bookId}">

                                    <table class="table table-bordered text-start">

                                        <tr>
                                            <th width="30%">Book ID</th>
                                            <td>
                                                <input class="form-control" value="${book.bookId}" disabled>
                                            </td>
                                        </tr>

                                        <tr>
                                            <th>Title</th>
                                            <td>
                                                <input class="form-control" name="title" value="${book.title}" required>
                                            </td>
                                        </tr>

                                        <tr>
                                            <th>Author</th>
                                            <td>
                                                <input class="form-control" name="author" value="${book.author}" required>
                                            </td>
                                        </tr>

                                        <tr>
                                            <th>Category</th>
                                            <td>
                                                <select name="categoryId" class="form-select">
                                                    <c:forEach var="c" items="${categoryList}">
                                                        <option value="${c.categoryId}"
                                                                ${c.categoryId == book.categoryId ? 'selected' : ''}>
                                                            ${c.categoryName}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>

                                        <tr>
                                            <th>Price</th>
                                            <td>
                                                <input type="number" step="0.01" class="form-control"
                                                       name="price" value="${book.price}" required>
                                            </td>
                                        </tr>

                                        <tr>
                                            <th>Stock</th>
                                            <td>
                                                <input type="number" class="form-control"
                                                       name="stock" value="${book.stock}" required>
                                            </td>
                                        </tr>

                                        <tr>
                                            <th>Status</th>
                                            <td>
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio"
                                                           name="isActive" value="true"
                                                           ${book.isActive ? 'checked' : ''}>
                                                    <label class="form-check-label">Active</label>
                                                </div>
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio"
                                                           name="isActive" value="false"
                                                           ${!book.isActive ? 'checked' : ''}>
                                                    <label class="form-check-label">Inactive</label>
                                                </div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <th>Description</th>
                                            <td>
                                                <textarea name="description" rows="4"
                                                          class="form-control">${book.description}</textarea>
                                            </td>
                                        </tr>

                                        <tr>
                                            <th>Upload Images</th>
                                            <td>
                                                <input type="file" name="images" multiple class="form-control">
                                            </td>
                                        </tr>

                                    </table>

                                    <!-- Save & Back Button -->
                                    <div class="mt-3">
                                        <button type="submit" class="btn btn-primary">
                                            <i class="fa fa-save me-2"></i>Save Changes
                                        </button>

                                        <a href="${pageContext.request.contextPath}/book"
                                           class="btn btn-secondary ms-2">
                                            Cancel
                                        </a>
                                    </div>


                                </div>
                            </div>
                        </form>

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
        <script>
                                                                    function previewImage(input) {
                                                                        if (input.files && input.files[0]) {
                                                                            const reader = new FileReader();
                                                                            reader.onload = function (e) {
                                                                                document.getElementById('previewImg').src = e.target.result;
                                                                            }
                                                                            reader.readAsDataURL(input.files[0]);
                                                                        }
                                                                    }
        </script>

        <script>
            let selectedPrimary = null;

            function setPrimary(imageId, btn) {

                // Bỏ màu tất cả sao
                document.querySelectorAll('.fa-star').forEach(icon => {
                    icon.parentElement.classList.remove('btn-warning');
                    icon.parentElement.classList.add('btn-outline-warning');
                });

                // Set màu cho cái đang chọn
                btn.classList.remove('btn-outline-warning');
                btn.classList.add('btn-warning');

                selectedPrimary = imageId;

                // Nếu chưa có input hidden thì tạo
                let input = document.getElementById("primaryImageInput");
                if (!input) {
                    input = document.createElement("input");
                    input.type = "hidden";
                    input.name = "primaryImageId";
                    input.id = "primaryImageInput";
                    document.querySelector("form[action*='/book']").appendChild(input);
                }

                input.value = imageId;
            }

            function markDelete(imageId, btn) {

                // Ẩn ảnh ngay lập tức
                btn.closest(".border").style.display = "none";

                // Set hidden value
                document.getElementById("delete_" + imageId).value = imageId;
            }
        </script>

    </body>

</html>
