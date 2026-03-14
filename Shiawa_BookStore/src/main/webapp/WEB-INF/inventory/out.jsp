<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerInventory.jsp" %>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h6 class="mb-0">Phiếu xuất kho (Issue)</h6>
            <a class="btn btn-sm btn-outline-success" href="${pageContext.request.contextPath}/inventory?view=report">Báo cáo</a>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/inventory" method="post">
            <input type="hidden" name="view" value="out"/>

            <div class="row g-3 mb-3">
                <div class="col-md-4">
                    <label class="form-label">Mã phiếu</label>
                    <input class="form-control" name="txnCode" placeholder="Để trống sẽ tự tạo"/>
                </div>
                <div class="col-md-8">
                    <label class="form-label">Ghi chú</label>
                    <input class="form-control" name="note"/>
                </div>
            </div>

            <div class="table-responsive mb-3">
                <table class="table table-bordered align-middle">
                    <thead>
                        <tr class="text-success">
                            <th>Sách</th>
                            <th style="width:140px;">Số lượng</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach begin="1" end="5" var="i">
                            <tr>
                                <td>
                                    <select class="form-select" name="bookId">
                                        <option value="">-- chọn sách --</option>
                                        <c:forEach var="b" items="${books}">
                                            <option value="${b.bookId}">#${b.bookId} - ${b.title} (tồn: ${b.stock})</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td><input class="form-control" name="qty" type="number" min="1"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <button class="btn btn-success" type="submit">Tạo phiếu xuất</button>
            <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/inventory?view=low">Xem tồn thấp</a>
        </form>
    </div>
</div>

<%@include file="../include/footerInventory.jsp" %>




