package edu.pdx.cs410J.ewing.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.ewing.client.PhoneBill;
import edu.pdx.cs410J.ewing.client.PhoneBillService;
import edu.pdx.cs410J.ewing.client.PhoneCall;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The server-side implementation of the Phone Bill service
 */


public class PhoneBillServiceImpl extends RemoteServiceServlet implements PhoneBillService
{
    private Map<String, PhoneBill> myBills = new HashMap<>();

    public PhoneBillServiceImpl(){

    }

  @Override
  protected void doUnexpectedFailure(Throwable unhandled) {
    unhandled.printStackTrace(System.err);
    super.doUnexpectedFailure(unhandled);
  }

  @Override
  public AbstractPhoneBill saveNewRecord(String name, PhoneCall newCall) {

      if (myBills.containsKey(name))
            myBills.get(name).addPhoneCall(newCall);
      else {
            PhoneBill newBill = new PhoneBill(name);
            newBill.addPhoneCall(newCall);
            myBills.put(name,newBill);
        }
    return myBills.get(name);
  }

    @Override
    public AbstractPhoneBill printRecord(String name) {
        if (myBills.containsKey(name))
            return myBills.get(name);

        return null;
    }

    @Override
    public AbstractPhoneBill searchForRecord(String customer, Date start, Date end) {
        PhoneBill results = new PhoneBill("Search results for " + customer);

        if (myBills.containsKey(customer))
        {
            ArrayList<AbstractPhoneCall> calls = myBills.get(customer).getPhoneCalls(start,end);
            results.addPhoneCalls(calls);
            return results;
        }
        else{
            return null;
        }
    }
}
