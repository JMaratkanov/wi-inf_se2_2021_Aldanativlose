package control;

import control.exceptions.DatabaseUserException;
import daos.StellenanzeigeDAO;
import db.exceptions.DatabaseLayerException;
import dtos.impl.StellenanzeigeDTOimpl;

import java.util.List;


public class adControl extends MainControl{

    public List<StellenanzeigeDTOimpl> getAlleStellenanzeigen() throws DatabaseLayerException {
        StellenanzeigeDAO dao = new StellenanzeigeDAO();

        List<StellenanzeigeDTOimpl> liste = dao.getAll();

        return liste;
    }

    public void insertnewad(String bezeichnung, String inhalt, String standort) throws DatabaseUserException {
        StellenanzeigeDAO dao = new StellenanzeigeDAO();
        try {
            dao.newadtodao(bezeichnung, inhalt, standort);
        }
        catch (DatabaseLayerException e){
            checkReasonAndThrowEx(e.getReason());
        }

    }
}
