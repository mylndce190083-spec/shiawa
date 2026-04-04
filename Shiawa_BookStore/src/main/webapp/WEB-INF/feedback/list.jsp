<%-- 
    Document   : list
    Created on : Mar 20, 2026, 5:31:52 PM
    Author     : MY
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerAdmin.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feedback</title>
        <style>
            .feedback-box {
                background: #f5f5f5;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            }

            .feedback-box h6 {
                font-size: 18px;
                font-weight: bold;
            }

            .table thead {
                background: #e9ecef;
            }

            .table tbody tr:hover {
                background-color: #f1f1f1;
                transition: 0.2s;
            }
            .text-muted {
                background-color: #d6d6d6 !important;
                color: #555 !important;
                opacity: 0.8;
            }
        </style>
    </head>
    <body>
        <div class="container mt-4">
            <div class="feedback-box">

                <div class="d-flex align-items-center justify-content-between mb-3">
                    <h6 class="mb-0">Feedback List</h6>
                </div>

                <div class="table-responsive">
                    <c:choose>
                        <c:when test="${empty feedbackList}">
                            <div class="alert alert-warning text-start mb-3">
                                No feedback found!
                            </div>
                        </c:when>

                        <c:otherwise>
                            <table class="table text-start align-middle table-bordered table-hover mb-0">
                                <thead>
                                    <tr class="text-success">
                                        <th>Order Detail ID</th>
                                        <th>Username</th>
                                        <th>Book</th>
                                        <th>Comment</th>
                                        <th>Rating</th>
                                        <th class="text-center">Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="f" items="${feedbackList}">
                                        <tr id="row-${f.id}" class="${f.status == 0 ? 'text-muted' : ''}">
                                            <td>${f.orderdetailId}</td>
                                            <td>${f.username}</td>
                                            <td>${f.bookTitle}</td>
                                            <td>${f.content}</td>
                                            <td>${f.rating}</td>
                                            <td class="text-center">
                                                <c:choose>
                                                    <c:when test="${f.status == 0}">
                                                        <button class="btn btn-secondary btn-sm"
                                                                onclick="hideFeedback(${f.id}, 1)">
                                                            Unhide
                                                        </button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button class="btn btn-danger btn-sm"
                                                                onclick="hideFeedback(${f.id}, 0)">
                                                            Hide
                                                        </button>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </div>

            </div>
        </div>
        <script>
            setTimeout(function () {
                document.getElementById("spinner").classList.remove("show");
            }, 300);
            function hideFeedback(id, status) {
                fetch("feedback-admin", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    body: "id=" + id + "&status=" + status
                })
                        .then(res => res.text())
                        .then(data => {
                            if (data.trim() === "success") {
                                const row = document.getElementById("row-" + id);

                                if (row) {
                                    const btn = row.querySelector("button");

                                    if (status === 0) {

                                        row.classList.add("text-muted");

                                        btn.classList.remove("btn-danger");
                                        btn.classList.add("btn-secondary");
                                        btn.innerText = "Unhide";
                                        btn.setAttribute("onclick", "hideFeedback(" + id + ",1)");
                                    } else {
                                        
                                        row.classList.remove("text-muted");

                                        btn.classList.remove("btn-secondary");
                                        btn.classList.add("btn-danger");
                                        btn.innerText = "Hide";
                                        btn.setAttribute("onclick", "hideFeedback(" + id + ",0)");
                                    }
                                }
                            } else {
                                alert("Update failed!");
                            }
                        })
                        .catch(err => {
                            console.error(err);
                            alert("Error!");
                        });
            }
        </script>
    </body>
    <%@include file="../include/footerAdmin.jsp" %>
</html>
