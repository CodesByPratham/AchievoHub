package javaServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import utilities.DatabaseUtil;

/**
 * @author PRATHAM
 */
public class DeleteUser extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DatabaseUtil dbUtil = new DatabaseUtil();
        int userId = Integer.parseInt(request.getSession().getAttribute("id").toString());

        // Fetch the profile picture path before deleting the user
        String profilePicture = dbUtil.getUserProfileImage(userId);

        // Fetch and delete all achievement images
        List<String> achievementImages = dbUtil.getUserAchievementImages(userId);
        for (String imagePath : achievementImages) {
            deleteFileFromServer(imagePath);
        }

        // Delete the user (this also deletes achievements in DB)
        String result = dbUtil.deleteUser(userId);

        // Delete profile picture if exists
        if (profilePicture != null && !profilePicture.isEmpty()) {
            deleteFileFromServer(profilePicture);
        }

        // Invalidate session and return success message
        request.getSession().invalidate();
        request.setAttribute("message", result);
    }

    private void deleteFileFromServer(String filePath) {
        File file = new File(getServletContext().getRealPath("") + File.separator + filePath);
        if (file.exists()) {
            file.delete();
        }
    }
}
