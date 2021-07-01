package ui.appViews;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dtos.UserDTO;
import globals.Globals;
import org.springframework.data.domain.Page;
import ui.appViews.SettingsViewParts.SettingsView_Tab1;
import ui.appViews.SettingsViewParts.SettingsView_Tab2;
import ui.appViews.SettingsViewParts.SettingsView_Tab3;
import ui.appViews.SettingsViewParts.SettingsView_Tab4;
import ui.layouts.AppLayout;

import java.util.HashMap;
import java.util.Map;


@Route(value = "settings", layout = AppLayout.class)
@PageTitle("Settings")
public class SettingsView extends Div {
    private boolean isEmployer = getTrueIfSessionIsEmployer();

    //Die 4 Tabs werden in diesen Klassen jeweils einzeln gebaut und dann als Div zurück hierhin geschickt.
    //Idealerweise sollte der Zugriff auf die DB in den jeweiligen _Tabx Klassen passieren um die Logik voneinander zu trennen
    private SettingsView_Tab1 buildTab1 = new SettingsView_Tab1();
    private SettingsView_Tab2 buildTab2 = new SettingsView_Tab2();
    private SettingsView_Tab3 buildTab3 = new SettingsView_Tab3();
    private SettingsView_Tab4 buildTab4 = new SettingsView_Tab4();

    //Tab1 Profildaten aktualisieren - vars
    private TextField Vorname = new TextField("Vorname");
    private TextField Nachname = new TextField("Nachname");
    private TextArea description = new TextArea("Kurzbeschreibung über dich");
    private TextArea skills = new TextArea("Deine Fachlichen Kenntnisse");
    private TextArea references = new TextArea("Referenzen wie Github");
    private DatePicker datePicker = new DatePicker();
    private Select<String> Fachbereich = new Select<>();
    private Select<String> Studiengang = new Select<>();
    //private Select<String> Semester = new Select<>();
    private DatePicker semesterdatePicker = new DatePicker();
    private Button actualize = new Button("Aktualisieren");

    //Tab3 Passwort ändern - vars
    private PasswordField passwordOld = new PasswordField("Passwort alt");
    private PasswordField passwordNew = new PasswordField("Passwort neu");
    private PasswordField passwordNew2 = new PasswordField("Passwort bestätigen");
    private Button cancel = new Button("Abbrechen");
    private Button save = new Button("Ändern");

    //Tab4 Konto löschen - vars
    private Button delete = new Button("Konto löschen");

    //Konstruktor
    public SettingsView() {
        addClassName("wrapper");
        setId("settings-view");
        add(createTitle());



            //Tab 1
        Tab tab1 = new Tab("Allgemeine Daten");
        Div page1 = new Div();
        Tab tab2 = new Tab("Lebenslauf anpassen");
        Div page2 = new Div();
        if(!isEmployer) {
            page1 = buildTab1.createView(Vorname, Nachname, description, skills, references, datePicker, Fachbereich, Studiengang, semesterdatePicker, actualize);

            //Tab2

            page2.setVisible(false);
            page2 = buildTab2.createView(page2);
        }
        //Tab3
        Tab tab3 = new Tab("Passwort ändern");
        Div page3 = new Div();
        page3.setVisible(false);
        page3 = buildTab3.createView(page3, passwordOld, passwordNew, passwordNew2, cancel, save);

        //Tab4
        Tab tab4 = new Tab("Konto löschen");
        Div page4 = new Div();
        page4.setText("Wollen Sie Ihr Konto löschen?");
        page4.setVisible(false);
        page4 = buildTab4.createView(isEmployer, page4, delete);


        //allgemein Tab management
        Map<Tab, Component> tabsToPages = new HashMap<>();
        if(!isEmployer) {
            tabsToPages.put(tab1, page1);
            tabsToPages.put(tab2, page2);
        }
        tabsToPages.put(tab3, page3);
        tabsToPages.put(tab4, page4);
        if(isEmployer) {
            Tabs tabs = new Tabs(tab3, tab4);
            tabs.setSelectedTab(tab4);
            Div pages = new Div(page3, page4);
            tabs.addSelectedChangeListener(event -> {
                tabsToPages.values().forEach(page -> page.setVisible(false));
                Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
                selectedPage.setVisible(true);
            });
            add(tabs, pages);

        }
        else {
            Tabs tabs = new Tabs(tab1, tab2, tab3, tab4);
            tabs.setSelectedTab(tab1);
            Div pages = new Div(page1, page2, page3, page4);
            tabs.addSelectedChangeListener(event -> {
                tabsToPages.values().forEach(page -> page.setVisible(false));
                Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
                selectedPage.setVisible(true);
            });
            add(tabs, pages);

        }
        //switch tabs listener


        //add(tabs, pages);
    }

    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }

    private boolean getTrueIfSessionIsEmployer(){
        int rolle = getCurrentUser().getRole();
        return (rolle==2)?true:false;
    }

    private Component createTitle() {
        return new H3("Einstellungen");
    }
}
