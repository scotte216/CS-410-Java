package edu.pdx.cs410J.ewing;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 *  This class handles formatting and printing/saving of a PhoneBill. Created by Scott Ewing on 7/19/2015.
 */
public class PrettyPrinter implements PhoneBillDumper {
    private final String filename;

    /**
     * Constructor for the PrettyPrinter. For Project - it defaults to printing to standard out rather than an optional
     * file.
     */
    public PrettyPrinter(){
        filename = "-";
    }


    /**
     * This function will take a PhoneBill object and format the phone calls readably. It will optionally print to
     * screen or write to file.
     * @param bill The PhoneBill object to be formatted.
     * @throws IOException if a filename has been properly specified via the default constructor or if the file already exists.
     */
    @Override
    public void dump(AbstractPhoneBill bill) throws IOException {
        if (filename == null) throw new IOException("IO ERROR: No filename found.");

        String prettyResult = formatCalls(bill);
        //print to screen
        if (Objects.equals(filename, "-")) {

            System.out.println(prettyResult);
        }
        //Print to file
        else {
            if ( new File(filename).isFile()) throw new IOException("Error: Filename specified by -pretty already exists.");

            PrintWriter out = new PrintWriter(filename);
            out.print(prettyResult);
            out.close();
        }
    }

    /**
     * This is a helper method to do the actual formatting of the PhoneBill.
     * @param toFormat This is the PhoneBill object to be formatted
     * @return a string of the properly formatted phone bill. Used by <code>dump()</code>
     */
    public String formatCalls(AbstractPhoneBill toFormat){
        ArrayList<PhoneCall> phoneCalls = (ArrayList<PhoneCall>) toFormat.getPhoneCalls();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm aa");

        StringBuilder display = new StringBuilder();
        display.append("\nDetailed phone bill for: ").append(toFormat.getCustomer()).append("\n\n");
        display.append("    Call from          Call to            Start time               End time           Duration\n");
        display.append("    ------------    ------------    ---------------------   ---------------------    ----------\n");

        int counter = 0;
        for (PhoneCall aPhoneCall : phoneCalls) {
            Date start = aPhoneCall.getStartTime();
            Date end = aPhoneCall.getEndTime();

            Long duration = end.getTime() - start.getTime();
            duration = Math.abs(duration);

            int minutes = (int) (duration / (1000 * 60) % 60);
            int hours = (int) (duration / (1000 * 60 * 60) % 24);
            int days = (int) (duration / (1000 * 60 * 60 * 24));

            display.append(String.format("%2d", ++counter)).append(") ").append(aPhoneCall.getCaller()).append("    ").append(aPhoneCall.getCallee()).append("    ");


            display.append(sdf.format(start)).append("   ").append(sdf.format(end)).append("    ");

            if (days != 0) display.append(days).append("d ");
            if (hours != 0) display.append(hours).append("hr ");
            if (minutes <= 1)
                display.append(String.format("%02d", minutes)).append(" min\n");
            else
                display.append(String.format("%02d", minutes)).append(" mins\n");
        }
        return display.toString();
    }

}
