<%-- 
    Document   : updateProfile
    Created on : 25-Nov-2024, 12:48:46 pm
    Author     : PRATHAM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="errorPage.jsp"%>
<%@page import="model.User"%>
<%
    User user = (User) request.getAttribute("user");

    if (session == null || session.getAttribute("username") == null || user == null) {
        request.setAttribute("message", "urlMessing");
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
        return;
    }

    request.setAttribute("pageTitle", "Update Profile");
    String gender = user.getGender();
%>
<!DOCTYPE html>
<html lang="en">
    <%@include file="includes/head.jsp" %>
    <body class="no-scroll">
        <%@include file="includes/navbar.jsp" %>
        <div class="container-fluid p-md-5 p-3">
            <div class="card">
                <div class="card-header">
                    <h4>Profile Update</h4>
                    <%if (request.getAttribute("errorMessage") != null) {%>
                    <p><%= request.getAttribute("errorMessage")%></p>
                    <%}%>
                </div>
                <div class="card-body pb-0">
                    <form action="Controller" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="id" value="<%=session.getAttribute("id")%>" readonly="true" />
                        <input type="hidden" name="previousPicture" value="<%=user.getPicture()%>" readonly="true" />
                        <div class="row">
                            <div class="form-group col-md-3 flex">
                                <label>Profile Pic:</label><br />
                                <div class="row justify-content-center">
                                    <img id="imgPhoto" class="img-fluid profile-img" src="<%=user.getPicture() == null ? "assets/images/profile.jpg" : user.getPicture()%>" />
                                </div>
                            </div>
                            <div class="col-md-9">
                                <div class="row">
                                    <div class="form-group col-md-6">
                                        <label>First Name:</label>
                                        <input type="text" name="fname" value="<%=user.getFname()%>" placeholder="Enter First Name" class="form-control" required />
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label>Last Name:</label>
                                        <input type="text" name="lname" value="<%=user.getLname()%>" placeholder="Enter Last Name" class="form-control" required />
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-6">
                                        <label>Photograph:</label>
                                        <input type="file" id="formFile" name="picture" class="form-control-file" accept="image/*" />
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label>Contact:</label>
                                        <input type="tel" name="contact" value="<%=user.getContact()%>" placeholder="Enter Contact Number" class="form-control" required />
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-6">
                                        <label>Gender:</label><br />
                                        <div class="form-control">
                                            <input type="radio" name="gender" <%=gender == null ? "" : gender.equals("male") ? "checked" : ""%> value="male" required /> Male&nbsp;&nbsp;&nbsp;&nbsp;
                                            <input type="radio" name="gender" <%=gender == null ? "" : gender.equals("female") ? "checked" : ""%> value="female"required /> Female 
                                        </div>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label>Birth Date:</label>
                                        <input type="date" value="<%=user.getDob() != null ? user.getDob() : ""%>" name="dob" class="form-control" required />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-md-6">
                                <label>State:</label>
                                <select id="state" name="state" class="form-control form-select" required>
                                    <option value="<%=user.getState()%>"><%=user.getStateName()%></option>
                                </select>
                            </div>
                            <div class="form-group col-md-6">
                                <label>City:</label>
                                <select id="city" name="city" class="form-control form-select" required>
                                    <option value="<%=user.getCity()%>"><%=user.getCityName()%></option>
                                </select>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-md-6">
                                <label>Address:</label>
                                <input type="text" name="address" value="<%=user.getAddress() != null ? user.getAddress() : ""%>" placeholder="Enter Address" class="form-control" required />
                            </div>
                            <div class="form-group col-md-6">
                                <label>Pincode:</label>
                                <input type="text" name="pincode" value="<%=user.getPincode() != null ? user.getPincode() : ""%>" placeholder="Enter Pincode" class="form-control" required />
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-md-12">
                                <button type="submit" class="btn btn-primary btn-block" name="submit" value="updateProfile">Update Profile</button>
                            </div>
                        </div>
                    </form> 
                </div>
            </div>
        </div>
        <script src="assets/js/updateProfile.js"></script>
    </body>
</html>