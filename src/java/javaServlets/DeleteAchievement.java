package javaServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import utilities.DatabaseUtil;

/**
 * @author PRATHAM
 */
public class DeleteAchievement extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DatabaseUtil dbUtil = new DatabaseUtil();
        int achievementId = Integer.parseInt(request.getParameter("achievementId"));

        // Fetch the image path before deleting the achievement
        String imagePath = dbUtil.getAchievementImagePath(achievementId);

        // Delete the achievement from the database
        String result = dbUtil.deleteAchievement(achievementId);

        // If deletion was successful and imagePath exists, delete the image file
        if (result.equals("success") && imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File(getServletContext().getRealPath("") + File.separator + imagePath);
            if (imageFile.exists()) {
                imageFile.delete();
            }
        }

        request.setAttribute("message", result);
    }
}
