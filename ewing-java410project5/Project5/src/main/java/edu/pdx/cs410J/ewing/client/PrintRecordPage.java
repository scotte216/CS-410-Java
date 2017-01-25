package edu.pdx.cs410J.ewing.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Created by Scott Ewing on 8/10/2015.
 * CS 410 -- Summer 2015
 */
public class PrintRecordPage extends VerticalPanel {
    TextBox name;
    PrintListener listener;

    public PrintRecordPage() {
        Label body = new Label("Enter the name of a phone bill customer to print: ");
        this.add(body);

        this.add(name = new TextBox());

        Button printButton = new Button("Print Details");

        printButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (name.getValue() == null || name.getValue().equals(""))
                {
                    Window.alert("Please enter a valid name.");
                    return;
                }

                listener.printListener(name.getValue());

            }
        });

        this.add(printButton);
    }

    public void addPrintListener(PrintListener listener){
        this.listener = listener;
    }

    interface PrintListener{
        void printListener(String name);
    }

}
