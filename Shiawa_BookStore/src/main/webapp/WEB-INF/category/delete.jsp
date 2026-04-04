<%-- 
    Document   : delete
    Created on : Mar 16, 2026, 2:53:41 PM
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerAdmin.jsp" %>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light text-center rounded p-4">
        <div class="d-flex align-items-center justify-content-between mb-4">
            <!--<h6 class="mb-0">User List</h6>-->
            <div class="d-flex justify-content-between align-items-center">
                <h6 class="mb-0">Delete Category</h6>
            </div>                         
        </div>


        <div class="row">

            <div class="col-md-8">
                <form action="${pageContext.request.contextPath}/category-admin" method="post">
                    <input type="hidden" name="view" value="delete">
                    <input type="hidden" name="categoryId" value="${category.categoryId}">

                    <table class="table table-bordered text-start">
                        <tr>
                            <th width="30%">Category ID</th>
                            <td>${category.categoryId}</td>
                        </tr>
                        <tr>
                            <th>Name</th>
                            <td>${category.categoryName}</td>
                        </tr>
                        <c:if test="${categoryParent != null}">
                            <tr>
                                <th>Parent Category</th>
                                <td>${categoryParent.categoryName}</td>
                            </tr>
                        </c:if>                      
                        

                    </table>

                    <div class="mt-3">
                        <button type="submit" class="btn btn-danger"
                                onclick="return confirm('Are you sure you want to delete this category?')">
                            <i class="fa fa-trash me-2"></i>Confirm Delete
                        </button>

                        <a href="${pageContext.request.contextPath}/category-admin"
                           class="btn btn-secondary ms-2">
                            Cancel
                        </a>
                    </div>
                </form>

            </div>
        </div>


    </div>
</div>

                        <%@include file="../include/footerAdmin.jsp" %>
