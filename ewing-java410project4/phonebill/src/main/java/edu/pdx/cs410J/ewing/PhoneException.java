package edu.pdx.cs410J.ewing;

/**
 * A generic exception used for the phone bill program. Created by Scott Ewing on 7/19/2015.
 */
public class PhoneException extends Exception {
    private String message = null;


    /**
     * Basic constructor with message parameter
     * @param message Message to be included in the exception.
     */
    public PhoneException(String message) {
        super(message);
        this.message = message;
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
