package edu.pdx.cs410J.ewing;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Scott Ewing on 7/11/2015.
 * This class is used to load a phone bill from a file saved on disk.
 */
public class TextParser implements PhoneBillParser {

    private String filename;

    /**
     * This is the constructor for the class and is used to indicate the filename to parse from
     * @param file where <code>file</code> is the name of the file to load the phone bill.
     */
    public TextParser(String file){
        filename = file;
    }

    /**
     * This method will parse the text file specified by the filename given in the constructor.
     * @return This returns the parsed phone bill or null if empty.
     * @throws ParserException for various errors.
     */
    @Override
    public AbstractPhoneBill parse() throws ParserException {

        if (filename == null) throw new ParserException("Error: No filename found.");

        PhoneBill anotherBill = null;
        try{
            Scanner fileScanner = new Scanner(new File(filename));

            String name = fileScanner.nextLine();
            anotherBill = new PhoneBill(name);

            while (fileScanner.hasNextLine()){
                String [] callString = fileScanner.nextLine().split(" ");
                ArrayList<String> arguments = new ArrayList<>();
                arguments.add(name);
                arguments.addAll(Arrays.asList(callString));

                PhoneCall aCall = new ValidateInformation().parseArguments(arguments);
                anotherBill.addPhoneCall(aCall);

            }
            fileScanner.close();
        } catch (Exception exp) {

            throw new ParserException("Error loading file \"" + filename + "\" Unexpected file format.");
        }

        return anotherBill;
    }
}

