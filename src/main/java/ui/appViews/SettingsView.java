package ui.appViews;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import control.LoginControl;
import control.SettingsControl;
import control.exceptions.DatabaseUserException;
import ui.layouts.AppLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
/*
*
* In Bearbeitung von Johann 03.06.
* TODO in Zeile 112,163,184,202
*  */


@Route(value = "setting", layout = AppLayout.class)
@PageTitle("Settings")
public class SettingsView extends Div {
    private SettingsControl settingsControl = new SettingsControl();
    private LoginControl loginControl = new LoginControl();

    //Tab1
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

    //Tab3 Passwort ändern
    private EmailField email1 = new EmailField("Email Adresse");
    private EmailField email2 = new EmailField("Email Adresse bestätigen");
    private PasswordField password1 = new PasswordField("Passwort");
    private PasswordField password2 = new PasswordField("Passwort bestätigen");
    private Button cancel = new Button("Abbrechen");
    private Button save = new Button("Ändern");

    //Tab4 Konto löschen
    private Button delete = new Button("Konto löschen");

    //Konstruktor Basic
    public SettingsView() {
        addClassName("wrapper");
        setId("settings-view");
        add(createTitle());
        //add(new Text("Hier können Sie Anpassungen an den Einstellungen vornehmen."));

        //Tab 1
        Tab tab1 = new Tab("Allgemeine Daten");
        Div page1 = new Div();
        SettingsView_Tab1(page1);

        //Tab2
        Tab tab2 = new Tab("Lebenslauf anpassen");
        Div page2 = new Div();
        page2.setVisible(false);
        SettingsView_Tab2(page2);

        //Tab3
        Tab tab3 = new Tab("Passwort ändern");
        Div page3 = new Div();
        page3.setVisible(false);
        SettingsView_Tab3(page3);

        //Tab4
        Tab tab4 = new Tab("Konto löschen");
        Div page4 = new Div();
        page4.setText("Wollen Sie Ihr Konto löschen?");
        page4.setVisible(false);
        SettingsView_Tab4(page4);

        //allgemein
        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(tab1, page1);
        tabsToPages.put(tab2, page2);
        tabsToPages.put(tab3, page3);
        tabsToPages.put(tab4, page4);

        Tabs tabs = new Tabs(tab1, tab2, tab3, tab4);
        tabs.setSelectedTab(tab1);
        Div pages = new Div(page1, page2, page3, page4);

        //switch tabs
        tabs.addSelectedChangeListener(event -> {
            tabsToPages.values().forEach(page -> page.setVisible(false));
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
        });

        add(tabs, pages);
    }
    //Konstruktor von Tab1: Profil aktualisieren
    private void SettingsView_Tab1(Div page1) {
        //TODO get this vals from DB
        //###########################
        String vNameFromDB = "getthisfromDB"; //TextFields
        String nNameFromDB = "getthisfromDB";
        String desFromDB = "getthisfromDB";  //TextAreas
        String skillFromDB = "getthisfromDB";
        String refFromDB = "getthisfromDB";
        String fachfromDB = "getthisfromDB"; //Selects
        String sGangfromDB = "getthisfromDB";
        //String semFromDB = "getthisfromDB";
        //##########################

        //init Placeholders Textfields & Areas
        Vorname.setPlaceholder(vNameFromDB);
        Nachname.setPlaceholder(nNameFromDB);
        description.setPlaceholder(desFromDB);
        skills.setPlaceholder(skillFromDB);
        references.setPlaceholder(refFromDB);

        //Datepicker
        datePicker.setLabel("Geburtstag");
        Div value = new Div();
        value.setText("Setze deinen Geburtstag");
        datePicker.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                value.setText("No date selected");
            } else {
                value.setText("Selected date: " + event.getValue());
            }
        });

        //Semester Datepicker
        semesterdatePicker.setLabel("Geburtstag");
        Div value2 = new Div();
        value2.setText("Studienbeginn: ");
        datePicker.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                value2.setText("No date selected");
            } else {
                value2.setText("Selected date: " + event.getValue());
            }
        });

        //Selects
        Fachbereich.setItems("Fisch", "Apfel", "Kürbis", fachfromDB);
        Fachbereich.setValue(fachfromDB);
        Fachbereich.setLabel("Fachbereich");
        Studiengang.setItems("Fisch", "Apfel", "Kürbis", sGangfromDB);
        Studiengang.setValue(sGangfromDB);
        Studiengang.setLabel("Studiengang");
        //Semester.setItems("Fisch", "Apfel", "Kürbis", semFromDB);
        //Semester.setValue(semFromDB);
        //Semester.setLabel("Semester");

        //Zsmkleben
        FormLayout formLayout = new FormLayout();
        formLayout.add(Vorname,Nachname, datePicker, value,Fachbereich,Studiengang, value2 /*Semester*/, description,skills,references);

        //Button
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(actualize);
        //TODO update vals into DB -> schicke daten richtung DB

        save.addClickListener(e -> update(
                Vorname.getValue(),
                Nachname.getValue(),
                description.getValue(),
                skills.getValue(),
                references.getValue(),
                datePicker.getValue(),
                Fachbereich.getValue(),
                Studiengang.getValue(),
                semesterdatePicker.getValue()
                //Semester.getValue()
        ));

        page1.add(formLayout, buttonLayout);
    }

    private void update(String Vorname, String Nachname, String description, String skills, String references, LocalDate date, String fachbereich, String studiengang, LocalDate semester /*String semester*/) {
        int ID = loginControl.getCurrentUser().getId();

        try {
            settingsControl.updateStudentWithJDBC(ID, Vorname, Nachname, description, skills, references, date, fachbereich, studiengang, semester);
            Notification.show("Update erfolgreich!");
            UI.getCurrent().navigate("setting");
        } catch (DatabaseUserException databaseException) {
            Dialog dialog = new Dialog();
            dialog.add( new Text( databaseException.getReason()) );
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
        }

    }


    //Konstruktor von Tab2: Upload
    private void SettingsView_Tab2(Div page2) {
        //Upload
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setMaxFiles(1);
        upload.setDropLabel(new Label("Lade deinen Lebenslauf als PDF hoch"));
        upload.setAcceptedFileTypes("text/pdf");
        upload.setMaxFileSize(10000000);
        Div output = new Div();

        upload.addFileRejectedListener(event -> {
            Paragraph component = new Paragraph();
            MyUploadContext.showOutput(event.getErrorMessage(), component, output);
        });
        upload.getElement().addEventListener("file-remove", event -> {
            output.removeAll();
            //TODO PDF irgendwo speichern
        });

        page2.add(upload, output);
    }

    //Konstruktor von Tab3: Passwort ändern
    public void SettingsView_Tab3(Div page3){
        //TODO Normalerweise wären hier nur die Felder Altes Passwort, Neues Passwort und Neues Passwort bestätigen notwendig
        // Altes Passwort zur Kontrolle ist ZWINGEND NOTWENDIG!
        FormLayout formLayout = new FormLayout();
        formLayout.add(email1, email2, password1, password2);
        page3.add(formLayout);

        //Button
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        page3.add(buttonLayout);

        Dialog dialog = new Dialog();
        dialog.setWidth("400px");
        dialog.setHeight("150px");
        dialog.add(new Text("Passwort Änderung bestätigen"));
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(false);

        Span message = new Span();

        Button confirmButton = new Button("Bestätigen", event -> {
            message.setText("Passwort erfolgreich geändert");
            //TODO Hier Action - Confirm -> Delegation richtung DB
            dialog.close();
        });
        Button cancelButton = new Button("Abbrechen", event -> {dialog.close();});
    }

    //Konstruktor von Tab4: Konto löschen
    public void SettingsView_Tab4(Div page4){
        //Button
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(delete);
        page4.add(buttonLayout);

        //Dialog
        Dialog dialog = new Dialog();
        dialog.setWidth("400px");
        dialog.setHeight("150px");
        dialog.add(new Text("Wollen Sie wirklich Ihr Konto löschen?"));
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);
        Span message = new Span();

        Button confirmButton = new Button("Bestätigen", event -> {
            message.setText("Konto gelöscht!");
            //TODO Hier Action - Confirm -> Delegation richtung DB
            dialog.close();
        });
        Button cancelButton = new Button("Abbrechen", event -> {
            message.setText("Wir freuen uns dass du geblieben bist :)");
            dialog.close();
        });

        // Cancel action on ESC press
        Shortcuts.addShortcutListener(dialog, () -> {
            message.setText("Cancelled...");
            dialog.close();
        }, Key.ESCAPE);

        dialog.add(new Div( confirmButton, cancelButton));
        delete.addClickListener(event -> dialog.open());
        page4.add(dialog);
    }

    private Component createTitle() {
        return new H3("Einstellungen");
    }
}

//Diese Klasse wird nur in Tab 2 zum Upload verwendet; Da Sie nichts mit der restlichen Logik zu tun hat ist sie der Übersicht halber
//ausgelagert. Die Methode kann wiederverwendet werden

class MyUploadContext {
    static void showOutput(String text, Component content, HasComponents outputContainer) {
        HtmlComponent p = new HtmlComponent(Tag.P);
        p.getElement().setText(text);
        outputContainer.add(p);
        outputContainer.add(content);
    }
}