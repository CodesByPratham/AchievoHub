package utilities;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author PRATHAM
 */
public class DatabaseSetupServlet extends HttpServlet {

    private String DB_URL;
    private String DB_USER;
    private String DB_PASSWORD;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.DB_URL = AppConfigListener.getDBUrl();
        this.DB_USER = AppConfigListener.getDBUser();
        this.DB_PASSWORD = AppConfigListener.getDBPassword();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            System.out.println("Database connection established.");

            createTables(conn);
            System.out.println("Table creation check complete.");

            populateData(conn);
            System.out.println("Data population check complete.");

            System.out.println("\nDatabase initialization finished successfully!");

        } catch (SQLException e) {
            System.err.println("A database error occurred.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Creates all necessary tables if they do not already exist.
     *
     * @param conn The active database connection.
     * @throws SQLException if a database access error occurs.
     */
    private static void createTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Create STATE Table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS STATE (
                    STATE_ID SERIAL PRIMARY KEY,
                    STATE_NAME VARCHAR(50) NOT NULL
                )
            """);

            // Create CITY Table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS CITY (
                    CITY_ID SERIAL PRIMARY KEY,
                    CITY_NAME VARCHAR(50) NOT NULL,
                    STATE_ID INTEGER NOT NULL,
                    CONSTRAINT CITY_STATE_FK FOREIGN KEY (STATE_ID)
                        REFERENCES STATE (STATE_ID)
                )
            """);

            // Create USERS Table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS USERS (
                    ID SERIAL PRIMARY KEY,
                    USERNAME VARCHAR(10) NOT NULL UNIQUE,
                    FNAME VARCHAR(20) NOT NULL,
                    LNAME VARCHAR(20) NOT NULL,
                    CONTACT VARCHAR(13) NOT NULL,
                    EMAIL VARCHAR(30) NOT NULL,
                    DOB DATE,
                    GENDER VARCHAR(8),
                    ADDRESS VARCHAR(255),
                    CITY INTEGER DEFAULT 1,
                    STATE INTEGER DEFAULT 1,
                    PINCODE VARCHAR(8),
                    VERIFIED BOOLEAN DEFAULT FALSE,
                    VERIFICATION_CODE VARCHAR(255),
                    RESET_TOKEN VARCHAR(255),
                    PASSWORD VARCHAR(100) NOT NULL,
                    PICTURE VARCHAR(100) DEFAULT NULL,
                    CONSTRAINT FK_USERS_CITY FOREIGN KEY (CITY) REFERENCES CITY(CITY_ID),
                    CONSTRAINT FK_USERS_STATE FOREIGN KEY (STATE) REFERENCES STATE(STATE_ID)
                )
            """);

            // Create ACHIEVEMENT Table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS ACHIEVEMENT (
                    ACHIEVEMENT_ID SERIAL PRIMARY KEY,
                    USER_ID INTEGER NOT NULL,
                    TITLE VARCHAR(255) NOT NULL,
                    ORGANIZATION VARCHAR(255),
                    WEBSITE VARCHAR(255),
                    CATEGORY VARCHAR(255),
                    TYPE VARCHAR(50),
                    METHOD VARCHAR(50),
                    DURATION VARCHAR(50),
                    DESCRIPTION VARCHAR(1000),
                    DATE_ACHIEVED DATE,
                    IMAGE VARCHAR(255),
                    ADDED_ON DATE DEFAULT CURRENT_DATE,
                    CONSTRAINT FK_USER FOREIGN KEY (USER_ID)
                        REFERENCES USERS (ID)
                )
            """);
        }
    }

    /**
     * Populates tables with default data if they are currently empty.
     *
     * @param conn The active database connection.
     * @throws SQLException if a database access error occurs.
     */
    private static void populateData(Connection conn) throws SQLException, ParseException {
        try (Statement stmt = conn.createStatement()) {

            // Insert Default Data into STATE Table (Only if empty)
            if (isTableEmpty(conn, "STATE")) {
                System.out.println("Populating STATE table...");
                stmt.executeUpdate("""
                    INSERT INTO STATE (STATE_ID, STATE_NAME) VALUES
                    (1, 'Select State'), (2, 'Gujarat'), (3, 'Maharashtra'), (4, 'Rajasthan'),
                    (5, 'Tamil Nadu'), (6, 'Karnataka'), (7, 'Punjab'), (8, 'Uttar Pradesh');
                """);
                // Reset the sequence to avoid conflicts with future inserts
                stmt.execute("SELECT setval('state_state_id_seq', (SELECT MAX(STATE_ID) FROM STATE));");
            }

            // Insert Default Data into CITY Table (Only if empty)
            if (isTableEmpty(conn, "CITY")) {
                System.out.println("Populating CITY table...");
                stmt.executeUpdate("""
                    INSERT INTO CITY (CITY_ID, CITY_NAME, STATE_ID) VALUES
                    (1, 'Select City', 1), (2, 'Ahmedabad', 2), (3, 'Surat', 2), (4, 'Vadodara', 2),
                    (5, 'Rajkot', 2), (6, 'Mumbai', 3), (7, 'Pune', 3), (8, 'Nagpur', 3),
                    (9, 'Nashik', 3), (10, 'Jaipur', 4), (11, 'Udaipur', 4), (12, 'Jodhpur', 4),
                    (13, 'Kota', 4), (14, 'Chennai', 5), (15, 'Coimbatore', 5), (16, 'Madurai', 5),
                    (17, 'Tiruchirappalli', 5), (18, 'Bengaluru', 6), (19, 'Mysuru', 6), (20, 'Mangaluru', 6),
                    (21, 'Hubballi', 6), (22, 'Amritsar', 7), (23, 'Ludhiana', 7), (24, 'Chandigarh', 7),
                    (25, 'Patiala', 7), (26, 'Lucknow', 8), (27, 'Kanpur', 8), (28, 'Varanasi', 8), (29, 'Agra', 8);
                """);
                stmt.execute("SELECT setval('city_city_id_seq', (SELECT MAX(CITY_ID) FROM CITY));");
            }

            // Insert Dummy Users (Only if USERS table is empty)
            if (isTableEmpty(conn, "USERS")) {
                System.out.println("Populating USERS table...");
                stmt.executeUpdate("""
                    INSERT INTO USERS (USERNAME, FNAME, LNAME, CONTACT, EMAIL, DOB, GENDER, ADDRESS, CITY, STATE, PINCODE, PASSWORD, PICTURE)
                    VALUES 
                        ('kritikal', 'Kritika', 'Lohani', '9876543211', 'kritika@example.com', '1998-07-22', 'female', 'Sunshine Tower, Mumbai', 6, 3, '400001', 'securepass2', 'assets/uploads/profiles/kritika.jpg'),
                        ('prathamr', 'Pratham', 'Rathod', '9876543210', 'pratham@example.com', '2000-05-12', 'male', '101 Residency, Ahmedabad', 2, 2, '380001', 'securepass1', 'assets/uploads/profiles/pratham.jpg'),
                        ('devpatel', 'Dev', 'Patel', '9876543212', 'dev@example.com', '1996-12-05', 'male', 'Green Street, Surat', 3, 2, '395003', 'securepass3', 'assets/uploads/profiles/dev.jpg');
                """);
            }

            // Insert Dummy Achievements (Only if ACHIEVEMENT table is empty)
            if (isTableEmpty(conn, "ACHIEVEMENT")) {
                System.out.println("Populating ACHIEVEMENT table...");
                int kritikaId = getUserId(conn, "kritikal");
                int prathamId = getUserId(conn, "prathamr");
                int devId = getUserId(conn, "devpatel");

                if (kritikaId != -1) {
                    insertAchievement(conn, kritikaId, "SQL", "HackerRank", "https://www.hackerrank.com/", "Academic", "Certificate", "Online Course", "Jun-2023 – Aug-2023", "The HackerRank SQL Basic Certification is designed to test fundamental SQL concepts...", "2023-06-25", "assets/uploads/achievements/sql.jpg");
                    insertAchievement(conn, kritikaId, "Generative Al", "GUVI", "https://www.guvi.in/", "Personal Development", "Participation", "Workshop", "Sep-2022 – Dec-2022", "Kritika from Baou is hereby awarded the certificate of achievement for the successful completion of SAWIT.AI Learnathon Program...", "2022-09-20", "assets/uploads/achievements/genai.jpg");
                    insertAchievement(conn, kritikaId, "Python", "HackerRank", "https://www.hackerrank.com/", "Professional", "Certificate", "Online Course", "Feb-2024 – May-2024", "The HackerRank Python Certification validates your proficiency in the Python programming language...", "2024-02-14", "assets/uploads/achievements/python.jpg");
                }

                if (prathamId != -1) {
                    insertAchievement(conn, prathamId, "GitHub Foundations", "GitHub", "https://github.com/", "Technology & Innovation", "Badge", "Exam", "Nov-2023 – Jan-2024", "Passing the GitHub Foundations certification exam validates subject matter expertise...", "2023-11-15", "assets/uploads/achievements/github.jpg");
                    insertAchievement(conn, prathamId, "Java", "HackerRank", "https://www.hackerrank.com/", "Academic", "Certificate", "Online Course", "Aug-2022 – Oct-2022", "It will cover basic topics in Java language such as classes, data structures, inheritance, exception handling, etc...", "2022-08-10", "assets/uploads/achievements/java.jpg");
                    insertAchievement(conn, prathamId, "Agile Development and Scrum", "IBM", "https://www.coursera.org/", "Personal Development", "Badge", "Online Course", "Jan-2024 – Mar-2024", "This badge earner has demonstrated how to use Git and GitHub as code repositories...", "2024-01-05", "assets/uploads/achievements/agile.jpg");
                }

                if (devId != -1) {
                    insertAchievement(conn, devId, "Tableau Desktop Specialist", "Tableau", "https://www.tableau.com/", "Technology & Innovation", "Certification", "Course", "Apr-2023 – Jul-2023", "The Tableau Desktop Specialist certification... validates a candidates foundational skills...", "2023-04-30", "assets/uploads/achievements/tableau.jpg");
                    insertAchievement(conn, devId, "Startup Founder", "Y Combinator", "https://loremipsum.io/", "Entrepreneurship", "Funding", "Pitch", "Dec-2022 – Feb-2023", "Entrepreneur awards have become a powerful tool for recognizing and celebrating outstanding achievements...", "2022-12-12", "assets/uploads/achievements/startup.jpg");
                    insertAchievement(conn, devId, "AI Innovation Grant", "MIT", "https://web.mit.edu/", "Research", "Grant", "Application", "Jul-2023 – Oct-2023", "The AI Innovation Grant is a prestigious recognition awarded to individuals and organizations...", "2023-07-08", "assets/uploads/achievements/grant.jpg");
                }
            }
        }
    }

    private static boolean isTableEmpty(Connection conn, String tableName) throws SQLException {
        // Use a PreparedStatement to be safe, although table names aren't typical injection vectors.
        String sql = "SELECT 1 FROM " + tableName + " LIMIT 1";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName)) {
            return rs.next() && rs.getInt(1) == 0;
        }
    }

    /**
     * Retrieves the user ID for a given username.
     *
     * @param conn The active database connection.
     * @param username The username to look up.
     * @return The user's ID, or -1 if not found.
     * @throws SQLException if a database access error occurs.
     */
    private static int getUserId(Connection conn, String username) throws SQLException {
        String sql = "SELECT ID FROM USERS WHERE USERNAME = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID");
                }
            }
        }
        return -1; // User not found
    }

    /**
     * Inserts a new achievement record using a secure PreparedStatement.
     */
    private static void insertAchievement(Connection conn, int userId, String title, String organization, String website, String category, String type, String method, String duration, String description, String dateAchieved, String imagePath) throws SQLException, ParseException {
        // Omit ACHIEVEMENT_ID and ADDED_ON, as they are handled by SERIAL and DEFAULT.
        String sql = """
            INSERT INTO ACHIEVEMENT 
            (USER_ID, TITLE, ORGANIZATION, WEBSITE, CATEGORY, TYPE, METHOD, DURATION, DESCRIPTION, DATE_ACHIEVED, IMAGE) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, title);
            pstmt.setString(3, organization);
            pstmt.setString(4, website);
            pstmt.setString(5, category);
            pstmt.setString(6, type);
            pstmt.setString(7, method);
            pstmt.setString(8, duration);
            pstmt.setString(9, description);

            // Convert String date to java.sql.Date
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = format.parse(dateAchieved);
            pstmt.setDate(10, new java.sql.Date(parsedDate.getTime()));

            pstmt.setString(11, imagePath);

            pstmt.executeUpdate();
        }
    }
}
