package control;

import control.exceptions.DatabaseUserException;
import daos.BewerbungDAO;
import daos.StudentDAO;
import db.exceptions.DatabaseLayerException;
import dtos.impl.ApplSetForEmployerDTO;
import dtos.impl.BewerbungDTOimpl;

import java.util.List;

public class applicationControl extends MainControl{

    public void bewerbungablehen(int applicationID) throws DatabaseUserException {
        BewerbungDAO dao = new BewerbungDAO();
        try {
            dao.bewerbungablehnen(applicationID);
        } catch ( DatabaseLayerException e) {
            checkReasonAndThrowEx(e.getReason());
        }
    }

    public List<BewerbungDTOimpl> getAllApplicationsForUserWithID(int ID) throws DatabaseLayerException {
        BewerbungDAO dao = new BewerbungDAO();

        List<BewerbungDTOimpl> liste = dao.getAll(ID);

        return liste;
    }

    public List<ApplSetForEmployerDTO> getAllApllicantsByEmployerID(int id) throws DatabaseLayerException{
        BewerbungDAO dao = new BewerbungDAO();

        List<ApplSetForEmployerDTO> liste = dao.getAllApllicantsByEmployerID(id);

        return liste;
    }
}
