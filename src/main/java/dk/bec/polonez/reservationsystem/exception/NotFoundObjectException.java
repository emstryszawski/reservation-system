package dk.bec.polonez.reservationsystem.exception;

public class NotFoundObjectException extends RuntimeException {

    public NotFoundObjectException(Class<?> clazz, long id) {
        super(clazz.getSimpleName() + " with id=" + id + " not found");
    }

    public NotFoundObjectException(Class<?> clazz, String code) {
        super(clazz.getSimpleName() + " with id=" + code + " not found");
    }
}