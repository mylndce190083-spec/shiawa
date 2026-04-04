<%-- 
    Document   : total
    Created on : Mar 23, 2026, 3:06:53 PM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerAdmin.jsp" %>

<style>
    .dashboard {
        padding: 40px;
        background: #f4f6f9;
    }

    .card-container {
        display: flex;
        gap: 30px;
        justify-content: center;
        margin-bottom: 40px;
    }

    .card {
        width: 300px;
        background: white;
        padding: 25px;
        border-radius: 12px;
        box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        text-align: center;

        opacity: 0;
        transform: translateY(30px) scale(0.95);
        animation: fadeInUp 0.6s ease forwards;
    }

    .card:nth-child(1) {
        animation-delay: 0.2s;
    }

    .card:nth-child(2) {
        animation-delay: 0.4s;
    }

    @keyframes fadeInUp {
        to {
            opacity: 1;
            transform: translateY(0) scale(1);
        }
    }

    .card h3 {
        color: #666;
        margin-bottom: 10px;
    }

    .value {
        font-size: 28px;
        font-weight: bold;
        color: #28a745;
    }

    .table-container {
        width: 70%;
        margin: auto;
        background: white;
        padding: 25px;
        border-radius: 12px;
        box-shadow: 0 5px 15px rgba(0,0,0,0.1);
    }

    table {
        width: 100%;
        border-collapse: collapse;
    }

    th, td {
        padding: 12px;
        border-bottom: 1px solid #ddd;
        text-align: center;
    }

    th {
        background: #28a745;
        color: white;
    }

    tr:hover {
        background: #f1f1f1;
    }
</style>

<div class="dashboard">

    <div class="card-container">

        <div class="card">
            <h3>💰 Total Income</h3>
            <div class="value">${totalIncome} VND</div>
        </div>

        <div class="card">
            <h3>📦 Total Sold</h3>
            <div class="value">${totalSold}</div>
        </div>

    </div>

    <div class="table-container">
        <h3 style="text-align:center; margin-bottom:20px;">🔥 Best Seller Books</h3>

        <table>
            <tr>
                <th>Rank</th>
                <th>Book Title</th>
                <th>Sold</th>
            </tr>

            <c:forEach var="b" items="${bestSeller}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td>${b.title}</td>
                    <td>${b.sold}</td>
                </tr>
            </c:forEach>

        </table>
    </div>

</div>

<%@include file="../include/footerAdmin.jsp" %>
