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
import com.vaadin.flow.component.textfield.NumberField;
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

    private AtomicReference<Dialog> dialog = new AtomicReference<>(new Dialog());
    private double verguetung = 0.0;
    private int stunden = 0;
    private adControl control = new adControl();
    private TextField suche = new TextField("Suche");
    Select<String> wasSelect = new Select<>();
    private TextField plztext = new TextField("PLZ");
    Select<String> umkreisSelect = new Select<>();
    private Button newAd = new Button("Neue Stellenanzeige Aufgeben");
    private Button newAdFinal = new Button("Stellenanzeige Aufgeben");

    //Inhalt der Stellenanzeige
    private TextField Bezeichnung = new TextField("Bezeichnung");
    private TextField Standort = new TextField("Standort"); //Lieber außerhalb eine Liste angeben
    private TextArea Inhalt = new TextArea("Inhalt");
    private DatePicker DateVon = new DatePicker("Frühstmöglicher Beginn");
    private DatePicker DateBis = new DatePicker("Ende oder unbefristet "); //Muss noch überlegt werden wie
    //Select<String> StundenProWoche = new Select<>("Unter 5", "Unter 10", "Unter 20", "Unter 30", "Über 30");
    private IntegerField StundenProWoche = new IntegerField("Stunden pro Woche");
    private NumberField VerguetungProStunde = new NumberField("Vergütung pro Stunde");
    Select<String> InseratTyp = new Select<>("Teilzeit", "Vollzeit", "Praktikum", "Bachelorarbeit", "Masterarbeit", "keine Angabe");
    private TextField Ansprechpartner = new TextField("Ansprechpartner");
    Select<String> Branche = new Select<>("It", "Automobil", "Sonstige");


    FormLayout formLayout = new FormLayout();

    public AdView() {
        formLayout.add(Bezeichnung, Inhalt, Standort, DateVon,  StundenProWoche, DateBis, VerguetungProStunde, InseratTyp, Ansprechpartner, Branche);

        //Button
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        newAd.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(newAd);



        if(isEmployer) {
        newAd.addClickListener(e -> {
                    dialog.set(new Dialog());
                    dialog.get().add(new Div(formLayout, newAdFinal));
                    dialog.get().setWidth("1000px");
                    dialog.get().setHeight("10000px");
                    dialog.get().open();
                });

        newAdFinal.addClickListener(e -> {
            if (!VerguetungProStunde.isEmpty()){ verguetung = VerguetungProStunde.getValue();}
            if (!StundenProWoche.isEmpty()){ stunden = StundenProWoche.getValue();}
            createNewAd(
                Bezeichnung.getValue(),
                Standort.getValue(),
                DateVon.getValue(),
                DateBis.getValue(),
                stunden,
                verguetung,
                InseratTyp.getValue(),
                Ansprechpartner.getValue(),
                Branche.getValue(),
                Inhalt.getValue()
            );
                //dialog.get().close();
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

    private void createNewAd(String Bezeichnung, String Standort, LocalDate DateVon, LocalDate DateBis, int StundenProWoche,  double VerguetungProStunde, String InseratTyp, String Ansprechpartner, String Branche, String Inhalt) {
        if (DateBis == null) {
            DateBis = LocalDate.of(3000, 1, 1);
        }

        if (Bezeichnung.isEmpty()) {
            Notification.show("Geben Sie eine Bezeichnung an!");
        } else if (Standort.isEmpty()) {
            Notification.show("Geben Sie einen Standort an!");
        } else if (DateVon == null) {
            Notification.show("Geben Sie ein Anfangsdatum an!");
        } else if (DateVon.isAfter(DateBis)) {
            Notification.show("Das eingegebene Datum des Beginns liegt zu einem späteren Zeitpunkt, als das Datum des Endes");
        } else if (StundenProWoche == 0) {
            Notification.show("Bitte geben Sie die Stunden pro Woche an!");
        } else if (VerguetungProStunde == 0.0) {
            Notification.show("Bitte geben SIe die Vergütung pro Stunde an!");
        } else if (InseratTyp.isEmpty()) {
            Notification.show("Bitte geben Sie einen Inserat Typ an!");
        } else if (Ansprechpartner.isEmpty()) {
            Notification.show("Bitte geben Sie einen Ansprechpartner an!");
        } else if (Branche.isEmpty()) {
            Notification.show("Bitte geben Sie eine Branche an!");
        } else if (Inhalt.isEmpty()) {
            Notification.show("Bitte geben Sie den Inhalt an!");
        } else {
            try {
                control.insertnewad(Bezeichnung, Standort, DateVon, DateBis, StundenProWoche, VerguetungProStunde, InseratTyp, Ansprechpartner, Branche, Inhalt);
                Notification.show("Stellenanzeige erfolgreich aufgegeben!");
                dialog.get().close();
                UI.getCurrent().navigate(Globals.Pages.HOME_VIEW);
                UI.getCurrent().navigate(Globals.Pages.AD_VIEW);
            } catch (DatabaseUserException e) {
                Dialog dialog = new Dialog();
                dialog.add(new Text(e.getReason()));
                dialog.setWidth("400px");
                dialog.setHeight("150px");
                dialog.open();
            }
        }
    }

    private  Component filter() {
        wasSelect.setItems("Option one", "Option two");
        wasSelect.setLabel("Was?");
        umkreisSelect.setItems("5km", "10km", "20km", "50km", "+50km");
        umkreisSelect.setLabel("Umkreis");
        suche.setMaxWidth("1000px");
        wasSelect.setMaxWidth("100px");
        plztext.setMaxWidth("100px");
        umkreisSelect.setMaxWidth("100px");

        Branche.setLabel("Branchenauswahl");
        Branche.setItems("It", "Automobil", "Sonstige");
        Branche.setValue("Sonstige");
        InseratTyp.setLabel("Typ des Inserats");
        InseratTyp.setItems("Teilzeit", "Vollzeit", "Praktikum", "Bachelorarbeit", "Masterarbeit", "keine Angabe");
        InseratTyp.setValue("keine Angabe");
        //StundenProWoche.setLabel("Wochenstunden");
        //StundenProWoche.setItems("Bis 5", "Bis 10", "Bis 15", "bis 20", "bis 30", "bis 40", "über 40");
        Bezeichnung.setRequired(true);
        Inhalt.setRequired(true);
        DateVon.setRequired(true);
        DateBis.setRequired(true);
        Ansprechpartner.setRequired(true);

        //Standort.setRequired(true);
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
        grid.addColumn(StellenanzeigeDTOimpl::getTitle).setHeader("Bezeichnung").setFlexGrow(0).setWidth("350px");
        grid.addColumn(StellenanzeigeDTOimpl::getDateVon).setHeader("Beginn der Tätigkeit").setFlexGrow(0).setWidth("200px");
        grid.addColumn(StellenanzeigeDTOimpl::getStundenProWoche).setHeader("Stunden").setFlexGrow(0).setWidth("200px");
        grid.addColumn(StellenanzeigeDTOimpl::getStandort).setHeader("Standort").setFlexGrow(0).setWidth("100px");
        grid.addColumn(StellenanzeigeDTOimpl::getInseratTyp).setHeader("Inserat Typ").setFlexGrow(0).setWidth("200px");

        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addItemClickListener(event -> {
                Dialog d = new Dialog();
                d.add( new Text( "TODO Clicked Item: " + event.getItem()) );
                d.setWidth("800px");
                d.setHeight("500px");
                d.open();
        });

        if(!isEmployer) {
            grid.addColumn(
                    new NativeButtonRenderer<>("Bewirb dich jetzt!",
                            clickedItem -> {
                                submitApplication(clickedItem.getID());
                            })
            ).setFlexGrow(0).setWidth("250px");
        }

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
        return rolle == 2;
    }

    private void submitApplication(int InseratID) {

    }
}