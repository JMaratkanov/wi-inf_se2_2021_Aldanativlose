package ui.appViews.SettingsViewParts;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import control.LoginControl;
import control.SettingsControl;
import control.exceptions.DatabaseUserException;
import dtos.UserDTO;
import globals.Globals;

public class SettingsView_Tab3 {
    private LoginControl loginControl = new LoginControl(); //Um die current user ID zu bekommen
    private SettingsControl settingsControl = new SettingsControl();

    public Div createView(Div page3, PasswordField alt, PasswordField neu,PasswordField neu2, Button cancel, Button save) {

        FormLayout formLayout = new FormLayout();
        formLayout.add(alt, neu, neu2);
        page3.add(formLayout);

        //init Buttons on Page
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        page3.add(buttonLayout);

        //Dialog
        Dialog dialog = new Dialog();
        dialog.setWidth("400px");
        dialog.setHeight("150px");
        dialog.add(new Text("Passwort Änderung bestätigen"));
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(false);

        //Dialog Buttons
        Button confirmDialog = new Button("Bestätigen");
        confirmDialog.addClickListener(event -> {
            changePassword( alt.getValue(), neu.getValue(), neu2.getValue());
            dialog.close();
        });

        Button cancelDialog = new Button("Abbrechen", event -> {
            dialog.close();
        });

        //Dialog build from parts
        dialog.add(new Div( confirmDialog, cancelDialog));

        save.addClickListener(e -> {
            dialog.open();
        });

        return page3;
    }

    private void changePassword(String alt , String neu, String neu2){
        int ID = getCurrentUser().getId();

        if(settingsControl.checkIfOldPasswordCorrect(ID, alt)) {
            if(checkIfNewPasswordsMatch(neu, neu2)){

                try {
                    settingsControl.updatePassword(ID, neu);
                } catch (DatabaseUserException e) {
                    Dialog dialog = new Dialog();
                    dialog.add( new Text( e.getReason()) );
                    dialog.setWidth("400px");
                    dialog.setHeight("150px");
                    dialog.open();
                }

                Notification.show("Ihr Passwort wurde geändert");
            }
            else{
                Notification.show("Ihre neuen Passwörter stimmen nicht überein");
            }
        }
        else{
            Notification.show("Ihr aktuelles Passwort wurde nicht korrekt eingegeben");
        }
    }

    private boolean checkIfNewPasswordsMatch(String neu, String neu2) {
        return neu.equals(neu2);
    }

    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }

}