<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerAdmin.jsp" %>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4 mb-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h6 class="mb-0">Customer Support - Chat session list</h6>
        </div>

        <c:if test="${empty chatSessions}">
            <div class="alert alert-info mb-0">No chat session has started yet.</div>
        </c:if>

        <c:if test="${not empty chatSessions}">
            <div class="table-responsive">
                <table class="table table-bordered align-middle">
                    <thead>
                        <tr class="text-success">
                            <th>Session ID</th>
                            <th>Customer ID</th>
                            <th>Staff ID</th>
                            <th>Last message</th>
                            <th>Time</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="s" items="${chatSessions}">
                            <tr>
                                <td>${s.sessionId}</td>
                                <td>${s.customerId}</td>
                                <td>${s.staffId}</td>
                                <td>${s.lastMessage}</td>
                                <td>${s.lastSentAt}</td>
                                <td>
                                    <a class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/support-admin?view=chat&id=${s.sessionId}">Mở chat</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>

    <div class="bg-light rounded p-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h6 class="mb-0">Automatic reply (Keyword)</h6>
            <a class="btn btn-success" href="${pageContext.request.contextPath}/support-admin?view=knowledge-add">Add</a>
        </div>

        <c:if test="${empty knowledgeList}">
            <div class="alert alert-info mb-0">Keyword data is not available.</div>
        </c:if>

        <c:if test="${not empty knowledgeList}">
            <div class="table-responsive">
                <table class="table table-bordered align-middle">
                    <thead>
                        <tr class="text-success">
                            <th>ID</th>
                            <th>Keyword</th>
                            <th>Answer</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="k" items="${knowledgeList}">
                            <tr>
                                <td>${k.id}</td>
                                <td>${k.keyword}</td>
                                <td>${k.answer}</td>
                                <td>
                                    <span class="badge ${k.active ? 'bg-success' : 'bg-danger'}">
                                        ${k.active ? 'Active' : 'Inactive'}
                                    </span>
                                </td>
                                <td>
                                    <a class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/support-admin?view=knowledge-edit&id=${k.id}">Edit</a>
                                    <a class="btn btn-sm btn-warning" href="${pageContext.request.contextPath}/support-admin?view=knowledge-toggle&id=${k.id}">
                                        ${k.active ? 'Tắt' : 'Bật'}
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>
</div>

<%@include file="../include/footerAdmin.jsp" %>
