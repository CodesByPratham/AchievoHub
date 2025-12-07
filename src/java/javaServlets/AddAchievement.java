package javaServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.UUID;
import model.Achievement;
import utilities.DatabaseUtil;

/**
 * @author PRATHAM
 */
public class AddAchievement extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Achievement achievement = new Achievement();
        DatabaseUtil dbUtil = new DatabaseUtil();
        String filePath = null;

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

            // start 
//        System.out.println("File Path: " + filePath);
//        System.out.println("\nParameters:");
//        Enumeration<String> parameterNames = request.getParameterNames();
//        while (parameterNames.hasMoreElements()) {
//            String paramName = parameterNames.nextElement();
//            System.out.println(paramName + ": " + request.getParameter(paramName));
//        }
            //end
            // Define the file path
            filePath = "assets/uploads/achievements/" + uniqueFileName;

            // Write the file to the server
            filePart.write(uploadPath + File.separator + uniqueFileName);
        } else {
            filePath = request.getParameter("previousPicture");
        }

        // Set achievement details
        achievement.setUserId(Integer.parseInt(request.getSession().getAttribute("id").toString()));
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

        // Save achievement to the database
        String result = dbUtil.addAchievement(achievement);
        request.setAttribute("message", result);
    }
}
