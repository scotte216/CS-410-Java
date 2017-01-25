package edu.pdx.cs410J.ewing;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>PhoneBill</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple key/value pairs.
 */
public class PhoneBillServlet extends HttpServlet
{
    /**
     * The map of Customer Names to PhoneBills held on the server.
     */
    private final Map<String, PhoneBill> data = new HashMap<>();

    /**
     * Constructor for the phone bill servlet. This constructor is used to pre-populate the servlet with calls.
     * Used primarily for testing purposes.
     * @throws ParseException if there is a parsing exception
     */
    public PhoneBillServlet() throws ParseException {
/*
        SimpleDateFormat sf = new SimpleDateFormat("MM/DD/yyyy hh:mm aa");
        Date start = sf.parse("07/26/2015 1:35 pm");
        Date end = sf.parse("07/26/2015 1:39 pm");
        PhoneBill aBill = new PhoneBill("Bob");
        aBill.addPhoneCall(new PhoneCall("123-456-7890","234-567-8901",start,end));
        start = sf.parse("07/22/2015 5:30 pm");
        end = sf.parse("07/22/2015 5:45 pm");
        aBill.addPhoneCall(new PhoneCall("123-456-7890","333-655-3446",start,end));
        data.put(aBill.getCustomer(), aBill);
        aBill = new PhoneBill("Jane");
        start = sf.parse("07/25/2015 9:43 am");
        end = sf.parse("07/25/2015 10:02 am");
        aBill.addPhoneCall(new PhoneCall("234-567-8901","123-456-7890",start, end));
        data.put(aBill.getCustomer(),aBill);
*/
    }

    /**
     * Determines what actions should be taken when an HTTP GET request reaches the servlet.
     * @param request The HTTP request received.
     * @param response The response the servlet will return
     * @throws ServletException In case of errors
     * @throws IOException If there are IO errors.
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String customer = getParameter( "customer", request );
        String startTime = getParameter("startTime",request);
        String endTime = getParameter("endTime",request);

        if (customer != null) {
            if (startTime == null && endTime == null) {
                writeValue(customer, response);
            }
            else if (startTime == null) {
                missingRequiredParameter(response, "startTime");

            } else if( endTime == null) {
                missingRequiredParameter(response, "endTime");

            } else {
                try {
                    searchBill(customer, startTime, endTime, response);
                } catch (ParseException e) {
                    //PrintWriter pw = response.getWriter();
                    //pw.print(Messages.badDateFormat());
                    response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, Messages.badDateFormat());
                }
            }
        }

        else {
            writeAllMappings(response);
        }
    }


    /**
     * Method to determine what the servlet does with a POST request.
     * @param request The details of the HTTP request
     * @param response The response from the servlet
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String key = getParameter( "customer", request );
        if (key == null)
        {
            missingRequiredParameter( response, "customer" );
            return;
        }

        String startTime = getParameter("startTime",request);
        if ( startTime == null )
        {
            missingRequiredParameter(response, "startTime");
            return;
        }

        String endTime = getParameter("endTime",request);
        if ( endTime == null )
        {
            missingRequiredParameter(response, "endTime");
            return;
        }

        String caller = getParameter( "caller", request );


        if ( caller == null)
        {
            missingRequiredParameter( response, "caller" );
            return;
        }

        String callee = getParameter("callee",request);
        if ( callee == null)
        {
            missingRequiredParameter(response, "callee");
            return;
        }

        PhoneCall aCall;
        try {
            aCall = new ValidateInformation().parseArguments(caller,callee,startTime,endTime);
        } catch (PhoneException e) {
            validationError(response, e.getMessage());
            return;
        }

        PhoneBill aBill = this.data.get(key);
        //If there isn't a record, create one
        if (aBill == null)
        {
            aBill = new PhoneBill(key);
            aBill.addPhoneCall(aCall);
            data.put(key,aBill);
        }
        else
        {
            data.get(key).addPhoneCall(aCall);
        }


        PrintWriter pw = response.getWriter();
        pw.println("Call added to bill " + key );
        pw.println(aCall);
        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK);
    }

    /**
     * If there is a validation error along the way this method is used to communicate back to the requesting object.
     * @param response The HTTP Response from this server.
     * @param message The message passed into this method to be relayed to the requesting server.
     * @throws IOException in case of IO errors.
     */
    private void validationError(HttpServletResponse response, String message) throws IOException{

        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }


