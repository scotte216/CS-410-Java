package edu.pdx.cs410J.ewing.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.Date;

/**
 * The client-side interface to the ping service
 */
public interface PhoneBillServiceAsync {

  void printRecord(String name, AsyncCallback<AbstractPhoneBill> async);

  void saveNewRecord(String name, PhoneCall newCall, AsyncCallback<AbstractPhoneBill> async);

  void searchForRecord(String customer, Date start, Date end, AsyncCallback<AbstractPhoneBill> async);
}
