package at.technikum.security;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordVerifier {
    public static BCrypt.Result VerifyHash(String password, char[] hashedPassword){
        return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
    }
}
