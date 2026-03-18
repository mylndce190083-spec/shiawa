<%-- 
    Document   : post-book
    Created on : Mar 5, 2026, 3:55:12 PM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerAdmin.jsp" %>
<!-- Recent Sales Start -->

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">

        <h4 class="mb-4 text-success text-center">
            Publish Book To Marketplace
        </h4>

        <div class="row justify-content-center">
            <div class="col-lg-8">

                <form action="${pageContext.request.contextPath}/book-admin" method="post">

                    <input type="hidden" name="view" value="post"/>

                    <!-- BOOK SELECT -->
                    <div class="mb-3">
                        <label class="form-label">Select Book From Inventory</label>

                        <select name="bookId" class="form-select">
                            <c:forEach var="b" items="${books}">
                                <option value="${b.bookId}">
                                    ${b.title} (Stock: ${b.stock})
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- PRICE -->
                    <div class="mb-3">
                        <label class="form-label">Selling Price</label>

                        <input type="number"
                               name="price"
                               class="form-control"
                               required>
                    </div>

                    <!-- BUTTON -->
                    <div class="text-center mt-4">

                        <button class="btn btn-success px-4">
                            Publish Book
                        </button>

                        <a href="${pageContext.request.contextPath}/book-admin"
                           class="btn btn-secondary px-4">
                            Cancel
                        </a>

                    </div>

                </form>

            </div>
        </div>

    </div>
</div>


<!-- Recent Sales End -->
<%@include file="../include/footerAdmin.jsp" %>