    /**
     * Writes an error message about a missing parameter to the HTTP response.
     * @param response The response to be sent back to the requester
     * @param parameterName Name of the missing parameter.
     * @throws IOException
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
        throws IOException
    {
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, Messages.missingRequiredParameter(parameterName));
    }

    /**
     * Writes the value of the given customer to the HTTP response. This is the PrettyPrint details of the phone call for customer.
     * @param customer Name of the customer
     * @param response Response to the requester
     * @throws IOException
     */
    private void writeValue( String customer, HttpServletResponse response ) throws IOException
    {
        PhoneBill value = this.data.get(customer);
        PrintWriter pw = response.getWriter();
        if (value == null)
        {
            missingPhoneCall(response, customer);
            return;

        } else {
            PrettyPrinter printer = new PrettyPrinter();
            pw.println(Messages.formatCustomerAndBill(customer, printer.formatCalls(value)));
        }
        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * Message and response to be sent if there is a missing phone call that is requested in a search.
     * @param response Response sent to the requester
     * @param customer Name of the missing customer
     * @throws IOException
     */
    private void missingPhoneCall(HttpServletResponse response, String customer) throws IOException{
        PrintWriter pw = response.getWriter();
        pw.println(Messages.missingPhoneCall(customer));
        pw.flush();

        response.setStatus ( HttpServletResponse.SC_PRECONDITION_FAILED);

    }

    /**
     * Method used to search a specific customer's bill for calls in a given time frame.
     * @param customer Name of the customer bill to search
     * @param startTime Start time of the search for calls
     * @param endTime end time of the search window.
     * @param response
     * @throws IOException
     * @throws ParseException
     */
    private void searchBill(String customer, String startTime, String endTime, HttpServletResponse response) throws IOException, ParseException {
        PhoneBill aBill = this.data.get(customer);
        PrintWriter pw = response.getWriter();

        if (aBill == null)
        {
            pw.println(Messages.missingPhoneCall(customer));
            pw.flush();
            response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
            return;
        }
        else
        {
            PrettyPrinter printer = new PrettyPrinter();
            SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
            Date start = sf.parse(startTime);
            Date end = sf.parse(endTime);

            PhoneBill results = new PhoneBill("Search Result for " + aBill.getCustomer());
            results.addPhoneCalls(aBill.getPhoneCalls(start, end));

            pw.println("\nDisplaying search results for the following times:");
            pw.println(  "--------------------------------------------------");
            pw.println("Start time: " + sf.format(start));
            pw.println("End time  : " + sf.format(end));
            if (results.hasCalls()) {
                pw.println(printer.formatCalls(results));
            }
            else{
                pw.println("\n\nNo matches found in this time range.");
            }
        }

        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Method to display all of the customers and bills.
     * @param response The response to the requester. This is the prettyprint of all the bills in the servlet.
     * @throws IOException
     */
    private void writeAllMappings( HttpServletResponse response ) throws IOException
    {
        PrintWriter pw = response.getWriter();
        PrettyPrinter printer = new PrettyPrinter();
        pw.println(Messages.getMappingCount( data.size() ));

        for (Map.Entry<String, PhoneBill> entry : this.data.entrySet()) {
            pw.println(Messages.formatCustomerAndBill(entry.getKey(), printer.formatCalls(entry.getValue())));
        }

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * Returns the name of the parameters given in the request. used for error checking.
     * @param name Name of the parameter being checked
     * @param request The HTTP request object
     * @return the string value fo the extracted parameter.
     */
    private String getParameter(String name, HttpServletRequest request) {
      String value = request.getParameter(name);
      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }
    }

}
