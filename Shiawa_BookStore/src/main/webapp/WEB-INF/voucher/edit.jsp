<%-- 
    Document   : edit
    Created on : Mar 11, 2026, 4:03:04 PM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerAdmin.jsp" %>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4 col-lg-8 mx-auto">

        <h4 class="mb-4">Edit Voucher</h4>

        <form action="${pageContext.request.contextPath}/voucher-admin" method="post">

            <input type="hidden" name="view" value="update">
            <input type="hidden" name="id" value="${voucher.voucher_id}">

            <div class="mb-3">
                <label class="form-label">Voucher Name</label>
                <input type="text" name="name" value="${voucher.name}" class="form-control">
            </div>

            <div class="row">

                <div class="col-md-6 mb-3">
                    <label class="form-label">Discount (%)</label>
                    <input type="number" name="discount" value="${voucher.discount}" class="form-control">
                </div>

                <div class="col-md-6 mb-3">
                    <label class="form-label">Quantity</label>
                    <input type="number" name="quantity" value="${voucher.quantity}" class="form-control">
                </div>

            </div>

            <div class="row">

                <div class="col-md-6 mb-3">
                    <label class="form-label">Created Date</label>
                    <input type="date" name="createdAt" value="${voucher.createdAt}" class="form-control">
                </div>

                <div class="col-md-6 mb-3">
                    <label class="form-label">End Date</label>
                    <input type="date" name="endedAt" value="${voucher.endedAt}" class="form-control">
                </div>

            </div>

            <button class="btn btn-primary">
                Update Voucher
            </button>

            <a href="${pageContext.request.contextPath}/voucher-admin" class="btn btn-secondary">
                Cancel
            </a>

        </form>

    </div>
</div>

<%@include file="../include/footerAdmin.jsp" %>