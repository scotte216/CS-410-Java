package edu.pdx.cs410J.ewing;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages
{
    /**
     * Missing phone call method. Used by the servlet.
     * @param customer Name of the customer received by the server
     * @return String message.
     */
    public static String missingPhoneCall(String customer)
    {
        return String.format("Phone bill for \"%s\" does not exist on the server. This field is case sensitive.",customer);
    }

    /**
     * Method for returning the number of phone bills.
     * @param count number of bills
     * @return formatted message.
     */
    public static String getMappingCount( int count )
    {
        return String.format( "Server contains %d phone bill(s)", count );
    }

    /**
     * Method to output a customer and their bill.
     * @param customer customer name
     * @param phoneBill string of the total phone bill.
     * @return formatted string.
     */
    public static String formatCustomerAndBill(String customer, String phoneBill)
    {
        return String.format("  %s -> %s", customer, phoneBill);
    }

    /**
     * Method for displaying missing expected parameter
     * @param parameterName name of the missing parameter.
     * @return formatted string message.
     */
    public static String missingRequiredParameter( String parameterName )
    {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

    /**
     * Method for returning a bad date format message
     * @return Message to be displayed.
     */
    @SuppressWarnings("SameReturnValue")
    public static String badDateFormat() {
        return "Bad date format. Dates must be of the form MM/dd/yyyy hh:mm am/pm";
    }

}
