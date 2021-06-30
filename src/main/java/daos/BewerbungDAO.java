package daos;

import db.JDBCConnection;
import db.exceptions.DatabaseLayerException;
import dtos.impl.BewerbungDTOimpl;
import globals.Globals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BewerbungDAO {
    StudentDAO GetStudentID_ByUserID = new StudentDAO();

    public List<BewerbungDTOimpl> getAll(int ID) throws DatabaseLayerException {
        ArrayList<BewerbungDTOimpl> liste = new ArrayList<>();

        ResultSet set = null;

        //Hole raw Bewerbungsdaten passend zu StudentID aus DB
        try {
            PreparedStatement sql = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("SELECT id, inserat_id, status FROM collhbrs.bewerbung WHERE student_profil = " + GetStudentID_ByUserID.getStudentIdByUserId(ID));
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            assert sql != null;
            set = sql.executeQuery();

        } catch (SQLException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Fehler im SQL-Befehl1!");
            e.setReason(Globals.Errors.SQLERROR);
            throw e;
        } catch (NullPointerException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Fehler bei Datenbankverbindung!");
            e.setReason(Globals.Errors.DATABASE);
            throw e;
        }

        BewerbungDTOimpl result = null;

        boolean flipflop;
        try {
            do {
                flipflop = set.next();
                if (flipflop) {
                    result = new BewerbungDTOimpl();
                    result.setID(set.getInt(1));
                    result.setInseratID(set.getInt(2));
                    result.setStatusFromDB(set.getInt(3));
                    liste.add(result);
                }
            }while(flipflop);
        } catch (SQLException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Probleme mit der Datenbank");
            e.setReason(Globals.Errors.DATABASE);
            throw e;

        } finally {
            JDBCConnection.getInstance().closeConnection();
        }

        //BewerbungDTOs mit Daten der Tabelle inserat füllen
        Iterator<BewerbungDTOimpl> iter = liste.iterator();
        ArrayList<BewerbungDTOimpl> completeDTOliste = new ArrayList<>();
        ResultSet set2 = null;

        if(iter.hasNext()) {
            do {
                BewerbungDTOimpl get = iter.next();
                int idOfSpecificDTO = get.getInseratID();
                //set2 = null; //clean

                try {
                    PreparedStatement sql2 = null;
                    try {
                        sql2 = JDBCConnection.getInstance().getPreparedStatement("SELECT inserat.title, inserat.unternehmen_profil_id, inserat.content, collhbrs.unternehmen_profil.firmenname FROM collhbrs.inserat JOIN collhbrs.unternehmen_profil ON inserat.unternehmen_profil_id = collhbrs.unternehmen_profil.id WHERE inserat.id = " + idOfSpecificDTO);
                    } catch (DatabaseLayerException e) {
                        e.printStackTrace();
                    }
                    assert sql2 != null;
                    set2 = sql2.executeQuery();

                } catch (SQLException ex) {
                    DatabaseLayerException e = new DatabaseLayerException("Fehler im SQL-Befehl2!");
                    e.setReason(Globals.Errors.SQLERROR);
                    throw e;
                } catch (NullPointerException ex) {
                    DatabaseLayerException e = new DatabaseLayerException("Fehler bei Datenbankverbindung!");
                    e.setReason(Globals.Errors.DATABASE);
                    throw e;
                }

                try {
                    set2.next();
                    get.setName(set2.getString(1));
                    get.setUnternehmenID(set2.getInt(2));
                    get.setMehr(set2.getString(3));
                    get.setUnternehmen(set2.getString(4));

                    completeDTOliste.add(get);
                } catch (SQLException ex) {
                    DatabaseLayerException e = new DatabaseLayerException("Probleme mit der Datenbank");
                    e.setReason(Globals.Errors.DATABASE);
                    throw e;

                } finally {
                    JDBCConnection.getInstance().closeConnection();
                }


            } while (iter.hasNext());
        }



        //sql3 = JDBCConnection.getInstance().getPreparedStatement("SELECT firmenname FROM collhbrs.unternehmen_profil WHERE id = " + get.getUnternehmenID());

        return completeDTOliste;
    }
}