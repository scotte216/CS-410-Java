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
