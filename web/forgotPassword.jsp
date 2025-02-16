<%-- 
    Document   : forgotPassword
    Created on : 11-Feb-2025, 9:45:23 pm
    Author     : PRATHAM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="errorPage.jsp"%>
<%request.setAttribute("pageTitle", "Forgot Password");%>
<!DOCTYPE html>
<html>
    <%@include file="includes/head.jsp"%>
    <body class="no-scroll">
        <nav class="bg-primary">
            <div class="container">
                <div class="py-2 px-2 px-xl-5 bg-primary">
                    <div class="text-white mb-3 mb-md-0">
                        <%if (request.getAttribute("message") != null) {
                                String message = (String) request.getAttribute("message");
                                out.println("<p class='color-white m-0'>" + message + "</p>");
                            }%>
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
                    <form action="Controller" method="post">
                        <div class="form-outline mb-4">
                            <label class="form-label">Email address:</label>
                            <input type="text" class="form-control" name="identifier" placeholder="Enter a valid email address" required />
                        </div>
                        <div class="text-center text-md-end mt-4">
                            <button type="submit" class="btn btn-primary btn-md btn-block" name="submit" value="forgotPassword" >Send Reset Link</button>
                            <div class="d-flex justify-content-between">
                                <p class="medium fw-bold mt-2 pt-1 mb-0">New here? <a href="register.jsp" class="link-danger">Register</a></p>
                                <p class="medium fw-bold mt-2 pt-1 mb-0">Already have an account? <a href="index.jsp" class="link-danger">Login</a></p>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>