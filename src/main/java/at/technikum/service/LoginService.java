package at.technikum.service;

import at.technikum.model.User;

import java.security.InvalidParameterException;

import static at.technikum.security.PasswordVerifier.VerifyHash;

public class LoginService {
    //TODO INITIALIZE
    User user;
    //TODO Register
    public void loginCheck(String userName, String password){
        if (userName.isEmpty() || password.isEmpty()) throw new InvalidParameterException("Username or Password is empty");
        if (!user.getUsername().equals(userName)) throw new InvalidParameterException("Username is wrong");
        if (VerifyHash(password,user.getPassword().toCharArray()).verified) throw new InvalidParameterException("Password is wrong");
    }
     //TODO LOGOUT
}
