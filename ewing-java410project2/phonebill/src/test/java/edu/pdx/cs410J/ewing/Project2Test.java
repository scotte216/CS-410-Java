package edu.pdx.cs410J.ewing;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.ParserException;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the functionality in the {@link Project2} main class.
 */
public class Project2Test extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project2} with the given arguments.
     */
    private MainMethodResult invokeMain(String ... args) {
        return invokeMain( Project2.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  public void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertEquals(new Integer(1), result.getExitCode());
    assertTrue(result.getErr().contains( "Missing command line argument(s)." ));
  }

    @Test
    public void invokeMainWith1ArgNotReadme()
    {
        MainMethodResult result = invokeMain("-print");
        //System.out.println("---invokeMainWith1ArgNotReadMe");
        //System.out.println(result.getOut());
        assertEquals(new Integer(1), result.getExitCode());
    }


    @Test
    public void invokeMainWith5Args()
    {
        //This should exit with one because 5 args isn't valid
        MainMethodResult result = invokeMain("One","Two","Three","Four","Five");
        assertEquals(new Integer(1), result.getExitCode());

        //System.out.println("InvokeMainWith5Args");
        //System.out.println(result.getOut());
    }

    @Test
    public void invokeMainWith8GoodArgs()
    {

        MainMethodResult result = invokeMain("-print","NAME","111-111-1111","222-222-2222","01/20/2015","5:15","01/20/2015","05:20");

        //System.out.println("invokeMainWith8good args");
        //System.out.println(result.getOut());

        assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void invalidCommandLineArgument(){
        MainMethodResult result = invokeMain("-print","-bad","NAME","111-111-1111","222-222-2222","01/20/2015","5:15","01/20/2015","05:20");

        assertTrue(result.getErr().startsWith("Invalid command line argument: -bad"));
    }


    @Test
    public void invokeMainWith8ArgsWithBadTimes()
    {

        MainMethodResult result = invokeMain("-print","NAME","103-654-9638","222-222-2222","mm-dd-yyyy","hh:mm","mm/dd/yyyy","hh:mm");
        assertEquals(new Integer(1), result.getExitCode());

        //System.out.println("8 args, bad num");
        //System.out.println(result.getOut());
    }

    @Test
    public void invokeMainWith8ArgsWithBadNumber()
    {

        MainMethodResult result = invokeMain("-print","NAME","10a-654-9638","222-222-2222","mm-dd-yyyy","hh:mm","mm/dd/yyyy","hh:mm");
        assertEquals(new Integer(1), result.getExitCode());

        //System.out.println("8 args, bad num");
        //System.out.println(result.getOut());
    }
    @Test
    public void invokeMainWith9Args()
    {
        //Main will only execute -README if it is contained as one of the 9 args.
        MainMethodResult result = invokeMain("-README", "-print","Name","000-000-1000","111-111-1111","1/20/2015","5:15","01/20/2015","05:20");

        assertEquals(new Integer(0), result.getExitCode());

        //System.out.println("9 good args");
        //System.out.println(result.getOut());
    }

    @Test
    public void invokeMainWithJustREADME(){
        MainMethodResult result = invokeMain("-README");
        //System.out.print(result.getOut());
        assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void invokeMainWithREADME()
    {
        MainMethodResult result = invokeMain("-blah","-README","WHATEVER");
        assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void invokeMainWithREADMEAgain()
    {
        MainMethodResult result = invokeMain("-README","WHATEVER","Stuff","Doesn't matter");
        assertEquals(new Integer(0), result.getExitCode());
    }
    @Test
    public void invokeMainWithREADMEThirdTime()
    {
        MainMethodResult result = invokeMain("-README","-print","Stuff","Goes","Here");
        assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void invokeMainWithREADMEInBadLocation()
    {
        MainMethodResult result = invokeMain("Name","123-456-7890","505-555-5555","6/15 12:30","6/15 12:35","-README");
        assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void invokeMainWithExampleArgsGood()
    {

        MainMethodResult result = invokeMain("-print","Peter Griffin","555-867-5309","503-555-1212","1/20/2015","5:15","01/20/2015","05:25");
        assertEquals(new Integer(0), result.getExitCode());

        //System.out.println("8 good args");
        //System.out.println(result.getErr());
    }

    @Test
    public void invokeMainWith24HourClockTime()
    {

        MainMethodResult result = invokeMain("-print","Peter Griffin","555-867-5309","503-555-1212","1/20/2015","16:15","01/20/2015","16:25");

        //System.out.println("8 good args w/24 hour clock");
        //System.out.println(result.getErr());

        assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void testPhoneBillWithName(){
        PhoneBill aBill = new PhoneBill("Scott");
        assertTrue(aBill.getCustomer().contains("Scott"));
    }

    @Test
    public void testPhoneBillWithDifferentName(){
        PhoneBill aBill = new PhoneBill("Joe");
        assertTrue(aBill.getCustomer().contains("Joe"));
    }

    @Test
    public void phoneCallGetCaller()
    {
        PhoneCall aPhoneCall = new PhoneCall("1234");
        assertTrue(aPhoneCall.getCaller().contains("1234"));
    }

    @Test
    public void phoneCallGetDifferentCaller()
    {
        PhoneCall aPhoneCall = new PhoneCall("4321");
        assertTrue(aPhoneCall.getCaller().contains("4321"));
    }

    @Test
    public void phoneCallCreation()
    {
        PhoneCall aPhoneCall = new PhoneCall("Caller","Callee","Start Time","End Time");
        assertEquals(aPhoneCall.toString(), "Phone call from Caller to Callee from Start Time to End Time");
    }

    @Test
    public void phoneBillCreation(){
        PhoneBill aPhoneBill = new PhoneBill("Name");
        PhoneCall aPhoneCall = new PhoneCall("Caller","Callee","Start Time","End Time");
        aPhoneBill.addPhoneCall(aPhoneCall);

        assertEquals(aPhoneBill.toString(), "Name's phone bill with 1 phone calls:\n" +
                "Phone call from Caller to Callee from Start Time to End Time\n");
        //System.out.println(aPhoneBill);

    }
    @Test
    public void phoneBillCreationWithMultipleCalls(){
        PhoneBill aPhoneBill = new PhoneBill("Name");
        PhoneCall phoneCall1 = new PhoneCall("Caller1","Callee1","Start Time1","End Time1");
        PhoneCall phoneCall2 = new PhoneCall("Caller2","Callee2","Start Time2","End Time2");
        aPhoneBill.addPhoneCall(phoneCall1);
        aPhoneBill.addPhoneCall(phoneCall2);

        assertEquals(aPhoneBill.toString(), "Name's phone bill with 2 phone calls:\n" +
                "Phone call from Caller1 to Callee1 from Start Time1 to End Time1\n" +
                "Phone call from Caller2 to Callee2 from Start Time2 to End Time2\n");
        //System.out.println(aPhoneBill);

    }

    @Test
    public void testingCommandLineOptionFile(){
        MainMethodResult result = invokeMain("-textFile","Phonebill.bin","Peter Griffin","555-867-5309","503-555-1212","1/20/2015","5:15","01/20/2015","05:25");

       // System.out.print(result.getErr());
        assertEquals(new Integer(0), result.getExitCode());
    }


    @Test
    public void testingTextDumper(){
        PhoneBill aPhoneBill = new PhoneBill("file.tmp bill");
        PhoneCall aPhoneCall = new PhoneCall("503-555-1212","301-654-8638","07/08/2015 12:55","07/08/2015 13:15");

        aPhoneBill.addPhoneCall(aPhoneCall);


       // System.out.print("First bill\n------------------------------------\n" + aPhoneBill);

        try {
            new TextDumper("file.tmp").dump(aPhoneBill);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PhoneBill anotherPhoneBill = null;


        try {
            anotherPhoneBill= (PhoneBill) new TextParser("file.tmp").parse();
        } catch (ParserException e) {
            e.printStackTrace();
        }

        //System.out.println(aPhoneBill.hashCode());
        //System.out.println(anotherPhoneBill.hashCode());

        assertTrue(aPhoneBill.equals(anotherPhoneBill));
        //System.out.print("\nLoaded bill\n--------------------------------------------\n" + anotherPhoneBill);
    }

    @Test
    public void testVariousOrderOfOptions(){
        MainMethodResult result = invokeMain("-print","-textFile", "testVariousOrderOfOptions","Stewie","555-111-2222","503-333-4444","7/20/2015","5:15","07/20/2015","05:25");

        //System.out.print(result.getErr());
        assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void testPrintingExistingFile(){
        MainMethodResult result = invokeMain("-print","-textFile","file.tmp");
        //System.out.println("---testPrintingExistingFile");
        System.out.print(result.getErr());

        assertEquals(new Integer(0),result.getExitCode());
    }

    @Test
    public void graderTestWithNonIntegerPhoneNumber(){
        MainMethodResult result = invokeMain("Test3", "ABC-123-4567", "123-456-7890", "03/03/2015","12:00", "03/03/2015", "16:00");
        //System.out.print(result.getErr());
        assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void graderTestStartTimeMalFormatted(){
        MainMethodResult result = invokeMain("Test4", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:XX", "03/03/2015", "16:00");
        //System.out.print(result.getErr());
        assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void graderTestEndTimeMalFormatted(){
        MainMethodResult result = invokeMain("Test5", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:00", "01/04/20/1", "16:00");
        //System.out.print(result.getErr());
        assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void graderTestUnknownCommandLineOption(){
        MainMethodResult result = invokeMain("-fred","Test6", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:00", "04/04/2015", "16:00");
        //System.out.print(result.getErr());
        assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void graderTestUnknownCommandLineArgument(){
        MainMethodResult result = invokeMain("Test7", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:00", "04/04/2015", "16:00","fred");
        //System.out.print(result.getErr());
        assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void graderTestPrintingOutAPhoneCall(){
        MainMethodResult result = invokeMain("-print","Test8", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:00", "05/04/2015", "16:00");
        //System.out.print(result.getOut());
        assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void graderTestMultiwordCustomerName(){
        MainMethodResult result = invokeMain("-print","Test 8", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:00", "09/04/2015", "16:00");
        //System.out.print(result.getOut());
        assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void graderTestMissingEndTime(){
        MainMethodResult result = invokeMain("Test9", "123-456-7890" ,"234-567-3452" ,"03/03/2015" ,"12:00");
        //System.out.print(result.getErr());
        assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void project2TestForStartingFile(){
        MainMethodResult result = invokeMain("-textFile","ewing.txt","-print","Project2","123-456-7890","234-567-9081","01/07/2015","07:00","01/17/2015","17:00");
        //System.out.print(result.getOut());
        //System.out.print(result.getErr());
        assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void project2TestToAddToExistingFile(){
        MainMethodResult result = invokeMain("-textFile","ewing.txt","-print","Project2","123-456-7890","456-789-0123","01/08/2015","08:00","01/08/2015","18:00");
        //System.out.print(result.getOut());
        //System.out.print(result.getErr());
        assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void project2TestWithDifferentCustomerName(){
        MainMethodResult result = invokeMain("-textFile","ewing.txt","DIFFERENT","123-456-7890","789-012-3456","01/09/2015","09:00","02/04/2015","16:00");
        //System.out.print(result.getOut());
        System.out.print(result.getErr());
        assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void project2TestWithMalFormattedFile(){
        MainMethodResult result = invokeMain("-textFile","bogus.txt","Project2","123-456-7890","385-284-2342","01/10/2015","10:00","01/20/2015","20:00");
        //System.out.print(result.getOut());
        System.out.print(result.getErr());
        assertEquals(new Integer(1), result.getExitCode());
    }
}