package edu.pdx.cs410J.ewing.client;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Created by Scott on 8/10/2015.
 */
public class Navigation extends VerticalPanel {
    private NavigationScreenListener listener;
    public Navigation(){

        Button newRecordButton = new Button("New Record");
        newRecordButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                listener.loadAddRecordScreen();

            }
        });

        Button searchRecordButton = new Button("Search Records");
        searchRecordButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                listener.loadSearchScreen();
            }
        });

        Button printRecordButton = new Button("Print Records");
        printRecordButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                listener.loadPrintScreen();

            }
        });

        Button helpButton = new Button("Help");
        helpButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                listener.loadHelpScreen();
            }
        });

        this.add(newRecordButton);
        this.add(searchRecordButton);
        this.add(printRecordButton);
        this.add(helpButton);
    }

    public void addNavigationScreenListener(NavigationScreenListener listener){
        this.listener = listener;
    }
    interface NavigationScreenListener {
        void loadHelpScreen();
        void loadPrintScreen();
        void loadSearchScreen();
        void loadAddRecordScreen();
    }
}
