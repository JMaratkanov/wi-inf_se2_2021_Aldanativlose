package ui.appViews.SettingsViewParts;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;

public class SettingsView_Tab3 {
    public Div createView(Div page3, EmailField email1, EmailField email2, PasswordField password1, PasswordField password2, Button cancel, Button save) {
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
        return page3;
    }
}
