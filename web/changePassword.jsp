<%-- 
    Document   : changePassword
    Created on : 14-Feb-2025, 4:55:36 pm
    Author     : PRATHAM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="errorPage.jsp"%>
<%
    if (session == null || session.getAttribute("username") == null) {
        request.setAttribute("message", "urlMessing");
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
        return;
    }

    request.setAttribute("pageTitle", "Change Password");
%>
<!DOCTYPE html>
<html lang="en">
    <%@include file="includes/head.jsp" %>
    <body class="no-scroll">
        <%@include file="includes/navbar.jsp" %>
        <div class="container-fluid p-md-5 p-3">
            <div class="card">
                <div class="card-header">
                    <h4>Password Change</h4>
                    <%if (request.getAttribute("message") != null) {%>
                    <p><%= request.getAttribute("message")%></p>
                    <%}%>
                </div>
                <div class="card-body pb-0">
                    <form action="Controller" method="post" id="changePasswordForm">
                        <div class="row">
                            <div class="form-group col">
                                <label>Current Password:</label>
                                <input type="password" class="form-control" name="currentPassword" placeholder="Enter current password" required />
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col">
                                <label>New Password:</label>
                                <input type="password" id="newPassword" class="form-control" name="newPassword" placeholder="Enter new password" required />
                                <small id="passwordStrength" class="text-danger"></small>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col">
                                <label>Confirm New Password:</label>
                                <input type="password" id="confirmPassword" class="form-control" name="confirmPassword" placeholder="Re-enter new password" required />
                                <small id="passwordMatch" class="text-danger"></small>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-md-12">
                                <button type="submit" class="btn btn-primary btn-md btn-block" value="changePassword" name="submit" id="submitBtn" disabled>Update Password</button>
                            </div>
                        </div>
                    </form> 
                </div>
            </div>
        </div>
        <script src="assets/js/changePassword.js"></script>
    </body>
</html>