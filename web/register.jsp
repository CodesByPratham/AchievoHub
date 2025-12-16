<%-- 
    Document   : register
    Created on : 21-Nov-2024, 9:31:46 am
    Author     : PRATHAM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="errorPage.jsp"%>
<%request.setAttribute("pageTitle", "User Registeration");%>
<!DOCTYPE html>
<html>
    <%@include file="includes/head.jsp"%>
    <body class="">
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
        <div class="container-fluid h-custom mb-4">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-md-9 col-lg-6 col-xl-5">
                    <dotlottie-player src="https://lottie.host/4f0e05f1-20d4-4ff2-b147-c8b7fcf5c1d9/0F1bgwf5Bn.lottie" background="transparent" speed="1" style="width: 100%; height: 100%" loop autoplay></dotlottie-player>
                </div>
                <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
                    <form action="Controller" method="post">
                        <div class="row">
                            <div class="form-outline col-md-6 mb-3">
                                <label class="form-label">First Name:</label>
                                <input type="text" class="form-control" name="fname" placeholder="Enter first name" required />
                            </div>
                            <div class="form-outline col-md-6 mb-3">
                                <label class="form-label">Last Name:</label>
                                <input type="text" class="form-control" name="lname" placeholder="Enter last name" required />
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-outline col-md-6 mb-3">
                                <label class="form-label">Contact No:</label>
                                <input type="text" class="form-control" name="contact" placeholder="Enter contact number" required />
                            </div>
                            <div class="form-outline col-md-6 mb-3">
                                <label class="form-label">Email:</label>
                                <input type="text" id="email" class="form-control" name="email" placeholder="Enter email" required />
                                <small id="emailFeedback" class="text-danger"></small>
                            </div>
                        </div>
                        <div class="form-outline mb-3">
                            <label class="form-label">Username:</label>
                            <input type="text" id="username" class="form-control" name="username" placeholder="Enter username" required />
                            <small id="usernameFeedback" class="text-danger"></small>
                        </div>
                        <div class="row">
                            <div class="form-outline col-md-6 mb-3">
                                <label class="form-label">Password:</label>
                                <input type="password" id="password" class="form-control" name="password" placeholder="Enter password" required />
                            </div>
                            <div class="form-outline col-md-6 mb-3">
                                <label class="form-label">Confirm Password:</label>
                                <input type="password" id="confirmPassword" class="form-control" name="confirmPassword" placeholder="Confirm password" required />
                                <small id="passwordFeedback" class="text-danger"></small>
                            </div>
                        </div>
                        <div class="text-center text-md-end mt-4">
                            <button type="submit" class="btn btn-primary btn-md btn-block" name="submit" value="register">Register</button>
                            <p class="medium fw-bold mt-2 pt-1 mb-0">Already have an account? <a href="index.jsp" class="link-danger">Login</a></p>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script src="assets/js/register.js"></script>
    </body>
</html>