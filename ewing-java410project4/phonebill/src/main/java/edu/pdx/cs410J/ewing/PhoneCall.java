package edu.pdx.cs410J.ewing;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.DateFormat;
import java.util.Date;

/**
 * PhoneCall Class for CS410J Phone Bill Project. Created by Scott Ewing on 7/2/15.
 *
 */
public class PhoneCall extends AbstractPhoneCall implements Comparable {
    private Date startTime;
    private Date endTime;
    private String callee;
    private final String caller;


    /**
     * Constructor for creating a call record using all the call record data. Data must be correct at this point.
     * No error checking is done on this constructor.
     * @param caller String number of the caller in the form nnn-nnn-nnnn where n is 0-9
     * @param callee String number of the callee in the form nnn-nnn-nnnn where n is 0-9
     * @param start String start time in the format "MM/DD/YYYY HH:MM AA"
     * @param end String end time in the format "MM/DD/YYYY HH:MM AA"
     */
    public PhoneCall(String caller, String callee, Date start, Date end) {
        this.caller = caller;
        this.callee = callee;
        this.startTime = start;
        this.endTime = end;
    }

    /**
     * Used to determine if an object is equal to this PhoneCall
     * @param o Object to be compared to this one.
     * @return returns <code>true</code> if if the caller number, callee number, start and end times are all the same. <code>false</code> otherwise.
     */
    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneCall phoneCall = (PhoneCall) o;

        if (startTime != null ? !startTime.equals(phoneCall.startTime) : phoneCall.startTime != null)
            return false;
        if (endTime != null ? !endTime.equals(phoneCall.endTime) : phoneCall.endTime != null)
            return false;
        if (callee != null ? !callee.equals(phoneCall.callee) : phoneCall.callee != null) return false;
        return !(caller != null ? !caller.equals(phoneCall.caller) : phoneCall.caller != null);

    }

    /**
     * Determines the hashCode of the  PhoneCall based on startTime, endTime, caller, and callee.
     * @return unique integer hash code for this object based on caller and callee numbers, and start & end times.
     */
    @Override
    public int hashCode() {
        int result = startTime != null ? startTime.hashCode() : 0;
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (callee != null ? callee.hashCode() : 0);
        result = 31 * result + (caller != null ? caller.hashCode() : 0);
        return result;
    }

    /**
     * Getter for the start time string
     * @return startTime in SHORT date format
     */
    @Override
    public String getStartTimeString() {

        return DateFormat.getDateInstance(DateFormat.SHORT).format(startTime);
    }

    /**
     * This method gets the startTime for this PhoneCall
     * @return the start time of the phone call in Date format.
     */
    @Override
    public Date getStartTime(){
        return startTime;
    }

    /**
     * Getter for the end time string
     * @return endTime in SHORT date format
     */
    @Override
    public String getEndTimeString() {
        return DateFormat.getDateInstance(DateFormat.SHORT).format(endTime);
    }

    /**
     * This method gets the endTime for this PhoneCall
     * @return the end time of the phone call in Date format.
     */
    @Override
    public Date getEndTime(){
        return endTime;
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

    /**
     * This method is used to compare two objects.
     * @param o the object to compare to this object.
     * @return Returns 0 if the two objects are the same. In this case, if they have the same start time and the same
     * caller phone number. Otherwise negative if the calling object comes before the object compared to, positive if
     * it comes after.
     */
    @Override
    public int compareTo(Object o) {
        int result;
        PhoneCall aCall = (PhoneCall) o;
        if (this == o)
            return 0;
        if (o == null) throw new NullPointerException();

        result = this.startTime.compareTo(aCall.startTime);

        if (result == 0)
            result = this.caller.compareTo(aCall.caller);

        return result;
    }

}
