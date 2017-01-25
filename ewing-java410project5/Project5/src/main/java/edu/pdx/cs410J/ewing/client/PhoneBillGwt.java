package edu.pdx.cs410J.ewing.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.Date;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 */
public class PhoneBillGwt implements EntryPoint {
    static final int ADD_RECORD = 0;
    static final int SEARCH_RECORD = 1;
    static final int PRINT_RECORD = 2;
    static final int HELP = 3;
    private DeckPanel mainBody;
    private DockLayoutPanel frontPage;

    private PhoneBillServiceAsync phoneBillService;

    @Override
  public void onModuleLoad() {
        this.phoneBillService = GWT.create(PhoneBillService.class);

        frontPage = new DockLayoutPanel(Style.Unit.EM);
        frontPage.setWidth("100");
        frontPage.getElement().getStyle().setMargin(20, Style.Unit.PX);

        mainBody = new DeckPanel();
        mainBody.add(createNewRecordPage());
        mainBody.add(createSearchPage());

        mainBody.add(createPrintRecordPage());
        mainBody.add(new HelpPage());

        frontPage.addNorth(new HTML("Welcome to the Phone Bill System for CS 410, Summer 2015"), 2);
        frontPage.addWest(createNavigationScreen(), 10);
        frontPage.addSouth(new HTML(), 30);
        frontPage.add(mainBody);


        RootLayoutPanel rootPanel = RootLayoutPanel.get();

        rootPanel.add(frontPage);
        mainBody.showWidget(ADD_RECORD);

  }

    private SearchRecordPage createSearchPage() {
        final SearchRecordPage searchRecordPage = new SearchRecordPage();

        searchRecordPage.addSearchListener(new SearchRecordPage.SearchListener() {
            @Override
            public void searchListener(String name, Date start, Date end) {
                phoneBillService.searchForRecord(name, start, end, new AsyncCallback<AbstractPhoneBill>() {


                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }

                    @Override
                    public void onSuccess(AbstractPhoneBill result) {
                        if (result == null)
                            Window.alert("Customer does not exist.");
                        else {
                            new SearchResultsPopup(result, searchRecordPage.getStart(), searchRecordPage.getEnd()).show();
                        }
                    }
                });
            }
        });

        return searchRecordPage;
    }


    private PrintRecordPage createPrintRecordPage() {
        PrintRecordPage printRecordPage = new PrintRecordPage();

        printRecordPage.addPrintListener(new PrintRecordPage.PrintListener() {
            @Override
            public void printListener(String name) {
                phoneBillService.printRecord(name, new AsyncCallback<AbstractPhoneBill>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }

                    @Override
                    public void onSuccess(AbstractPhoneBill result) {
                        if (result == null)
                            Window.alert("Phone bill not found.");
                        else {

                            new PrintResultPopup(result).show();

                        }

                    }
                });

            }
        });

        return printRecordPage;
    }


    private NewRecordPage createNewRecordPage() {

        NewRecordPage newRecordPage = new NewRecordPage();
        newRecordPage.addValidUserListener(new NewRecordPage.ValidUserListener() {
            @Override
            public void validUserListener( String name, PhoneCall newCall) {

                phoneBillService.saveNewRecord(name, newCall, new AsyncCallback<AbstractPhoneBill>() {
                    @Override
                    public void onFailure(Throwable throwable) {

                        Window.alert(throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(AbstractPhoneBill abstractPhoneBill) {

                    }
                });
            }
        });

        return newRecordPage;
    }

    private Navigation createNavigationScreen() {
        Navigation navigationScreen = new Navigation();
        navigationScreen.addNavigationScreenListener(new Navigation.NavigationScreenListener() {
            @Override
            public void loadHelpScreen() {
                mainBody.showWidget(HELP);
            }

            @Override
            public void loadPrintScreen() {
                mainBody.showWidget(PRINT_RECORD);
            }

            @Override
            public void loadSearchScreen() {
                mainBody.showWidget(SEARCH_RECORD);
            }

            @Override
            public void loadAddRecordScreen() {
                mainBody.showWidget(ADD_RECORD);
            }
        });

        return navigationScreen;
    }
}
