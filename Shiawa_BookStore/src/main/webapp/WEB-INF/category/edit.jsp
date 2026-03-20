<%-- 
    Document   : edit
    Created on : Mar 14, 2026, 11:34:10 AM
    Author     : Lenovo
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerAdmin.jsp" %>
<!-- Recent Sales Start -->
<div class="container-fluid pt-4 px-4">
    <div class="bg-light text-center rounded p-4">
        <div class="d-flex align-items-center justify-content-between mb-4">
            <!--<h6 class="mb-0">User List</h6>-->
            <div class="d-flex justify-content-between align-items-center">
                <h6 class="mb-0">Edit Category</h6>
            </div>                         
        </div>

        <form action="${pageContext.request.contextPath}/category-admin" method="post">
            <div class="row">
                <!-- Book Information -->
                <div class="col-md-8">

                    <input type="hidden" name="view" value="edit">
                    <input type="hidden" name="categoryId" value="${category.categoryId}">

                    <table class="table table-bordered text-start">

                        <tr>
                            <th width="30%">Category ID</th>
                            <td>
                                <input class="form-control" value="${category.categoryId}" disabled>
                            </td>
                        </tr>

                        <tr>
                            <th>Name</th>
                            <td>
                                <input class="form-control" name="name" value="${category.categoryName}" required>
                            </td>
                        </tr>

                        <c:if test="${categoryParentList != null}">
                            <tr>
                                <th>Parent Category</th>
                                <td>
                                    <select name="categoryParentId" class="form-select">
                                        <c:forEach var="pc" items="${categoryParentList}">
                                            <option value="${pc.categoryId}"
                                                    ${pc.categoryId == category.parentId ? 'selected' : ''}>
                                                ${pc.categoryName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                        </c:if>
                        <c:if test="${categoryParentList == null}">
                            <input type="hidden" name="categoryParentId" value="0"/>
                        </c:if>

                    </table>

                    <!-- Save & Back Button -->
                    <div class="mt-3">
                        <button type="submit" class="btn btn-primary">
                            <i class="fa fa-save me-2"></i>Save Changes
                        </button>

                        <a href="${pageContext.request.contextPath}/category-admin"
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

<%@include file="../include/footerAdmin.jsp" %>
