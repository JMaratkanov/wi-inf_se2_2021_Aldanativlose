package Views.Login_Registration;

import Views.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
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

@Route(value = "selection", layout = MainView.class)
@PageTitle("Selection")
public class Selection extends VerticalLayout {

    private Button registerStudent = new Button("Als Student registrieren", event -> UI.getCurrent().navigate("registration_student"));
    private Button registerEmployer = new Button("Als Unternehmen registrieren", event -> UI.getCurrent().navigate("registration_employer"));

    public Selection() {
        addClassName("selection");

        add(createTitle());
        //add(createFormLayout());
        add(createButtonLayout());

        setAlignItems(Alignment.CENTER);

    }

    private Component createTitle() {
        return new H3("Registrieren");
    }

    private Component createButtonLayout() {
        VerticalLayout buttonLayout = new VerticalLayout();
        buttonLayout.addClassName("button-layout");
        registerStudent.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerEmployer.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(registerStudent);
        buttonLayout.add(registerEmployer);
        return buttonLayout;
    }
}