package ui.Login_Registration;

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

    private TextField compamyName = new TextField("Firmenname");
    private TextField country = new TextField("Land des Hauptsitzes");

    /*
    public static final ContentMode HTML

    Label textLabel = new Label(
            "Text where formatting characters, such as \\n, " +
                    "and HTML, such as <b>here</b>, are quoted.",
            ContentMode.HTML);
     */

    private TextField street = new TextField("Straße");
    private TextField number = new TextField("Haus Nr.");
    private TextField place = new TextField("Ort");
    private NumberField plz = new NumberField("PLZ");
    private EmailField email1 = new EmailField("Email Adresse");
    private EmailField email2 = new EmailField("Email Adresse bestätigen");
    private PasswordField password1 = new PasswordField("Passwort");
    private PasswordField password2 = new PasswordField("Passwort bestätigen");

    private Button cancel = new Button("Abbrechen");
    private Button save = new Button("Registrieren");

    private Binder<UserDTO> binder = new Binder(UserDTOimpl.class);

    public RegistrationEmployer() {
        addClassName("registration-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        //binder.bindInstanceFields(this);
        //clearForm();

        //cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> register(
                compamyName.getValue(),
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
    }

    /*
    private void clearForm() {
        binder.setBean(new UserDTOimpl());
    }
     */

    private Component createTitle() {
        return new H3("Unternehmens Registrierung");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        email1.setErrorMessage("Bitte geben Sie eine gültige E-Mail Adresse an");
        email2.setErrorMessage("Bitte geben Sie eine gültige E-Mail Adresse an");
        formLayout.add(compamyName, country, street, number, place, plz, email1, email2, password1, password2);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }

    private void register(String companyName, String country, String street, String number, String place, double plz, String email1, String email2, String password1, String password2) {
        if (email1.isEmpty()) {
            Notification.show("Geben Sie Ihre E-Mail Adresse an");
        } else if (password1.isEmpty()) {
            Notification.show("Geben Sie ein Passwort an");
        } else if (!password1.equals(password2)) {
            Notification.show("Passwörter stimmen nicht überein");
        } else {
            //authService.register(username, password1);
            Notification.show("E-Mail Bestätigung versendet!");
        }
    }
}