<%-- 
    Document   : resetPassword
    Created on : 11-Feb-2025, 10:22:15  pm
    Author     : PRATHAM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="errorPage.jsp"%>
<%request.setAttribute("pageTitle", "Reset Password");%>
<!DOCTYPE html>
<html>
    <%@include file="includes/head.jsp"%>
    <body class="no-scroll">
        <nav class="bg-primary">
            <div class="container">
                <div class="py-2 px-2 px-xl-5 bg-primary">
                    <div class="text-white mb-3 mb-md-0">
                        <%if (request.getAttribute("message") != null) {%>
                        <p><%= request.getAttribute("message")%></p>
                        <%}%>
                    </div>
                </div>
            </div>
        </nav>
        <div class="container-fluid h-custom">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-md-9 col-lg-6 col-xl-5">
                    <dotlottie-player src="https://lottie.host/4f0e05f1-20d4-4ff2-b147-c8b7fcf5c1d9/0F1bgwf5Bn.lottie" background="transparent" speed="1" style="width: 100%; height: 100%" loop autoplay></dotlottie-player>
                </div>
                <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
                    <form action="Controller" method="post" id="resetPasswordForm">
                        <input type="hidden" name="token" value="<%=request.getParameter("token")%>">
                        <div class="form-outline mb-4">
                            <label class="form-label">New Password:</label>
                            <input type="text" class="form-control" id="password" name="password" placeholder="Enter new password" required />
                            <small id="passwordStrength" class="text-danger"></small>
                        </div>
                        <div class="form-outline mb-3">
                            <label class="form-label">Confirm Password:</label>
                            <input type="password" class="form-control" name="confirmPassword" id="confirmPassword" placeholder="Re-enter new password" required />
                            <small id="passwordMatch" class="text-danger"></small>
                        </div>
                        <div class="text-center text-md-end mt-4">
                            <button type="submit" class="btn btn-primary btn-md btn-block" id="submitBtn" name="submit" value="resetPassword" disabled>Reset Password</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script src="assets/js/resetPassword.js"></script>
    </body>
</html>