package at.technikum.util.exception.apiExceptions;

public class ForbiddenException extends ApiException {
    public ForbiddenException(String message) {
        super(403,message);
    }
}
