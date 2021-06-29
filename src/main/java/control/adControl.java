package control;

import com.vaadin.flow.component.datepicker.DatePicker;
import control.exceptions.DatabaseUserException;
import daos.StellenanzeigeDAO;
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

    public void insertnewad(String bezeichnung, String standort, LocalDate DateVon, LocalDate DateBis, String StundenProWoche, double VerguetungProStunde, String InseratTyp, String Ansprechpartner, String Branche, String inhalt) throws DatabaseUserException {
        StellenanzeigeDAO dao = new StellenanzeigeDAO();
        try {
            dao.newadtodao(bezeichnung, standort, DateVon, DateBis, StundenProWoche, VerguetungProStunde, InseratTyp, Ansprechpartner, Branche, inhalt);
        }
        catch (DatabaseLayerException e){
            checkReasonAndThrowEx(e.getReason());
        }

    }
}
