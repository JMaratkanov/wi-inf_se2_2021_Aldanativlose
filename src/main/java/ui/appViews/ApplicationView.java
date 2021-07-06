package ui.appViews;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
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
public class  ApplicationView extends Div {
    private applicationControl applicationControl = new applicationControl();
    private int ID = getCurrentUser().getId();
    private boolean isEmployer = getTrueIfSessionIsEmployer();

    ComboBox<String> filter = new ComboBox<>();

    public ApplicationView() {
        setId("application-view");
        addClassName("wrapper");
        add(createTitle());

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
        } catch (DatabaseUserException e) {
            Dialog dialog = new Dialog();
            dialog.add( new Text( e.getReason()) );
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
        }

        Grid<ApplSetForEmployerDTO> grid = new Grid<>();

        grid.setItems(anzeigen);
        grid.addColumn(ApplSetForEmployerDTO::getStelle).setHeader("Stelle").setFlexGrow(0).setSortable(true).setWidth("200px");
        grid.addColumn(ApplSetForEmployerDTO::getStudent_vorname).setHeader("Bewerber Vorname").setFlexGrow(0).setSortable(true).setWidth("190px");
        grid.addColumn(ApplSetForEmployerDTO::getStudentname).setHeader("Nachname").setFlexGrow(0).setSortable(true).setWidth("190px");
        grid.addColumn(ApplSetForEmployerDTO::getStatus).setHeader("Status").setFlexGrow(0).setSortable(true).setWidth("230px");
        grid.addColumn(ApplSetForEmployerDTO::getStudID).setHeader("HIDE").setFlexGrow(0).setSortable(true).setWidth("100px").setVisible(false);


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
                            //if(ApplSetForEmployerDTO.getStatus()=="Ausschreibung beendet"){Notification.show("Ausschreibung bereits beendet!");}
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
        ).setFlexGrow(0).setWidth("260px");

        //grid.setHeight("400px");
        grid.setHeightByRows(true);
        return grid;
    }

    private Component creatgrid(){
        List<BewerbungDTOimpl> anzeigen = null;

        try {
            anzeigen = applicationControl.getAllApplicationsForUserWithID(ID);
        } catch (DatabaseUserException e) {
            Dialog dialog = new Dialog();
            dialog.add( new Text( e.getReason()) );
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
        }

        Grid<BewerbungDTOimpl> grid = new Grid<>();

        grid.setItems(anzeigen);
        grid.addColumn(BewerbungDTOimpl::getName).setHeader("Jobtitel").setFlexGrow(0).setWidth("220px");
        grid.addColumn(BewerbungDTOimpl::getUnternehmen).setHeader("Unternehmen").setFlexGrow(0).setWidth("200px");
        grid.addColumn(BewerbungDTOimpl::getStatus).setHeader("Status").setFlexGrow(0).setWidth("270px");
        grid.addColumn(BewerbungDTOimpl::getMehr).setHeader("mehr").setFlexGrow(0).setWidth("250px");

        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addItemClickListener(event -> {
            Dialog d = new Dialog();
            d.add( new Text( "TODO Clicked Item: " + event.getItem()) );
            d.setWidth("800px");
            d.setHeight("500px");
            d.open();
        });

        //grid.setHeight("600px");
        return grid;
    }

    private void apllicationEdit(int applicationID, int status) {
        try {
            applicationControl.apllicationEdit(applicationID,status);
            UI.getCurrent().navigate(Globals.Pages.HOME_VIEW);
            UI.getCurrent().navigate(Globals.Pages.APPLICATION_VIEW);
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
