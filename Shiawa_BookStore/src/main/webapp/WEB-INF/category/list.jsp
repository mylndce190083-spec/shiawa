<%-- 
    Document   : list
    Created on : Mar 12, 2026, 3:11:26 PM
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerAdmin.jsp" %>
<!-- Recent Sales Start -->
<div class="container-fluid pt-4 px-4">
    <div class="bg-light text-center rounded p-4">
        <c:if test="${not empty sessionScope.msg}">
            <div class="alert alert-${sessionScope.msgType} alert-dismissible fade show" role="alert">
                ${sessionScope.msg}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>

            <!-- Xóa message sau khi hiển thị -->
            <c:remove var="msg" scope="session"/>
            <c:remove var="msgType" scope="session"/>
        </c:if>

        <div class="d-flex align-items-center justify-content-between mb-4">
            <!--<h6 class="mb-0">User List</h6>-->
            <div class="d-flex justify-content-between align-items-center">
                <h6 class="mb-0">Category List</h6>
            </div>

            <div class="d-flex gap-3 align-items-center">
                <form action="${pageContext.request.contextPath}/category-admin"
                      method="get">

                    <select name="categoryParentId"
                            class="form-select form-select-sm"
                            onchange="this.form.submit()">

                        <option value="">All Parent Categories</option>

                        <c:forEach var="pc" items="${categoryParentList}">
                            <option value="${pc.categoryId}"
                                    ${pc.categoryId == selectedCategoryId ? "selected" : ""}>
                                ${pc.categoryName}
                            </option>
                        </c:forEach>

                    </select>
                </form>

                <form action="${pageContext.request.contextPath}/category-admin" method="get" class="d-flex">
                    <input type="text" 
                           name="keyword"
                           value="${keyword}"
                           class="form-control form-control-sm me-2"
                           placeholder="Search category by name">
                    <button type="submit" class="btn btn-sm btn-outline-success">
                        <i class="fa fa-search"></i>
                    </button>
                </form>

                <c:if test="${not empty keyword}">
                    <a href="${pageContext.request.contextPath}/category-admin"
                       class="btn btn-sm btn-secondary">
                        Show All
                    </a>
                </c:if>

                <div class="d-flex gap-4">
                    <a class="btn btn-sm btn-success"
                       href="${pageContext.request.contextPath}/category-admin?view=add">
                        Add New Category
                    </a>
                    <a class="btn btn-sm btn-success"
                       href="${pageContext.request.contextPath}/category-admin?view=addParent">
                        Add New Parent Category
                    </a>
                </div>
            </div>

        </div>
        <c:if test="${not empty searchMsg}">
            <div class="alert alert-warning text-start mb-3">
                ${searchMsg}
            </div>
        </c:if>
        <!--%
            java.util.Enumeration<String> attrs = request.getAttributeNames();

            while (attrs.hasMoreElements()) {
                String name = attrs.nextElement();
                Object value = request.getAttribute(name);

                out.println("<h3>Attribute: " + name + "</h3>");

                if (value instanceof java.util.List) {
                    java.util.List list = (java.util.List) value;

                    for (Object item : list) {
                        out.println(item + "<br>");
                    }
                } else {
                    out.println(value + "<br>");
                }
            }
        %-->
        <div class="table-responsive">
            <c:choose>
                <c:when test="${empty categoryList}">
                    <!-- chỉ hiện thông báo, không hiện bảng -->
                </c:when>
                <c:otherwise>
                    <table class="table text-start align-middle table-bordered table-hover mb-0">
                        <thead>
                            <tr class="text-success">
                                <th>ID</th>
                                <th>Name</th>
                                <th>Type</th>
                                <th>Parent Category</th>
                                <th class="text-center">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="c" items="${categoryList}">
                                <tr>
                                    <td>${c.categoryId}</td>
                                    <td>${c.categoryName}</td>
                                    <c:choose>
                                        <c:when test="${c.parentId == 0}">
                                            <td>Parent Category</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>Category</td>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${c.parentId == 0}">
                                            <td>None</td>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="pc" items="${categoryParentList}">
                                                <c:if test="${c.parentId == pc.categoryId}">
                                                    <td>${pc.categoryName}</td>
                                                </c:if>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="text-center">
                                        <c:choose>
                                            <c:when test="${c.parentId == 0}">
                                                <a href="${pageContext.request.contextPath}/category-admin?view=editParent&id=${c.categoryId}"
                                                   class="btn btn-sm btn-warning">
                                                    Edit
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="${pageContext.request.contextPath}/category-admin?view=edit&id=${c.categoryId}"
                                                   class="btn btn-sm btn-warning">
                                                    Edit
                                                </a>
                                            </c:otherwise>
                                        </c:choose>

                                        <a href="${pageContext.request.contextPath}/category-admin?view=delete&id=${c.categoryId}"
                                           class="btn btn-sm btn-danger">
                                            Delete
                                        </a>

                                    </td>
                                </tr>
                            </c:forEach>

                        </tbody>

                    </table>
                </c:otherwise>
            </c:choose>
        </div>

    </div>
</div>
<!-- Recent Sales End -->
<%@include file="../include/footerAdmin.jsp" %>