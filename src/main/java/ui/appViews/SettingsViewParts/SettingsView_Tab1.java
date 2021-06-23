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
import control.SettingsControl;
import control.exceptions.DatabaseUserException;
import dtos.UserDTO;
import dtos.impl.StudentDTOimpl;
import globals.Globals;

import java.sql.Date;
import java.time.LocalDate;

public class SettingsView_Tab1 {
    private int ID = getCurrentUser().getId();

    //Vars um die Daten des aktuellen Users aus der Datenbank zu speichern um sie als Placeholder ins Eingabefeld des Formulars zu setzen
    private String vNameFromDB;
    private String nNameFromDB;
    private String desFromDB;  //TextAreas
    private String skillFromDB;
    private String refFromDB;
    private String fachfromDB; //Selects
    private String sGangfromDB;
    private Date semFromDB;
    private Date gebFromDB;

    private SettingsControl settingsControl = new SettingsControl();

    public Div createView(TextField Vorname, TextField Nachname, TextArea referenzen, TextArea kenntnisse, TextArea description, DatePicker datePicker, Select<String> Fachbereich, Select<String> Studiengang, DatePicker semesterdatePicker,Button actualize) {
        Div page1 = new Div();

        //Get current Userdata to fill in the placeholders
        getCurrentUserData();

        //init Placeholders Textfields & Areas
        Vorname.setPlaceholder(vNameFromDB);
        Nachname.setPlaceholder(nNameFromDB);
        description.setPlaceholder(desFromDB);
        kenntnisse.setPlaceholder(skillFromDB);
        referenzen.setPlaceholder(refFromDB);
        semesterdatePicker.setPlaceholder(semFromDB.toString());
        datePicker.setPlaceholder(gebFromDB.toString());

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
        Fachbereich.setItems("Informatik", "Angewandte Naturwissenschaften", "Elektrotechnik/Maschinenbau", fachfromDB);
        Fachbereich.setValue(fachfromDB);
        Fachbereich.setLabel("Fachbereich");
        Studiengang.setItems("W.Informatik (B.Sc.)","Informatik (B.Sc.)", "Applied Biology (B.Sc.)", "Elektrotechnik (B.Eng.)", "Maschinenbau (B.Eng.)", sGangfromDB);
        Studiengang.setValue(sGangfromDB);
        Studiengang.setLabel("Studiengang");

        //Zsmkleben
        FormLayout formLayout = new FormLayout();
        formLayout.add(Vorname,Nachname, datePicker, value, semesterdatePicker, value2, Fachbereich,Studiengang /*Semester*/, description,kenntnisse, referenzen);

        //Button
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        actualize.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(actualize);

        actualize.addClickListener(e -> update(
                Vorname.getValue(),
                Nachname.getValue(),
                referenzen.getValue(),
                kenntnisse.getValue(),
                description.getValue(),
                semesterdatePicker.getValue(),
                Studiengang.getValue(),
                Fachbereich.getValue(),
                datePicker.getValue()
        ));

        page1.add(formLayout, buttonLayout);
        return page1;
    }
    private void update(String vorname, String nachname, String referenzen, String kenntnisse, String kurzbeschreibung, LocalDate semester, String studiengang, String fachbereich, LocalDate gebDate) {
        if(vorname.isEmpty()){ vorname = vNameFromDB; }
        if(nachname.isEmpty()){ nachname = nNameFromDB; }
        if(kurzbeschreibung.isEmpty()){kurzbeschreibung = desFromDB; }
        if(kenntnisse.isEmpty()){ kenntnisse = skillFromDB; }
        if(referenzen.isEmpty()){ referenzen = refFromDB; }
        if(fachbereich.isEmpty()){ fachbereich = fachfromDB; }
        if(studiengang.isEmpty()){ studiengang = sGangfromDB; }
        if(gebDate == null) {gebDate = gebFromDB.toLocalDate();}
        if(semester == null) {semester = semFromDB.toLocalDate();}

        try {
            settingsControl.updateStudentWithJDBC(this.ID, vorname, nachname,  referenzen, kenntnisse,  kurzbeschreibung, semester, studiengang, fachbereich, gebDate);
            Notification.show("Update erfolgreich!");
            UI.getCurrent().navigate(Globals.Pages.HOME_VIEW);
            UI.getCurrent().navigate(Globals.Pages.SETTINGS_VIEW);
        } catch (DatabaseUserException databaseException) {
            Dialog dialog = new Dialog();
            dialog.add( new Text( databaseException.getReason()) );
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
        }
    }

    private void getCurrentUserData(){
        UserDTO currentUserValues = null;

        try {
            currentUserValues = settingsControl.getStudentWithJDBCByID(ID);
        } catch (DatabaseUserException databaseException) {
            Dialog dialog = new Dialog();
            dialog.add( new Text( databaseException.getReason()) );
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
        }
        StudentDTOimpl x = (StudentDTOimpl) currentUserValues;

        assert x != null;
        vNameFromDB = x.getFirstName();
        nNameFromDB = x.getLastName();
        desFromDB = x.getDesFromDB();
        skillFromDB = x.getSkillFromDB();
        refFromDB = x.getRefFromDB();
        fachfromDB = x.getFachfromDB();
        sGangfromDB = x.getsGangfromDB();
        semFromDB = x.getSemester();
        gebFromDB = x.getGeb_date();
    }

    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }
}
