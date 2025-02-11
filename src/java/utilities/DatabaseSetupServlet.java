package utilities;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class DatabaseSetupServlet extends HttpServlet {

    private String dbURL;
    private String dbUser;
    private String dbPassword;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dbURL = AppConfigListener.getDBUrl();
        dbUser = AppConfigListener.getDBUser();
        dbPassword = AppConfigListener.getDBPassword();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword); Statement stmt = conn.createStatement()) {

            // Switch to the PRATHAM_DBA schema
            stmt.execute("ALTER SESSION SET CURRENT_SCHEMA = " + AppConfigListener.getDBUser());

            // Create USERS Table
            stmt.execute("""
                CREATE TABLE USERS (
                    ID NUMBER NOT NULL PRIMARY KEY,
                    USERNAME VARCHAR2(10) UNIQUE NOT NULL,
                    FNAME VARCHAR2(20) NOT NULL,
                    LNAME VARCHAR2(20) NOT NULL,
                    CONTACT VARCHAR2(13) NOT NULL,
                    EMAIL VARCHAR2(30) NOT NULL,
                    DOB DATE,
                    GENDER VARCHAR2(8),
                    ADDRESS VARCHAR2(255),
                    CITY NUMBER DEFAULT 1,
                    STATE NUMBER DEFAULT 1,
                    PINCODE VARCHAR2(8),
                    VERIFIED BOOLEAN DEFAULT FALSE,
                    VERIFICATION_CODE VARCHAR2(255),
                    RESET_TOKEN VARCHAR2(255),
                    PASSWORD VARCHAR2(100) NOT NULL,
                    PICTURE VARCHAR2(100) DEFAULT NULL
                )
            """);

            // Create STATE Table
            stmt.execute("""
                CREATE TABLE STATE (
                    STATE_ID NUMBER NOT NULL PRIMARY KEY,
                    STATE_NAME VARCHAR2(50) NOT NULL
                )
            """);

            // Create CITY Table
            stmt.execute("""
                CREATE TABLE CITY (
                    CITY_ID NUMBER NOT NULL PRIMARY KEY,
                    CITY_NAME VARCHAR2(50) NOT NULL,
                    STATE_ID NUMBER NOT NULL,
                    CONSTRAINT CITY_STATE_FK FOREIGN KEY (STATE_ID) REFERENCES STATE(STATE_ID)
                )
            """);

            // Insert Default Data into STATE Table (Only if empty)
            if (isTableEmpty(conn, "STATE")) {
                stmt.executeUpdate("""
                    INSERT INTO STATE (STATE_ID, STATE_NAME) VALUES
                    (1, 'Select State'),
                    (2, 'Gujarat'),
                    (3, 'Maharashtra'),
                    (4, 'Rajasthan'),
                    (5, 'Tamil Nadu'),
                    (6, 'Karnataka'),
                    (7, 'Punjab'),
                    (8, 'Uttar Pradesh')
                """);
            }

            // Insert Default Data into CITY Table (Only if empty)
            if (isTableEmpty(conn, "CITY")) {
                stmt.executeUpdate("""
                    INSERT INTO CITY (CITY_ID, CITY_NAME, STATE_ID) VALUES
                    (1, 'Select City', 1),
                    (2, 'Ahmedabad', 2),
                    (3, 'Surat', 2),
                    (4, 'Vadodara', 2),
                    (5, 'Rajkot', 2),
                    (6, 'Mumbai', 3),
                    (7, 'Pune', 3),
                    (8, 'Nagpur', 3),
                    (9, 'Nashik', 3),
                    (10, 'Jaipur', 4),
                    (11, 'Udaipur', 4),
                    (12, 'Jodhpur', 4),
                    (13, 'Kota', 4),
                    (14, 'Chennai', 5),
                    (15, 'Coimbatore', 5),
                    (16, 'Madurai', 5),
                    (17, 'Tiruchirappalli', 5),
                    (18, 'Bengaluru', 6),
                    (19, 'Mysuru', 6),
                    (20, 'Mangaluru', 6),
                    (21, 'Hubballi', 6),
                    (22, 'Amritsar', 7),
                    (23, 'Ludhiana', 7),
                    (24, 'Chandigarh', 7),
                    (25, 'Patiala', 7),
                    (26, 'Lucknow', 8),
                    (27, 'Kanpur', 8),
                    (28, 'Varanasi', 8),
                    (29, 'Agra', 8)
                """);
            }

            // Create ACHIEVEMENT Table
            stmt.execute("""
                CREATE TABLE ACHIEVEMENT (
                    ACHIEVEMENT_ID NUMBER PRIMARY KEY,
                    USER_ID NUMBER NOT NULL,
                    TITLE VARCHAR2(255) NOT NULL,
                    ORGANIZATION VARCHAR2(255),
                    WEBSITE VARCHAR2(255),
                    CATEGORY VARCHAR2(255),
                    TYPE VARCHAR2(50),
                    METHOD VARCHAR2(50),
                    DURATION VARCHAR2(50),
                    DESCRIPTION VARCHAR2(1000),
                    DATE_ACHIEVED DATE,
                    IMAGE VARCHAR2(255),
                    ADDED_ON DATE DEFAULT SYSDATE,
                    CONSTRAINT FK_USER FOREIGN KEY (USER_ID) REFERENCES USERS(ID)
                )
            """);

            // Create Sequences
            if (!isSequenceExists(conn, "USERS_SEQ")) {
                stmt.execute("""
                    CREATE SEQUENCE USERS_SEQ
                        START WITH 1
                        INCREMENT BY 1
                        NOCACHE
                        NOCYCLE
                """);
            }

            if (!isSequenceExists(conn, "ACHIEVEMENTS_SEQ")) {
                stmt.execute("""
                    CREATE SEQUENCE ACHIEVEMENTS_SEQ
                        START WITH 1
                        INCREMENT BY 1
                        NOCACHE
                        NOCYCLE
                """);
            }

            // Insert Dummy Users (Only if USERS table is empty)
            if (isTableEmpty(conn, "USERS")) {
                stmt.executeUpdate("""
                    INSERT INTO USERS (ID, USERNAME, FNAME, LNAME, CONTACT, EMAIL, DOB, GENDER, ADDRESS, CITY, STATE, PINCODE, PASSWORD, PICTURE)
                    VALUES (USERS_SEQ.NEXTVAL, 'kritikal', 'Kritika', 'Lohani', '9876543211', 'kritika@example.com', TO_DATE('1998-07-22', 'YYYY-MM-DD'), 'female', 'Sunshine Tower, Mumbai', 6, 3, '400001', 'securepass2', 'resources/uploads/profiles/kritika.jpg')
                """);
                
                stmt.executeUpdate("""
                    INSERT INTO USERS (ID, USERNAME, FNAME, LNAME, CONTACT, EMAIL, DOB, GENDER, ADDRESS, CITY, STATE, PINCODE, PASSWORD, PICTURE)
                    VALUES (USERS_SEQ.NEXTVAL, 'prathamr', 'Pratham', 'Rathod', '9876543210', 'pratham@example.com', TO_DATE('2000-05-12', 'YYYY-MM-DD'), 'male', '101 Residency, Ahmedabad', 2, 2, '380001', 'securepass1', 'resources/uploads/profiles/pratham.jpg')
                """);

                stmt.executeUpdate("""
                    INSERT INTO USERS (ID, USERNAME, FNAME, LNAME, CONTACT, EMAIL, DOB, GENDER, ADDRESS, CITY, STATE, PINCODE, PASSWORD, PICTURE)
                    VALUES (USERS_SEQ.NEXTVAL, 'devpatel', 'Dev', 'Patel', '9876543212', 'dev@example.com', TO_DATE('1996-12-05', 'YYYY-MM-DD'), 'male', 'Green Street, Surat', 3, 2, '395003', 'securepass3', 'resources/uploads/profiles/dev.jpg')
                """);
            }

            // Insert Dummy Achievements (Only if ACHIEVEMENT table is empty)
            if (isTableEmpty(conn, "ACHIEVEMENT")) {
                int prathamId = getUserId(conn, "prathamr");
                int kritikaId = getUserId(conn, "kritikal");
                int devId = getUserId(conn, "devpatel");
                
                if (kritikaId != -1) {
                    insertAchievement(stmt, kritikaId, "SQL", "HackerRank", "https://www.hackerrank.com/", "Academic", "Certificate", "Online Course", "Jun-2023 – Aug-2023", "The HackerRank SQL Basic Certification is designed to test fundamental SQL concepts, such as querying databases, filtering data, and performing basic joins. The questions typically involve retrieving data from multiple tables, using conditions to filter results, and employing functions like GROUP BY and ORDER BY.", "2023-06-25", "resources/uploads/achievements/sql.jpg");
                    insertAchievement(stmt, kritikaId, "Generative Al", "GUVI", "https://www.guvi.in/", "Personal Development", "Participation", "Workshop", "Sep-2022 – Dec-2022", "Kritika from Baou is hereby awarded the certificate of achievement for the successful completion of SAWIT.AI Learnathon Program held on 21st September, where they completed the fundamentals of Generative Al", "2022-09-20", "resources/uploads/achievements/genai.jpg");
                    insertAchievement(stmt, kritikaId, "Python", "HackerRank", "https://www.hackerrank.com/", "Professional", "Certificate", "Online Course", "Feb-2024 – May-2024", "The HackerRank Python Certification validates your proficiency in the Python programming language. The certification assesses your problem-solving skills, algorithmic thinking, and ability to write efficient code. Earning this certification can enhance your career prospects and demonstrate your dedication to continuous learning.", "2024-02-14", "resources/uploads/achievements/python.jpg");
                }

                if (prathamId != -1) {
                    insertAchievement(stmt, prathamId, "GitHub Foundations", "GitHub", "https://github.com/", "Technology & Innovation", "Badge", "Exam", "Nov-2023 – Jan-2024", "Passing the GitHub Foundations certification exam validates subject matter expertise by measuring entry-level skills with GitHub basics like repositories, commits, branching, markdowns, and project management.", "2023-11-15", "resources/uploads/achievements/github.jpg");
                    insertAchievement(stmt, prathamId, "Java", "HackerRank", "https://www.hackerrank.com/", "Academic", "Certificate", "Online Course", "Aug-2022 – Oct-2022", "It will cover basic topics in Java language such as classes, data structures, inheritance, exception handling, etc. You are expected to be proficient in either Java 7 or Java 8.", "2022-08-10", "resources/uploads/achievements/java.jpg");
                    insertAchievement(stmt, prathamId, "Agile Development and Scrum", "IBM", "https://www.coursera.org/", "Personal Development", "Badge", "Online Course", "Jan-2024 – Mar-2024", "This badge earner has demonstrated how to use Git and GitHub as code repositories for developing applications. They understand why version control is essential in creating open-source and commercial software. The badge earner can create repositories and branches, perform pull requests and merge operations, and use these repositories to collaborate with their teammates.", "2024-01-05", "resources/uploads/achievements/agile.jpg");
                }

                if (devId != -1) {
                    insertAchievement(stmt, devId, "Tableau Desktop Specialist", "Tableau", "https://www.tableau.com/", "Technology & Innovation", "Certification", "Course", "Apr-2023 – Jul-2023", "The Tableau Desktop Specialist certification, offered by Tableau, validates a candidates foundational skills and understanding of Tableau Desktops functionality. It focuses on data preparation, exploration, and visualization, emphasizing the ability to connect to and analyze data to create insightful, interactive visualizations.", "2023-04-30", "resources/uploads/achievements/tableau.jpg");
                    insertAchievement(stmt, devId, "Startup Founder", "Y Combinator", "https://loremipsum.io/", "Entrepreneurship", "Funding", "Pitch", "Dec-2022 – Feb-2023", "Entrepreneur awards have become a powerful tool for recognizing and celebrating the outstanding achievements of individuals who have made significant contributions to their industries. Recent studies show that the number of entrepreneurship awards has grown by an impressive 35% over the past decade, with more than 500 awards now being given out each year worldwide.", "2022-12-12", "resources/uploads/achievements/startup.jpg");
                    insertAchievement(stmt, devId, "AI Innovation Grant", "MIT", "https://web.mit.edu/", "Research", "Grant", "Application", "Jul-2023 – Oct-2023", "The AI Innovation Grant is a prestigious recognition awarded to individuals and organizations that have made groundbreaking advancements in artificial intelligence and machine learning. Over the past decade, funding for AI research and development has surged by over 40%, enabling innovators to push the boundaries of automation, deep learning, and neural networks.", "2023-07-08", "resources/uploads/achievements/grant.jpg");
                }
            }

            response.getWriter().println("Schema PRATHAM_DBA initialized with tables, sequences, users, and achievements!");

        } catch (Exception e) {
            response.getWriter().println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isTableEmpty(Connection conn, String tableName) throws Exception {
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName)) {
            return rs.next() && rs.getInt(1) == 0;
        }
    }

    private boolean isSequenceExists(Connection conn, String sequenceName) throws Exception {
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM USER_SEQUENCES WHERE SEQUENCE_NAME = UPPER('" + sequenceName + "')")) {
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    private int getUserId(Connection conn, String username) throws Exception {
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT ID FROM USERS WHERE USERNAME = '" + username + "'")) {
            if (rs.next()) {
                return rs.getInt("ID");
            }
        }
        return -1; // User not found
    }

    private void insertAchievement(Statement stmt, int userId, String title, String organization, String website, String category, String type, String method, String duration, String description, String dateAchieved, String imagePath) throws Exception {
        stmt.executeUpdate("INSERT INTO ACHIEVEMENT (ACHIEVEMENT_ID, USER_ID, TITLE, ORGANIZATION, WEBSITE, CATEGORY, TYPE, METHOD, DURATION, DESCRIPTION, DATE_ACHIEVED, IMAGE, ADDED_ON) "
                + "VALUES (ACHIEVEMENTS_SEQ.NEXTVAL, " + userId + ", '" + title + "', '" + organization + "', '" + website + "', '" + category + "', '" + type + "', '" + method + "', '" + duration + "', '" + description + "', TO_DATE('" + dateAchieved + "', 'YYYY-MM-DD'), '" + imagePath + "', SYSDATE)");
    }
}
