package ui.appViews;

import com.vaadin.flow.component.Html;
import search.SearchControlproxy;
import search.Suche;
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
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
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
    private final boolean isEmployer = getTrueIfSessionIsEmployer();

    private final AtomicReference<Dialog> dialog = new AtomicReference<>(new Dialog());
    private double verguetung = 0.0;
    private int stunden = 0;
    private final adControl adControl = new adControl();
    private final Button newAdFinal = new Button("Stellenanzeige Aufgeben");

    //Inhalt der Stellenanzeige
    private final TextField Bezeichnung = new TextField("Bezeichnung");
    private final TextField Standort = new TextField("Standort"); //Lieber außerhalb eine Liste angeben
    private final TextArea Inhalt = new TextArea("Inhalt");
    private final DatePicker DateVon = new DatePicker("Frühstmöglicher Beginn");
    private final DatePicker DateBis = new DatePicker("Ende oder unbefristet "); //Muss noch überlegt werden wie
    private final IntegerField StundenProWoche = new IntegerField("Stunden pro Woche");
    private final NumberField VerguetungProStunde = new NumberField("Vergütung pro Stunde");
    Select<String> InseratTyp = new Select<>("Teilzeit", "Vollzeit", "Praktikum", "Bachelorarbeit", "Masterarbeit", "keine Angabe");
    private final TextField Ansprechpartner = new TextField("Ansprechpartner");
    Select<String> Branche = new Select<>("It", "Automobil", "Sonstige");


    FormLayout formLayout = new FormLayout();

    public AdView() {
        formLayout.add(Bezeichnung, Inhalt, Standort, DateVon,  StundenProWoche, DateBis, VerguetungProStunde, InseratTyp, Ansprechpartner, Branche);

        //Button
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        Button newAd = new Button("Neue Stellenanzeige Aufgeben");
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
                if (!VerguetungProStunde.isEmpty()) {
                    verguetung = VerguetungProStunde.getValue();
                }
                if (!StundenProWoche.isEmpty()) {
                    stunden = StundenProWoche.getValue();
                }
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
            });
            add(newAd);
        }

        setId("ad-view");
        addClassName("wrapper");
        add(createTitle());
        add(filter());
        add(creategrid(false));
        add(createYourAdsTitle());
        add(createBodyText());
        if (isEmployer) {
            add(creategrid(true));
        }
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
                adControl.insertnewad(Bezeichnung, Standort, DateVon, DateBis, StundenProWoche, VerguetungProStunde, InseratTyp, Ansprechpartner, Branche, Inhalt, getCurrentUser().getId());
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
        Branche.setLabel("Branchenauswahl");
        Branche.setItems("It", "Automobil", "Sonstige");
        Branche.setValue("Sonstige");
        InseratTyp.setLabel("Typ des Inserats");
        InseratTyp.setItems("Teilzeit", "Vollzeit", "Praktikum", "Bachelorarbeit", "Masterarbeit", "keine Angabe");
        InseratTyp.setValue("keine Angabe");
        Bezeichnung.setRequired(true);
        Inhalt.setRequired(true);
        DateVon.setRequired(true);
        DateBis.setRequired(true);
        Ansprechpartner.setRequired(true);

        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("1000px", 1),
                new FormLayout.ResponsiveStep("100px", 2),
                new FormLayout.ResponsiveStep("100px", 3),
                new FormLayout.ResponsiveStep("100px", 4));

        return formLayout;
    }

    private Component creategrid(boolean ownAds){
        List<StellenanzeigeDTOimpl> stellenanzeigenList = null;

        try {
            if (ownAds){
                stellenanzeigenList = adControl.getAllAdsOf1Emp(getCurrentUser().getId());
            } else {
                stellenanzeigenList = adControl.getAlleStellenanzeigen();
            }

        } catch (DatabaseUserException e) {
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


        grid.addItemClickListener(event -> {
                Dialog d = new Dialog();

            d.add( new Html("<h2>" + event.getItem().getTitle() + "</h2>") );
            d.add( new Html("<hr class='dickelinie'/>") );
            d.add( new Html("<span class='inseratdetailsbox'><b>Verfügbar ab: </b>" + event.getItem().getDateVon() + "</span>") );
            d.add( new Html("<span class='inseratdetailsbox'><b>Standort: </b>" + event.getItem().getStandort() + "</span>") );
            d.add( new Html("<span class='inseratdetailsbox'><b>Typ: </b>" + event.getItem().getInseratTyp() + "</span>") );
            d.add( new Html("<span class='inseratdetailsbox'><b>Arbeitszeit: </b>" + event.getItem().getStundenProWoche() + " Stunden pro Woche</span>") );
            d.add( new Html("<span class='inseratdetailsbox'><b>Stundenlohn: </b>" + event.getItem().getStundenlohn() + "€/h</span>") );
            d.add( new Html("<hr class='dickelinie'/>") );
            d.add( new Html("<span class='inseratcontent'>" + event.getItem().getContent() + "</span>") );
            d.add( new Html("<hr class='dickelinie'/>") );
            d.add( new Html("<span><b>Unternehmen: </b>" + event.getItem().getFirmenname() + "</span>") );
            d.add( new Html("<br/>") );
            if(event.getItem().getAnsprechpartner()!=null){
                d.add( new Html("<span><b>Ansprechpartner: </b>" + event.getItem().getAnsprechpartner() + "</span>") );
                d.add( new Html("<br/>") );
            }

            if(!isEmployer){
                if(event.getItem().getStatus().equals("Offen")){
                    d.add( new Button("Jetzt bewerben!" , e -> submitApplication(event.getItem().getID()) ) );
                }else{
                    d.add( new Html("<vaadin-button disabled>Ausschreibung beendet</vaadin-button>"));
                    d.add( new Html("<span class='smalltext'><b>Eine Bewerbung ist leider nicht mehr möglich</b></span>") );
                }

            }

            d.add( new Html("<span class='grey-text'>Job-ID: #" + event.getItem().getID() + "</span>") );

                d.setWidth("800px");
                d.setHeight("500px");
                d.open();
        });


        if(!isEmployer) {
            grid.addColumn(
                    new NativeButtonRenderer<>("Jetzt bewerben!",
                            clickedItem -> {
                                submitApplication(clickedItem.getID());
                            })
            ).setFlexGrow(0).setWidth("200px");
        } else {
            if(ownAds) {
                grid.addColumn(
                        new NativeButtonRenderer<>("Ausschreibung beenden!",
                                clickedItem -> {
                                    ausschreibungBeenden(clickedItem.getID());
                                })
                ).setFlexGrow(0).setWidth("200px");
            }
        }

        TextField modelField = new TextField();

        modelField.setValueChangeMode(ValueChangeMode.EAGER);
        Suche filterSuche = new SearchControlproxy();
        grid = filterSuche.filter(dataProvider, grid);

        grid.setHeight("600px");
        return grid;
    }

    private Component createTitle() {
        return new H3("Alle Stellenanzeigen");
    }
    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }

    private boolean getTrueIfSessionIsEmployer(){
        int rolle = getCurrentUser().getRole();
        return rolle == 2;
    }

    private void submitApplication(int inseratID) {
        try {
            adControl.bewerben(inseratID, getCurrentUser().getId());
            Notification.show("Vielen Dank für Ihre Bewerbung!");
        } catch (DatabaseUserException e) {
            Dialog dialog = new Dialog();
            dialog.add(new Text(e.getReason()));
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
        }
    }

    private void ausschreibungBeenden(int inseratID) {
        try {
            adControl.ausschreibungBeenden(inseratID);
            Notification.show("Ausschreibung wurde beendet!");
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

    private Component createYourAdsTitle() { return new H3("Ihre Stellenanzeigen:");}
    private Component createBodyText() {
        return new Text("Hier sehen Sie Ihre Stellenanzeigen: \n");
    }
}