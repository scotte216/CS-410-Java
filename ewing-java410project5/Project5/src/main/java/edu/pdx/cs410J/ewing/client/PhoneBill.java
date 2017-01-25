package edu.pdx.cs410J.ewing.client;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/** This is the Phone Bill class for CS410J Phone Bill Project. Created by Scott Ewing on 7/2/15.
 *
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
    phoneCalls = new ArrayList<>();
  }

  public PhoneBill() {
    name = null;
  }

  /**
   * Equals method to return the result of a comparison between this object and another.
   * @param o the object to compare to this one.
   * @return <code>true</code> if the name and phone calls are equal. <code>false</code> otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PhoneBill phoneBill = (PhoneBill) o;

    if (name != null) {
      if (!name.equals(phoneBill.name)) return false;
    } else {
      if (phoneBill.name != null) return false;
    }
    if (phoneCalls != null) return phoneCalls.equals(phoneBill.phoneCalls);
    else return phoneBill.phoneCalls == null;

  }

  /**
   * Method to return a unique hashCode. Equal objects will have the same hashCode
   * @return unique integer hashcode based on the name and phone calls of this bill.
   */
  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (phoneCalls != null ? phoneCalls.hashCode() : 0);
    return result;
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
   * @param toAdd -- PhoneCall object to be added to this bill
   */
  @Override
  public void addPhoneCall(AbstractPhoneCall toAdd) {

    if (phoneCalls.size() == 0)
    {
      phoneCalls.add(toAdd);
      return;
    }


    int position;
    for (position = 0; position < phoneCalls.size(); ++position)
    {
      if (((PhoneCall) toAdd).compareTo(phoneCalls.get(position)) <= 0)
        break;
    }

    phoneCalls.add(position,toAdd);
    //phoneCalls.sort(null);
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

  /**
   * Method to get all phone calls from within a specified date range
   * @param startSearch start date
   * @param endSearch end date
   * @return returns an array list of the phone calls within this time span.
   */
  @SuppressWarnings("Convert2streamapi")
  public ArrayList getPhoneCalls(Date startSearch,Date endSearch){
    ArrayList<AbstractPhoneCall> results = new ArrayList<>();

    for (AbstractPhoneCall aPhoneCall : phoneCalls) {
      if (aPhoneCall.getStartTime().compareTo(startSearch) >= 0 && aPhoneCall.getEndTime().compareTo(endSearch) <= 0) {
        results.add(aPhoneCall);
      }
    }

    return results;
  }

  /**
   * Method to add phone calls to a bill using an array list of phone call objects.
   * @param callsToAdd ArrayList of phoneCall objects to be added to this phone bill.
   */
  public void addPhoneCalls(ArrayList<AbstractPhoneCall> callsToAdd) {
    this.phoneCalls = callsToAdd;
  }

}
