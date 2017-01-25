package edu.pdx.cs410J.ewing.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.*;
import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Scott on 8/10/2015.
 * CS 410 -- Summer 2015
 */
public class SearchResultsPopup extends PopupPanel {

    public SearchResultsPopup(AbstractPhoneBill result, Date startDate, Date endDate) {


        setAnimationEnabled(true);
        setGlassEnabled(true);

        VerticalPanel body = new VerticalPanel();

        DateTimeFormat fmt = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a");


        body.add(new Label(result.getCustomer()));
        body.add(new Label("Search range: "));
        body.add(new Label(fmt.format(startDate)));
        body.add(new Label(fmt.format(endDate)));

        Button done = new Button("Done");

        done.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                SearchResultsPopup.this.hide();
            }
        });

        FlexTable bill = new FlexTable();
        ArrayList<PhoneCall> calls = (ArrayList<PhoneCall>) result.getPhoneCalls();

        if (calls.size() > 0 ) {
            bill.setText(0,0, "Caller");
            bill.setText(1,0, "-------------------------");

            bill.setText(0,1, "Callee");
            bill.setText(1,1, "--------------------------");

            bill.setText(0,2, "Start time");
            bill.setText(1,2, "---------------------------------");

            bill.setText(0,3, "End time");
            bill.setText(1,3, "---------------------------------");

            bill.setText(0,4, "Duration");
            bill.setText(1,4, "------------------");





            for (int i = 0; i < calls.size(); ++i) {
                PhoneCall aCall = calls.get(i);
                int offset = i + 2;
                bill.setText(offset, 0, aCall.getCaller());
                bill.setText(offset, 1, aCall.getCallee());
                bill.setText(offset, 2, aCall.getStartTimeString());
                bill.setText(offset, 3, aCall.getEndTimeString());
                Date start = aCall.getStartTime();
                Date end = aCall.getEndTime();

                Long duration = end.getTime() - start.getTime();
                duration = Math.abs(duration);

                int minutes = (int) (duration / (1000 * 60) % 60);
                int hours = (int) (duration / (1000 * 60 * 60) % 24);
                int days = (int) (duration / (1000 * 60 * 60 * 24));
                NumberFormat formatted = NumberFormat.getFormat("00");
                StringBuilder durationString = new StringBuilder();

                if (days != 0) durationString.append(days).append("d ");
                if (hours != 0) durationString.append(hours).append("hr ");
                if (minutes <= 1)
                    durationString.append(formatted.format(minutes)).append(" min\n");
                else
                    durationString.append(formatted.format(minutes)).append(" mins\n");

                bill.setText(offset, 4, durationString.toString());
            }
        }else {
            bill.setText(0,0,"No results for this search.");
        }
        body.add(bill);
        body.add(done);
        add(body);

    }
}
