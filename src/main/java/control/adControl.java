package control;

import daos.StellenanzeigeDAO;
import db.exceptions.DatabaseLayerException;
import dtos.impl.StellenanzeigeDTOimpl;

import java.util.List;


public class adControl {

    public List<StellenanzeigeDTOimpl> getAlleStellenanzeigen() throws DatabaseLayerException {
        StellenanzeigeDAO dao = new StellenanzeigeDAO();

        List<StellenanzeigeDTOimpl> liste = dao.getAll();

        return liste;
    }
}
