package at.technikum.util.exception.apiExceptions;

public class UnauthorizedExcpetion extends ApiException {
    public UnauthorizedExcpetion(String message) {
        super(401, message);
    }
}
