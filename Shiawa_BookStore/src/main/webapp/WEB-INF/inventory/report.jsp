<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerInventory.jsp" %>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h6 class="mb-0">Import report</h6>
        </div>

        <form class="row g-2 align-items-end mb-4" method="get" action="${pageContext.request.contextPath}/inventory">
            <input type="hidden" name="view" value="report"/>
            <div class="col-md-3">
                <label class="form-label">From</label>
                <input class="form-control" type="date" name="from" value="${from}"/>
            </div>
            <div class="col-md-3">
                <label class="form-label">To</label>
                <input class="form-control" type="date" name="to" value="${to}"/>
            </div>
            <div class="col-md-4">
                <label class="form-label">Filter by book</label>
                <select class="form-select" name="bookId">
                    <option value="">-- All --</option>
                    <c:forEach var="b" items="${books}">
                        <option value="${b.bookId}" ${selectedBookId == b.bookId ? 'selected' : ''}>#${b.bookId} - ${b.title}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-2">
                <button class="btn btn-success w-100" type="submit">View</button>
            </div>
        </form>

        <div class="row g-4">
            <div class="col-lg-6">
                <h6 class="mb-2">Summary by day</h6>
                <div class="table-responsive">
                    <table class="table table-bordered table-hover mb-0">
                        <thead>
                            <tr class="text-success">
                                <th>Day</th>
                                <th>Type</th>
                                <th>Total Quantity</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="r" items="${dailyRows}">
                                <tr>
                                    <td>${r.day}</td>
                                    <td>${r.txnType}</td>
                                    <td>${r.totalQty}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="col-lg-6">
                <h6 class="mb-2">Summary by product</h6>
                <div class="table-responsive">
                    <table class="table table-bordered table-hover mb-0">
                        <thead>
                            <tr class="text-success">
                                <th>Book</th>
                                <th>Type</th>
                                <th>Total Qantity</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="r" items="${productRows}">
                                <tr>
                                    <td>#${r.bookId} - ${r.title}</td>
                                    <td>${r.txnType}</td>
                                    <td>${r.totalQty}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="../include/footerInventory.jsp" %>

