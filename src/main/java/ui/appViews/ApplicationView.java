package ui.appViews;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import control.applicationControl;
import db.exceptions.DatabaseLayerException;
import dtos.UserDTO;
import dtos.impl.BewerbungDTOimpl;
import globals.Globals;
import ui.layouts.AppLayout;

import java.util.List;

@Route(value = "application", layout = AppLayout.class)
@PageTitle("Bewerbungen")
public class ApplicationView extends Div {
    private applicationControl control = new applicationControl();
    private int ID = getCurrentUser().getId();

    ComboBox<String> filter = new ComboBox<>();

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
        List<BewerbungDTOimpl> anzeigen = null;

        try {
            anzeigen = control.getAllApplicationsForUserWithID(ID);
        } catch (DatabaseLayerException e) {
            Dialog dialog = new Dialog();
            dialog.add( new Text( e.getReason()) );
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
        }

        Grid<BewerbungDTOimpl> grid = new Grid<>();

        grid.setItems(anzeigen);
        grid.addColumn(BewerbungDTOimpl::getName).setHeader("Jobtitel").setFlexGrow(0).setWidth("210px");
        grid.addColumn(BewerbungDTOimpl::getUnternehmen).setHeader("Unternehmen").setFlexGrow(0).setWidth("800px");
        grid.addColumn(BewerbungDTOimpl::getStatus).setHeader("Status").setFlexGrow(0).setWidth("100px");
        grid.addColumn(BewerbungDTOimpl::getMehr).setHeader("mehr").setFlexGrow(0).setWidth("100px");

        //Notification.show(Integer.toString(anzeigen.get(0).getID()));

        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addItemClickListener(event -> {
            Dialog d = new Dialog();
            d.add( new Text( "Clicked Item: " + event.getItem()) );
            d.setWidth("800px");
            d.setHeight("500px");
            d.open();
        });

        grid.addColumn(
                new NativeButtonRenderer<>("Button",
                        clickedItem -> {
                            // mach was
                        })
        ).setFlexGrow(0).setWidth("250px");

        return grid;
    }

    private Component createTitle() {
        return new H3("Meine Bewerbungen");
    }

    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }
}
