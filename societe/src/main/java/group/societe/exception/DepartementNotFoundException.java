package group.societe.exception;

public class DepartementNotFoundException extends RuntimeException {
    public DepartementNotFoundException(String message) {
        super(message);
    }
}