package control;

import control.exceptions.DatabaseUserException;
import daos.BewerbungDAO;
import daos.StellenanzeigeDAO;
import daos.StudentDAO;
import daos.UserDAO;
import db.exceptions.DatabaseLayerException;
import dtos.UserDTO;
import dtos.impl.StellenanzeigeDTOimpl;

import java.time.LocalDate;
import java.util.List;


public class adControl extends MainControl{

    public List<StellenanzeigeDTOimpl> getAlleStellenanzeigen() throws DatabaseLayerException {
        StellenanzeigeDAO dao = new StellenanzeigeDAO();

        List<StellenanzeigeDTOimpl> liste = dao.getAll();

        return liste;
    }

    public List<StellenanzeigeDTOimpl> getAllAdsOf1Emp(int userId) throws DatabaseLayerException {
        StellenanzeigeDAO dao = new StellenanzeigeDAO();

        return dao.getAllAdsOf1Employer(userId);
    }

    public List<StellenanzeigeDTOimpl> getLatestAds() throws DatabaseLayerException {
        StellenanzeigeDAO dao = new StellenanzeigeDAO();
        List<StellenanzeigeDTOimpl> list = dao.getLatest();
        return list;
    }


    /*public static StellenanzeigeDTOimpl getInseratById(int InseratId) throws DatabaseLayerException {
        StellenanzeigeDAO dao = new StellenanzeigeDAO();
        StellenanzeigeDTOimpl inserat = dao.getInseratById(InseratId);
        return inserat;
    }*/

    public void insertnewad(String bezeichnung, String standort, LocalDate DateVon, LocalDate DateBis, int StundenProWoche, double VerguetungProStunde, String InseratTyp, String Ansprechpartner, String Branche, String inhalt, int userID) throws DatabaseUserException {
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
            dao.newadtodao(bezeichnung, standort, DateVon, DateBis, StundenProWoche, VerguetungProStunde, InseratTypInt, Ansprechpartner, BrancheID, inhalt, userID);
        }
        catch (DatabaseLayerException e){
            checkReasonAndThrowEx(e.getReason());
        }
    }

    public void bewerben(int stellenanzeigeID, int userID) throws DatabaseUserException {
        StudentDAO dao = new StudentDAO();
        try {
            dao.checkBewerbungDoppelt(stellenanzeigeID, userID);
            dao.bewerbungDurchfuehren(stellenanzeigeID, userID);
        } catch ( DatabaseLayerException e) {
            checkReasonAndThrowEx(e.getReason());
        }
    }

    public void ausschreibungBeenden(int inseratID) throws DatabaseUserException {
        StellenanzeigeDAO dao = new StellenanzeigeDAO();
        try {
            dao.cancelAd(inseratID);
        } catch (DatabaseLayerException e) {
            checkReasonAndThrowEx(e.getReason());
        }
    }
}
