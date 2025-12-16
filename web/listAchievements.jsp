<%-- 
    Document   : index
    Created on : 14-Feb-2025, 4:37:43  pm
    Author     : PRATHAM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="errorPage.jsp"%>
<%@page import="java.util.List, model.Achievement, model.User"%>

<%
    if (session == null || session.getAttribute("username") == null) {
        request.setAttribute("message", "urlMessing");
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
        return;
    }

    List<Achievement> achievements = (List<Achievement>) request.getAttribute("achievements");

    if (achievements == null) {
        response.sendRedirect("Controller?message=profile");
    }

    request.setAttribute("pageTitle", "Achievements");
%>

<!DOCTYPE html>
<html lang="en">
    <%@include file="includes/head.jsp" %>
    <body>
        <%@include file="includes/navbar.jsp" %>

        <div class="container-fluid p-md-5 p-3">
            <div class="card">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h4 class="mt-2">Achievements</h4>
                    <a href="Controller?message=redirectToAddAchievements" class="btn btn-primary btn-sm">
                        Add Achievement
                    </a>
                </div>

                <% if (request.getAttribute("message") != null) {
                        String message = (String) request.getAttribute("message");
                        if (message.equals("fail")) {%>
                <div class="alert alert-danger text-center m-3">
                    Failed Adding Achievement: <%= message%>
                </div>
                <% }
                    } %>

                <div class="card-body pb-0">
                    <% if (achievements.isEmpty()) { %>
                    <p class="text-center">No achievements to display.</p>
                    <% } else { %>
                    <div class="row">
                        <% for (Achievement achievement : achievements) {
                                User user = achievement.getUser();%>
                        <div class="col-lg-4 col-md-6 col-12 d-flex align-items-stretch">
                            <div class="card mb-4 shadow-sm h-auto" style="width: inherit">
                                <div class="card-header d-flex align-items-center">
                                    <img src="<%=user.getPicture() == null ? "assets/images/profile.jpg" : user.getPicture()%>" alt="Profile Picture" class="rounded-circle me-2" width="50" height="50">
                                    <h6 class="ml-4"><%= user.getFname() + " " + user.getLname()%></h6>
                                </div>
                                <div class="card-body text-center">
                                    <img src="<%=achievement.getImage()%>" alt="Achievement Image" class="achievement-preview">
                                    <h5 class="card-title"><%=achievement.getTitle()%></h5>
                                    <p class="card-text">
                                        <%= achievement.getDescription()%>
                                    </p>
                                    <a href="Controller?message=redirectToAchievement&id=<%=achievement.getAchievementId()%>" class="btn btn-primary btn-sm">
                                        View Details
                                    </a>
                                </div>
                            </div>
                        </div>
                        <% } %>
                    </div>
                    <% }%>
                </div>
            </div>
        </div>
    </body>
</html>
