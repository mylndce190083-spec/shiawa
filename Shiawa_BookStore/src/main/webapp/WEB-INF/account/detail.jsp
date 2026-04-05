<%-- 
    Document   : detail
    Created on : Mar 5, 2026, 5:44:18 PM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerAdmin.jsp" %>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">

        <div class="d-flex align-items-center justify-content-between mb-4">
            <h6 class="mb-0">User Detail</h6>
        </div>

        <div class="row">

            <!-- Avatar -->
            <div class="col-md-4 text-center">

                <c:choose>
                    <c:when test="${not empty account.avatar}">
                        <img src="${pageContext.request.contextPath}/image?file=${account.avatar}"
                             class="img-fluid rounded-circle border"
                             style="width:220px;height:220px;object-fit:cover;">
                    </c:when>

                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/assets/img/default-avatar.png"
                             class="img-fluid rounded-circle border"
                             style="width:220px;height:220px;object-fit:cover;">
                    </c:otherwise>
                </c:choose>

            </div>


            <div class="col-md-8">

                <table class="table table-bordered text-start">

                    <tr>
                        <th width="30%">Username</th>
                        <td>${account.username}</td>
                    </tr>

                    <tr>
                        <th>Full Name</th>
                        <td>${account.fullName}</td>
                    </tr>

                    <tr>
                        <th>Gender</th>
                        <td>${account.gender}</td>
                    </tr>

                    <tr>
                        <th>Email</th>
                        <td>${account.email}</td>
                    </tr>

                    <tr>
                        <th>Phone</th>
                        <td>${account.phone}</td>
                    </tr>

                    <tr>
                        <th>Address</th>
                        <td>${account.address}</td>
                    </tr>

                    <tr>
                        <th>Status</th>
                        <td>
                            <c:choose>
                                <c:when test="${account.status == 'active'}">
                                    <span class="badge bg-success">Active</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-danger">Inactive</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>

                </table>

                <a href="${pageContext.request.contextPath}/account"
                   class="btn btn-secondary mt-3">
                    <i class="fa fa-arrow-left me-2"></i>Back to User List
                </a>

            </div>
        </div>
    </div>
</div>


<%@include file="../include/footerAdmin.jsp" %>