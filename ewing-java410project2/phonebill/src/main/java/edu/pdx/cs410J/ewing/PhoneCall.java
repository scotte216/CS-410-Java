package edu.pdx.cs410J.ewing;

import edu.pdx.cs410J.AbstractPhoneCall;

/**
 * Created by Scott Ewing on 7/2/15.
 * PhoneCall Class for CS410J Phone Bill Project.
 */
public class PhoneCall extends AbstractPhoneCall {
    private String startTimeString;
    private String endTimeString;
    private String callee;
    private String caller;

    /**
     * Constructor which takes a caller's number
     * @param aCaller A caller's phone number in the form nnn-nnn-nnnn where n is 0-9
     */
    public PhoneCall(String aCaller) {
        this.caller = aCaller;
    }

    /**
     * Constructor for creating a call record using all the call record data. Data must be correct at this point.
     * No error checking is done on this constructor.
     * @param caller String number of the caller in the form nnn-nnn-nnnn where n is 0-9
     * @param callee String number of the callee in the form nnn-nnn-nnnn where n is 0-9
     * @param start String start time in the format "MM/DD/YYYY HH:MM"
     * @param end String end time in the format "MM/DD/YYYY HH:MM"
     */
    public PhoneCall(String caller, String callee, String start, String end) {
        this.caller = caller;
        this.callee = callee;
        this.startTimeString = start;
        this.endTimeString = end;
    }

    /**
     *
     * @param o Object to be compared to this one.
     * @return returns <code>true</code> if if the caller number, callee number, start and end times are all the same. <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneCall phoneCall = (PhoneCall) o;

        if (startTimeString != null ? !startTimeString.equals(phoneCall.startTimeString) : phoneCall.startTimeString != null)
            return false;
        if (endTimeString != null ? !endTimeString.equals(phoneCall.endTimeString) : phoneCall.endTimeString != null)
            return false;
        if (callee != null ? !callee.equals(phoneCall.callee) : phoneCall.callee != null) return false;
        return !(caller != null ? !caller.equals(phoneCall.caller) : phoneCall.caller != null);

    }

    /**
     *
     * @return unique integer hash code for this object based on caller and callee numbers, and start & end times.
     */
    @Override
    public int hashCode() {
        int result = startTimeString != null ? startTimeString.hashCode() : 0;
        result = 31 * result + (endTimeString != null ? endTimeString.hashCode() : 0);
        result = 31 * result + (callee != null ? callee.hashCode() : 0);
        result = 31 * result + (caller != null ? caller.hashCode() : 0);
        return result;
    }

    /**
     * Getter for the start time
     * @return startTimeString
     */
    @Override
    public String getStartTimeString() {
        return startTimeString;
    }

    /**
     * Getter for the end time string
     * @return endTimeString
     */
    @Override
    public String getEndTimeString() {
        return endTimeString;
    }

    /**
     * Getter for the caller phone number
     * @return caller phone number string
     */
    public String getCaller(){
        return caller;
    }

    /**
     * Getter for the callee phone number
     * @return callee phone number string
     */
    public String getCallee(){
        return callee;
    }

}
