package edu.pdx.cs410J.ewing;

import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST. This is the main 'client' in Project 4 for CS 410 Advanced Java
 * Summer 2015 -- Scott Ewing
 */
public class Project4 {

    private static String hostName;
    private static String portString;
    static final private int NUM_OPTIONS = 7; //-README, -print, -search, -host <name>, -port <number>
    static final private int EXPECTED_ARGS_FULL = 9;
    static final private int EXPECTED_ARGS_SEARCH = 7;

    private static ArrayList<String> options;
    private static ArrayList<String> arguments;
    private static int port;

    public static void main(String... args) {
        PhoneBill myBill = null;
        hostName = null;
        portString = null;
        port = -1;
        PhoneCall aCall;
        options = new ArrayList<>();
        arguments = new ArrayList<>();

        formatArguments(args);
        doReadme();
        validateOptions();


        //If there are no options or arguments, print error message
        if (arguments.size() == 0 && options.size() == 0)
            printErrorMessageAndExit("Missing command line arguments. Use -README for usage.");

        //If there any arguments, they must match the number of expected arguments.
        if (options.contains("-search")){
            if ( arguments.size() < EXPECTED_ARGS_SEARCH && 0 < arguments.size()) {
                printErrorMessageAndExit("Missing command line argument(s) for the -search option. Use -README for usage.");
            }
            if (arguments.size() > EXPECTED_ARGS_SEARCH) {
                printErrorMessageAndExit("Too many command line arguments for the -search option. Use -README for usage.");
            }
        }else{
            if ( arguments.size() < EXPECTED_ARGS_FULL && 0 < arguments.size()) {
                printErrorMessageAndExit("Missing command line argument(s). Use -README for usage.");
            }
            if (arguments.size() > EXPECTED_ARGS_FULL) {
                printErrorMessageAndExit("Too many command line arguments. Use -README for usage.");
            }
        }

        if ((options.contains("-search") && arguments.size() != EXPECTED_ARGS_SEARCH) ||
                !options.contains("-search") && arguments.size() != EXPECTED_ARGS_FULL){
            printErrorMessageAndExit("Missing command line arguments. See -README for usage.");
        }

        aCall = null;
        if (arguments.size() == EXPECTED_ARGS_FULL)
        {
            myBill = new PhoneBill(arguments.get(0));
            try{
                aCall = new ValidateInformation().parseArguments(arguments);
            } catch (PhoneException ex) {
                printErrorMessageAndExit(ex.getMessage());
            }
        }
        else if (arguments.size() == EXPECTED_ARGS_SEARCH){
            myBill = new PhoneBill(arguments.get(0));
            try {
                aCall = new ValidateInformation().parseArgumentsForSearchOption(arguments);
            } catch (PhoneException ex){
                printErrorMessageAndExit(ex.getMessage());
            }
        }
        else{
            printErrorMessageAndExit("Missing command line arguments. See -README for usage.");
        }

        myBill.addPhoneCall(aCall);


        HttpRequestHelper.Response response = null;
        PhoneBillRestClient client;
        if (hostName != null && port !=-1){
            client = new PhoneBillRestClient(hostName, port);


            try {

                if (options.contains("-search")) {
                    response = client.searchCalls(myBill);
                } else {
                    response = client.addCall(myBill);
                }

                checkResponseCode( HttpURLConnection.HTTP_OK, response);

            } catch ( IOException ex ) {
                printErrorMessageAndExit("Error while contacting server: " + ex.getMessage());
            }
        }


        //If the -print option has been set, print out myBill
        if (options.contains("-print")) {
            if (response != null){
                System.out.println(response.getContent());
            }
            else
            {
                //System.out.println("This shouldn't happen.");
                System.out.println(myBill);
            }
        } else if (options.contains("-search") && response != null) {
            System.out.println(response.getContent());
        }

        System.out.println("Execution complete.");
        System.exit(0);
    }

