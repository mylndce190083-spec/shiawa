<%-- 
    Document   : list
    Created on : Jan 31, 2026, 10:33:09 AM
    Author     : BA LIEM
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
                <h6 class="mb-0">User List</h6>
            </div>

            <div class="d-flex gap-4">
                <form action="${pageContext.request.contextPath}/account"
                      method="get">

                    <select name="role"
                            class="form-select form-select-sm"
                            onchange="this.form.submit()">

                        <option value="">All Roles</option>

                        <option value="Customer"
                                ${selectedRole == 'Customer' ? 'selected' : ''}>
                            Customer
                        </option>

                        <option value="Admin"
                                ${selectedRole == 'Admin' ? 'selected' : ''}>
                            Admin
                        </option>

                        <option value="Inventory"
                                ${selectedRole == 'Inventory' ? 'selected' : ''}>
                            Inventory
                        </option>

                        <option value="Sales"
                                ${selectedRole == 'Customer' ? 'selected' : ''}>
                            Customer
                        </option>

                    </select>
                </form>
                <a class="btn btn-sm btn-success" href="${pageContext.request.contextPath}/account?view=add">
                    Add user</a>
            </div>
        </div>
        <div class="table-responsive">
            <table class="table text-start align-middle table-bordered table-hover mb-0">
                <thead>
                    <tr class="text-success">
                        <th scope="col">ID</th>
                        <th scope="col">Username</th>
                        <th scope="col">Role</th>
                        <th scope="col">Email</th>
                        <th scope="col" class="text-center">Manage</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="a" items="${accounts}">
                        <tr>
                            <td>${a.id}</td>
                            <td>${a.username}</td>
                            <td>${a.role}</td>
                            <td>${a.email}</td>
                            <td class="text-center">

                                <div class="d-flex justify-content-center gap-2">

                                    <!-- View Detail -->
                                    <a href="account?view=detail&id=${a.id}&role=${a.role}"
                                       class="btn btn-sm btn-primary">
                                        Detail
                                    </a>

                                    <!-- Toggle Status -->
                                    <c:choose>
                                        <c:when test="${a.status == 'active'}">
                                            <a href="account?view=deactivate&id=${a.id}&role=${a.role}"
                                               class="btn btn-sm btn-danger">
                                                Deactivate
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="account?view=activate&id=${a.id}&role=${a.role}"
                                               class="btn btn-sm btn-success">
                                                Activate
                                            </a>
                                        </c:otherwise>
                                    </c:choose>

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
<%@include file="../include/footerAdmin.jsp" %>
