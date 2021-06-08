package ui.appViewsEmployer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ui.layouts.AppLayout;

//@Route(value = "application", layout = AppLayout.class)
//@PageTitle("BewerbungenStudent")
public class ApplicationViewEmployer extends Div {
    ComboBox<String> filter = new ComboBox<>();
    Grid<String> tabelle = new Grid<>();

    public ApplicationViewEmployer() {
        setId("application-view");
        addClassName("wrapper");
        add(createTitle());
        add(createcombobox());
        add(creatgrid());
    }
    private Component createcombobox() {
        add(new Text("Für folgende Anzeigen"));
        filter.setPlaceholder("-alle-");
        filter.setItems("-alle-");
        filter.setItems("Anzeige A", "Anzeige B");

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
        return new H3("Bewerbungen");
    }
}
