package domain.domainException;

public class IncorrectItemInformationException extends Exception {
    public IncorrectItemInformationException() {
        super();
    }

    public IncorrectItemInformationException(String message) {
        super(message);
    }

    public IncorrectItemInformationException(Throwable argument) {
        super(argument);
    }

    public IncorrectItemInformationException(String message, Throwable argument) {
        super(message, argument);
    }
}
