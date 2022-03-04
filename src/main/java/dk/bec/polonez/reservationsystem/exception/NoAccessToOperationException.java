package dk.bec.polonez.reservationsystem.exception;

public class NoAccessToOperationException extends RuntimeException {

    public NoAccessToOperationException(String message) {
        super(message);
    }

    public NoAccessToOperationException() {
        super("Access denied, log as Place Owner or System Admin");
    }
}
