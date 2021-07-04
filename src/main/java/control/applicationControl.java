package control;

import control.exceptions.DatabaseUserException;
import daos.BewerbungDAO;
import daos.StudentDAO;
import db.exceptions.DatabaseLayerException;
import dtos.impl.ApplSetForEmployerDTO;
import dtos.impl.BewerbungDTOimpl;

import java.util.List;

public class applicationControl extends MainControl{

    public void apllicationEdit(int applicationID, int status) throws DatabaseUserException {
        BewerbungDAO dao = new BewerbungDAO();
        try {
            dao.apllicationEdit(applicationID, status);
        } catch ( DatabaseLayerException e) {
            checkReasonAndThrowEx(e.getReason());
        }
    }

    public List<BewerbungDTOimpl> getAllApplicationsForUserWithID(int ID) throws DatabaseUserException {
        BewerbungDAO dao = new BewerbungDAO();
        List<BewerbungDTOimpl> liste = null;
        try {
            liste = dao.getAll(ID);
        } catch (DatabaseLayerException e) {
            checkReasonAndThrowEx(e.getReason());
        }
        return liste;
    }

    public List<ApplSetForEmployerDTO> getAllApllicantsByEmployerID(int id) throws DatabaseUserException{
        BewerbungDAO dao = new BewerbungDAO();
        List<ApplSetForEmployerDTO> liste = null;
        try {
            liste = dao.getAllApllicantsByEmployerID(id);
        } catch (DatabaseLayerException e) {
            checkReasonAndThrowEx(e.getReason());
        }

        return liste;
    }
}
