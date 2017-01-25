package edu.pdx.cs410J.ewing;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link Project4} class by invoking its main method with various arguments 
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Project4Test extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    @Test
    public void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain(Project4.class);
        assertThat(result.getExitCode(), equalTo(1));
        //assertThat(result.getErr(), containsString(Project4.MISSING_ARGS));
    }

    @Test
    public void noCommandLineArgs(){
        MainMethodResult result = invokeMain(Project4.class);
        assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void readmeArgument(){
        MainMethodResult result = invokeMain(Project4.class,"-README");
        //System.out.print(result.getOut());
        assertEquals(new Integer(0), result.getExitCode());
    }


    @Test
    public void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain(Project4.class);
        //System.out.println(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());
        assertTrue(result.getErr().contains("Missing command line argument(s)."));
    }

    @Test
    public void invokeMainWith1ArgNotReadme()
    {
        MainMethodResult result = invokeMain(Project4.class,"-print");
        //System.out.println("---invokeMainWith1ArgNotReadMe");
        //System.out.println(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }


    @Test
    public void invokeMainWith5Args()
    {
        //This should exit with one because 5 args isn't valid
        MainMethodResult result = invokeMain(Project4.class,"One","Two","Three","Four","Five");

        //System.out.println("InvokeMainWith5Args");
        //System.out.println(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());

    }

    @Test
    public void invokeMainWith8GoodArgs()
    {

        MainMethodResult result = invokeMain(Project4.class,"-print","NAME","111-111-1111","222-222-2222","01/20/2015","5:15", "am","01/20/2015","05:20", "am");

        //System.out.println("invokeMainWith8good args");
        //System.out.println(result.getOut());

        Assert.assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void invalidCommandLineArgument(){
        MainMethodResult result = invokeMain(Project4.class,"-print","-bad","NAME","111-111-1111","222-222-2222","01/20/2015","5:15","01/20/2015","05:20");

        //System.out.print(result.getErr());
        assertTrue(result.getErr().startsWith("Invalid command line argument: -bad"));
    }


    @Test
    public void invokeMainWith8ArgsWithBadTimes()
    {

        MainMethodResult result = invokeMain(Project4.class,"-print","NAME","103-654-9638","222-222-2222","mm-dd-yyyy","hh:mm","mm/dd/yyyy","hh:mm");
        //System.out.print(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());

        //System.out.println("8 args, bad num");
        //System.out.println(result.getOut());
    }

    @Test
    public void invokeMainWith8ArgsWithBadNumber()
    {

        MainMethodResult result = invokeMain(Project4.class,"-print","NAME","10a-654-9638","222-222-2222","mm-dd-yyyy","hh:mm","mm/dd/yyyy","hh:mm");
        //System.out.print(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());

        //System.out.println("8 args, bad num");
        //System.out.println(result.getOut());
    }
    @Test
    public void invokeMainWith9Args()
    {
        //Main will only execute -README if it is contained as one of the 9 args.
        MainMethodResult result = invokeMain(Project4.class,"-README", "-print","Name","000-000-1000","111-111-1111","1/20/2015","5:15","01/20/2015","05:20");
        //System.out.print(result.getErr());
        Assert.assertEquals(new Integer(0), result.getExitCode());

        //System.out.println("9 good args");
        //System.out.println(result.getOut());
    }

    @Test
    public void invokeMainWithJustREADME(){
        MainMethodResult result = invokeMain(Project4.class,"-README");
        //System.out.print(result.getOut());
        Assert.assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void invokeMainWithREADME()
    {
        MainMethodResult result = invokeMain(Project4.class,"-blah","-README","WHATEVER");
        //System.out.println(result.getOut());
        Assert.assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void invokeMainWithREADMEAgain()
    {
        MainMethodResult result = invokeMain(Project4.class,"-README","WHATEVER","Stuff","Doesn't matter");
        Assert.assertEquals(new Integer(0), result.getExitCode());
    }
    @Test
    public void invokeMainWithREADMEThirdTime()
    {
        MainMethodResult result = invokeMain(Project4.class,"-README","-print","Stuff","Goes","Here");
        Assert.assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void invokeMainWithREADMEInBadLocation()
    {
        MainMethodResult result = invokeMain(Project4.class,"Name","123-456-7890","505-555-5555","6/15 12:30","6/15 12:35","-README");
        //System.out.print(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void invokeMainWithExampleArgsGood()
    {

        MainMethodResult result = invokeMain(Project4.class,"-print","Peter Griffin","555-867-5309","503-555-1212","1/20/2015","05:15","am","01/20/2015","05:25","pm");
        //System.out.println("8 good args");
        //System.out.println(result.getErr());

        Assert.assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void invokeMainWith24HourClockTime()
    {

        MainMethodResult result = invokeMain(Project4.class,"-print","Peter Griffin","555-867-5309","503-555-1212","1/20/2015","4:15","pm","01/20/2015","04:25","pm");

        //System.out.println("8 good args w/24 hour clock");
        //System.out.println(result.getErr());

        Assert.assertEquals(new Integer(0), result.getExitCode());
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
    public void phoneCallCreation() throws ParseException {
        //Note: this uses TODAY'S date (when you execute the program) so adjust accordingly.
        SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date start = sf.parse("07/26/2015 12:55");
        Date end = sf.parse("07/26/2015 01:15");
        PhoneCall aPhoneCall = new PhoneCall("Caller","Callee",start,end);
        Assert.assertEquals(aPhoneCall.toString(), "Phone call from Caller to Callee from 7/26/15 to 7/26/15");
    }

    @Test
    public void phoneBillCreation() throws ParseException {
        PhoneBill aPhoneBill = new PhoneBill("Name");
        SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date start = sf.parse("07/26/2015 12:55");
        Date end = sf.parse("07/26/2015 01:15");
        PhoneCall aPhoneCall = new PhoneCall("Caller","Callee",start,end);
        aPhoneBill.addPhoneCall(aPhoneCall);

        //Note: this uses TODAY'S date (when you execute the program) so adjust accordingly.
        Assert.assertEquals(aPhoneBill.toString(), "Name's phone bill with 1 phone calls:\n" +
                "Phone call from Caller to Callee from 7/26/15 to 7/26/15\n");
        //System.out.println(aPhoneBill);

    }
    @Test
    public void phoneBillCreationWithMultipleCalls() throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        Date start = sf.parse("07/08/2015 12:55");
        Date end = sf.parse("07/08/2015 01:15");

        PhoneBill aPhoneBill = new PhoneBill("Name");
        PhoneCall phoneCall1 = new PhoneCall("111-111-1111","111-111-1111",start,end);
        PhoneCall phoneCall2 = new PhoneCall("222-222-2222","222-222-2222",start,end);

        aPhoneBill.addPhoneCall(phoneCall1);
        aPhoneBill.addPhoneCall(phoneCall2);

        //System.out.println(aPhoneBill);

        Assert.assertEquals(aPhoneBill.toString(), "Name's phone bill with 2 phone calls:\n" +
                "Phone call from 111-111-1111 to 111-111-1111 from 7/8/15 to 7/8/15\n" +
                "Phone call from 222-222-2222 to 222-222-2222 from 7/8/15 to 7/8/15\n");


    }


    @Test
    public void graderTestWithNonIntegerPhoneNumber(){
        MainMethodResult result = invokeMain(Project4.class,"Test3", "ABC-123-4567", "123-456-7890", "03/03/2015","12:00", "03/03/2015", "16:00");
        //System.out.print(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void graderTestStartTimeMalFormatted(){
        MainMethodResult result = invokeMain(Project4.class,"Test4", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:XX", "03/03/2015", "16:00");
        //System.out.print(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void graderTestEndTimeMalFormatted(){
        MainMethodResult result = invokeMain(Project4.class,"Test5", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:00", "01/04/20/1", "16:00");
        //System.out.print(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void graderTestUnknownCommandLineOption(){
        MainMethodResult result = invokeMain(Project4.class,"-fred","Test6", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:00", "04/04/2015", "16:00");
        //System.out.print(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void graderTestUnknownCommandLineArgument(){
        MainMethodResult result = invokeMain(Project4.class,"Test7", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:00", "04/04/2015", "16:00","fred");
        //System.out.print(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void graderTestPrintingOutAPhoneCall(){
        MainMethodResult result = invokeMain(Project4.class,"-print","Test8", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:00","pm", "05/04/2015", "4:00","pm");
        //System.out.print(result.getOut());
        Assert.assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void graderTestMultiWordCustomerName(){
        MainMethodResult result = invokeMain(Project4.class,"-print","Test 8", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:00", "pm", "09/04/2015", "06:00","pm");
        //System.out.print(result.getOut());
        Assert.assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void graderTestMissingEndTime(){
        MainMethodResult result = invokeMain(Project4.class,"Test9", "123-456-7890" ,"234-567-3452" ,"03/03/2015" ,"12:00");
        //System.out.print(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void testPhoneCallCompareToEqual() throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy HH:mm aa");

        Date start1 = sf.parse("07/08/2015 12:55 am");
        Date end1 = sf.parse("07/08/2015 1:15 pm");
        Date start2 =  sf.parse("07/08/2015 12:55 am");
        Date end2 = sf.parse("07/08/2015 2:55 pm");

        //System.out.println("Compare number strings: " + "555-777-1212".compareTo("655-777-1212"));

        PhoneCall call1 = new PhoneCall("555-555-5555", "target", start1, end1);
        PhoneCall call2 = new PhoneCall("555-555-5555", "target", start2, end2);

        Assert.assertEquals(0, call1.compareTo(call2));

    }

    @Test
    public void testPhoneCallCompareToEarlier() throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy HH:mm aa");

        Date start1 = sf.parse("07/08/2015 2:55 am");
        Date end1 = sf.parse("07/08/2015 1:15 pm");
        Date start2 =  sf.parse("07/08/2015 12:55 am");
        Date end2 = sf.parse("07/08/2015 2:55 pm");

        //System.out.println("Compare number strings: " + "555-777-1212".compareTo("655-777-1212"));

        PhoneCall call1 = new PhoneCall("555-555-5555", "target", start1, end1);
        PhoneCall call2 = new PhoneCall("555-555-5555", "target", start2, end2);

        Assert.assertEquals(-1, call1.compareTo(call2));

    }


    @Test
    public void testPhoneCallCompareToLater() throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy HH:mm aa");

        Date start1 =  sf.parse("07/08/2015 12:55 am");
        Date end1 = sf.parse("07/08/2015 2:55 pm");
        Date start2 = sf.parse("07/08/2015 2:55 am");
        Date end2 = sf.parse("07/08/2015 1:15 pm");

        PhoneCall call1 = new PhoneCall("555-555-5555", "target", start1, end1);
        PhoneCall call2 = new PhoneCall("555-555-5555", "target", start2, end2);

        Assert.assertEquals(1, call1.compareTo(call2));

    }


    @Test
    public void testPhoneCallCompareToEqualTimeSmallerNumber() throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy HH:mm aa");

        Date start1 = sf.parse("07/08/2015 12:55 am");
        Date end1 = sf.parse("07/08/2015 1:15 pm");
        Date start2 =  sf.parse("07/08/2015 12:55 am");
        Date end2 = sf.parse("07/08/2015 2:55 pm");

        //System.out.println("Compare number strings: " + "555-777-1212".compareTo("655-777-1212"));

        PhoneCall call1 = new PhoneCall("555-555-5554", "target", start1, end1);
        PhoneCall call2 = new PhoneCall("555-555-5555", "target", start2, end2);

        Assert.assertEquals(-1, call1.compareTo(call2));

    }

    @Test
    public void testPhoneCallCompareToEqualTimeLargerNumber() throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy HH:mm aa");

        Date start1 = sf.parse("07/08/2015 12:55 am");
        Date end1 = sf.parse("07/08/2015 1:15 pm");
        Date start2 =  sf.parse("07/08/2015 12:55 am");
        Date end2 = sf.parse("07/08/2015 2:55 pm");

        //System.out.println("Compare number strings: " + "555-777-1212".compareTo("655-777-1212"));

        PhoneCall call1 = new PhoneCall("555-555-5556", "target", start1, end1);
        PhoneCall call2 = new PhoneCall("555-555-5555", "target", start2, end2);

        Assert.assertEquals(1, call1.compareTo(call2));

    }

    @Test
    public void testMissingPortOption(){
        MainMethodResult result = invokeMain(Project4.class,"-host","localhost","Test", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:00", "pm", "09/04/2015", "06:00","pm");
        //System.out.print(result.getOut());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void testMissingPortNumber(){
        MainMethodResult result = invokeMain(Project4.class,"-host","localhost","-port","Test", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:00", "pm", "09/04/2015", "06:00","pm");
        //System.out.print(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void testMissingHostOption(){
        MainMethodResult result = invokeMain(Project4.class,"-port","8080","Test", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:00", "pm", "09/04/2015", "06:00","pm");
        //System.out.print(result.getOut());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void testMissingHostName(){
        MainMethodResult result = invokeMain(Project4.class,"-port","8080","-host","Test", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:00", "pm", "09/04/2015", "06:00","pm");
        //System.out.print(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void testBadPort(){
        MainMethodResult result = invokeMain(Project4.class,"-port","808a","-host","localhost","Test", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:00", "pm", "09/04/2015", "06:00","pm");
        //System.out.print(result.getOut());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void testGoodCallsToAddToServer(){
        MainMethodResult result = invokeMain(Project4.class,"-port","8080","-host","localhost","-print","Test", "123-456-7890" ,"234-567-8901" ,"03/02/2015" ,"12:00", "pm", "03/02/2015", "12:05","pm");
        //System.out.print(result.getOut());
        Assert.assertEquals(new Integer(0), result.getExitCode());

        result = invokeMain(Project4.class,"-port","8080","-host","localhost","-print","Test", "123-456-7890" ,"234-567-8901" ,"03/2/2015" ,"12:30", "pm", "03/2/2015", "12:45","pm");
        //System.out.print(result.getOut());
        Assert.assertEquals(new Integer(0), result.getExitCode());

        result = invokeMain(Project4.class,"-port","8080","-host","localhost","-print","Test", "123-456-7890" ,"234-567-8901" ,"03/2/2015" ,"12:50", "pm", "03/2/2015", "1:03","pm");
        //System.out.print(result.getOut());
        Assert.assertEquals(new Integer(0), result.getExitCode());

    }
    @Test
    public void testGoodCallsToAddToServerAndSearch(){
        MainMethodResult result = invokeMain(Project4.class,"-port","8080","-host","localhost","-print","Test", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:00", "pm", "03/03/2015", "12:05","pm");
        //System.out.print(result.getOut());
        Assert.assertEquals(new Integer(0), result.getExitCode());

        result = invokeMain(Project4.class,"-port","8080","-host","localhost","-print","Test", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:30", "pm", "03/03/2015", "12:45","pm");
        //System.out.print(result.getOut());
        Assert.assertEquals(new Integer(0), result.getExitCode());

        result = invokeMain(Project4.class,"-port","8080","-host","localhost","-print","Test", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:50", "pm", "03/03/2015", "1:03","pm");
        //System.out.print(result.getOut());
        Assert.assertEquals(new Integer(0), result.getExitCode());

        result = invokeMain(Project4.class,"-port","8080","-host","localhost","-search","Test","03/03/2015","12:00","pm","3/3/2015","1:15","pm");
        //System.out.print(result.getOut());
        Assert.assertEquals(new Integer(0),result.getExitCode());

        result = invokeMain(Project4.class,"-port","8080","-host","localhost","-search","Test","03/02/2015","12:00","pm","3/2/2015","1:00","pm");
        //System.out.print(result.getOut());
        Assert.assertEquals(new Integer(0),result.getExitCode());

        result = invokeMain(Project4.class,"-port","8080","-host","localhost","-search","Test","03/01/2015","12:00","pm","3/1/2015","1:15","pm");
        //System.out.print(result.getOut());
        Assert.assertEquals(new Integer(0),result.getExitCode());
    }

    @Test
    public void testingSearchWithoutHost(){
        MainMethodResult result = invokeMain(Project4.class,"-search","Test","03/03/2015","12:00","pm","3/3/2015"
                ,"1:15","pm");
        //System.out.print(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }


    @Test
    public void testingSearchUnknownName(){
        MainMethodResult result = invokeMain(Project4.class,"-host","localhost","-port","8080","-search",
                "ASDF","03/03/2015","12:00","pm","3/3/2015","1:15","pm");
        //System.out.println(result.getOut());
        //System.out.println(result.getErr());
        Assert.assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void testVariousErrorConditions(){
        //bad date
        MainMethodResult result = invokeMain(Project4.class,"-host","localhost","-port","8080","-search","Bob","5/5a/2014","10:00","am","12/20/2015","9:00","pm");
        //System.out.println(result.getErr());
        //System.out.println(result.getOut());
        Assert.assertEquals(new Integer(1),result.getExitCode());

        //bad time
        result = invokeMain(Project4.class,"-host","localhost","-port","8080","-search","Bob","5/5/2014","1z:00","am","12/20/2015","9:00","pm");
        Assert.assertEquals(new Integer(1), result.getExitCode());

        //Start time after end time
        result = invokeMain(Project4.class,"-host","localhost","-port","8080","Bob","555-555-5555","666-666-6666","5/5/2015","10:00","pm","5/5/2015","9:00","pm");
        //System.out.println(result.getErr());
        //System.out.println(result.getOut());
        Assert.assertEquals(new Integer(1), result.getExitCode());

        //bad phone number
        result = invokeMain(Project4.class,"-host","localhost","-port","8080","Bob","555-aaa-5555","666-666-6666","5/5/2015","10:00","pm","5/5/2015","10:01","pm");
        //System.out.println(result.getErr());
        //System.out.println(result.getOut());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void testGoodPost(){
        MainMethodResult result = invokeMain(Project4.class,"-print","-host","localhost","-port","8080","Bob"
                ,"453-224-6345","663-223-9948","7/27/2013","10:00","am","7/27/2013","10:11","am");
        //System.out.println(result.getErr());
        //System.out.println(result.getOut());
        Assert.assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void testWithJustPrint(){
        MainMethodResult result = invokeMain(Project4.class,"-print","Bob"
                ,"453-224-6345","663-223-9948","7/27/2013","10:00","am","7/27/2013","10:11","am");
        //System.out.println(result.getErr());
        //System.out.println(result.getOut());
        Assert.assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void testBadHostName(){
        MainMethodResult result = invokeMain(Project4.class,"-host","akdjsfkl.com","-port","8080","-search","Bob","7/27/2013","10:00","am","7/27/2013","10:11","am");
        //System.out.print(result.getErr());
        //System.out.println(result.getOut());
        Assert.assertEquals(new Integer(1),result.getExitCode() );
    }

    @Test
    public void graderTestWithNonIntegerPhoneNumberWithServer(){
        MainMethodResult result = invokeMain(Project4.class,"-host","localhost","-port","8080","Test3","ABC-123-4567", "123-456-7890", "03/03/2015","12:00", "03/03/2015", "16:00");
        //System.out.print(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void graderTestStartTimeMalFormattedWithServer(){
        MainMethodResult result = invokeMain(Project4.class,"-host","localhost","-port","8080","Test4", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:XX", "03/03/2015", "16:00");
        //System.out.print(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void graderTestEndTimeMalFormattedWithServer(){
        MainMethodResult result = invokeMain(Project4.class,"-host","localhost","-port","8080","Test5", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:00", "01/04/20/1", "16:00");
        //System.out.print(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void graderTestUnknownCommandLineOptionWithServer(){
        MainMethodResult result = invokeMain(Project4.class,"-host","localhost","-port","8080","-fred","Test6", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:00", "04/04/2015", "16:00");
        //System.out.print(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void graderTestUnknownCommandLineArgumentWithServer(){
        MainMethodResult result = invokeMain(Project4.class,"-host","localhost","-port","8080","Test7", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:00", "04/04/2015", "16:00","fred");
        //System.out.print(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void graderTestPrintingOutAPhoneCallWithServer(){
        MainMethodResult result = invokeMain(Project4.class,"-host","localhost","-port","8080","-print","Test8", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:00","pm", "05/04/2015", "4:00","pm");
        //System.out.print(result.getOut());
        Assert.assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void graderTestMultiWordCustomerNameWithServer(){
        MainMethodResult result = invokeMain(Project4.class,"-host","localhost","-port","8080","-print","Test 8", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:00", "pm", "09/04/2015", "06:00","pm");
        //System.out.print(result.getOut());
        Assert.assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void graderTestMissingEndTimeWithServer(){
        MainMethodResult result = invokeMain(Project4.class,"-host","localhost","-port","8080","Test9", "123-456-7890" ,"234-567-3452" ,"03/03/2015" ,"12:00");
        //System.out.print(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void coverageTestForNoArguments(){
        MainMethodResult result = invokeMain(Project4.class,"-host","localhost","-port","8080");
        //System.out.print(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void coverageTestForNoTooManyArguments(){
        MainMethodResult result = invokeMain(Project4.class,"-host","localhost","-port","8080","-print","Test 8", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:00", "pm", "09/04/2015", "06:00","pm","More");
        //System.out.print(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }


    @Test
    public void coverageTestForOneArgumentsAndSearch(){
        MainMethodResult result = invokeMain(Project4.class,"-search", "-host","localhost","-port","8080","Bob");
        //System.out.print(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void coverageTestForNoArgumentsAndSearch(){
        MainMethodResult result = invokeMain(Project4.class,"-search", "-host","localhost","-port","8080");
        //System.out.print(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void coverageTestForNoTooManyArgumentsAndSearch(){
        MainMethodResult result = invokeMain(Project4.class,"-search","-host","localhost","-port","8080","-print","Test 8", "123-456-7890" ,"234-567-8901" ,"03/03/2015" ,"12:00", "pm", "09/04/2015", "06:00","pm");
        //System.out.print(result.getErr());
        Assert.assertEquals(new Integer(1), result.getExitCode());
    }

    @Test
    public void justPrint(){
        MainMethodResult result = invokeMain(Project4.class,"-print");
        //System.out.println(result.getErr());
        Assert.assertEquals(new Integer(1),result.getExitCode());
    }
}