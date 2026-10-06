package at.technikum.util.exception.apiExceptions;

public class NotFoundException extends ApiException {
    public NotFoundException(String message) {
        super(404,message);
    }
}
