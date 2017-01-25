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
 * CS 450 -- Summer 2015
 */
public class SearchRecordPage extends VerticalPanel {
    private SearchListener listener;

    private TextBox name;
    private SetCall start;
    private SetCall end;
    private Date searchStart;
    private Date searchEnd;

    public SearchRecordPage(){


        this.add(setNamePanel());

        start = new SetCall("Start");
        this.add(start);

        end = new SetCall("End");
        this.add(end);

        Button searchButton = new Button("Search calls");
        this.add(searchButton);

        searchButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                String billName;
                String callerString = "111-111-1111";
                String calleeString = "111-111-1111";
                String startTime;
                String endTime;
                billName = name.getText();

                if (!isValidString(billName)) {
                    Window.alert("Invalid customer name.");
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

                searchStart = start.toDate();
                searchEnd = end.toDate();
                listener.searchListener(billName, start.toDate(), end.toDate());

                clearLabels();
            }
        });


    }
    public class SetCall extends HorizontalPanel {
        DateBox callDate;
        ListBox callHour;
        ListBox callMinute;
        ListBox amPm;

        public SetCall(String title) {
            //HorizontalPanel this = new HorizontalPanel();
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

        public Date toDate(){
            return DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a").parse(toString());
        }
    }


    private HorizontalPanel setNamePanel() {
        HorizontalPanel namePanel = new HorizontalPanel();

        namePanel.add(new Label("Name: "));

        name = new TextBox();
        namePanel.add(name);
        return namePanel;
    }

    private boolean isValidString(String toTest){
        return toTest != null && !toTest.equals("");
    }

    public Date getStart(){
        return searchStart;
    }

    public Date getEnd(){
        return searchEnd;
    }

    private void clearLabels() {
        name.setText("");

        start.callDate.setValue(new Date());
        start.callHour.setSelectedIndex(0);
        start.callMinute.setSelectedIndex(0);
        start.amPm.setSelectedIndex(0);

        end.callDate.setValue(new Date());
        end.callHour.setSelectedIndex(0);
        end.callMinute.setSelectedIndex(0);
        end.amPm.setSelectedIndex(0);

    }

    public void addSearchListener(SearchListener listener){
        this.listener = listener;
    }

    interface SearchListener{
        void searchListener(String name, Date start, Date end);
    }
}
