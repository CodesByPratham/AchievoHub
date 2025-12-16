package javaServlets;

import java.sql.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.UUID;
import utilities.DatabaseUtil;
import model.User;

/**
 * @author PRATHAM
 */
public class UpdateProfile extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DatabaseUtil dbUtil = new DatabaseUtil();
        User user = new User();
        HttpSession session = request.getSession();
        Date dob = request.getParameter("dob").isEmpty() ? null : Date.valueOf(request.getParameter("dob"));

        String filePath = request.getParameter("previousPicture"); // Default to previous image
        Part filePart = request.getPart("picture");

        if (filePart != null && filePart.getSize() > 0) {
            // Extract original file name
            String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            
            // Generate a unique file name using UUID + timestamp
            String extension = originalFileName.contains(".") ? originalFileName.substring(originalFileName.lastIndexOf(".")) : "";
            String uniqueFileName = UUID.randomUUID().toString() + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()) + extension;

            // Define the upload directory
            String uploadPath = getServletContext().getRealPath("") + File.separator + "assets/uploads/profiles";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Define the new file path
            filePath = "assets/uploads/profiles/" + uniqueFileName;

            // Save the new image
            filePart.write(uploadPath + File.separator + uniqueFileName);

            // Delete the previous image if it exists
            String previousPicture = request.getParameter("previousPicture");
            if (previousPicture != null && !previousPicture.isEmpty()) {
                File previousFile = new File(getServletContext().getRealPath("") + File.separator + previousPicture);
                if (previousFile.exists()) {
                    previousFile.delete();
                }
            }
        }

        // Set user details for update
        user.setUsername((String) session.getAttribute("username"));
        user.setFname(request.getParameter("fname"));
        user.setLname(request.getParameter("lname"));
        user.setContact(request.getParameter("contact"));
        user.setGender(request.getParameter("gender") == null ? null : request.getParameter("gender"));
        user.setState(request.getParameter("state") == null ? null : Integer.parseInt(request.getParameter("state")));
        user.setCity(request.getParameter("city") == null ? null : Integer.parseInt(request.getParameter("city")));
        user.setAddress(request.getParameter("address") == null ? null : request.getParameter("address"));
        user.setPincode(request.getParameter("pincode") == null ? null : request.getParameter("pincode"));
        user.setDob(dob);
        user.setPicture(filePath);

        // Update user profile in database
        String result = dbUtil.updateUser(user);
        request.setAttribute("message", result);
    }
}
