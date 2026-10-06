package at.technikum.util.exception;

import java.sql.SQLException;

public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }

}
