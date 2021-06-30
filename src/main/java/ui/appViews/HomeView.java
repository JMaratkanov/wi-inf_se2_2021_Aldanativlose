package ui.appViews;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import ui.layouts.AppLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "home", layout = AppLayout.class)
@PageTitle("Home")
public class HomeView extends Div {
    Grid<String> offeneBew = new Grid<>();
    Grid<String> neueBew = new Grid<>();
    private TextField suche = new TextField("Suche");
    Select<String> wasSelect = new Select<>();
    private TextField plztext = new TextField("PLZ");
    Select<String> umkreisSelect = new Select<>();

    public HomeView() {
        setId("home-view");
        addClassName("wrapper");
        add(createTitle());
        add(createLayout());
        add(filter());
    }

    private Component createLayout() {
        FormLayout gridLayout = new FormLayout();
        gridLayout.add(offenebewerbungen(),neueAngebote());
        return gridLayout;
    }

    private Component neueAngebote() {
        add(createTitle2());
        //neue Angebote anbinden
        return neueBew;
    }

    private Component offenebewerbungen() {
        //offeneBewerbungen
        return offeneBew;
    }

    private  Component filter(){
        wasSelect.setItems("Option one", "Option two");
        wasSelect.setLabel("Was?");
        umkreisSelect.setItems("5km","10km","20km","50km","+50km");
        umkreisSelect.setLabel("Umkreis");
        suche.setMaxWidth("1000px");
        wasSelect.setMaxWidth("100px");
        plztext.setMaxWidth("100px");
        umkreisSelect.setMaxWidth("100px");
        FormLayout formLayout = new FormLayout();
        formLayout.add(suche,wasSelect,plztext,umkreisSelect);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("1000px", 1),
                new FormLayout.ResponsiveStep("100px", 2),
                new FormLayout.ResponsiveStep("100px", 3),
                new FormLayout.ResponsiveStep("100px", 4));

        return formLayout;
    }


    private Component createTitle() { return new H2("Guten Tag!"); }
    private Component createTitle2() { return new H4("Neue Stellenangebote");}
    private Component createTitle3() { return new H4("Deine Bewerbungen");}



}
