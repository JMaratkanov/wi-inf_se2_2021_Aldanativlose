package ui.Login_Registration;

/*
@Route(value = "registration", layout = MainView.class)
@PageTitle("Registration")
public class RegistrationViewOld extends Div {

    private TextField firstname = new TextField("Vorname");
    private TextField lastname = new TextField("Nachname");
    private EmailField email = new EmailField("Email Adresse");
    private PasswordField password1 = new PasswordField("Passwort");
    private PasswordField password2 = new PasswordField("Passwort bestätigen");

    private Button cancel = new Button("Abbrechen");
    private Button save = new Button("Registrieren");

    private Binder<UserDTO> binder = new Binder(UserDTOimpl.class);

    public RegistrationViewOld() {
        addClassName("registration-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        //binder.bindInstanceFields(this);
        //clearForm();

        //cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> register(
                firstname.getValue(),
                lastname.getValue(),
                email.getValue(),
                password1.getValue(),
                password2.getValue()
        ));
    }


    private void clearForm() {
        binder.setBean(new UserDTOimpl());
    }


    private Component createTitle() {
        return new H3("Persönliche Informationen");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        email.setErrorMessage("Bitte geben Sie eine gültige E-Mail Adresse an");
        formLayout.add(firstname, lastname, email, password1, password2);
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
*/