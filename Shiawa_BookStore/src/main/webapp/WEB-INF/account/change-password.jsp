<%-- 
    Document   : change-password
    Created on : Mar 6, 2026, 1:30:52 PM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Chang Password</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link href="${pageContext.request.contextPath}/assets/css/css.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <jsp:include page="/client/layout/header.jsp"/>
        <section class="account-page">

            <h2>Change Password</h2>

            <!-- TABS -->
            <div class="tab-content active">

                <form action="${pageContext.request.contextPath}/change-password" method="post">

                    <label>Current Password</label>
                    <input type="password" name="currentPassword" placeholder="Enter current password" required>

                    <label>New Password</label>
                    <input type="password" name="newPassword" placeholder="Enter new password" required>

                    <label>Confirm Password</label>
                    <input type="password" name="confirmPassword" placeholder="Confirm your password" required>

                    <p style="color:red;">
                        ${error}
                    </p>

                    <button type="submit" class="submit-btn">
                        Save new password
                    </button>

                </form>

            </div>

        </section>

        <script src="assets/main.js"></script>
    </body>
</html>


