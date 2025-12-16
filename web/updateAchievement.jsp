<%-- 
    Document   : updateAchievement
    Created on : 11-Jan-2025, 6:47:41 PM
    Author     : PRATHAM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="errorPage.jsp"%>
<%@page import="model.Achievement, model.User" %>
<%@page session="true" %>
<%
    Achievement achievement = (Achievement) request.getAttribute("achievement");

    if (session == null || session.getAttribute("username") == null || achievement == null) {
        request.setAttribute("message", "urlMessing");
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
        return;
    }

    request.setAttribute("pageTitle", "Update Achievement");
%>
<!DOCTYPE html>
<html lang="en">
    <%@include file="includes/head.jsp" %>
    <body>
        <%@include file="includes/navbar.jsp" %>
        <div class="container-fluid p-md-5 p-3">
            <div class="card">
                <div class="card-header">
                    <h4>Update Your Achievement</h4>
                    <%if (request.getAttribute("message") != null) {
                            String message = (String) request.getAttribute("message");
                            out.println(message);
                        }
                    %>
                </div>
                <div class="card-body pb-0">
                    <form action="Controller" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="previousImage" id="previousImage" value="<%=achievement.getImage()%>" />
                        <input type="hidden" name="achievementId" value="<%=achievement.getAchievementId()%>">
                        <div class="row justify-content-center">
                            <label for="input-file" id="drop-area">
                                <input type="file" name="image" accept="image/*" id="input-file" hidden />
                                <div id="image-view" class="image-view" >
                                    <img src="assets/images/upload-icon.png"  />
                                    <p>Drag and drop or click here<br />to upload proof of achievement</p>
                                    <span>Only images supported</span>
                                </div>
                            </label>
                        </div>
                        <div class="row">
                            <div class="form-group col-md-4">
                                <label>Title:</label>
                                <input type="text" name="title" class="form-control" value="<%=achievement.getTitle()%>" placeholder="Enter Achievement Title" required />
                            </div>
                            <div class="form-group col-md-4">
                                <label>Organization's Name:</label>
                                <input type="text" name="organization" class="form-control" value="<%=achievement.getOrganization()%>" placeholder="Enter Organization's Name" required />
                            </div>
                            <div class="form-group col-md-4">
                                <label>Organization's Website:</label>
                                <input type="url" name="orgUrl" class="form-control" value="<%=achievement.getWebsite()%>" placeholder="Enter Organization's Website" required />
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-md-6">
                                <label>Duration:</label>
                                <input type="" id="litepicker" name="duration" placeholder="Enter Duration" value="<%=achievement.getDuration()%>" class="form-control" required />
                            </div> 
                            <div class="form-group col-md-6">
                                <label>Date Achieved:</label>
                                <input type="date" name="dateAchieved" value="<%=achievement.getDateAchieved()%>" class="form-control" required />
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-md-4">
                                <label>Category:</label>
                                <select name="category" class="form-control form-select" required >
                                    <option value="<%=achievement.getCategory()%>" selected><%=achievement.getCategory()%></option>
                                    <optgroup label="General Categories">
                                        <option value="academic">Academic</option>
                                        <option value="professional">Professional</option>
                                        <option value="personal_development">Personal Development</option>
                                        <option value="sports_fitness">Sports & Fitness</option>
                                        <option value="creative">Creative</option>
                                        <option value="social_impact">Social Impact</option>
                                        <option value="leadership">Leadership</option>
                                        <option value="technology_innovation">Technology & Innovation</option>
                                        <option value="entrepreneurship">Entrepreneurship</option>
                                        <option value="travel_exploration">Travel & Exploration</option>
                                    </optgroup>
                                    <optgroup label="Specific Categories">
                                        <option value="certifications">Certifications</option>
                                        <option value="competitions">Competitions</option>
                                        <option value="workshops">Workshops</option>
                                        <option value="publications">Publications</option>
                                        <option value="awards">Awards</option>
                                        <option value="hackathons">Hackathons</option>
                                        <option value="community_service">Community Service</option>
                                        <option value="recognition">Recognition</option>
                                        <option value="volunteer_work">Volunteer Work</option>
                                    </optgroup>
                                    <optgroup label="Professional and Industry-Specific Categories">
                                        <option value="leadership">Leadership</option>
                                        <option value="project_management">Project Management</option>
                                        <option value="customer_success">Customer Success</option>
                                        <option value="sales_marketing">Sales & Marketing</option>
                                        <option value="tech_development">Tech Development</option>
                                    </optgroup>
                                    <optgroup label="Miscellaneous Categories">
                                        <option value="philanthropy">Philanthropy</option>
                                        <option value="entrepreneurial_ventures">Entrepreneurial Ventures</option>
                                        <option value="awards_honors">Awards & Honors</option>
                                    </optgroup>
                                    <optgroup label="Other">
                                        <option value="other">Other</option>
                                    </optgroup>
                                </select>
                            </div>
                            <div class="form-group col-md-4">
                                <label>Type:</label>
                                <select name="type" class="form-control form-select" required >
                                    <option value="<%=achievement.getType()%>" selected><%=achievement.getType()%></option>
                                    <optgroup label="General Types">
                                        <option value="certificate">Certificate</option>
                                        <option value="badge">Badge</option>
                                        <option value="award">Award</option>
                                        <option value="honor">Honor</option>
                                        <option value="medal">Medal</option>
                                        <option value="trophy">Trophy</option>
                                        <option value="scholarship">Scholarship</option>
                                    </optgroup>
                                    <optgroup label="Professional Types">
                                        <option value="promotion">Promotion</option>
                                        <option value="patents">Patents</option>
                                        <option value="publications">Publications</option>
                                        <option value="project_completion">Project Completion</option>
                                    </optgroup>
                                    <optgroup label="Academic Types">
                                        <option value="degree">Degree</option>
                                        <option value="rank">Rank</option>
                                        <option value="distinction">Distinction</option>
                                        <option value="research">Research</option>
                                    </optgroup>
                                    <optgroup label="Competition-Based Types">
                                        <option value="winner">Winner</option>
                                        <option value="runner_up">Runner-up</option>
                                        <option value="participation">Participation</option>
                                        <option value="hackathon">Hackathon</option>
                                    </optgroup>
                                    <optgroup label="Community & Social Types">
                                        <option value="volunteer_recognition">Volunteer Recognition</option>
                                        <option value="community_service_award">Community Service Award</option>
                                        <option value="leadership_recognition">Leadership Recognition</option>
                                    </optgroup>
                                    <optgroup label="Creativity & Arts Types">
                                        <option value="art_exhibit">Art Exhibit</option>
                                        <option value="performance">Performance</option>
                                        <option value="design">Design</option>
                                    </optgroup>
                                    <optgroup label="Miscellaneous Types">
                                        <option value="membership">Membership</option>
                                        <option value="certification_of_appreciation">Certification of Appreciation</option>
                                        <option value="innovation">Innovation</option>
                                        <option value="milestone_achievement">Milestone Achievement</option>
                                        <option value="recognition_letter">Recognition Letter</option>
                                    </optgroup>
                                    <optgroup label="Other">
                                        <option value="other">Other</option>
                                    </optgroup>
                                </select>
                            </div>
                            <div class="form-group col-md-4">
                                <label>Method:</label>
                                <select name="method" class="form-control form-select" required >
                                    <option value="<%=achievement.getMethod()%>" selected><%=achievement.getMethod()%></option>
                                    <optgroup label="General Methods">
                                        <option value="online_course">Online Course</option>
                                        <option value="exam">Exam</option>
                                        <option value="workshop">Workshop</option>
                                        <option value="seminar">Seminar</option>
                                        <option value="conference">Conference</option>
                                        <option value="bootcamp">Bootcamp</option>
                                        <option value="self_study">Self-Study</option>
                                        <option value="internship">Internship</option>
                                        <option value="mentorship">Mentorship</option>
                                        <option value="project_work">Project Work</option>
                                        <option value="training_program">Training Program</option>
                                    </optgroup>
                                    <optgroup label="Competition-Based Methods">
                                        <option value="hackathon">Hackathon</option>
                                        <option value="contest">Contest</option>
                                        <option value="quiz_trivia">Quiz/Trivia</option>
                                    </optgroup>
                                    <optgroup label="Professional Development">
                                        <option value="job">Job</option>
                                        <option value="certification_program">Certification Program</option>
                                        <option value="networking_event">Networking Event</option>
                                        <option value="company_training">Company Training</option>
                                    </optgroup>
                                    <optgroup label="Creative and Social Methods">
                                        <option value="creative_collaboration">Creative Collaboration</option>
                                        <option value="volunteer_work">Volunteer Work</option>
                                        <option value="social_campaigns">Social Campaigns</option>
                                        <option value="community_program">Community Program</option>
                                    </optgroup>
                                    <optgroup label="Academic Methods">
                                        <option value="research">Research</option>
                                        <option value="thesis_dissertation">Thesis/Dissertation</option>
                                        <option value="classroom_learning">Classroom Learning</option>
                                        <option value="study_group">Study Group</option>
                                    </optgroup>
                                    <optgroup label="Miscellaneous Methods">
                                        <option value="event_participation">Event Participation</option>
                                        <option value="open_source_contribution">Open Source Contribution</option>
                                        <option value="reading_books">Reading/Books</option>
                                        <option value="exploration">Exploration</option>
                                        <option value="experiential_learning">Experiential Learning</option>
                                    </optgroup>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Description:</label>
                            <textarea name="description" class="form-control" rows="4" placeholder="Enter Description" required ><%=achievement.getDescription()%></textarea>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary btn-block" name="submit" value="updateAchievement">Update Achievement</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script src="assets/js/updateAchievement.js"></script>
    </body>
</html>