package at.technikum.util.exception.apiExceptions;

public class ValidationException extends ApiException {
    public ValidationException(String message) {
        super(400, message);
    }
}
