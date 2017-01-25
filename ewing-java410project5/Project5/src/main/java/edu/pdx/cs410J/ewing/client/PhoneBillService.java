package edu.pdx.cs410J.ewing.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.Date;

/**
 * A GWT remote service that returns a dummy Phone Bill
 */
@RemoteServiceRelativePath("ping")
public interface PhoneBillService extends RemoteService {

  AbstractPhoneBill saveNewRecord(String name, PhoneCall newCall);

  AbstractPhoneBill printRecord(String name);

  AbstractPhoneBill searchForRecord(String customer, Date start, Date end);
}
