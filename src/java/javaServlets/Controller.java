package javaServlets;

import utilities.DatabaseUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Achievement;
import model.User;

/**
 * @author PRATHAM
 */
public class Controller extends HttpServlet {

    static final DatabaseUtil dbUtil = new DatabaseUtil();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Gets the "message" parameter from url(URL Rewriting).
        String message = request.getParameter("message");

        // Gets the username and id from session.
        String username = (String) request.getSession().getAttribute("username");
        Object objId = request.getSession().getAttribute("id");
        int id = objId == null ? 0 : Integer.parseInt((String) objId);

        // Checks if someone is messing with url.
        if (username == null || username.isEmpty() || message == null || message.isEmpty()) {
            request.setAttribute("message", "urlMessing");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        // Gets the user profile to display it on profile.jsp.
        if (message.equals("profile")) {
            // Checks if user is requesting their profile or someone else's. 
            int userId = request.getParameter("id") == null ? id : Integer.parseInt(request.getParameter("id"));
            User user = dbUtil.getProfile(userId);
            request.setAttribute("user", user);
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }

        // Gets all the achievements.
        if (message.equals("getAllAchievements")) {
            List<Achievement> achievements = dbUtil.getAllAchievements();
            request.setAttribute("achievements", achievements);
            RequestDispatcher profileDispatcher = request.getRequestDispatcher("listAchievements.jsp");
            profileDispatcher.forward(request, response);
        }

        // Redirects user to specific achievement. 
        if (message.equals("redirectToAchievement")) {
            Achievement achievement = dbUtil.getAchievement(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("achievement", achievement);
            RequestDispatcher profileDispatcher = request.getRequestDispatcher("achievement.jsp");
            profileDispatcher.forward(request, response);
        }

        // Redirects user to add achievement page. 
        if (message.equals("redirectToAddAchievements")) {
            RequestDispatcher profileDispatcher = request.getRequestDispatcher("addAchievement.jsp");
            profileDispatcher.forward(request, response);
        }

        // Redirects the user to update their profile.
        if (message.equals("redirectToUpdateProfile")) {
            User user = dbUtil.getProfile(id);
            request.setAttribute("user", user);
            request.getRequestDispatcher("updateProfile.jsp").forward(request, response);
        }

        // Redirects the user to change their password.
        if (message.equals("redirectToChangePassword")) {
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
        }

        // Redirects the user to update their Achievement.
        if (message.equals("redirectToUpdateAchievement")) {
            Achievement achievement = dbUtil.getAchievement(Integer.parseInt(request.getParameter("achievementId")));
            request.setAttribute("achievement", achievement);
            request.getRequestDispatcher("updateAchievement.jsp").forward(request, response);
        }

        // Redirects user to login page after logout.
        if (message.equals("logout")) {
            request.getRequestDispatcher("Logout").include(request, response);
            RequestDispatcher profileDispatcher = request.getRequestDispatcher("index.jsp");
            profileDispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Gets the 'submit' parameter of form submission.
        String submit = request.getParameter("submit");

        // Gets the user's id from session.
        Object objId = request.getSession().getAttribute("id");
        int id = objId == null ? 0 : Integer.parseInt(objId.toString());

        // Registers the user.
        if (submit.equals("register")) {
            request.getRequestDispatcher("UserRegisteration").include(request, response);
            request.getRequestDispatcher("index.jsp").forward(request, response);

        }

        // Log in the user.
        if (submit.equals("login")) {
            request.getRequestDispatcher("UserLogin").include(request, response);
            String message = (String) request.getAttribute("message");
            if ("success".equals(message)) {
                response.sendRedirect("Controller?message=profile");
            } else {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        }

        // Forget password
        if (submit.equals("forgotPassword")) {
            request.getRequestDispatcher("ForgotPassword").include(request, response);
            request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
        }

        // Resets the password
        if (submit.equals("resetPassword")) {
            request.getRequestDispatcher("ResetPassword").include(request, response);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }

        // Process password change request
        if (submit.equals("changePassword")) {
            request.getRequestDispatcher("ChangePassword").include(request, response);
            String message = (String) request.getAttribute("message");
            if ("success".equals(message)) {
                response.sendRedirect("Controller?message=profile");
            } else {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        }

        // Updates the user data.
        if (submit.equals("updateProfile")) {
            request.getRequestDispatcher("UpdateProfile").include(request, response);
            if (request.getAttribute("message").equals("success")) {
                response.sendRedirect("Controller?message=profile");
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("updateProfile.jsp");
                dispatcher.forward(request, response);
            }
        }

        // Adds the user achievement.
        if (submit.equals("addAchievement")) {
            request.getRequestDispatcher("AddAchievement").include(request, response);
            if (request.getAttribute("message").equals("success")) {
                response.sendRedirect("Controller?message=getAllAchievements");
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("addAchievement.jsp");
                dispatcher.forward(request, response);
            }
        }

        // Updates the user Achievement.
        if (submit.equals("updateAchievement")) {
            request.getRequestDispatcher("UpdateAchievement").include(request, response);
            if (request.getAttribute("message").equals("success")) {
                response.sendRedirect("Controller?message=getAllAchievements");
            }
        }

        // Deletes the user Achievement.
        if (submit.equals("deleteAchievement")) {
            request.getRequestDispatcher("DeleteAchievement").include(request, response);
            if (request.getAttribute("message").equals("success")) {
                response.sendRedirect("Controller?message=getAllAchievements");
            }
        }

        // Deletes the user with along with all their achievements.
        if (submit.equals("deleteUser")) {
            request.getRequestDispatcher("DeleteUser").include(request, response);
            if (request.getAttribute("message").equals("success")) {
                response.sendRedirect("index.jsp");
            }
        }
    }
}
