<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerInventory.jsp" %>
<!-- Recent Sales Start -->
<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h6 class="mb-0">Thông báo & lịch sử yêu cầu nhập sách</h6>
            <a class="btn btn-outline-secondary btn-sm" href="${pageContext.request.contextPath}/inventory?view=in">Quay lại nhập kho</a>
        </div>

        <c:if test="${approvedCount > 0}">
            <div class="alert alert-success">
                Bạn có <strong>${approvedCount}</strong> yêu cầu đã được Admin đồng ý.
            </div>
        </c:if>

        <c:if test="${empty requestHistory}">
            <div class="alert alert-info mb-0">Chưa có yêu cầu nhập kho nào.</div>
        </c:if>

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
                        <h6 class="mb-0">Thông báo & lịch sử yêu cầu nhập sách</h6>
                        <a class="btn btn-outline-secondary btn-sm" href="${pageContext.request.contextPath}/inventory?view=in">Quay lại nhập kho</a>
                    </div>

                    <c:if test="${approvedCount > 0}">
                        <div class="alert alert-success">
                            Bạn có <strong>${approvedCount}</strong> yêu cầu đã được Admin đồng ý.
                        </div>
                    </c:if>

                    <c:if test="${empty requestHistory}">
                        <div class="alert alert-info mb-0">Chưa có yêu cầu nhập kho nào.</div>
                    </c:if>

                    <c:if test="${not empty requestHistory}">
                        <div class="table-responsive">
                            <table class="table table-bordered align-middle mb-0">
                                <thead>
                                    <tr class="text-success">
                                        <th>ID</th>
                                        <th>Mã yêu cầu</th>
                                        <th>Trạng thái</th>
                                        <th>Ghi chú</th>
                                        <th>Xem chi tiết</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="r" items="${requestHistory}">
                                        <tr>
                                            <td>${r.requestId}</td>
                                            <td>${r.requestCode}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${r.status == 'APPROVED'}"><span class="badge bg-success">Đã duyệt</span></c:when>
                                                    <c:when test="${r.status == 'REJECTED'}"><span class="badge bg-danger">Từ chối</span></c:when>
                                                    <c:otherwise><span class="badge bg-warning text-dark">Chờ duyệt</span></c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${r.note}</td>
                                            <td>
                                                <a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/inventory?view=history-detail&id=${r.requestId}">Xem chi tiết</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                </div>

            
    </div>
</div>
<!-- Recent Sales End -->
<%@include file="../include/footerInventory.jsp" %>



