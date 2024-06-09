package domain.domainException;

public class IncorrectCustomerInformationException extends Exception {
    public IncorrectCustomerInformationException() {
        super();
    }

    public IncorrectCustomerInformationException(String message) {
        super(message);
    }

    public IncorrectCustomerInformationException(Throwable argument) {
        super(argument);
    }

    public IncorrectCustomerInformationException(String message, Throwable argument) {
        super(message, argument);
    }
}
