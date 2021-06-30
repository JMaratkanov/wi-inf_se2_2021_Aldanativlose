package ui.appViews;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import control.adControl;
import control.exceptions.DatabaseUserException;
import db.exceptions.DatabaseLayerException;
import dtos.UserDTO;
import dtos.impl.StellenanzeigeDTOimpl;
import globals.Globals;
import ui.layouts.AppLayout;


import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Route(value = "ads", layout = AppLayout.class)
@PageTitle("Stellenanzeigen")
public class AdView extends Div {
    private boolean isEmployer = getTrueIfSessionIsEmployer();

    private adControl control = new adControl();
    private TextField suche = new TextField("Suche");
    Select<String> wasSelect = new Select<>();
    private TextField plztext = new TextField("PLZ");
    Select<String> umkreisSelect = new Select<>();
    private Button newAd = new Button("Neue Stellenanzeige Aufgeben");
    private Button newAdFinal = new Button("Stellenanzeige Aufgeben");

    //Inhalt der Stellenanzeige
    private TextField Bezeichnung = new TextField("Bezeichnung");
    Select<String> Standort = new Select<>("Bonn","St. Augustin", "Köln", "Koblenz"); //Lieber außerhalb eine Liste angeben
    //Standort.setItems("option one", "option 2");
    private TextArea Inhalt = new TextArea("Inhalt");
    private DatePicker DateVon = new DatePicker("Frühstmöglicher Beginn");
    private DatePicker DateBis = new DatePicker("Ende oder unbefristet "); //Muss noch überlegt werden wie
    Select<String> StundenProWoche = new Select<>("Unter 5", "Unter 10", "Unter 20", "Unter 30", "Über 30");
    private IntegerField VerguetungProStunde = new IntegerField("Vergütung");
    Select<String> InseratTyp = new Select<>("Teilzeit", "Vollzeit", "Praktikum", "Bachelorarbeit", "Masterarbeit", "keine Angabe");
    private TextField Ansprechpartner = new TextField("Ansprechpartner");
    Select<String> Branche = new Select<>("It", "Automobil", "Sonstige");


    FormLayout formLayout = new FormLayout();

    public AdView() {
        formLayout.add(Bezeichnung, Inhalt, Standort, DateVon, DateBis, StundenProWoche, VerguetungProStunde, InseratTyp, Ansprechpartner, Branche);

        //Button
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        newAd.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(newAd);

        AtomicReference<Dialog> dialog = new AtomicReference<>(new Dialog());

        if(isEmployer) {
            newAd.addClickListener(e -> {
                dialog.set(new Dialog());
                //dialog.add(new Text("Hallo"));
                dialog.get().add(new Div(formLayout, newAdFinal));
                dialog.get().setWidth("1000px");
                dialog.get().setHeight("10000px");
                dialog.get().open();
            });

            newAdFinal.addClickListener(e -> {
                createNewAd(
                        Bezeichnung.getValue(),
                        Standort.getValue(),
                        DateVon.getValue(),
                        DateBis.getValue(),
                        StundenProWoche.getValue(),
                        VerguetungProStunde.getValue(),
                        InseratTyp.getValue(),
                        Ansprechpartner.getValue(),
                        Branche.getValue(),
                        Inhalt.getValue()
                );
                dialog.get().close();
               /* Notification.show(Bezeichnung.getValue() +
                        Standort.getValue() +
                        DateVon.getValue() +DateBis.getValue() + StundenProWoche.getValue()
                        + VerguetungProStunde.getValue() + InseratTyp.getValue() + Ansprechpartner.getValue()
                        + Branche.getValue() + Inhalt.getValue());*/
            });

            add(newAd);

        }
        setId("ad-view");
        addClassName("wrapper");
        add(createTitle());
        add(filter());
        add(creategrid());
    }

    private void createNewAd(String Bezeichnung, String Standort, LocalDate DateVon, LocalDate DateBis, String StundenProWoche, int VerguetungProStunde, String InseratTyp, String Ansprechpartner, String Branche, String Inhalt) {
        try {
            //Standort.setItems("Bonn","St. Augustin", "Köln", "Koblenz");
            control.insertnewad(Bezeichnung, Standort, DateVon, DateBis, StundenProWoche, VerguetungProStunde, InseratTyp, Ansprechpartner, Branche,  Inhalt);
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

    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }

    private boolean getTrueIfSessionIsEmployer(){
        int rolle = getCurrentUser().getRole();
        return (rolle==2)?true:false;
    }
}