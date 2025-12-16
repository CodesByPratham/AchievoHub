package javaServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.UUID;
import java.sql.Date;
import model.Achievement;
import utilities.DatabaseUtil;

/**
 * @author PRATHAM
 */
public class UpdateAchievement extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Achievement achievement = new Achievement();
        DatabaseUtil dbUtil = new DatabaseUtil();

        String filePath = request.getParameter("previousImage"); // Default to previous image
        Part filePart = request.getPart("image");

        if (filePart != null && filePart.getSize() > 0) {
            // Extract original file name
            String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            // Generate a unique file name using UUID + timestamp
            String extension = originalFileName.contains(".") ? originalFileName.substring(originalFileName.lastIndexOf(".")) : "";
            String uniqueFileName = UUID.randomUUID().toString() + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()) + extension;

            // Define the upload directory
            String uploadPath = getServletContext().getRealPath("") + File.separator + "assets/uploads/achievements";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Define the new file path
            filePath = "assets/uploads/achievements/" + uniqueFileName;

            // Save the new image
            filePart.write(uploadPath + File.separator + uniqueFileName);

            // Delete the previous image if it exists
            String previousImage = request.getParameter("previousImage");
            if (previousImage != null && !previousImage.isEmpty()) {
                File previousFile = new File(getServletContext().getRealPath("") + File.separator + previousImage);
                if (previousFile.exists()) {
                    previousFile.delete();
                }
            }
        }

        // Set achievement details
        achievement.setAchievementId(Integer.valueOf(request.getParameter("achievementId")));
        achievement.setTitle(request.getParameter("title"));
        achievement.setOrganization(request.getParameter("organization"));
        achievement.setWebsite(request.getParameter("orgUrl"));
        achievement.setDuration(request.getParameter("duration"));
        achievement.setDateAchieved(Date.valueOf(request.getParameter("dateAchieved")));
        achievement.setCategory(request.getParameter("category"));
        achievement.setType(request.getParameter("type"));
        achievement.setMethod(request.getParameter("method"));
        achievement.setDescription(request.getParameter("description"));
        achievement.setImage(filePath);

        // Update achievement in the database
        String result = dbUtil.updateAchievement(achievement);
        request.setAttribute("message", result);
    }
}
