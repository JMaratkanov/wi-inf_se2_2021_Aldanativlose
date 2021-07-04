package ui.Login_Registration;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import control.RegistrationControl;
import control.exceptions.DatabaseUserException;
import globals.Globals;
import ui.layouts.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dtos.UserDTO;
import dtos.impl.UserDTOimpl;

@Route(value = "registration_employer", layout = MainLayout.class)
@PageTitle("Registration Employer")
public class RegistrationEmployer extends Div {

    private TextField companyName = new TextField("Firmenname");
    private TextField country = new TextField("Land des Hauptsitzes");

    private RegistrationControl registrationControl = new RegistrationControl();

    private TextField street = new TextField("Straße");
    private TextField number = new TextField("Haus Nr.");
    private TextField plz = new TextField("PLZ");
    private TextField place = new TextField("Ort");
    private EmailField email1 = new EmailField("Email Adresse");
    private EmailField email2 = new EmailField("Email Adresse bestätigen");
    private PasswordField password1 = new PasswordField("Passwort");
    private PasswordField password2 = new PasswordField("Passwort bestätigen");

    private Button back = new Button("Zurück");
    private Button save = new Button("Registrieren");

    private Binder<UserDTO> binder = new Binder(UserDTOimpl.class);

    public RegistrationEmployer() {
        addClassName("registration-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        back.addClickListener(e -> UI.getCurrent().navigate("selection"));
        save.addClickListener(e -> register(
                companyName.getValue(),
                country.getValue(),
                street.getValue(),
                number.getValue(),
                place.getValue(),
                plz.getValue(),
                email1.getValue(),
                email2.getValue(),
                password1.getValue(),
                password2.getValue()
        ));

        getElement().getStyle().set("background-color", Globals.Backgrounds.VIEW_BACKGROUND);
        getElement().getStyle().set("margin-top", "16px");
        getElement().getStyle().set("padding-top", "1px");
        getElement().getStyle().set("padding-bottom", "1px");
    }

    private Component createTitle() {
        return new H3("Unternehmens Registrierung");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        email1.setErrorMessage("Bitte geben Sie eine gültige E-Mail Adresse an");
        email2.setErrorMessage("Bitte geben Sie eine gültige E-Mail Adresse an");
        formLayout.add(companyName, country, street, number, place, plz, email1, email2, password1, password2);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(back);
        return buttonLayout;
    }

    private void register(String companyName, String country, String street, String number, String place, String plz, String employerEmail1, String employerEmail2, String employerPassword1, String employerPassword2) {
        if (employerEmail1.isEmpty()) {
            Notification.show("Geben Sie Ihre E-Mail Adresse an");
        } else if (employerEmail2.isEmpty()) {
            Notification.show("Bestätigen Sie Ihre E-Mail Adresse");
        } else if (!employerEmail1.equals(employerEmail2)) {
            Notification.show("E-Mail Adressen stimmen nicht überein");
        } else if (employerPassword1.isEmpty()) {
            Notification.show("Geben Sie ein Passwort an");
        } else if (employerPassword2.isEmpty()) {
            Notification.show("Bestätigen Sie Ihr Passwort");
        } else if (!employerPassword1.equals(employerPassword2)) {
            Notification.show("Passwörter stimmen nicht überein");
        } else if (companyName.isEmpty()) {
            Notification.show("Bitte geben Sie einen Firmennamen an!");
        } else if (country.isEmpty()) {
            Notification.show("Bitte geben Sie das Land des Hauptsitzes an!");
        } else if (street.isEmpty()) {
            Notification.show("Bitte geben Sie eine Staße an!");
        } else if (number.isEmpty()) {
            Notification.show("Bitte geben Sie eine Hausnumer an!");
        } else if (place.isEmpty()) {
            Notification.show("Bitte geben Sie eine Stadt an!");
        } else if (plz.isEmpty()) {
            Notification.show("Bitte geben Sie eine Postleitzahl an!");
        } else if (plz.length() != 5) {
            Notification.show("Bitte geben sie eine korrekte Postleitzahl an!");
        } else {
            try {
                registrationControl.registerEmployerWithJDBC(companyName, country, street, number, place, plz, employerEmail1, employerPassword1);
                Notification.show("Registrierung erfolgreich: E-Mail Bestätigung versendet!");
                UI.getCurrent().navigate(Globals.Pages.LOGIN_VIEW);
            } catch (DatabaseUserException databaseException) {
                Dialog dialog = new Dialog();
                dialog.add( new Text( databaseException.getReason()) );
                dialog.setWidth("400px");
                dialog.setHeight("150px");
                dialog.open();
            }
        }
    }
}