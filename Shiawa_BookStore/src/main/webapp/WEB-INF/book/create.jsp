<%-- 
    Document   : create
    Created on : Jan 31, 2026, 10:50:31 AM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Add Book</title>
    </head>
    <body>
        <h2>Add New Book</h2>

        <form action="${pageContext.request.contextPath}/book" method="post">
            <input type="hidden" name="view" value="add"/>

            Title: <input type="text" name="title" required/><br/>
            Author: <input type="text" name="author" required/><br/>
            Price: <input type="number" step="0.01" name="price" required/><br/>
            Stock: <input type="number" name="stock" required/><br/>
            <label>Category:</label>
            <select name="categoryId" required>
                <option value="">-- Select Category --</option>
                <c:forEach var="c" items="${categoryList}">
                    <option value="${c.categoryId}">
                        ${c.name}
                    </option>
                </c:forEach>
            </select>
            <br/>

            <button type="submit">Save</button>
            <a href="${pageContext.request.contextPath}/book">Cancel</a>
        </form>
    </body>
</html>
