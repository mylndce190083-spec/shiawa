<%-- 
    Document   : list
    Created on : Mar 11, 2026, 3:41:38 PM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerAdmin.jsp" %>
<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">

        <div class="d-flex justify-content-between mb-3">
            <h4>Voucher Management</h4>
            <a href="${pageContext.request.contextPath}/voucher-admin?view=add" class="btn btn-success">
                <i class="fa fa-plus"></i> Add Voucher
            </a>
        </div>  
        <c:if test="${not empty sessionScope.msg}">
            <div class="alert alert-${sessionScope.msgType}">
                ${sessionScope.msg}
            </div>
            <c:remove var="msg" scope="session"/>
            <c:remove var="msgType" scope="session"/>
        </c:if>
                
        <table class="table table-bordered table-hover">
            <thead class="table-success">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Discount (%)</th>
                    <th>Quantity</th>
                    <th>Created Date</th>
                    <th>Expired Date</th>
                    <th>Action</th>
                </tr>
            </thead>

            <tbody>
                <c:forEach items="${voucherList}" var="v">
                    <tr>
                        <td>${v.voucher_id}</td>
                        <td>${v.name}</td>
                        <td>${v.discount}</td>
                        <td>${v.quantity}</td>
                        <td>${v.createdAt}</td>
                        <td>${v.endedAt}</td>

                        <td>
                            <a href="${pageContext.request.contextPath}/voucher-admin?view=edit&id=${v.voucher_id}" 
                               class="btn btn-warning btn-sm">
                                <i class="fa fa-edit"></i>
                            </a>

                            <a href="${pageContext.request.contextPath}/voucher-admin?view=delete&id=${v.voucher_id}" 
                               class="btn btn-danger btn-sm"
                               onclick="return confirm('Are you sure you want to delete this voucher?');">
                                <i class="fa fa-trash"></i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>

        </table>
    </div>
</div>
<%@include file="../include/footerAdmin.jsp" %>