<%-- 
    Document   : index
    Created on : 14-Feb-2025, 3:32:15  pm
    Author     : PRATHAM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="errorPage.jsp"%>
<%@page import="model.User"%>

<%
    User user = (User) request.getAttribute("user");

//    if (session == null || session.getAttribute("username") == null || user == null) {
//        request.setAttribute("message", "urlMessing");
//        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
//        dispatcher.forward(request, response);
//        return;
//    }

    request.setAttribute("pageTitle", "User Profile");
%>

<!DOCTYPE html>
<html lang="en">
    <%@include file="includes/head.jsp" %>
    <body class="no-scroll">
        <%@include file="includes/navbar.jsp" %>
        <div class="container-fluid p-md-5 p-3">
            <div class="card">
                <div class="card-header">
                    <h4>User Profile</h4>
                    <% if (request.getAttribute("message") != null) {%>
                    <p class="text-danger"><%= request.getAttribute("message")%></p>
                    <% }%>
                </div>
                <div class="card-body">
                    <form action="Controller" method="post">
                        <div class="row">
                            <div class="col-lg-3 col-md-4 text-center" style="padding-top: 17px">
                                <img id="imgPhoto" class="profile-img img-fluid" src="<%=user.getPicture() == null ? "assets/images/profile.jpg" : user.getPicture()%>" alt="Profile Picture">
                            </div>
                            <div class="col-lg-9 col-md-8">
                                <div class="row">
                                    <div class="col-md-6">
                                        <label>First Name:</label>
                                        <input type="text" class="form-control" value="<%=user.getFname()%>" readonly>
                                    </div>
                                    <div class="col-md-6">
                                        <label>Last Name:</label>
                                        <input type="text" class="form-control" value="<%=user.getLname()%>" readonly>
                                    </div>
                                </div>
                                <div class="row mt-2">
                                    <div class="col-md-4">
                                        <label>Email:</label>
                                        <input type="text" class="form-control" value="<%=user.getEmail()%>" readonly>
                                    </div>
                                    <div class="col-md-4">
                                        <label>Username:</label>
                                        <input type="text" class="form-control" value="<%=user.getUsername()%>" readonly>
                                    </div>
                                    <div class="col-md-4">
                                        <label>Contact:</label>
                                        <input type="text" class="form-control" value="<%=user.getContact()%>" readonly>
                                    </div>
                                </div>
                                <div class="row mt-2">
                                    <div class="col-md-6">
                                        <label>Gender:</label>
                                        <input type="text" class="form-control" value="<%=user.getGender() != null ? user.getGender() : "Update Profile"%>" readonly>
                                    </div>
                                    <div class="col-md-6">
                                        <label>Birth Date:</label>
                                        <input type="text" class="form-control" value="<%=user.getDob() != null ? user.getDob() : "Update Profile"%>" readonly>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row mt-3">
                            <div class="col-md-6">
                                <label>Address:</label>
                                <input type="text" class="form-control" value="<%=user.getAddress() != null ? user.getAddress() : "Update Profile"%>" readonly>
                            </div>
                            <div class="col-md-6">
                                <label>City:</label>
                                <input type="text" class="form-control" value="<%=user.getCityName().equals("Select City") ? "Update Profile" : user.getCityName()%>" readonly>
                            </div>
                        </div>
                        <div class="row mt-2">
                            <div class="col-md-6">
                                <label>State:</label>
                                <input type="text" class="form-control" value="<%=user.getStateName().equals("Select State") ? "Update Profile" : user.getStateName()%>" readonly>
                            </div>
                            <div class="col-md-6">
                                <label>Pincode:</label>
                                <input type="text" class="form-control" value="<%=user.getPincode() != null ? user.getPincode() : "Update Profile"%>" readonly>
                            </div>
                        </div>
                        <% if (session.getAttribute("id").toString().equals(String.valueOf(user.getId()))) { %>
                        <div class="row mt-4">
                            <div class="col-md-4 mb-2">
                                <a href="Controller?message=redirectToUpdateProfile" class="btn btn-primary btn-block w-100">Update Profile</a>
                            </div>
                            <div class="col-md-4 mb-2">
                                <a href="Controller?message=redirectToChangePassword" class="btn btn-warning btn-block w-100">Change Password</a>
                            </div>
                            <div class="col-md-4">
                                <button type="submit" class="btn btn-danger btn-block w-100"
                                        onClick="return confirm('Are you sure you want to delete this account? All achievements will be deleted.');"
                                        name="submit" value="deleteUser">
                                    Delete Profile
                                </button>
                            </div>
                        </div>
                        <% }%>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
