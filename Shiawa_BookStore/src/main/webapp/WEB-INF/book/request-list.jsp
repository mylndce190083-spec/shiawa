<%-- 
    Document   : create
    Created on : Jan 31, 2026, 10:50:31 AM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerAdmin.jsp" %>
<!-- Recent Sales Start -->
<div class="container-fluid pt-4 px-4">

    <div class="bg-light text-center rounded p-4">

        <div class="d-flex align-items-center justify-content-between mb-4">
            <h6 class="mb-0">Book Request List</h6>

            <a href="${pageContext.request.contextPath}/book-admin"
               class="btn btn-secondary btn-sm">
                <i class="fa fa-arrow-left me-1"></i> Back
            </a>
        </div>

        <div class="table-responsive">
            <table class="table table-bordered table-hover">

                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Author</th>
                        <th>Requested By</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>

                <tbody>

                    <c:forEach var="r" items="${requestList}">

                        <tr>
                            <td>${r.requestId}</td>
                            <td>${r.title}</td>
                            <td>${r.author}</td>
                            <td>${r.staffId}</td>
                            <td>${r.status}</td>

                            <td>

                                <a class="btn btn-sm btn-success"
                                   href="${pageContext.request.contextPath}/book-request?action=accept&id=${r.requestId}">
                                    Accept
                                </a>

                                <a class="btn btn-sm btn-danger"
                                   href="${pageContext.request.contextPath}/book-request?action=reject&id=${r.requestId}">
                                    Reject
                                </a>

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

