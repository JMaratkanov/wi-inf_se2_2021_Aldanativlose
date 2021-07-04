package ui.appViews;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import control.adControl;
import db.exceptions.DatabaseLayerException;
import dtos.impl.StellenanzeigeDTOimpl;
import search.SearchControlproxy;
import search.Suche;
import ui.layouts.AppLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "home", layout = AppLayout.class)
@PageTitle("Home")
public class HomeView extends Div {
    Grid<String> offeneBew = new Grid<>();
    //Grid<String> neueBew = new Grid<>();
    private TextField suche = new TextField("Suche");
    private final adControl adControl = new adControl();

    public HomeView() {
        setId("home-view");
        addClassName("wrapper");
        add(createTitle());
        add(createTitle2());
        add(createBodyText());
        add(creategrid());
        //add(filter());
    }

    /*
    private Component createLayout() {
        FormLayout gridLayout = new FormLayout();
        gridLayout.add(offenebewerbungen(),neueAngebote());
        return gridLayout;
    }
     */

    private Component creategrid(){
        List<StellenanzeigeDTOimpl> stellenanzeigenList = null;

        try {
            stellenanzeigenList = adControl.getLatestAds();
        } catch (DatabaseLayerException e) {
            Dialog dialog = new Dialog();
            dialog.add( new Text( e.getReason()) );
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
        }

        // Create a grid bound to the list
        Grid<StellenanzeigeDTOimpl> grid = new Grid<>();
        assert stellenanzeigenList != null;
        ListDataProvider<StellenanzeigeDTOimpl> dataProvider = new ListDataProvider<>(stellenanzeigenList);

        grid.setDataProvider(dataProvider);
        grid.addColumn(StellenanzeigeDTOimpl::getTitle).setHeader("Bezeichnung").setSortable(true).setFlexGrow(0).setWidth("200px").setKey("titleColum");
        grid.addColumn(StellenanzeigeDTOimpl::getDateVon).setHeader("Beginn der Tätigkeit").setSortable(true).setFlexGrow(0).setWidth("180px").setKey("startColum");
        grid.addColumn(StellenanzeigeDTOimpl::getStundenProWoche).setHeader("Stunden").setSortable(true).setFlexGrow(0).setWidth("120px").setKey("hoursColum");
        grid.addColumn(StellenanzeigeDTOimpl::getStandort).setHeader("Standort").setSortable(true).setFlexGrow(0).setWidth("170px").setKey("placeColum");
        grid.addColumn(StellenanzeigeDTOimpl::getInseratTyp).setHeader("Inserat Typ").setSortable(true).setFlexGrow(0).setWidth("200px").setKey("typeColum");
        grid.addColumn(StellenanzeigeDTOimpl::getStatus).setHeader("Status").setSortable(true).setFlexGrow(0).setWidth("250px").setKey("statusColum");
        grid.setHeight("260px");
        //grid.setWidth("1150px");
        return grid;
    }

/*
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
     */

    private Component createTitle() { return new H2("Guten Tag!"); }
    private Component createTitle2() { return new H3("Neue Stellenangebote");}
    private Component createBodyText() {
        return new Text("Hier sehen Sie die 5 Neuesten Stellenanzeigen: \n");
    }
    private Component createTitle3() { return new H4("Deine Bewerbungen");}
}
