package Views.Login_Registration;


import Views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dtos.UserDTO;
import dtos.impl.UserDTOimpl;

@Route(value = "registration_student", layout = MainView.class)
@PageTitle("Registration Student")
public class RegistrationStudent extends Div {

    private EmailField email1 = new EmailField("Email Adresse");
    private EmailField email2 = new EmailField("Email Adresse bestätigen");
    private PasswordField password1 = new PasswordField("Passwort");
    private PasswordField password2 = new PasswordField("Passwort bestätigen");

    private Button cancel = new Button("Abbrechen");
    private Button save = new Button("Registrieren");

    private Binder<UserDTO> binder = new Binder(UserDTOimpl.class);

    public RegistrationStudent() {
        addClassName("registration-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        //binder.bindInstanceFields(this);
        //clearForm();

        //cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> register(
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
        return new H3("Studenten Registrierung");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        email1.setErrorMessage("Bitte geben Sie eine gültige E-Mail Adresse an");
        email2.setErrorMessage("Bitte geben Sie eine gültige E-Mail Adresse an");
        formLayout.add(email1, email2, password1, password2);
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

    private void register(String email1, String email2, String password1, String password2) {
        if (email1.isEmpty()) {
            Notification.show("Geben Sie Ihre E-Mail Adresse an");
        } else if (email2.isEmpty()) {
            Notification.show("Bestätigen Sie Ihre E-Mail Adresse");
        } else if (!email1.equals(email2)) {
            Notification.show("E-Mail Adressen stimmen nicht überein");
        } else if (password1.isEmpty()) {
            Notification.show("Geben Sie ein Passwort an");
        } else if (password2.isEmpty()) {
            Notification.show("Bestätigen Sie Ihr Passwort");
        } else if (!password1.equals(password2)) {
            Notification.show("Passwörter stimmen nicht überein");
        } else {
            //authService.register(username, password1);
            Notification.show("E-Mail Bestätigung versendet!");
        }
    }
}