package edu.pdx.cs410J.ewing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This Class is a helper class to validate information such as call numbers and dates. Created by Scott Ewing on 7/19/2015.
 *
 */
public class ValidateInformation {

    /**
     * This is the method used to parse an array list of the arguments required for a new phone bill.
     * @param args The command-line arguments less the optional arguments (optional args specified by -) in an ArrayList
     * @return A properly validated PhoneCall object.
     * @throws PhoneException if there are any problems validating any of the arguments.
     */
    @SuppressWarnings("UnusedAssignment")
    public PhoneCall parseArguments(ArrayList<String> args)  throws PhoneException {

        //Determine validity of phone number data.
        String callerNumberString = args.get(1);
        //Integers may be used in future version
        int callerNumber = checkNumber(callerNumberString);

        String calleeNumberString = args.get(2);
        //Integers may be used in future version
        int calleeNumber = checkNumber(calleeNumberString);

        //Determines validity of the call times and properly formats them.
        String startTimeString = args.get(3) + ' ' + args.get(4) + ' ' + args.get(5);
        Date startDate = checkDate(startTimeString);

        String endTimeString = args.get(6) + ' ' + args.get(7) + ' ' + args.get(8);
        Date endDate = checkDate(endTimeString);

        if (startDate.getTime() > endDate.getTime())
            throw new PhoneException("Error: Call start time must be before call end time.");

        //Creates aCall based on this verified call data and returns it.
        return new PhoneCall(callerNumberString, calleeNumberString, startDate, endDate);
    }

    /**
     * If the -search option is used, the argument list is different, so this method handles the difference.
     * @param args ArrayList of string arguments used in the -search option. These would be Customer name, a start time
     *             and an end time.
     * @return A dummy phone call object that will be used in the search. Dummy values for caller and callee numbers
     * are used.
     * @throws PhoneException if an argument is invalid.
     */
    public PhoneCall parseArgumentsForSearchOption(ArrayList<String> args) throws PhoneException {
        String startTimeString = args.get(1) + ' ' + args.get(2) + ' ' + args.get(3);
        Date startDate = checkDate(startTimeString);

        String endTimeString = args.get(4) + ' ' + args.get(5) + ' ' + args.get(6);
        Date endDate = checkDate(endTimeString);

        if (startDate.getTime() > endDate.getTime()){
            throw new PhoneException("Error: Call start time must be before call end time.");
        }

        return new PhoneCall("111-111-1111","111-111-1111",startDate,endDate);
    }

    /**
     * Method overloading to use strings intead of an ArrayList of strings for parsing valid arguments. used by the
     * servlet class in Project 4.
     * @param callerNumberString Argument that should be be the caller's number
     * @param calleeNumberString Argument that should be the callee's number
     * @param startTimeString Argument for what should be the start time "MM/dd/yyyy hh:mm aa"
     * @param endTimeString Argument for what should be the end time "MM/dd/yyyy hh:mm aa"
     * @return A phone call object based on these arguments
     * @throws PhoneException if an argument is invalid.
     */
    public PhoneCall parseArguments(String callerNumberString, String calleeNumberString, String startTimeString, String endTimeString) throws PhoneException{

        //Determine validity of phone number data.
        //Integers may be used in future version
        int callerNumber = checkNumber(callerNumberString);

        //Integers may be used in future version
        int calleeNumber = checkNumber(calleeNumberString);

        //Determines validity of the call times and properly formats them.

        Date startDate = checkDate(startTimeString);
        Date endDate = checkDate(endTimeString);

        if (startDate.getTime() > endDate.getTime())
            throw new PhoneException("Error: Call start time must be before call end time.");

        //Creates aCall based on this verified call data and returns it.
        return new PhoneCall(callerNumberString, calleeNumberString, startDate, endDate);
    }

    /**
     * This function will validate a date using the Date functionality in Java.
     * @param dateAndTimeString Expected format is "MM/DD/YYYY hh:mm aa"
     * @return Returns a date object with the validated parsed date data.
     */
    private Date checkDate(String dateAndTimeString)  throws PhoneException {

        SimpleDateFormat setFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        Date date = null;
        try {
            setFormat.setLenient(false);
            date = setFormat.parse(dateAndTimeString);
        } catch (ParseException e) {
            throw new PhoneException("Error: bad call time. Call times must be valid dates of " +
                    "the form MM/dd/yyyy hh:mm am/pm.");
        }

        return date;
    }

    /**
     * This function will check the phone numbers.
     * @param numberString Expected format is "nnn-nnn-nnnn" where n is 0-9.
     * @return Returns the phone number in Int format. Currently not used.
     */
    private int checkNumber(String numberString)  throws PhoneException {
        int number = -1;
        int areaCode, first, second;
        char dash1;
        char dash2;

        //Counts the length of the string.
        if (numberString.length() != 12)
            throw new PhoneException("Error: bad phone number. Phone numbers must be of the form nnn-nnn-nnnn.");


        //Tries to extract integers from the number string.
        try {
            areaCode = Integer.valueOf((String) numberString.subSequence(0, 2));
            first = Integer.valueOf((String) numberString.subSequence(4, 6));
            second = Integer.valueOf((String) numberString.subSequence(8, 12));

            number = Integer.valueOf(String.valueOf(areaCode) + String.valueOf(first) + String.valueOf(second));

        } catch (NumberFormatException ex) {
            throw new PhoneException("Error: bad phone number. Phone numbers must be of the " +
                    "form nnn-nnn-nnnn where 'n' is an integer.");
        }
        //Extracts and tests for dashes (-) in the correct positions.
        dash1 = numberString.charAt(3);
        dash2 = numberString.charAt(7);

        if (dash1 != '-' || dash2 != '-' || number < 0)
            throw new PhoneException("Error: Bad phone number. Phone numbers must be of the " +
                    "form nnn-nnn-nnnn where 'n' is an integer. Dashes are not optional.");

        return number;
    }
}
