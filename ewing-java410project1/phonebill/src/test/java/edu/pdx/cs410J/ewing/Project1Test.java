package edu.pdx.cs410J.ewing;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import edu.pdx.cs410J.InvokeMainTestCase;
import static junit.framework.Assert.assertEquals;

/**
 * Tests the functionality in the {@link Project1} main class.
 */
public class Project1Test extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String ... args) {
        return invokeMain( Project1.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  public void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertEquals(new Integer(1), result.getExitCode());
    assertTrue(result.getErr().contains( "Missing or too many command line argument(s)." ));
  }

    @Test
    public void invokeMainWith1ArgNotReadme()
    {
        MainMethodResult result = invokeMain("-print");
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
        assertEquals(new Integer(0),result.getExitCode());

        //System.out.println("invokeMainwith8good args");
        //System.out.println(result.getOut());
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
    public void invokeMainWith8Args24HourTime()
    {
        //Main will only execute -README if it is contained as one of the 9 args.
        MainMethodResult result = invokeMain("-print","Name","000-000-1000","111-111-1111","1/20/2015","15:15","01/20/2015","15:20");
        assertEquals(new Integer(0), result.getExitCode());

        //System.out.println("9 good args");
        //System.out.println(result.getOut());
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

}