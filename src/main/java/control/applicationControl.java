package control;

import daos.BewerbungDAO;
import db.exceptions.DatabaseLayerException;
import dtos.impl.ApplSetForEmployerDTO;
import dtos.impl.BewerbungDTOimpl;

import java.util.List;

public class applicationControl extends MainControl{

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
