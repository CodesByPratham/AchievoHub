package javaFilters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.time.LocalDate;
import java.util.regex.Pattern;
import utilities.DatabaseUtil;
import model.User;

/**
 * @author PRATHAM
 */
public class UpdateProfileValidationFilter implements Filter {

    private final DatabaseUtil dbUtil = new DatabaseUtil(); // Utility to fetch user data

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);
        String submit = req.getParameter("submit");

        if ("updateProfile".equals(submit)) {

            int userId = Integer.parseInt(session.getAttribute("id").toString());
            String validationMessage = validateUpdateProfile(req);

            if (!validationMessage.isEmpty()) {
                User user = dbUtil.getProfile(userId);
                request.setAttribute("user", user);
                request.setAttribute("errorMessage", validationMessage);
                req.getRequestDispatcher("updateProfile.jsp").forward(request, response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private String validateUpdateProfile(HttpServletRequest request) throws IOException, ServletException {
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String contact = request.getParameter("contact");
        String gender = request.getParameter("gender");
        String dob = request.getParameter("dob");
        String state = request.getParameter("state");
        String city = request.getParameter("city");
        String address = request.getParameter("address");
        String pincode = request.getParameter("pincode");
        Part filePart = request.getPart("picture"); // File input field

        // First Name and Last Name Validation
        if (fname == null || !Pattern.matches("^[A-Za-z]{1,50}$", fname)) {
            return "Invalid First Name.";
        }
        if (lname == null || !Pattern.matches("^[A-Za-z]{1,50}$", lname)) {
            return "Invalid Last Name.";
        }

        // Contact Validation
        if (contact == null || !Pattern.matches("^\\d{10}$", contact)) {
            return "Invalid Contact Number.";
        }

        // Gender Validation (Optional but must be "male" or "female" if provided)
        if (gender != null && !gender.isEmpty() && !gender.equals("male") && !gender.equals("female")) {
            return "Invalid Gender Selection.";
        }

        // Date of Birth Validation (Optional but must be a valid past date)
        if (dob != null && !dob.isEmpty()) {
            try {
                LocalDate birthDate = LocalDate.parse(dob);
                if (birthDate.isAfter(LocalDate.now())) {
                    return "Date of Birth cannot be in the future.";
                }
            } catch (Exception e) {
                return "Invalid Date Format.";
            }
        }

        // Address Validation
        if (address == null || address.isEmpty()) {
            return "Address cannot be empty.";
        }
        if (address.length() > 255) {
            return "Address too long (max 255 characters).";
        }

        // State Validation
        if (state == null || state.isEmpty()) {
            return "Select State.";
        }

        // City Validation
        if (city == null || city.isEmpty()) {
            return "Select City.";
        }

        // Pincode Validation (Optional but must be 6 digits)
        if (pincode != null && !pincode.isEmpty() && !Pattern.matches("^\\d{6}$", pincode)) {
            return "Invalid Pincode. It should be exactly 6 digits.";
        }

        // File Validation (If a file is uploaded)
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = filePart.getSubmittedFileName();
            long fileSize = filePart.getSize();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

            // Allowed extensions
            String[] allowedExtensions = {"jpg", "jpeg", "png", "gif"};
            boolean isValidExtension = false;
            for (String ext : allowedExtensions) {
                if (ext.equals(fileExtension)) {
                    isValidExtension = true;
                    break;
                }
            }

            if (!isValidExtension) {
                return "Invalid file type. Only JPG, JPEG, PNG, and GIF are allowed.";
            }

            // File size validation (Max: 2MB)
            long maxSize = 2 * 1024 * 1024; // 2MB
            if (fileSize > maxSize) {
                return "File size too large. Maximum allowed size is 2MB.";
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
