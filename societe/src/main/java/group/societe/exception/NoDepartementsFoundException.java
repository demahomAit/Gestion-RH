package group.societe.exception;



public class NoDepartementsFoundException extends RuntimeException {

    public NoDepartementsFoundException(String message) {
        super(message);
    }

    public NoDepartementsFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}