package control;

import daos.BewerbungDAO;
import db.exceptions.DatabaseLayerException;
import dtos.impl.BewerbungDTOimpl;

import java.util.List;

public class applicationControl extends MainControl{

    public List<BewerbungDTOimpl> getAllApplicationsForUserWithID(int ID) throws DatabaseLayerException {
        BewerbungDAO dao = new BewerbungDAO();

        List<BewerbungDTOimpl> liste = dao.getAll(ID);

        return liste;
    }
}
