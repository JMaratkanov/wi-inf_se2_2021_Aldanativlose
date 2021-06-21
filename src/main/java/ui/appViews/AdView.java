package ui.appViews;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import control.adControl;
import db.exceptions.DatabaseLayerException;
import dtos.impl.StellenanzeigeDTOimpl;
import ui.layouts.AppLayout;

import java.util.List;

@Route(value = "ads", layout = AppLayout.class)
@PageTitle("Stellenanzeigen")
public class AdView extends Div {

    private adControl control = new adControl();

    private TextField suche = new TextField("Suche");
    Select<String> wasSelect = new Select<>();
    private TextField plztext = new TextField("PLZ");
    Select<String> umkreisSelect = new Select<>();



    public AdView() {
        setId("ad-view");
        addClassName("wrapper");
        add(createTitle());
        add(filter());
        add(creategrid());

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

    private Component creategrid(){
        List<StellenanzeigeDTOimpl> anzeigen = null;

        try {
            anzeigen = control.getAlleStellenanzeigen();
        } catch (DatabaseLayerException e) {
            Dialog dialog = new Dialog();
            dialog.add( new Text( e.getReason()) );
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
        }

        // Create a grid bound to the list
        Grid<StellenanzeigeDTOimpl> grid = new Grid<>();

        grid.setItems(anzeigen);
        grid.addColumn(StellenanzeigeDTOimpl::getTitle).setHeader("Bezeichnung");
        grid.addColumn(StellenanzeigeDTOimpl::getContent).setHeader("Inhalt");
        grid.addColumn(StellenanzeigeDTOimpl::getStandort).setHeader("Standort");

        return grid;
    }

    private Component createTitle() {
        return new H3("Stellenanzeigen");
    }
}