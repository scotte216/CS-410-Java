package edu.pdx.cs410J.ewing;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Scott Ewing on 7/11/2015.
 * This class is used for saving phone bills to disk
 */
public class TextDumper implements PhoneBillDumper {
    private String filename;

    /**
     * Constructor for the TextDumper
     * @param file where <code>file</code> is the name of the file to save the phone bill.
     */
    public TextDumper(String file){
        filename = file;
    }

    /**
     * This method will take a phonebill and add it to the file determined in the constructor
     * @param abstractPhoneBill A phone bill to add to the text file
     * @throws IOException
     */
    @Override
    public void dump(AbstractPhoneBill abstractPhoneBill) throws IOException {
        if (filename == null) throw new IOException("IO ERROR: No filename found.");

        PrintWriter pw = new PrintWriter(filename);

        pw.println(abstractPhoneBill.getCustomer());
        ArrayList<PhoneCall> calls = (ArrayList<PhoneCall>) abstractPhoneBill.getPhoneCalls();
        for (PhoneCall aCall : calls) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            pw.println(aCall.getCaller() + ' ' + aCall.getCallee() + ' ' + aCall.getStartTimeString() + ' ' +
                    aCall.getEndTimeString());
        }

        pw.close();

    }

}
