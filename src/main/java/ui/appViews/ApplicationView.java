package ui.appViews;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import dtos.InseratDTO;
import ui.layouts.AppLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "application", layout = AppLayout.class)
@PageTitle("Bewerbungen")
public class ApplicationView extends Div {
    ComboBox<String> filter = new ComboBox<>();
    Grid<String> tabelle = new Grid<>();

    public ApplicationView() {
        setId("application-view");
        addClassName("wrapper");
        add(createTitle());
        add(createcombobox());
        add(creatgrid());
    }

    private Component createcombobox() {
        add(new Text("Für folgende Stellen"));
        filter.setPlaceholder("-alle-");
        filter.setItems("-alle-");
        filter.setItems("Stelle A", "Stelle B");

        return filter;
    }
    private Component creatgrid(){

        //tabelle.setItems();  Hier die Inserate anbinden
       // tabelle.addColumn(::getName).setHeader("Jobtitel"); // Hier die Insterat Namen ansprechen
       // tabelle.addColumn(::getUnternehmen).setHeader("Unternehmen"); //Hier die Firma der Stelle holen
       // tabelle.addColumn(::getStatus).setHeader("Status"); //Hier der Status
       // tabelle.addColumn(::getmehr).setHeader("mehr");  //hier die sachen in "mehr"
        return tabelle;
    }


    private Component createTitle() {
        return new H3("Meine Bewerbungen");
    }
}
