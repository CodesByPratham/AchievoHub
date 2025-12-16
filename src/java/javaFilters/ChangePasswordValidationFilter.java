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
import java.util.regex.Pattern;
import utilities.DatabaseUtil;

/**
 * @author PRATHAM
 */
public class ChangePasswordValidationFilter implements Filter {

    private final DatabaseUtil dbUtil = new DatabaseUtil(); 

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);
        String submit = req.getParameter("submit");

        if ("changePassword".equals(submit)) {
            int userId = Integer.parseInt(session.getAttribute("id").toString());
            String validationMessage = validatePasswordChange(req, userId);

            if (!validationMessage.isEmpty()) {
                request.setAttribute("message", validationMessage);
                req.getRequestDispatcher("changePassword.jsp").forward(request, response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private String validatePasswordChange(HttpServletRequest request, int userId) {
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Ensure current password is provided
        if (currentPassword == null || currentPassword.trim().isEmpty()) {
            return "Current password is required.";
        }

        // Check if the current password is correct
        if (!dbUtil.isCurrentPasswordCorrect(userId, currentPassword)) {
            return "Current password is incorrect.";
        }

        // Validate new password strength
        if (!isValidPassword(newPassword)) {
            return "Password must be at least 8 characters long, including an uppercase letter, a lowercase letter, a digit, and a special character.";
        }

        // Ensure both new passwords match
        if (!newPassword.equals(confirmPassword)) {
            return "Passwords do not match.";
        }

        return ""; // No validation errors
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$";
        return Pattern.matches(passwordPattern, password);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
