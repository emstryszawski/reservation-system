package dk.bec.polonez.reservationsystem.exception;

public class UserIsBlockedException extends RuntimeException {

    public UserIsBlockedException() {
        super("User has been blocked, operation rejected");
    }
}
