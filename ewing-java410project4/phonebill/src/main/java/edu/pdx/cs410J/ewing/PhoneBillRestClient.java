package edu.pdx.cs410J.ewing;

import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send key/value pairs.
 */
public class PhoneBillRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "phonebill";
    private static final String SERVLET = "calls";

    private final String url;


    /**
     * Creates a client to the Phone Bil REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public PhoneBillRestClient( String hostName, int port )
    {
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
    }

    /**
     * Method to send a GET to the server for searching for phone calls.
     * @param toSearch Pass a phoneBill object for searching the server. This phone bill needs the name of the
     *                 customer on the bill and 1 dummy phone call to specify the search range. The numbers of the
     *                 caller and callee can be anything and the start and end times of the call will specify
     *                 the search range.
     *
     * @return Returns the response from the server.
     * @throws IOException Any GET method can throw an IOException if the GET operation fails.
     */
    public Response searchCalls(PhoneBill toSearch) throws IOException{
        PhoneCall call = ((ArrayList<PhoneCall>) toSearch.getPhoneCalls()).get(0);

        SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        String startTime = sf.format(call.getStartTime());
        String endTime = sf.format(call.getEndTime());

        return get(this.url,"customer",toSearch.getCustomer(),"startTime",startTime,"endTime",endTime);
    }

    /**
     * Method to add a call to the remote server.
     * @param toAdd A phone bill to add to the server.
     * @return Response from the server.
     * @throws IOException Any GET method can throw an IOException if the GET operation fails.
     */
    public Response addCall(PhoneBill toAdd) throws IOException
    {
        ArrayList<PhoneCall> calls = (ArrayList<PhoneCall>) toAdd.getPhoneCalls();
        String caller = calls.get(0).getCaller();
        String callee = calls.get(0).getCallee();

        SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        String startTime = sf.format(calls.get(0).getStartTime());
        String endTime = sf.format(calls.get(0).getEndTime());

        return post(this.url, "customer", toAdd.getCustomer(), "caller", caller, "callee", callee,
                "startTime", startTime, "endTime", endTime);
    }

}
