<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../include/headerAdmin.jsp" %>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">
        <h6 class="mb-3">Thêm câu trả lời tự động</h6>
        <form method="post" action="${pageContext.request.contextPath}/support-admin">
            <input type="hidden" name="view" value="knowledge-add"/>
            <div class="mb-3">
                <label class="form-label">Keyword</label>
                <input class="form-control" name="keyword" required />
            </div>
            <div class="mb-3">
                <label class="form-label">Answer</label>
                <textarea class="form-control" name="answer" rows="4" required></textarea>
            </div>
            <div class="form-check mb-3">
                <input class="form-check-input" type="checkbox" name="isActive" id="isActive" checked>
                <label class="form-check-label" for="isActive">Active</label>
            </div>
            <div class="d-flex gap-2">
                <button class="btn btn-success" type="submit">Lưu</button>
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/support-admin">Hủy</a>
            </div>
        </form>
    </div>
</div>

<%@include file="../include/footerAdmin.jsp" %>
