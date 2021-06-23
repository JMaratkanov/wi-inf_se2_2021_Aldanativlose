package ui.appViews;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import control.adControl;
import control.exceptions.DatabaseUserException;
import db.exceptions.DatabaseLayerException;
import dtos.impl.StellenanzeigeDTOimpl;
import globals.Globals;
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
    private Button newAd = new Button("Neue Stellenanzeige Aufgeben");
    private Button newAdFinal = new Button("Stellenanzeige Aufgeben");
    private TextField Bezeichnung = new TextField("Bezeichnung");
    private Select<String> Standort = new Select<>("Bonn","St. Augustin", "Köln", "Koblenz");
    private TextArea Inhalt = new TextArea("Inhalt");

    FormLayout formLayout = new FormLayout();

    public AdView() {
        formLayout.add(Bezeichnung, Inhalt, Standort);
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        newAd.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(newAd);
        Dialog dialog = new Dialog();
        newAd.addClickListener(e -> {
                    dialog.add(new Text("Hallo"));
                    dialog.add(new Div(formLayout, newAdFinal));
                    dialog.setWidth("1000px");
                    dialog.setHeight("10000px");
                    dialog.open();
                });
        newAdFinal.addClickListener(e -> {createNewAd(
                Bezeichnung.getValue(),
                Inhalt.getValue(),
                Standort.getValue());
                dialog.close();});




        add(newAd);
        setId("ad-view");
        addClassName("wrapper");
        add(createTitle());
        add(filter());
        add(creategrid());
    }

    private void createNewAd(String Bezeichnung, String Inhalt, String Standort) {
        try {
            control.insertnewad(Bezeichnung, Inhalt, Standort);
            Notification.show("Stellenanzeige erfolgreich aufgegeben!");
            UI.getCurrent().navigate(Globals.Pages.HOME_VIEW);
            UI.getCurrent().navigate(Globals.Pages.AD_VIEW);
        } catch (DatabaseUserException e) {
            Dialog dialog = new Dialog();
            dialog.add( new Text(e.getReason()) );
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
        }
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
        grid.addColumn(StellenanzeigeDTOimpl::getTitle).setHeader("Bezeichnung").setFlexGrow(0).setWidth("200px");
        grid.addColumn(StellenanzeigeDTOimpl::getContent).setHeader("Inhalt").setFlexGrow(0).setWidth("800px");
        grid.addColumn(StellenanzeigeDTOimpl::getStandort).setHeader("Standort").setFlexGrow(0).setWidth("100px");;

        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addItemClickListener(event -> {
                Dialog d = new Dialog();
                d.add( new Text( "Clicked Item: " + event.getItem()) );
                d.setWidth("800px");
                d.setHeight("500px");
                d.open();
        });

        grid.addColumn(
                new NativeButtonRenderer<>("B-bb-Bewirb dich!",
                        clickedItem -> {
                            // tue etwas
                        })
        ).setFlexGrow(0).setWidth("250px");

        return grid;
    }

    private Component createTitle() {
        return new H3("Stellenanzeigen");
    }
}