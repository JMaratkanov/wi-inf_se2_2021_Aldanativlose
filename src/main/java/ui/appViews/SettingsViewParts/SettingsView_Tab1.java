package ui.appViews.SettingsViewParts;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import control.LoginControl;
import control.SettingsControl;
import control.exceptions.DatabaseUserException;

import java.time.LocalDate;

public class SettingsView_Tab1 {
    private LoginControl loginControl = new LoginControl(); //Um die current user ID zu bekommen
    private SettingsControl settingsControl = new SettingsControl();

    public Div createView(TextField Vorname, TextField Nachname, TextArea description, TextArea skills, TextArea references, DatePicker datePicker, Select<String> Fachbereich, Select<String> Studiengang, DatePicker semesterdatePicker,Button actualize){
        Div page1 = new Div();

        //TODO get this vals from DB
        //###########################
        String vNameFromDB = "getthisfromDB"; //TextFields
        String nNameFromDB = "getthisfromDB";
        String desFromDB = "getthisfromDB";  //TextAreas
        String skillFromDB = "getthisfromDB";
        String refFromDB = "getthisfromDB";
        String fachfromDB = "getthisfromDB"; //Selects
        String sGangfromDB = "getthisfromDB";
        String semFromDB = "getthisfromDB";
        String gebFromDB = "getthisfromDB";
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
        semesterdatePicker.setLabel("Beginn des Studiums");
        Div value2 = new Div();
        value2.setText("Studienbeginn: ");
        semesterdatePicker.addValueChangeListener(event2 -> {
            if (event2.getValue() == null) {
                value2.setText("No date selected");
            } else {
                value2.setText("Selected date: " + event2.getValue());
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
        formLayout.add(Vorname,Nachname, datePicker, value, semesterdatePicker, value2, Fachbereich,Studiengang /*Semester*/, description,skills,references);

        //Button
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        actualize.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(actualize);
        //TODO update vals into DB -> schicke daten richtung DB

        actualize.addClickListener(e -> update(
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
        return page1;
    }
    private void update(String Vorname, String Nachname, String description, String skills, String references, LocalDate date, String fachbereich, String studiengang, LocalDate semester /*String semester*/) {
        int ID = loginControl.getCurrentUser().getId();

        try {
            //int id, String vorname, String nachname,  String description, String skills, String references, String fachbereich, LocalDate semester, String studiengang, LocalDate gebTag
            settingsControl.updateStudentWithJDBC(ID, Vorname, Nachname, description, skills, references,  fachbereich, semester, studiengang, date);
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

}
