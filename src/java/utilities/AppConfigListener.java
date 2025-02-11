package utilities;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * @author PRATHAM
 */
@WebListener
public class AppConfigListener implements ServletContextListener {

    private static ServletContext context;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        context = event.getServletContext();

        try {
            Class.forName(getDBDriver());
            System.out.println("Database Driver Loaded: " + getDBDriver());
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading database driver: " + e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        context = null;
    }

    public static ServletContext getServletContext() {
        return context;
    }

    public static String getDBDriver() {
        return context.getInitParameter("dbDriver");
    }

    public static String getDBUrl() {
        return context.getInitParameter("dbURL");
    }

    public static String getDBUser() {
        return context.getInitParameter("dbUser");
    }

    public static String getDBPassword() {
        return context.getInitParameter("dbPassword");
    }
}
