package edu.pdx.cs410J.ewing;

/**
 * Created by Scott Ewing on 7/19/2015. A generic exception used for the phone bill program.
 */
public class PhoneException extends Exception {
    private String message= null;

    /**
     * Generic constructor for the Exception
     */
    public PhoneException() {
        super();
    }

    /**
     * Basic constructor with message parameter
     * @param message Message to be included in the exception.
     */
    public PhoneException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * Constructor with case parameter.
     * @param cause Cause for the exception
     */
    public PhoneException(Throwable cause) {
        super(cause);
    }

    /**
     * toString expected by contract.
     * @return exception message
     */
    @Override
    public String toString() {
        return message;
    }

    /**
     * Returns the exception message
     * @return the exception message
     */
    @Override
    public String getMessage() {
        return message;
    }
}
