package utilities;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @author PRATHAM
 */
public class PasswordUtil {

    // Hash password
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Verify password
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
