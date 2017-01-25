package edu.pdx.cs410J.ewing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Scott Ewing on 7/19/2015.
 * This Class is a helper class to validate information such as call numbers and dates.
 */
public class ValidateInformation {

    /**
     * This is the main method for validating the commandline arguments minus any optional flags. Expected that
     * the main method Project2 will have parsed out the -options vs the required arguments.
     * @param args This is the array list of the required command line arguments
     * @return A phone call object created from the arguments.
     * @throws PhoneException if it encounters any issues creating the phone call (such as unexpected or invalid
     * information.
     */
    public PhoneCall parseArguments(ArrayList<String> args)  throws PhoneException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY HH:mm");

        //String name = args.get(0);

        //Determine validity of phone number data.
        String callerNumberString = args.get(1);
        //Integers may be used in future version
        int callerNumber = checkNumber(callerNumberString);

        String calleeNumberString = args.get(2);
        //Integers may be used in future version
        int calleeNumber = checkNumber(calleeNumberString);

        //Determines validity of the call times and properly formats them.
        String startTimeString = args.get(3) + ' ' + args.get(4);
        Date startDate = checkDate(startTimeString);
        String formattedStart = sdf.format(startDate);

        String endTimeString = args.get(5) + ' ' + args.get(6);
        Date endDate = checkDate(endTimeString);
        String formattedEnd = sdf.format(endDate);

        if (startDate.getTime() > endDate.getTime())
            throw new PhoneException("Error: Call start time must be before call end time.");

        //Creates aCall based on this verified call data and returns it.
        return new PhoneCall(callerNumberString, calleeNumberString, formattedStart, formattedEnd);
    }

    /**
     * This function will validate a date using the Date functionality in Java.
     * @param dateAndTimeString Expected format is "MM/DD/YYYY hh:mm aa"
     * @return Returns a date object with the validated parsed date data.
     */
    public Date checkDate(String dateAndTimeString)  throws PhoneException {

        SimpleDateFormat setFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date date = null;
        try {
            setFormat.setLenient(false);
            date = setFormat.parse(dateAndTimeString);
        } catch (ParseException e) {
            throw new PhoneException("Error: bad call time. Call times must be valid dates of " +
                    "the form MM/dd/yyyy HH:mm.");
        }

        return date;
    }

    /**
     * This function will check the phone numbers.
     * @param numberString Expected format is "nnn-nnn-nnnn" where n is 0-9.
     * @return Returns the phone number in Int format. Currently not used.
     */
    public int checkNumber(String numberString)  throws PhoneException {
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
