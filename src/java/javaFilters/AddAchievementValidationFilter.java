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

/**
 * @author PRATHAM
 */
public class AddAchievementValidationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String submit = req.getParameter("submit");

        if ("addAchievement".equals(submit)) {
            String validationMessage = validateAchievement(req);

            if (!validationMessage.isEmpty()) {
                request.setAttribute("message", validationMessage);
                System.out.println(validationMessage);
                req.getRequestDispatcher("addAchievement.jsp").forward(request, response);
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
        Part filePart = request.getPart("image"); // File input field name

        // Title and Organization Name Validation
        if (title == null || !Pattern.matches("^[A-Za-z0-9 ]{1,100}$", title)) {
            return "Invalid Title.";
        }
        if (organization == null || !Pattern.matches("^[A-Za-z0-9 ]{1,100}$", organization)) {
            return "Invalid Organization Name.";
        }

        // Organization Website Validation
        if (orgUrl == null || !Pattern.matches("^(https?:\\/\\/)?(www\\.)?[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}(/.*)?$", orgUrl)) {
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

        // File Validation (Image is required)
        if (filePart == null || filePart.getSize() == 0) {
            return "Achievement proof (image) is required.";
        }

        // Validate file type (only JPG, PNG, GIF allowed)
        String fileName = filePart.getSubmittedFileName();
        if (fileName != null) {
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            if (!fileExtension.matches("jpg|jpeg|png|gif")) {
                return "Invalid file format. Only JPG, PNG, and GIF allowed.";
            }
        }

        // Validate file size (max 2MB)
        if (filePart.getSize() > 2 * 1024 * 1024) {
            return "File size must be less than 2MB.";
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
