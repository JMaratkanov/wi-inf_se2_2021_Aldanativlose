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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import control.LoginControl;
import control.SettingsControl;
import dtos.UserDTO;
import globals.Globals;

public class SettingsView_Tab4 {
    private LoginControl loginControl = new LoginControl(); //Um die current user ID zu bekommen
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

        return page4;
    }

    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }
}
