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
public class LoginValidationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String submit = req.getParameter("submit");

        if ("login".equals(submit)) {
            String validationMessage = validateLogin(req);

            if (!validationMessage.isEmpty()) {
                request.setAttribute("message", validationMessage);
                req.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private String validateLogin(HttpServletRequest request) {
        String identifier = request.getParameter("identifier");
        String password = request.getParameter("password");

        // Regex for email and username
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        String usernameRegex = "^[A-Za-z0-9_]{5,20}$";

        // Validate identifier (email or username)
        if (identifier == null || identifier.isEmpty()) {
            return "Email or username is required.";
        }
        if (!Pattern.matches(emailRegex, identifier) && !Pattern.matches(usernameRegex, identifier)) {
            return "Invalid email or username format.";
        }

        // Validate password
        if (password == null || password.length() < 6) {
            return "Password must be at least 6 characters long.";
        }

        return ""; // No errors
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
