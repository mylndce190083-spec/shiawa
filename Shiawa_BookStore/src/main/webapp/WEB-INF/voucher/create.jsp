<%-- 
    Document   : create
    Created on : Mar 11, 2026, 3:49:26 PM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerAdmin.jsp" %>
<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4 col-lg-8 mx-auto">

        <h4 class="mb-4">Add Voucher</h4>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">
                ${error}
            </div>
        </c:if>
        <form action="${pageContext.request.contextPath}/voucher-admin" method="post">
            <input type="hidden" name="view" value="add">

            <div class="mb-3">
                <label class="form-label">Voucher Name</label>
                <input type="text" name="name" class="form-control" required>
            </div>

            <div class="row">

                <div class="col-md-6 mb-3">
                    <label class="form-label">Discount (%)</label>
                    <input type="number" name="discount" class="form-control" min="0" max="100" required>
                </div>

                <div class="col-md-6 mb-3">
                    <label class="form-label">Quantity</label>
                    <input type="number" name="quantity" class="form-control" required>
                </div>

            </div>

            <div class="row">

                <div class="col-md-6 mb-3">
                    <label class="form-label">Created Date</label>
                    <input type="date" name="createdAt" class="form-control">
                </div>

                <div class="col-md-6 mb-3">
                    <label class="form-label">End Date</label>
                    <input type="date" name="endedAt" class="form-control">
                </div>

            </div>

            <button class="btn btn-success">
                Save Voucher
            </button>

            <a href="${pageContext.request.contextPath}/voucher-admin" class="btn btn-secondary">
                Cancel
            </a>

        </form>

    </div>
</div>
<%@include file="../include/footerAdmin.jsp" %>