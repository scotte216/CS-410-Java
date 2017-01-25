package edu.pdx.cs410J.ewing;

import edu.pdx.cs410J.ParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * The main class for the CS410J Phone Bill Project, Summer 2015
 * @author Scott Ewing
 */
public class Project2 {
    static private PhoneBill myBill;
    static final private int NUM_OPTIONS = 4; //-README, -print, -textFile, <filename>
    static final private int EXPECTED_ARGS = 7;
    private static String filename;
    private static ArrayList<String> options;
    private static ArrayList<String> arguments;
    /**
     * This is the main function for the CS410J Phone Bill Project, project 2.
     * @param args Valid optional arguments -print -README
     *             See -README for usage.
     */
    public static void main(String[] args) {
        Boolean print = false;
        PhoneCall aCall = null;
        options = new ArrayList<>();
        arguments = new ArrayList<>();
        filename = null;

        formatArguments(args);
        doReadme();
        validateOptions();


        //If there are no options or arguments, print error message
        if (arguments.size() == 0 && options.size() == 0)
            printErrorMessageAndExit("Missing command line arguments.");

        //If there any arguments, they must match the number of expected arguments.
        if (arguments.size() < EXPECTED_ARGS && 0 < arguments.size()) {
            printErrorMessageAndExit("Missing command line argument(s).");
        }
        if (arguments.size() > EXPECTED_ARGS) {
            printErrorMessageAndExit("Too many command line arguments.");
        }

        //At this point we are assured that we have the expected number of arguments and the
        //-README option was not used.


        if (options.contains("-print")) print = true;


        if (options.contains("-textFile"))
            myBill = loadFile();
        else if (arguments.size() == EXPECTED_ARGS)
            myBill =  new PhoneBill(arguments.get(0));
        else
            printErrorMessageAndExit("Missing command line arguments.");

        if (arguments.size() != 0)
        {
            try {
                aCall = new ValidateInformation().parseArguments(arguments);
            } catch (PhoneException e) {
                printErrorMessageAndExit(e.getMessage());
            }
            myBill.addPhoneCall(aCall);
        }

        if (options.contains("-textFile")) try {
            new TextDumper(filename).dump(myBill);
        } catch (IOException e) {
            printErrorMessageAndExit(e.getMessage());
        }

        //If the -print option has been set, print out myBill
        if (print) {
            System.out.println(myBill);
        }

        System.exit(0);
    }

    /**
     * Method to determine if the -README option has been used and if so, execute and exit.
     */
    private static void doReadme() {
        //If the -README option is used, all it should do is display the readme file, and no other error checking.
        if ( options.contains("-README")) {
            readMe();
            System.exit(0);
        }
    }

    /**
     * Private method to validate the options passed in via the command line.
     */
    private static void validateOptions() {
        //Checks for valid -options
        for (String check : options)
        {
            if (!check.equals("-README") && !check.equals("-print") && !check.equals("-textFile"))
            {
                printErrorMessageAndExit("Invalid command line argument: " + check);
            }
        }
    }

    /**
     * Private method to format the arguments passed in via the command line.
     * @param args where <code>args</code> is the string list of arguments passed in from the command line.
     *            Expected format is: [options] /< args />
     *             Valid options are: -textFile < filename >, -print, -README
     *             Arguments are:
     *             customer, caller Number (nnn-nnn-nnnn), callee Number, start time (mm/dd/yyyy hh:mm), end time
     */
    private static void formatArguments(String[] args) {
        int numOfArgs = args.length;

        //Checks the number of arguments and sets up any optionals
        if (numOfArgs > 0)
        {
            //Looks through the first arguments for any -optional arguments.
            for (int i = 0; i < NUM_OPTIONS; ++i)
            {
                if ( i < numOfArgs && args[i].startsWith("-")) {
                    options.add(args[i]);

                    //If the -textFile option is used, extract the filename as well.
                    //Checks the corner case of a misplaced -README following immediately after -textFile.
                    if (args[i].equals("-textFile") && numOfArgs > i+1 && !args[i+1].equals("-README") )
                    {
                        filename = args[++i];
                    }
                }
                else
                    i = NUM_OPTIONS; // to break out of the -options for-loop
            }

            int fileOffset = 0;
            if (filename != null) fileOffset = 1;

            //Extracts the rest of the arguments that aren't -options. If the optional -textFile was used
            // offset by 1 to account for the filename in the list of command line arguments.
            arguments.addAll(Arrays.asList(args).subList(options.size() + fileOffset, numOfArgs));

        }
        else
            printErrorMessageAndExit("Missing command line argument(s).");
    }

    /**
     * Private method to attempt loading of a file and comparing to the supplied command line arguments.
     * @return the loaded and verified phone bill.
     */
    private static PhoneBill loadFile() {
        boolean fileExists = new File(filename).isFile();
        PhoneBill loadedBill = null;

        //If they try to load a file that doesn't exist and they are not supplying any new call information error.
        if (!fileExists && arguments.size() == 0)
            printErrorMessageAndExit("Error: File '" + filename + "' does not exist.");

        if (!fileExists) {
            loadedBill = new PhoneBill(arguments.get(0));
        } else {
            try {
                loadedBill = (PhoneBill) new TextParser(filename).parse();

            } catch (ParserException e) {
                printErrorMessageAndExit(e.getMessage());
            }

            //Ensure the bill loaded correctly.
            if (loadedBill == null )
                printErrorMessageAndExit("Error: File did not load correctly.");

            //If the loaded bill doesn't match the name for the new bill, exit with error
            if (arguments.size() != 0 && !loadedBill.getCustomer().equals(arguments.get(0)))
                printErrorMessageAndExit("Error: File '" + filename + "' contains bill for " + loadedBill.getCustomer() + " not for " + arguments.get(0));

            //If we reach this point, our loaded bill is accurate.
        }
        return loadedBill;
    }

    /**
     * The README file for this program, accessed from the commandline option -README.
     */
    private static void readMe() {
        System.out.println("Project2 README: This program will take a person and a call record and create a " +
                "phone bill for them.\n" +
                "Optionally add a filename to load and save information for a record.\n" +
                "This project is for 410 Advanced Programming -- Java at PSU for Summer 2015. \n" +
                "This program was written by Scott Ewing.\n\n" +
                "This program will take a user name, create a phone bill object for them then add phone call\n" +
                "data to this phone bill. All this information is passed into the program via the\n" +
                "command line or a file to load/save. The only way to see this information is via the -print function. \n" +
                "This program will create the framework for future projects in this class. \n" +
                "See below for usage.\n\n" +
                "usage: java edu.pdx.cs410J.ewing.Project2 [options] <args>\n\n" +
                "args are (in this order):\n" +
                "   customer           Person whose phone bill we’re modeling\n" +
                "   callerNumber       Phone number of caller\n" +
                "   calleeNumber       Phone number of person who was called\n" +
                "   startTime          Date and time call began (24-hour time)\n" +
                "   endTime            Date and time call ended (24-hour time)\n\n" +
                "[options] are (options may appear in any order):\n" +
                "   -textFile file     Where to read/write the phone bill\n" +
                "   -print             Prints a description of the new phone call\n" +
                "   -README            Prints this README for this project\n\n" +
                "For example: java Project2 -print \"Peter Griffin\" 555-867-5309 503-555-1212 1/20/2015 " +
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