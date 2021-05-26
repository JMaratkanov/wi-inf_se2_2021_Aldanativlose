package Views.Login_Registration;

import Views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "selection", layout = MainView.class)
@PageTitle("Selection")
public class Selection extends VerticalLayout {

    private Button registerStudent = new Button("Als Student registrieren", event -> UI.getCurrent().navigate("registration_student"));
    private Button registerEmployer = new Button("Als Unternehmen registrieren", event -> UI.getCurrent().navigate("registration_employer"));

    public Selection() {
        addClassName("selection");

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
        buttonLayout.add(createTitle());
        registerStudent.setWidth("15%");
        registerEmployer.setWidth("15%");
        buttonLayout.add(registerStudent);
        buttonLayout.add(registerEmployer);
        return buttonLayout;
    }
}