package edu.pdx.cs410J.ewing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The main class for the CS410J Phone Bill Project, Summer 2015
 * @author Scott Ewing
 */
public class Project1 {
    static private PhoneBill myBill;

    /**
     * This is the main function for the CS410J Phone Bill Project, project 1.
     * @param args Valid optional arguments -print -README
     *             See -README for usage.
     */
    public static void main(String[] args) {
        Boolean print = false;
        int numOfArgs = args.length;
        PhoneCall aCall = null;

        //If the -README option is used, all it should do is display the readme file, and no other error checking.
        //For this project there are only 2 possible options, so it will only check if -README is the first or
        //the second option.
        if ((numOfArgs > 0 && args[0].equals("-README")) || (numOfArgs > 1 && args[0].startsWith("-") && args[1].equals("-README"))) {
            readMe();
            System.exit(0);
        }


        //Determines if the number of arguments is valid.
        if (numOfArgs != 7 && numOfArgs != 8) {
            printErrorMessageAndExit("Missing or too many command line argument(s).");
        }

        //If at this point we have either 7, 8 arguments
        //If we have 7 arguments, then they all must be our caller data
        if (numOfArgs == 7) {
            //Parse caller data
            myBill = new PhoneBill(args[0]);
            aCall = parseArguments(args);
            myBill.addPhoneCall(aCall);
        }
        //With 8 args, the first argument must be -print
        else if (numOfArgs == 8 && args[0].equals("-print"))
        {
            print = true;
            String[] callerData = new String[7];
            System.arraycopy(args, 1, callerData, 0, 7);

            myBill = new PhoneBill(callerData[0]);
            aCall = parseArguments(callerData);
            myBill.addPhoneCall(aCall);
        }
        //Something's gone wrong. Invalid arguments.
        else
        {
            printErrorMessageAndExit("Invalid command line arguments.");
        }
        //If the -print option has been set, print out myBill
        if (print) {
            System.out.println(myBill);
        }


        System.exit(0);
    }

    /**
     * This function will parse the command line arguments for a call record.
     * @param args The command line arguments. Expects 7 strings.
     * @return Returns a PhoneCall object constructed with the validated call record data.
     */
    private static PhoneCall parseArguments(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY hh:mm");

        String name = args[0];

        //Determine validity of phone number data.
        String callerNumberString = args[1];
        //Integers may be used in future version
        int callerNumber = checkNumber(callerNumberString);

        String calleeNumberString = args[2];
        //Integers may be used in future version
        int calleeNumber = checkNumber(calleeNumberString);

        //Determines validity of the call times and properly formats them.
        String startTimeString = args[3] + ' ' + args[4];
        Date startDate = checkDate(startTimeString);
        String formattedStartTime = sdf.format(startDate);

        String endTimeString = args[5] + ' ' + args[6];
        Date endDate = checkDate(endTimeString);
        String formattedEndTime = sdf.format(endDate);

        //Creates aCall based on this verified call data and returns it.
        PhoneCall aCall = new PhoneCall(callerNumberString, calleeNumberString, formattedStartTime, formattedEndTime);

        return aCall;
    }

    /**
     * This function will validate a date using the Date functionality in Java.
     * @param dateAndTimeString Expected format is "MM/DD/YYYY hh:mm"
     * @return Returns a date object with the validated parsed date data.
     */
    private static Date checkDate(String dateAndTimeString) {

        SimpleDateFormat setFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date date = null;
        try {
            setFormat.setLenient(false);
            date = setFormat.parse(dateAndTimeString);
        } catch (ParseException e) {
            printErrorMessageAndExit("Error: bad call time. Call times must be valid dates of " +
                    "the form MM/DD/YYYY hh:mm.");
        }

        return date;
    }

    /**
     * This function will check the phone numbers.
     * @param numberString Expected format is "nnn-nnn-nnnn" where n is 0-9.
     * @return Returns the phone number in Int format. Currently not used.
     */
    private static int checkNumber(String numberString) {
        int number = -1;
        int areaCode, first, second;
        char dash1 = ' ';
        char dash2 = ' ';

        //Counts the length of the string.
        if (numberString.length() != 12)
            printErrorMessageAndExit("Error: bad phone number. Phone numbers must be of the form nnn-nnn-nnnn.");


        //Tries to extract integers from the number string.
        try {
            areaCode = Integer.valueOf((String) numberString.subSequence(0, 2));
            first = Integer.valueOf((String) numberString.subSequence(4, 6));
            second = Integer.valueOf((String) numberString.subSequence(8, 12));

            number = Integer.valueOf(String.valueOf(areaCode) + String.valueOf(first) + String.valueOf(second));

        } catch (NumberFormatException ex) {
            printErrorMessageAndExit("Error: bad phone number. Phone numbers must be of the " +
                    "form nnn-nnn-nnnn where 'n' is an integer.");
        }
        //Extracts and tests for dashes (-) in the correct positions.
        dash1 = numberString.charAt(3);
        dash2 = numberString.charAt(7);

        if (dash1 != '-' || dash2 != '-' || number < 0)
            printErrorMessageAndExit("Error: Bad phone number. Phone numbers must be of the " +
                    "form nnn-nnn-nnnn where 'n' is an integer. Dashes are not optional.");

        return number;
    }

    /**
     * The README file for this program, accessed from the commandline option -README.
     */
    private static void readMe() {
        System.out.println("Project1 README: This program will take a person and a call record and create a " +
                "phone bill for them.\n" +
                "This project is for 410 Advanced Programming -- Java at PSU for Summer 2015. \n" +
                "This program was written by Scott Ewing.\n" +
                "This program will take a user name, create a phone bill object for them then add phone call\n" +
                "data to this phone bill. Currently, all this information is passed into the program via the\n" +
                "command line. The only way to see this information is via the -print function. \n" +
                "This program will create the framework for future projects in this class. \n" +
                "See below for usage.\n\n" +
                "usage: java edu.pdx.cs410J.ewing.Project1 [options] <args>\n\n" +
                "args are (in this order):\n" +
                "\tcustomer Person whose phone bill we’re modeling\n" +
                "\tcallerNumber Phone number of caller\n" +
                "\tcalleeNumber Phone number of person who was called\n" +
                "\tstartTime Date and time call began (24-hour time)\n" +
                "\tendTime Date and time call ended (24-hour time)\n" +
                "[options] are (options may appear in any order):\n" +
                "\t-print Prints a description of the new phone call\n" +
                "\t-README Prints this README for this project\n\n" +
                "For example: java Project1 -print \"Peter Griffin\" 555-867-5309 503-555-1212 1/20/2015 " +
                "5:15 10/20/2015 5:25");
    }

    /**
     * This function generates an error message and exits with a value of 1 to indicate a problem.
     * @param message The error message to be displayed.
     */
    private static void printErrorMessageAndExit(String message) {
        System.err.println(message);
        System.exit(1);
    }

}