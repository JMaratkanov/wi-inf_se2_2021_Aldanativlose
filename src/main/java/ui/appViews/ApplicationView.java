package ui.appViews;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import control.applicationControl;
import control.exceptions.DatabaseUserException;
import db.exceptions.DatabaseLayerException;
import dtos.UserDTO;
import dtos.impl.ApplSetForEmployerDTO;
import dtos.impl.BewerbungDTOimpl;
import globals.Globals;
import ui.layouts.AppLayout;

import java.util.List;

@Route(value = "application", layout = AppLayout.class)
@PageTitle("Bewerbungen")
public class ApplicationView extends Div {
    private applicationControl applicationControl = new applicationControl();
    private int ID = getCurrentUser().getId();
    private boolean isEmployer = getTrueIfSessionIsEmployer();

    ComboBox<String> filter = new ComboBox<>();

    public ApplicationView() {
        setId("application-view");
        addClassName("wrapper");
        add(createTitle());
        add(createcombobox());

        if(isEmployer){
            add(creatGridEmployer());
        }else {
            add(creatgrid());
        }
    }

    private Component creatGridEmployer() {
        List<ApplSetForEmployerDTO> anzeigen = null;

        try {
            anzeigen = applicationControl.getAllApllicantsByEmployerID(ID);
        } catch (DatabaseLayerException e) {
            Dialog dialog = new Dialog();
            dialog.add( new Text( e.getReason()) );
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
        }

        Grid<ApplSetForEmployerDTO> grid = new Grid<>();

        grid.setItems(anzeigen);
        grid.addColumn(ApplSetForEmployerDTO::getStelle).setHeader("Stelle").setFlexGrow(0).setWidth("200px");
        grid.addColumn(ApplSetForEmployerDTO::getStudent_vorname).setHeader("Bewerber Vorname").setFlexGrow(0).setWidth("200px");
        grid.addColumn(ApplSetForEmployerDTO::getStudentname).setHeader("Nachname").setFlexGrow(0).setWidth("200px");
        grid.addColumn(ApplSetForEmployerDTO::getStatus).setHeader("Status").setFlexGrow(0).setWidth("250px");
        grid.addColumn(ApplSetForEmployerDTO::getStudID).setHeader("HIDE").setFlexGrow(0).setWidth("100px").setVisible(false);


        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addItemClickListener(event -> {
            Dialog d = new Dialog();
            d.add( new Text( "TODO Clicked Item: " + event.getItem()) );
            d.setWidth("800px");
            d.setHeight("500px");
            d.open();
        });

        grid.addColumn(
                new NativeButtonRenderer<>("Bewerbung ablehnen",
                        clickedItem -> {
                            apllicationEdit(clickedItem.getID(),2);
                            Notification.show("Bewerbung abgelehnt!");
                        })
        ).setFlexGrow(0).setWidth("200px");

        grid.addColumn(
                new NativeButtonRenderer<>("Zum Vorstellungsgespräch einladen",
                        clickedItem -> {
                            apllicationEdit(clickedItem.getID(),3);
                            Notification.show("Bewerber ist eingeladen!");
                        })
        ).setFlexGrow(0).setWidth("250px");

        return grid;
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
            anzeigen = applicationControl.getAllApplicationsForUserWithID(ID);
        } catch (DatabaseLayerException e) {
            Dialog dialog = new Dialog();
            dialog.add( new Text( e.getReason()) );
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
        }

        Grid<BewerbungDTOimpl> grid = new Grid<>();

        grid.setItems(anzeigen);
        grid.addColumn(BewerbungDTOimpl::getName).setHeader("Jobtitel").setFlexGrow(0).setWidth("200px");
        grid.addColumn(BewerbungDTOimpl::getUnternehmen).setHeader("Unternehmen").setFlexGrow(0).setWidth("200px");
        grid.addColumn(BewerbungDTOimpl::getStatus).setHeader("Status").setFlexGrow(0).setWidth("250px");
        grid.addColumn(BewerbungDTOimpl::getMehr).setHeader("mehr").setFlexGrow(0).setWidth("250px");

        //Notification.show(Integer.toString(anzeigen.get(0).getID()));

        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addItemClickListener(event -> {
            Dialog d = new Dialog();
            d.add( new Text( "TODO Clicked Item: " + event.getItem()) );
            d.setWidth("800px");
            d.setHeight("500px");
            d.open();
        });

        return grid;
    }

    private void apllicationEdit(int applicationID, int status) {
        try {
            applicationControl.apllicationEdit(applicationID,status);

        } catch (DatabaseUserException e) {
            Dialog dialog = new Dialog();
            dialog.add(new Text(e.getReason()));
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
        }
    }

    private Component createTitle() {
        if(isEmployer) {
            return new H3("Eingegangene Bewerbungen");
        }
        return new H3("Meine Bewerbungen");
    }

    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }

    private boolean getTrueIfSessionIsEmployer(){
        int rolle = getCurrentUser().getRole();
        return rolle == 2;
    }
}
