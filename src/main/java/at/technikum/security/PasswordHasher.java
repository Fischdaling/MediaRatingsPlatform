package at.technikum.security;


import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordHasher {

    public static String HashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
}