    /**
     * Makes sure that the give response has the expected HTTP status code
     * @param code The expected status code
     * @param response The response from the server
     */
    @SuppressWarnings("SameParameterValue")
    private static void checkResponseCode( int code, HttpRequestHelper.Response response )
    {
        if (response == null)
        {
            printErrorMessageAndExit("Error: no response from server.");
        }


        else if (response.getCode() != code) {
            printErrorMessageAndExit(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                                response.getCode(), response.getContent()));
        }
    }

    /**
     * Private method to format the arguments passed in via the command line.
     * @param args where <code>args</code> is the string list of arguments passed in from the command line.
     *            Expected format is: [options] /< args />
     *             Valid options are: -textFile < filename >, -print, -README
     *             Arguments are:
     *             customer, caller Number (nnn-nnn-nnnn), callee Number, start time (mm/dd/yyyy hh:mm aa), end time
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

                    //If the -host option is used, extract the host name as well.
                    //Checks the corner case of a misplaced -README following immediately after -host.
                    if (args[i].equals("-host") && numOfArgs > i+1 && !args[i+1].equals("-README") )
                    {
                        hostName = args[++i];
                    }

                    //Checks if the -port option is used. If so, the next argument should the port number.
                    //Checks the corner case of a misplaced -README following immediately after -port.
                    if (args[i].equals("-port") && numOfArgs > i+1 && !args[i+1].equals("-README"))
                    {
                        portString = args[++i];
                        try{
                            port = Integer.parseInt(portString);
                        } catch (NumberFormatException ex){
                            printErrorMessageAndExit("Invalid port number: " + portString + ". Use -README for usage.");
                        }
                    }
                }
                else
                    i = NUM_OPTIONS; // to break out of the -options for-loop
            }



            //If you have a port or a host you must have the other.
            if ((hostName != null && portString == null) || (hostName == null && portString !=null))
            {
                printErrorMessageAndExit("Must have both a hostname and port number. Use -README for usage.");
            }

            int argumentOffset = 0;
            if (hostName != null) ++argumentOffset;
            if (portString != null) ++argumentOffset;

            //Extracts the rest of the arguments that aren't -options. If the optional -textFile and/or -pretty was used
            // offset to account for the filename in the list of command line arguments.
            arguments.addAll(Arrays.asList(args).subList(options.size() + argumentOffset, numOfArgs));

        }
        else
            printErrorMessageAndExit("Missing command line argument(s).  Use -README for usage.");
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
    @SuppressWarnings("Convert2streamapi")
    private static void validateOptions() {
        //Checks for valid -options
        for (String check : options)
        {
            if (!check.equals("-README") && !check.equals("-print") && !check.equals("-host") && !check.equals("-port")
                    && !check.equals("-search"))
            {
                printErrorMessageAndExit("Invalid command line argument: " + check);
            }
        }

        if (options.contains("-search") && !options.contains("-host")){
            printErrorMessageAndExit("The -search option is only relevant if a host and port are specified. See -README for usage.");
        }
    }


    /**
     * The README file for this program, accessed from the commandline option -README.
     */
    private static void readMe() {
        System.out.println("Project4 README: This project will take the phone bill system online.\n" +
                "Best viewed with at least a screen of 96 characters wide.\n\n" +
                "This project is for 410 Advanced Programming -- Java at PSU for Summer 2015. \n" +
                "This program was written by Scott Ewing.\n\n" +
                "This program will take a user name, create a phone bill object for them then add phone call\n" +
                "data to this phone bill on the remote server. All this information is passed into the program via the\n" +
                "command line. The only way to see this information is via the -print function. \n" +
                "See below for usage.\n\n" +
                "usage: java edu.pdx.cs410J.ewing.Project4 [options] <args>\n\n" +
                "args are (in this order):\n" +
                "    customer             Person whose phone bill we're modeling\n" +
                "    callerNumber         Phone number of caller\n" +
                "    calleeNumber         Phone number of person who was called\n" +
                "    startTime            Date and time call began (12-hour time)\n" +
                "    endTime              Date and time call ended (12-hour time)\n\n" +
                "[options] are (options may appear in any order):\n" +
                "    -host name           Optional remote server host name where data is\n" +
                "                         saved or searched.\n" +
                "    -port number         Port number for the remote server.\n" +
                "    -search              Searches a specified customer name for phone calls that\n" +
                "                         occured between the start and end time specified.\n" +
                "    -print               Prints a description of the new phone call\n" +
                "    -README              Prints this README for this project\n\n" +
                "Example search: java Project3 -host localhost -port 8080 -search \"Peter Griffin\" 5:15 am 10/20/2015 5:25 am\n"

        );
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