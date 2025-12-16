package javaFilters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.time.LocalDate;
import java.util.regex.Pattern;
import model.Achievement;
import utilities.DatabaseUtil;

/**
 * @author PRATHAM
 */
public class UpdateAchievementValidationFilter implements Filter {

    private final DatabaseUtil dbUtil = new DatabaseUtil(); // Database utility to fetch achievements

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String submit = req.getParameter("submit");

        if ("updateAchievement".equals(submit)) {
            String validationMessage = validateAchievement(req);

            if (!validationMessage.isEmpty()) {
                int achievementId = Integer.parseInt(request.getParameter("achievementId"));
                Achievement achievement = dbUtil.getAchievement(achievementId);
                request.setAttribute("achievement", achievement);
                request.setAttribute("message", validationMessage);
                req.getRequestDispatcher("updateAchievement.jsp").forward(request, response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private String validateAchievement(HttpServletRequest request) throws IOException, ServletException {

        String title = request.getParameter("title");
        String organization = request.getParameter("organization");
        String orgUrl = request.getParameter("orgUrl");
        String duration = request.getParameter("duration");
        String dateAchieved = request.getParameter("dateAchieved");
        String category = request.getParameter("category");
        String type = request.getParameter("type");
        String method = request.getParameter("method");
        String description = request.getParameter("description");
        String previousImage = request.getParameter("previousImage");
        Part filePart = request.getPart("image"); // File input field name

        // Title and Organization Name Validation
        if (title == null || !Pattern.matches("^[A-Za-z0-9 ]{1,100}$", title)) {
            return "Invalid Title. Only alphabets, numbers, and spaces are allowed (Max: 100 characters).";
        }
        if (organization == null || !Pattern.matches("^[A-Za-z0-9 ]{1,100}$", organization)) {
            return "Invalid Organization Name. Only alphabets, numbers, and spaces are allowed (Max: 100 characters).";
        }

        // Organization Website Validation
        if (orgUrl != null && !orgUrl.isEmpty() &&
                !Pattern.matches("^(https?:\\/\\/)?(www\\.)?[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}(/.*)?$", orgUrl)) {
            return "Invalid Organization Website URL.";
        }

        // Duration Validation (should not be empty)
        if (duration == null || duration.isEmpty()) {
            return "Duration is required.";
        }

        // Date Achieved Validation (cannot be in the future)
        if (dateAchieved != null && !dateAchieved.isEmpty()) {
            try {
                LocalDate achievedDate = LocalDate.parse(dateAchieved);
                if (achievedDate.isAfter(LocalDate.now())) {
                    return "Date Achieved cannot be in the future.";
                }
            } catch (Exception e) {
                return "Invalid Date Format.";
            }
        } else {
            return "Date Achieved is required.";
        }

        // Category, Type, and Method Validation (must be selected)
        if (category == null || category.isEmpty()) {
            return "Please select a category.";
        }
        if (type == null || type.isEmpty()) {
            return "Please select a type.";
        }
        if (method == null || method.isEmpty()) {
            return "Please select a method.";
        }

        // Description Validation (Min 10 chars, Max 500 chars)
        if (description == null || description.length() < 10 || description.length() > 500) {
            return "Description must be between 10 and 500 characters.";
        }

        // File Validation (Image is required only if no previous image exists)
        if ((filePart == null || filePart.getSize() == 0) && (previousImage == null || previousImage.isEmpty())) {
            return "Achievement proof (image) is required.";
        }

        if (filePart != null && filePart.getSize() > 0) {
            // Validate file type (only JPG, JPEG, PNG, GIF allowed)
            String fileName = filePart.getSubmittedFileName();
            if (fileName != null) {
                String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                if (!fileExtension.matches("jpg|jpeg|png|gif")) {
                    return "Invalid file format. Only JPG, JPEG, PNG, and GIF are allowed.";
                }
            }

            // Validate file size (max 2MB)
            if (filePart.getSize() > 2 * 1024 * 1024) {
                return "File size must be less than 2MB.";
            }
        }

        return ""; // No validation errors
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}