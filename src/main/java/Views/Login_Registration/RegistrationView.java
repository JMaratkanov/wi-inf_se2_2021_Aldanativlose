package Views.Login_Registration;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "registration_alt")
@PageTitle("Registration Form alt")
public class RegistrationView extends Composite {

    //Welche Felder sollen noch hinzu?
    @Override
    protected Component initContent() {
        TextField firstName = new TextField("Vorname");
        TextField lastName = new TextField("Nachname");
        EmailField email = new EmailField("E-Mail");
        PasswordField password1 = new PasswordField("Passwort");
        PasswordField password2 = new PasswordField("Passwort bestätigen");

        return new VerticalLayout(
                new H2("Register"),
                firstName,
                lastName,
                email,
                password1,
                password2,
                new Button("Registrieren", event -> register(
                        firstName.getValue(),
                        lastName.getValue(),
                        email.getValue(),
                        password1.getValue(),
                        password2.getValue()
                ))
        );

    }

    private void register(String firstName, String lastName, String email, String password1, String password2) {
        if (firstName.trim().isEmpty()) {
            Notification.show("Geben Sie Ihren Vornamen an");
        } else if (lastName.trim().isEmpty()) {
            Notification.show("Geben Sie Ihren Nachnamen an");
        } else if (email.isEmpty()) {
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
