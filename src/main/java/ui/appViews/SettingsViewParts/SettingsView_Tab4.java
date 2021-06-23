package ui.appViews.SettingsViewParts;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Shortcuts;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import control.LoginControl;
import control.SettingsControl;
import control.exceptions.DatabaseUserException;
import dtos.UserDTO;
import globals.Globals;
import ui.layouts.AppLayout;

public class SettingsView_Tab4 {
    private SettingsControl settingsControl = new SettingsControl();
    private int id = getCurrentUser().getId();

    public Div createView(Div page4, Button delete) {
        //Button
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        delete.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(delete);
        page4.add(buttonLayout);

        //Dialog
        Dialog dialog = new Dialog();
        dialog.setWidth("400px");
        dialog.setHeight("150px");
        dialog.add(new Text("Wollen Sie Ihr Konto wirklich löschen?"));
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        Button confirmButton = new Button("Bestätigen", event -> delete());

        Button cancelButton = new Button("Abbrechen", event -> {
            dialog.close();
        });

        // Cancel action on ESC press
        Shortcuts.addShortcutListener(dialog, () -> {
            dialog.close();
        }, Key.ESCAPE);

        dialog.add(new Div( confirmButton, cancelButton));
        delete.addClickListener(event -> dialog.open());
        page4.add(dialog);

        return page4;
    }

    private void delete() {
        // Vor Löschen des Users eventuell erneut das Passwort abfragen
        // Dazu wäre noch ein neues Feld notwendig, ab besten in dem Dialog
        //loginControl.authenticate()
        try {
            settingsControl.deleteStudentWithJDBC(this.id);
            Notification.show("Konto erfolgreich gelöscht!");
            UI.getCurrent().getSession().close();
            UI.getCurrent().getPage().setLocation("./");
        } catch (DatabaseUserException databaseException) {
            Dialog dialog = new Dialog();
            dialog.add( new Text( databaseException.getReason()) );
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
        }
    }

    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }
}
