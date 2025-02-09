package javaFilters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.regex.Pattern;
import utilities.DatabaseUtil;
import operations.User;

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

    private String validateUpdateProfile(HttpServletRequest request) {
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String email = request.getParameter("email");
        String contact = request.getParameter("contact");
        String gender = request.getParameter("gender");
        String dob = request.getParameter("dob");
        String state = request.getParameter("state");
        String city = request.getParameter("city");
        String address = request.getParameter("address");
        String pincode = request.getParameter("pincode");

        // First Name and Last Name Validation
        if (fname == null || !Pattern.matches("^[A-Za-z]{1,50}$", fname)) {
            return "Invalid First Name.";
        }
        if (lname == null || !Pattern.matches("^[A-Za-z]{1,50}$", lname)) {
            return "Invalid Last Name.";
        }

        // Email Validation
        if (email == null || !Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email)) {
            return "Invalid Email Format.";
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

        // Address Validation (Optional, max 255 characters)
        if (address.length() > 255) {
            return "Address too long (max 255 characters).";
        }
        
        // Address Validation (Optional, max 255 characters)
        if (address == null) {
            return "Address cannot be empty.";
        }

        // State Validation
        if (state == null || state.isEmpty()) {
            return "Select State.";
        }
        
        // City Validation
        if (city == null || city.isEmpty()) {
            return "Select State.";
        }

        // Pincode Validation (Optional but must be 6 digits)
        if (pincode != null && !pincode.isEmpty() && !Pattern.matches("^\\d{6}$", pincode)) {
            return "Invalid Pincode. It should be exactly 6 digits.";
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
