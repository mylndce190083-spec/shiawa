<%-- 
    Document   : add
    Created on : Mar 14, 2026, 9:26:42 AM
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerAdmin.jsp" %>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">

        <h4 class="mb-4 text-success text-center">
            Add New Category
        </h4>

        <div class="row justify-content-center">
            <div class="col-lg-8">

                <form action="${pageContext.request.contextPath}/category-admin" method="post">
                    <input type="hidden" name="view" value="add"/>

                    <div class="mb-3">
                        <label class="form-label">Name</label>
                        <input type="text" name="name" class="form-control" required>
                    </div>

                    <c:if test="${categoryParentList != null}">
                        <div class="mb-3">
                            <label class="form-label">Parent Category</label>
                            <select name="categoryParentId" class="form-select">
                                <c:forEach var="pc" items="${categoryParentList}">
                                    <option value="${pc.categoryId}">
                                        ${pc.categoryName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </c:if>
                    <c:if test="${categoryParentList == null}">
                        <input type="hidden" name="categoryParentId" value="0"/>
                    </c:if>

                    <div class="text-center mt-4">
                        <button class="btn btn-success px-4">
                            Add Category
                        </button>

                        <a href="${pageContext.request.contextPath}/category-admin"
                           class="btn btn-secondary px-4">
                            Cancel
                        </a>
                    </div>

                </form>

            </div>
        </div>

    </div>
</div>

<!-- Recent Sales End -->
<%@include file="../include/footerAdmin.jsp" %>
