package dk.bec.polonez.reservationsystem.exception;

public class NoAccessToOfferOperationException extends RuntimeException {

    public NoAccessToOfferOperationException(String message) {
        super(message);
    }
}
