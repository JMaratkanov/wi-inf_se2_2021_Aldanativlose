package daos;

import db.JDBCConnection;
import db.exceptions.DatabaseLayerException;
import dtos.impl.StellenanzeigeDTOimpl;
import globals.Globals;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StellenanzeigeDAO {

    public List<StellenanzeigeDTOimpl> getAll() throws DatabaseLayerException {
        ArrayList<StellenanzeigeDTOimpl> liste = new ArrayList<>();

        ResultSet set = null;

        try {
            PreparedStatement sql = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("SELECT title, content, standort FROM collhbrs.inserat ");
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            assert sql != null;
            set = sql.executeQuery();

        } catch (SQLException ex) {
            DatabaseLayerException e = new DatabaseLayerException(Globals.Errors.SQLERROR);
            throw e;
        } catch (NullPointerException ex) {
            DatabaseLayerException e = new DatabaseLayerException(Globals.Errors.DATABASE);
            throw e;
        }

        StellenanzeigeDTOimpl result = null;

        boolean flipflop;
            try {
                do {
                    flipflop = set.next();
                    if (flipflop) {
                        result = new StellenanzeigeDTOimpl();
                        result.setTitle(set.getString(1));
                        result.setContent(set.getString(2));
                        result.setStandort(set.getString(3));
                        liste.add(result);
                    }
                }while(flipflop);
            } catch (SQLException ex) {
                DatabaseLayerException e = new DatabaseLayerException(Globals.Errors.DATABASE);
                throw e;

            } finally {
                JDBCConnection.getInstance().closeConnection();
            }

        return liste;
    }

    public void newadtodao(String title, String standort, LocalDate date_von, LocalDate date_bis, String stunden_pro_woche, int verguetung_pro_stunde, String inserat_typ, String ansprechpartner, String branche_id, String content) throws DatabaseLayerException {
        Date von = Date.valueOf(date_von);
        Date bis = Date.valueOf(date_bis);


        try {
            PreparedStatement sql = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("INSERT INTO collhbrs.inserat(title, content, standort, status, unternehmen_profil_id, date_von, date_bis, ansprechpartner, inserat_typ, verguetung_pro_stunde, stunden_pro_woche, branche_id ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
                sql.setString(1, title);                    //String
                sql.setString(2, content);                  //String
                sql.setString(3, standort);                 //String
                sql.setDate(6, von);                        //Notiz an Nick: should be Date but java.util.Date not sql.Date
                sql.setDate(7, bis);                        //Date
                sql.setString(8, ansprechpartner);         //String

                //TODO Hardcoded vals richtig übergeben
                sql.setInt(4, 1); //,status);                      - Was macht das @Simon?
                sql.setInt(5, 95);  //,unternehmen_profil_id);     - get from session- later
                sql.setInt(9, 2);//  inserat_typ);                 - Mit switch case den String aus dem Select d. view die ID in Tabelle inserattyp mappen
                sql.setDouble(10, 9.); //verguetung_pro_stunde
                sql.setDouble(11, 7.); //stunden_pro_woche
                sql.setInt(12, 4); //branche_id);                  - same

            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            assert sql != null;
            sql.executeUpdate();

        } catch (SQLException ex) {
            DatabaseLayerException e = new DatabaseLayerException(Globals.Errors.SQLERROR);
            throw e;
        } catch (NullPointerException ex) {
            DatabaseLayerException e = new DatabaseLayerException(Globals.Errors.DATABASE);
            throw e;
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }
}
