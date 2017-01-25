package edu.pdx.cs410J.ewing.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;

import java.util.Date;


/**
 * Created by Scott Ewing on 8/10/2015.
 * CS 410 -- Summer 2015
 */
public class NewRecordPage extends VerticalPanel {
    private ValidUserListener listener;

    private TextBox name;
    private SetCall start;
    private SetCall end;
    private TextBox caller;
    private TextBox callee;
    private Label success;

    public NewRecordPage(){

        success = new Label("Successfully added record.");


        this.add(setNamePanel());
        this.add(setCallerPanel());
        this.add(setCalleePanel());

        start = new SetCall("Start");
        this.add(start);

        end = new SetCall("End");
        this.add(end);

        Button createRecordButton = new Button("Add call record");
        this.add(createRecordButton);
        this.add(success);
        success.setVisible(false);
        createRecordButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                String billName;
                String callerString = null;
                String calleeString = null;
                String startTime;
                String endTime;
                billName = name.getText();

                if (!isValidString(billName)) {
                    Window.alert("Invalid customer name.");
                    return;
                }
                callerString = caller.getText();
                if (!isValidString(callerString)) {
                    Window.alert("Please enter a caller number.");
                    return;
                }
                calleeString = callee.getText();
                if (!isValidString(calleeString)) {
                    Window.alert("Please enter a callee number.");
                    return;
                }

                startTime = start.toString();
                endTime = end.toString();


                PhoneCall newCall = null;
                try {

                    newCall = new ValidateInformation().parseArguments(callerString, calleeString, startTime, endTime);

                } catch (PhoneException e) {
                    Window.alert(e.getMessage());
                    return;
                }


                clearLabels();
                success.setVisible(true);
                listener.validUserListener(billName, newCall);
            }
        });


    }

    private void clearLabels() {
        name.setText("");
        callee.setText("");
        caller.setText("");

        start.callDate.setValue(new Date());
        start.callHour.setSelectedIndex(0);
        start.callMinute.setSelectedIndex(0);
        start.amPm.setSelectedIndex(0);

        end.callDate.setValue(new Date());
        end.callHour.setSelectedIndex(0);
        end.callMinute.setSelectedIndex(0);
        end.amPm.setSelectedIndex(0);
    }

    public class SetCall extends HorizontalPanel {
        DateBox callDate;
        ListBox callHour;
        ListBox callMinute;
        ListBox amPm;

        public SetCall(String title) {

            this.add(new Label(title + " date: "));

            callDate = new DateBox();
            callDate.setFormat(new DateBox.DefaultFormat
                    (DateTimeFormat.getFormat("MM/dd/yyyy")));
            callDate.setValue(new Date());
            this.add(callDate);

            this.add(new Label(title + " time: "));

            callHour = new ListBox();
            for (int i = 1; i <= 12; ++i)
                callHour.addItem(Integer.toString(i));
            this.add(callHour);

            callMinute = new ListBox();

            for (int i = 0; i <= 60; i++) {
                String formatted = NumberFormat.getFormat("00").format(i);
                callMinute.addItem(formatted);
            }
            this.add(callMinute);

            amPm = new ListBox();
            amPm.addItem("am");
            amPm.addItem("pm");

            this.add(amPm);
        }

        public String toString(){
            String date = callDate.getTextBox().getValue();
            String time = callHour.getSelectedItemText() + ":" + callMinute.getSelectedItemText()
                    + " " + amPm.getSelectedItemText();
            return date + " " + time;
        }
    }

    private HorizontalPanel setNamePanel() {
        HorizontalPanel namePanel = new HorizontalPanel();

        namePanel.add(new Label("Name: "));

        name = new TextBox();
        namePanel.add(name);
        return namePanel;
    }

    private HorizontalPanel setCallerPanel() {
        HorizontalPanel callerPanel = new HorizontalPanel();

        callerPanel.add(new Label("Caller  (nnn-nnn-nnnn): "));

        caller = new TextBox();
        callerPanel.add(caller);
        return callerPanel;
    }

    private HorizontalPanel setCalleePanel() {
        HorizontalPanel calleePanel = new HorizontalPanel();

        calleePanel.add(new Label("Callee  (nnn-nnn-nnnn): "));

        callee = new TextBox();
        calleePanel.add(callee);
        return calleePanel;
    }

    private boolean isValidString(String toTest){
        return toTest != null && !toTest.equals("");
    }

    public void addValidUserListener (ValidUserListener listener){
        this.listener = listener;
    }

    interface ValidUserListener {
        void validUserListener(String name, PhoneCall newCall);
    }
}
