package domain.domainException;

public class IncorrectOwnerInformationException extends Exception {
    public IncorrectOwnerInformationException() {
        super();
    }

    public IncorrectOwnerInformationException(String message) {
        super(message);
    }

    public IncorrectOwnerInformationException(Throwable argument) {
        super(argument);
    }

    public IncorrectOwnerInformationException(String message, Throwable argument) {
        super(message, argument);
    }
}
