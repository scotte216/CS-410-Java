package edu.pdx.cs410J.ewing.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Created by Scott Ewing on 8/10/2015.
 * CS 410 -- Summer 2015
 */
public class HelpPage extends VerticalPanel {

    public HelpPage() {
        this.setWidth("10");
        String text1 = "Project4 Help: This project will take the phone bill system online. Best viewed with at least a screen of 96 characters wide." +
                "This project is for 410 Advanced Programming -- Java at PSU for Summer 2015. \n" +
                "This program was written by Scott Ewing. This program will take a user name, create a phone bill object for them then add phone call\n" +
                "data to this phone bill on the remote server. <Br><BR><BR>New Record -- Select this to add a new record to the system.<br>" +
                "Search Records -- Select this to search for calls between various times.<br>" +
                "Print Records -- Select this to print out the details of some one's calls.<br>" +
                "Help -- Select this to display this help screen.";
        HTML test = new HTML(text1);
        this.add(test);

    }

}
