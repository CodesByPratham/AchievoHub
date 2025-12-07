<%-- 
    Document   : achievement
    Created on : 25-Dec-2024, 4:11:54 pm
    Author     : PRATHAM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="errorPage.jsp"%>
<%@page import="model.Achievement, model.User"%>
<%
    Achievement achievement = (Achievement) request.getAttribute("achievement");

    if (session == null || session.getAttribute("username") == null || achievement == null || achievement.getUser() == null) {
        request.setAttribute("message", "urlMessing");
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
        return;
    }

    User user = achievement.getUser();
    request.setAttribute("pageTitle", (user.getFname() + " " + user.getLname()));
%>
<!DOCTYPE html>
<html lang="en">
    <%@include file="includes/head.jsp" %>
    <body>
        <%@include file="includes/navbar.jsp" %>
        <div class="container-fluid p-md-5 p-3">
            <div class="card">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <div class="d-flex">
                        <img src="<%=user.getPicture() == null ? "assets/images/profile.jpg" : user.getPicture()%>" alt="Profile Picture" class="rounded-circle pp" width="50" height="50">&nbsp;&nbsp;
                        <h4 class="mt-2"><%=(user.getFname() + " " + user.getLname())%></h4>
                    </div>
                    <a href="Controller?message=profile&id=<%=user.getId()%>" class="btn btn-primary btn-sm">
                        View Profile
                    </a>                       
                    <%if (request.getAttribute("message") != null) {
                            String message = (String) request.getAttribute("message");
                            if (message.equals("fail")) {
                                out.println("<p style='color: red;'>Failed Adding Achievement: " + message + "</p>");
                            }
                        }
                    %>
                </div>
                <div class="card-body pb-0">
                    <form action="Controller" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="achievementId" value="<%=achievement.getAchievementId()%>" readonly />
                        <div class="row justify-content-center">
                            <img id="achivement-view" src="<%=achievement.getImage()%>" />
                        </div>
                        <div class="row">
                            <div class="form-group col-md-4">
                                <label>Title:</label>
                                <input type="text" class="form-control" value="<%=achievement.getTitle()%>" readonly />
                            </div>
                            <div class="form-group col-md-4">
                                <label>Organization's Name:</label>
                                <input type="text" class="form-control" value="<%=achievement.getOrganization()%>" readonly />
                            </div>
                            <div class="form-group col-md-4">
                                <label>Organization's Website:</label>
                                <input type="text" class="form-control" value="<%=achievement.getWebsite()%>" readonly />
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-md-6">
                                <label>Duration:</label>
                                <input type="text" class="form-control" value="<%=achievement.getDuration()%>" readonly />
                            </div> 
                            <div class="form-group col-md-6">
                                <label>Date Achieved:</label>
                                <input type="text" class="form-control" value="<%=achievement.getDateAchieved()%>" readonly />
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-md-4">
                                <label>Category:</label>
                                <input type="text" class="form-control" value="<%=achievement.getCategory()%>" readonly />
                            </div>
                            <div class="form-group col-md-4">
                                <label>Type:</label>
                                <input type="text" class="form-control" value="<%=achievement.getType()%>" readonly />
                            </div>
                            <div class="form-group col-md-4">
                                <label>Method:</label>
                                <input type="text" class="form-control" value="<%=achievement.getMethod()%>" readonly />
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Description:</label>
                            <textarea class="form-control" rows="4" readonly ><%=achievement.getDescription()%></textarea>
                        </div>
                        <%if (session.getAttribute("id").toString().equals(String.valueOf(user.getId()))) {%>
                        <div class="row">
                            <div class="form-group col-md-6">
                                <a class="btn btn-primary btn-block" href="Controller?message=redirectToUpdateAchievement&achievementId=<%=achievement.getAchievementId()%>">Edit Achievement</a>
                            </div>
                            <div class="form-group col-md-6">
                                <button type="submit" class="btn btn-danger btn-block" name="submit" onClick="return confirm('Are you sure you want to delete this Achievement?');" value="deleteAchievement">Delete Achievement</button>
                            </div>
                        </div>
                        <%}%>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>