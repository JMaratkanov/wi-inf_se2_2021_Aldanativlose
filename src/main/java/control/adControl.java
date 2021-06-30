package control;

import com.vaadin.flow.component.datepicker.DatePicker;
import control.exceptions.DatabaseUserException;
import daos.BewerbungDAO;
import daos.StellenanzeigeDAO;
import daos.StudentDAO;
import db.exceptions.DatabaseLayerException;
import dtos.impl.StellenanzeigeDTOimpl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


public class adControl extends MainControl{

    public List<StellenanzeigeDTOimpl> getAlleStellenanzeigen() throws DatabaseLayerException {
        StellenanzeigeDAO dao = new StellenanzeigeDAO();

        List<StellenanzeigeDTOimpl> liste = dao.getAll();

        return liste;
    }

    public void insertnewad(String bezeichnung, String standort, LocalDate DateVon, LocalDate DateBis, int StundenProWoche, double VerguetungProStunde, String InseratTyp, String Ansprechpartner, String Branche, String inhalt) throws DatabaseUserException {
        StellenanzeigeDAO dao = new StellenanzeigeDAO();
        int InseratTypInt = 6;
        int BrancheID = 2;

        if(DateVon.isAfter(DateBis)){throw new DatabaseUserException("Das eingegebene Datum des Beginns liegt zu einem späteren Zeitpunkt, als das Datum des Endes");}
        switch (InseratTyp){
            case "Teilzeit":        InseratTypInt = 1; break;
            case "Vollzeit":        InseratTypInt = 2; break;
            case "Praktikum":       InseratTypInt = 3; break;
            case "Bachelorarbeit":  InseratTypInt = 4; break;
            case "Masterarbeit":    InseratTypInt = 5; break;
            default:                InseratTypInt = 0; break;
        }

        switch (Branche){
            case "It":          BrancheID = 1; break;
            case "Automobil":   BrancheID = 2; break;
            default:            BrancheID = 0; break;
        }


        try {
            dao.newadtodao(bezeichnung, standort, DateVon, DateBis, StundenProWoche, VerguetungProStunde, InseratTypInt, Ansprechpartner, BrancheID, inhalt);
        }
        catch (DatabaseLayerException e){
            checkReasonAndThrowEx(e.getReason());
        }
    }

    public void bewerben(int stellenanzeigeID, int userID) throws DatabaseUserException {
        StudentDAO dao = new StudentDAO();
        try {
            dao.bewerbungDurchführen(stellenanzeigeID, userID);
        } catch ( DatabaseLayerException e) {
            checkReasonAndThrowEx(e.getReason());
        }
    }
}
