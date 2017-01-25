package edu.pdx.cs410J.ewing;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Scott Ewing on 7/2/15.
 * This is the Phone Bill class for CS410J Phone Bill Project
 */

public class PhoneBill extends AbstractPhoneBill {
    /**
     * Name of the owner of the phone bill
     */
    private String name;
    /**
     * Array List of phone calls associated with this bill.
     */
    private ArrayList<AbstractPhoneCall> phoneCalls;

    /**
     * Phone Bill constructor for a given string name.
     * @param newName Name of the person who owns this phone bill.
     */
    public PhoneBill(String newName) {
        this.name = newName;
        phoneCalls = new ArrayList<AbstractPhoneCall>();
    }

    /**
     * Getter for the customer's name.
     * @return returns the customer's name
     */
    @Override
    public String getCustomer() {
        return name;
    }

    /**
     * Method to add a PhoneCall object to this phone bill.
     * @param abstractPhoneCall -- PhoneCall object to be added to this bill
     */
    @Override
    public void addPhoneCall(AbstractPhoneCall abstractPhoneCall) {
        phoneCalls.add(abstractPhoneCall);
    }

    /**
     * Getter for the collection of phone calls associated with this bill
     * @return returns the collection of phone calls associated with this bill.
     */
    @Override
    public Collection getPhoneCalls() {
        return phoneCalls;
    }

    /**
     * toString method to add proper formatting for multiple calls in the phoneCalls collection
     * @return Returns a string of phone call information associated with this account.
     */
    public String toString()
    {
        StringBuilder callsString = new StringBuilder();
        for (AbstractPhoneCall callData : phoneCalls)
        {
            callsString.append(callData).append("\n");
        }
        return super.toString() + ":\n" + callsString;

    }
}
