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
import utilities.DatabaseUtil;

/**
 * @author PRATHAM
 */
public class RegisterValidationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String submit = req.getParameter("submit");

        if ("register".equals(submit)) {
            String validationMessage = validateRegistration(req);

            if (!validationMessage.isEmpty()) {
                request.setAttribute("message", validationMessage);
                req.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private String validateRegistration(HttpServletRequest request) {
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String contact = request.getParameter("contact");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        DatabaseUtil dbUtil = new DatabaseUtil();

        if (fname == null || !Pattern.matches("^[A-Za-z]{1,50}$", fname)) {
            return "Invalid First Name.";
        }
        if (lname == null || !Pattern.matches("^[A-Za-z]{1,50}$", lname)) {
            return "Invalid Last Name.";
        }
        if (contact == null || !Pattern.matches("^\\d{10}$", contact)) {
            return "Invalid Contact Number.";
        }
        if (email == null || !Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email)) {
            return "Invalid Email Format.";
        }
        if (dbUtil.doesEmailExists(email)) {
            return "Email is already registered. Please use another.";
        }
        if (username == null || !Pattern.matches("^[A-Za-z0-9_]{5,20}$", username)) {
            return "Invalid Username.";
        }
        if (dbUtil.doesUsernameExists(username)) {
            return "Username is already taken. Please choose another.";
        }
        if (password == null || !Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d).{6,}$", password)) {
            return "Password must be at least 6 characters long and contain at least one letter and one number.";
        }
        if (!confirmPassword.equals(password)) {
            return "Passwords do not match.";
        }

        return "";
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
