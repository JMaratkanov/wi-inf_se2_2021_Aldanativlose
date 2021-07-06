package ui.Login_Registration;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import globals.Globals;
import ui.layouts.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "selection", layout = MainLayout.class)
@PageTitle("Selection")
public class Selection extends VerticalLayout{

    private Button registerStudent = new Button("Als Student registrieren", event -> UI.getCurrent().navigate("registration_student"));
    private Button registerEmployer = new Button("Als Unternehmen registrieren", event -> UI.getCurrent().navigate("registration_employer"));

    public Selection() {
        addClassName("selection");
        add(createButtonLayout());
        setAlignItems(Alignment.CENTER);
    }

    private Component createTitle() {
        return new H2("Registrieren");
    }

    private Component createButtonLayout() {
        VerticalLayout buttonLayout = new VerticalLayout();
        buttonLayout.addClassName("button-layout");
        registerStudent.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerEmployer.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerStudent.getElement().getStyle().set("border", "solid 2px");
        registerStudent.getElement().getStyle().set("border-color", "#233348");
        //registerStudent.getElement().getStyle().set("box-shadow", "2px 2px 4px #000000");
        registerEmployer.getElement().getStyle().set("border", "solid 2px");
        registerEmployer.getElement().getStyle().set("border-color", "#233348");
        //registerEmployer.getElement().getStyle().set("box-shadow", "2px 2px 4px #000000");
        buttonLayout.add(createTitle());
        registerStudent.setWidth("300px");
        registerEmployer.setWidth("300px");
        buttonLayout.add(registerStudent);
        buttonLayout.add(registerEmployer);
        return buttonLayout;
    }
}