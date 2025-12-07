package utilities;

import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Achievement;
import model.City;
import model.State;
import model.User;

/**
 * @author PRATHAM
 */
public class DatabaseUtil {

    private final String DB_URL = AppConfigListener.getDBUrl();
    private final String DB_USER = AppConfigListener.getDBUser();
    private final String DB_PASSWORD = AppConfigListener.getDBPassword();

    // The method returns the connection object to establish connection.
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // The method creates user by adding their data to database.
    public String registerUser(User user) {
        String query = "INSERT INTO USERS (USERNAME, FNAME, LNAME, CONTACT, EMAIL, PASSWORD) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getFname());
            pstmt.setString(3, user.getLname());
            pstmt.setString(4, user.getContact());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6, user.getPassword());

            int rowsInserted = pstmt.executeUpdate();
            return (rowsInserted > 0) ? "success" : "fail";

        } catch (SQLException e) {
            System.out.println("Exception in registerUser: " + e.toString());
            return "error";
        }
    }

    // The method checks if the username already exists.
    public boolean doesUsernameExists(String username) {
        String query = "SELECT 1 FROM USERS WHERE USERNAME = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Returns true if username exists
            }
        } catch (SQLException e) {
            System.out.println("Exception In doesUsernameExists: " + e.toString());
        }
        return false;
    }

    // The method checks if the email already exists.
    public boolean doesEmailExists(String email) {
        String query = "SELECT 1 FROM USERS WHERE EMAIL = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Returns true if email exists
            }
        } catch (SQLException e) {
            System.out.println("Exception In doesEmailExists: " + e.toString());
        }
        return false;
    }

    // The method verifies the user's credentials as they log in.
    public String[] loginUser(String identifier, String password) {
        String query = "SELECT id, username, password FROM users WHERE username = ? OR email = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, identifier);
            stmt.setString(2, identifier);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return new String[]{"no_user"}; // No such user exists
                }

                String username = rs.getString("username");
                String id = rs.getString("id");
                String hashedPassword = rs.getString("password");

                if (PasswordUtil.checkPassword(password, hashedPassword)) {                    
                    return new String[]{"success", username, id}; // Correct credentials
                } else {
                    return new String[]{"wrong_password"}; // Incorrect password
                }
            }
        } catch (SQLException e) {
            System.out.println("Exception In loginUser: " + e.toString());
            return new String[]{e.toString()};
        }
    }

    // The method verifies the user's current password.
    public boolean isCurrentPasswordCorrect(int userId, String currentPassword) {
        String query = "SELECT password FROM users WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("password");
                    return PasswordUtil.checkPassword(currentPassword, hashedPassword);
                }
            }
        } catch (SQLException e) {
            System.out.println("Exception in isCurrentPasswordCorrect: " + e.toString());
        }
        return false;
    }

    // The method updates the user's password.
    public String updateUserPassword(int userId, String newPassword) {
        String updateQuery = "UPDATE users SET password = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);
            int updated = stmt.executeUpdate();
            return updated > 0 ? "success" : "fail";
        } catch (SQLException e) {
            System.out.println("Exception in updateUserPassword: " + e.toString());
            return "An error occurred while updating the password.";
        }
    }

    // The method returns user's email from username or email input.
    public String getEmailByIdentifier(String identifier) {
        String query = "SELECT email FROM users WHERE username = ? OR email = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, identifier);
            stmt.setString(2, identifier);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("email");
                }
            }
        } catch (SQLException e) {
            System.out.println("Exception in getEmailByIdentifier: " + e.toString());
        }
        return null;
    }

    // The method stores reset token in the database.
    public void storeResetToken(String email, String token) {
        String query = "UPDATE users SET reset_token = ? WHERE email = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, token);
            stmt.setString(2, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception in storeResetToken: " + e.toString());
        }
    }

    // The method verifies reset token and update password.
    public boolean resetPassword(String token, String newPassword) {
        String query = "UPDATE users SET password = ?, reset_token = NULL WHERE reset_token = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, token);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Exception in resetPassword: " + e.toString());
        }
        return false;
    }

    // The method deletes the user's profile.
    public String deleteUser(int userId) {
        try (Connection conn = getConnection()) {
            String deleteAchievementsQuery = "DELETE FROM achievement WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteAchievementsQuery)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }

            String deleteUserQuery = "DELETE FROM users WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteUserQuery)) {
                stmt.setInt(1, userId);
                int userDeleted = stmt.executeUpdate();
                return userDeleted > 0 ? "success" : "fail";
            }
        } catch (SQLException e) {
            System.out.println("Exception In deleteUser: " + e.toString());
            return e.toString();
        }
    }

    // The method returns the user's profile.
    public User getProfile(int id) {
        User user = new User();
        String query = "SELECT u.id, u.fname, u.lname, u.contact, u.email, u.username, u.dob, u.city, u.state, u.gender, u.address, c.city_name, s.state_name, u.pincode, u.picture FROM users u JOIN city c ON u.city = c.city_id JOIN state s ON u.state = s.state_id WHERE u.id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user.setId(rs.getInt("id"));
                    user.setFname(rs.getString("fname"));
                    user.setLname(rs.getString("lname"));
                    user.setContact(rs.getString("contact"));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setDob(rs.getDate("dob"));
                    user.setGender(rs.getString("gender"));
                    user.setAddress(rs.getString("address"));
                    user.setCity(rs.getInt("city"));
                    user.setState(rs.getInt("state"));
                    user.setCityName(rs.getString("city_name"));
                    user.setStateName(rs.getString("state_name"));
                    user.setPincode(rs.getString("pincode"));
                    user.setPicture(rs.getString("picture"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Exception In getProfile: " + e.toString());
        }
        return user;
    }

    // The method updates the user's profile data.
    public String updateUser(User user) {
        String query = "UPDATE users SET fname = ?, lname = ?, contact = ?, dob = ?, gender = ?, address = ?, city = ?, state = ?, pincode = ?, picture = ? WHERE username = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getFname());
            stmt.setString(2, user.getLname());
            stmt.setString(3, user.getContact());
            stmt.setDate(4, user.getDob());
            stmt.setString(5, user.getGender());
            stmt.setString(6, user.getAddress());
            stmt.setInt(7, user.getCity());
            stmt.setInt(8, user.getState());
            stmt.setString(9, user.getPincode());
            stmt.setString(10, user.getPicture());
            stmt.setString(11, user.getUsername());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0 ? "success" : "fail";
        } catch (SQLException e) {
            System.out.println("Exception In updateUser: " + e.toString());
            return e.toString();
        }
    }

    // The method returns all the states.
    public List<State> getAllStates() {
        List<State> states = new ArrayList<>();
        String query = "SELECT state_id, state_name FROM state where state_id != ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, 1);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    states.add(new State(rs.getInt("state_id"), rs.getString("state_name")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Exception In getAllStates: " + e.toString());
        }
        return states;
    }

    // The method returns the cities of the selected state.
    public List<City> getCitiesByStateId(int stateId) {
        List<City> cities = new ArrayList<>();
        String query = "SELECT city_id, city_name, state_id FROM city WHERE state_id = ?";
        try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, stateId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    cities.add(new City(rs.getInt("city_id"), rs.getString("city_name")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Exception In getCitiesByStateId: " + e.toString());
        }
        return cities;
    }

    // The method adds the achievement in the database
    public String addAchievement(Achievement achievement) {
        String query = "INSERT INTO achievement(user_id, title, organization, website, category, type, method, duration, description, date_achieved, image) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, achievement.getUserId());
            pstmt.setString(2, achievement.getTitle());
            pstmt.setString(3, achievement.getOrganization());
            pstmt.setString(4, achievement.getWebsite());
            pstmt.setString(5, achievement.getCategory());
            pstmt.setString(6, achievement.getType());
            pstmt.setString(7, achievement.getMethod());
            pstmt.setString(8, achievement.getDuration());
            pstmt.setString(9, achievement.getDescription());
            pstmt.setDate(10, achievement.getDateAchieved());
            pstmt.setString(11, achievement.getImage());

            int rowInserted = pstmt.executeUpdate();
            return rowInserted > 0 ? "success" : "fail";
        } catch (SQLException e) {
            System.out.println("Exception In addAchievement: " + e.toString());
            return e.toString();
        }
    }

    // The method updates achievement of the user.
    public String updateAchievement(Achievement achievement) {
        String query = "UPDATE achievement SET title = ?, organization = ?, website = ?, category = ?, type = ?, method = ?, duration = ?, description = ?, date_achieved = ?, image = ? WHERE achievement_id = ?";

        try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, achievement.getTitle());
            pstmt.setString(2, achievement.getOrganization());
            pstmt.setString(3, achievement.getWebsite());
            pstmt.setString(4, achievement.getCategory());
            pstmt.setString(5, achievement.getType());
            pstmt.setString(6, achievement.getMethod());
            pstmt.setString(7, achievement.getDuration());
            pstmt.setString(8, achievement.getDescription());
            pstmt.setDate(9, achievement.getDateAchieved());
            pstmt.setString(10, achievement.getImage());
            pstmt.setInt(11, achievement.getAchievementId());

            int rowInserted = pstmt.executeUpdate();
            return rowInserted > 0 ? "success" : "fail";
        } catch (SQLException e) {
            System.out.println("Exception In updateAchievement: " + e.toString());
            return e.toString();
        }
    }

    // The method returns image path of an achievement.
    public String getAchievementImagePath(int achievementId) {
        String imagePath = null;
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement("SELECT image FROM achievement WHERE achievement_id = ?")) {
            ps.setInt(1, achievementId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                imagePath = rs.getString("image");
            }
        } catch (Exception e) {
            System.out.println("Exception In getAchievementImagePath: " + e.toString());
            return e.toString();
        }
        return imagePath;
    }

    // The method returns image path of the user.
    public String getUserProfileImage(int userId) {
        String imagePath = null;
        String query = "SELECT picture FROM users WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                imagePath = rs.getString("picture");
            }
        } catch (SQLException e) {
            System.out.println("Exception In getUserProfileImage: " + e.toString());
            return e.toString();
        }
        return imagePath;
    }

    // The method returns image paths of all the achievements.
    public List<String> getUserAchievementImages(int userId) {
        List<String> images = new ArrayList<>();
        String query = "SELECT image FROM achievement WHERE user_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                images.add(rs.getString("image"));
            }
        } catch (SQLException e) {
            System.out.println("Exception In getUserAchievementImages: " + e.toString());
        }
        return images;
    }

    // The method deletes achievement of the user.
    public String deleteAchievement(int id) {
        String query = "DELETE FROM achievement WHERE achievement_id = ?";

        try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, id);

            int rowInserted = pstmt.executeUpdate();
            return rowInserted > 0 ? "success" : "fail";
        } catch (SQLException e) {
            System.out.println("Exception In deleteAchievement: " + e.toString());
            return e.toString();
        }
    }

    // The method returns all the achievements.
    public List<Achievement> getAllAchievements() {
        List<Achievement> achievements = new ArrayList<>();

        String query = "SELECT a.*, u.* FROM achievement a JOIN users u ON a.user_id = u.id";
        try (Connection con = getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Achievement achievement = new Achievement();
                achievement.setAchievementId(rs.getInt("achievement_id"));
                achievement.setUserId(rs.getInt("user_id"));
                achievement.setTitle(rs.getString("title"));
                achievement.setOrganization(rs.getString("organization"));
                achievement.setWebsite(rs.getString("website"));
                achievement.setCategory(rs.getString("category"));
                achievement.setType(rs.getString("type"));
                achievement.setMethod(rs.getString("method"));
                achievement.setDuration(rs.getString("duration"));
                achievement.setDescription(rs.getString("description"));
                achievement.setDateAchieved(rs.getDate("date_achieved"));
                achievement.setImage(rs.getString("image"));

                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setFname(rs.getString("fname"));
                user.setLname(rs.getString("lname"));
                user.setEmail(rs.getString("email"));
                user.setPicture(rs.getString("picture"));

                achievement.setUser(user);

                achievements.add(achievement);
            }
        } catch (SQLException e) {
            System.out.println("Exception In getAllAchievements: " + e.toString());
        }

        return achievements;
    }

    // The method returns a single achievement.
    public Achievement getAchievement(int id) {
        Achievement achievement = new Achievement();
        String query = "SELECT a.*, u.* FROM achievement a JOIN users u ON a.user_id = u.id WHERE achievement_id = ?";

        try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    achievement.setAchievementId(rs.getInt("achievement_id"));
                    achievement.setUserId(rs.getInt("user_id"));
                    achievement.setTitle(rs.getString("title"));
                    achievement.setOrganization(rs.getString("organization"));
                    achievement.setWebsite(rs.getString("website"));
                    achievement.setCategory(rs.getString("category"));
                    achievement.setType(rs.getString("type"));
                    achievement.setMethod(rs.getString("method"));
                    achievement.setDuration(rs.getString("duration"));
                    achievement.setDescription(rs.getString("description"));
                    achievement.setDateAchieved(rs.getDate("date_achieved"));
                    achievement.setImage(rs.getString("image"));

                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setFname(rs.getString("fname"));
                    user.setLname(rs.getString("lname"));
                    user.setEmail(rs.getString("email"));
                    user.setPicture(rs.getString("picture"));

                    achievement.setUser(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("Exception In getAchievement: " + e.toString());
        }
        return achievement;
    }

    // For debugging purpose.
    public static void main(String[] args) {
        DatabaseUtil dbUtil = new DatabaseUtil();
        dbUtil.getAllAchievements();
    }

    public static void printResultSet(ResultSet rs) {
        try {
            // Get metadata to retrieve column details
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Print column names
            System.out.println("-----------------------------------------------------");
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i) + "\t");
            }
            System.out.println();
            System.out.println("-----------------------------------------------------");

            // Iterate through rows and print each column value
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }
            System.out.println("-----------------------------------------------------");
        } catch (SQLException e) {
            System.out.println("Exception In printResultSet: " + e.toString());
        }
    }
}
