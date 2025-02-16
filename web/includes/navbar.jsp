<%-- 
    Document   : header
    Created on : 24-Nov-2024, 11:43:57 pm
    Author     : PRATHAM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar navbar-expand-sm navbar-toggleable-sm navbar-dark bg-primary">
    <div class="container">
        <a class="navbar-brand" runat="server" href="profile.jsp">AchievoHub</a>
        <button type="button" class="navbar-toggler" data-bs-toggle="collapse" data-bs-target=".navbar-collapse" title="Toggle navigation" aria-controls="navbarSupportedContent"
                aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse d-sm-inline-flex justify-content-between">
            <ul class="navbar-nav flex-grow-1">
                <li class="nav-item"><a class="nav-link text-light" runat="server" href="Controller?message=profile">Profile</a></li>
                <li class="nav-item"><a class="nav-link text-light" runat="server" href="Controller?message=getAllAchievements">Achievements</a></li>
                <li class="nav-item"><a class="nav-link text-light" runat="server" href="Controller?message=logout">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>
