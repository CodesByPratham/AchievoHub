package javaFilters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author PRATHAM
 */
public class ResetPasswordValidationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String submit = req.getParameter("submit");

        if ("resetPassword".equals(submit)) {
            String validationMessage = validateResetPassword(req);

            if (!validationMessage.isEmpty()) {
                request.setAttribute("message", validationMessage);
                req.getRequestDispatcher("resetPassword.jsp?token=" + request.getParameter("token")).forward(request, response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private String validateResetPassword(HttpServletRequest request) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Ensure the reset token is present
        if (token == null || token.trim().isEmpty()) {
            return "Invalid or expired reset link.";
        }

        // Validate password strength
        if (!isValidPassword(password)) {
            return "Password must be at least 8 characters long, including an uppercase letter, a lowercase letter, a digit, and a special character.";
        }

        // Ensure both passwords match
        if (!password.equals(confirmPassword)) {
            return "Passwords do not match." + password + " " + confirmPassword;
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