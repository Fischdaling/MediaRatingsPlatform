package at.technikum.util.exception.apiExceptions;

public class ConflictException extends ApiException {
    public ConflictException(String message) {
        super(409,message);
    }
}
