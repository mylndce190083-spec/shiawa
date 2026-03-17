

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerInventory.jsp" %>

<!-- Recent Sales Start -->
<div class="container-fluid pt-4 px-4">
    <div class="bg-light text-center rounded p-4">
        <div class="d-flex align-items-center justify-content-between mb-4">
            <!--<h6 class="mb-0">User List</h6>-->
            <div class="d-flex justify-content-between align-items-center">
                <h6 class="mb-0">Kho sách (tồn kho)</h6>
            </div>
            <div class="d-flex gap-3 align-items-center">
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
                    <div class="bg-light text-center rounded p-4">
                        <div class="d-flex align-items-center justify-content-between mb-4">
                            <!--<h6 class="mb-0">User List</h6>-->
                            <div class="d-flex justify-content-between align-items-center">
                                <h6 class="mb-0">Kho sách (tồn kho)</h6>
                            </div>
                            <div class="d-flex gap-3 align-items-center">
                            </div>
                        </div>
                        <form class="row g-2 mb-3" method="get" action="${pageContext.request.contextPath}/inventory">
                            <input type="hidden" name="view" value="list"/>
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
                                <a class="btn btn-outline-secondary w-100" href="${pageContext.request.contextPath}/inventory?view=list">Reset</a>
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
                                        <th>Stock</th>
                                        <th>Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="b" items="${bookList}">
                                        <tr>
                                            <td>${b.bookId}</td>
                                            <td class="text-center">
                                                <c:choose>
                                                    <c:when test="${not empty b.urlImg}">
                                                        <img src="${pageContext.request.contextPath}/image?file=${b.urlImg}"
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
                                            <td>${b.stock}</td>
                                            <td>
                                                <span class="badge ${b.isIsActive() ? 'bg-success' : 'bg-danger'}">
                                                    ${b.isIsActive() ? 'Active' : 'Inactive'}
                                                </span>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                </tbody>

                            </table>
                        </div>

                        <c:if test="${totalPages > 1}">
                            <nav class="mt-4">
                                <ul class="pagination justify-content-end mb-0">
                                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                        <a class="page-link" href="${pageContext.request.contextPath}/inventory?view=list&page=${currentPage - 1}&minStock=${minStock}&maxStock=${maxStock}&sort=${sort}">Previous</a>
                                    </li>

                                    <c:forEach begin="1" end="${totalPages}" var="p">
                                        <li class="page-item ${p == currentPage ? 'active' : ''}">
                                            <a class="page-link" href="${pageContext.request.contextPath}/inventory?view=list&page=${p}&minStock=${minStock}&maxStock=${maxStock}&sort=${sort}">${p}</a>
                                        </li>
                                    </c:forEach>

                                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                        <a class="page-link" href="${pageContext.request.contextPath}/inventory?view=list&page=${currentPage + 1}&minStock=${minStock}&maxStock=${maxStock}&sort=${sort}">Next</a>
                                    </li>
                                </ul>
                            </nav>
                        </c:if>


                    </div>
                </div>
                <!-- Recent Sales End -->

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
                <a class="btn btn-outline-secondary w-100" href="${pageContext.request.contextPath}/inventory?view=list">Reset</a>
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
                        <th>Stock</th>
                        <th>Status</th>
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
                            <td>${b.stock}</td>
                            <td>
                                <span class="badge ${b.isIsActive() ? 'bg-success' : 'bg-danger'}">
                                    ${b.isIsActive() ? 'Active' : 'Inactive'}
                                </span>
                            </td>
                        </tr>
                    </c:forEach>

                </tbody>

            </table>
        </div>

        <c:if test="${totalPages > 1}">
            <nav class="mt-4">
                <ul class="pagination justify-content-end mb-0">
                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/book?page=${currentPage - 1}&minStock=${minStock}&maxStock=${maxStock}&sort=${sort}">Previous</a>
                    </li>

                    <c:forEach begin="1" end="${totalPages}" var="p">
                        <li class="page-item ${p == currentPage ? 'active' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/book?page=${p}&minStock=${minStock}&maxStock=${maxStock}&sort=${sort}">${p}</a>
                        </li>
                    </c:forEach>

                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/book?page=${currentPage + 1}&minStock=${minStock}&maxStock=${maxStock}&sort=${sort}">Next</a>
                    </li>
                </ul>
            </nav>
        </c:if>

    </div>
</div>
<!-- Recent Sales End -->
<%@include file="../include/footerInventory.jsp" %>               

