package daos;

import com.vaadin.flow.component.datepicker.DatePicker;
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

        ResultSet set;

        try {
            PreparedStatement sql = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("SELECT title, content, standort FROM collhbrs.inserat ORDER BY standort ASC");
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            assert sql != null;
            set = sql.executeQuery();

        } catch (SQLException ex) {
            throw new DatabaseLayerException(Globals.Errors.SQLERROR);
        } catch (NullPointerException ex) {
            throw new DatabaseLayerException(Globals.Errors.DATABASE);
        }

        StellenanzeigeDTOimpl result;

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
                throw new DatabaseLayerException(Globals.Errors.DATABASE);
            } finally {
                JDBCConnection.getInstance().closeConnection();
            }

        return liste;
    }

    public void newadtodao(String title, String standort, LocalDate dateVon, LocalDate dateBis, int stunden_pro_woche, double verguetung_pro_stunde, int inserat_typ, String ansprechpartner, int branche_id, String content) throws DatabaseLayerException {
        Date date_von = null; //java.sql.Date.valueOf(dateVon);
        Date date_bis = null; //java.sql.Date.valueOf(dateBis);
        if (dateVon != null){
            date_von = Date.valueOf(dateVon);
        }
        if (dateBis != null) {
            date_bis = Date.valueOf(dateBis);
        }
        try {
            PreparedStatement sql = null;
            try {


                sql = JDBCConnection.getInstance().getPreparedStatement("INSERT INTO collhbrs.inserat(title, content, standort, date_von, date_bis, status, stunden_pro_woche, verguetung_pro_stunde, unternehmen_profil_id, inserat_typ, ansprechpartner, branche_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"); //date_von, date_bis,
                sql.setString(1, title);
                sql.setString(2, content);
                sql.setString(3, standort);
                sql.setDate(4, date_von);
                sql.setDate(5, date_bis);
                sql.setInt(6, 1);
                sql.setInt(7, stunden_pro_woche);
                sql.setDouble(8, verguetung_pro_stunde);
                sql.setInt(9, 95);
                sql.setInt(10, inserat_typ);
                sql.setString(11, ansprechpartner);
                sql.setInt(12, branche_id);

                //TODO Hardcoded vals richtig übergeben
                /*
                sql.setInt(4, 1); //,status);                      - Was macht das @Simon?
                sql.setInt(5, 95);  //,unternehmen_profil_id);     - get from session- later
                sql.setInt(9, 2);//  inserat_typ);                 - Mit switch case den String aus dem Select d. view die ID in Tabelle inserattyp mappen
                sql.setDouble(10, 9.); //verguetung_pro_stunde
                sql.setDouble(11, 7.); //stunden_pro_woche
                sql.setInt(12, 4); //branche_id);                  - same

                 */

            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            assert sql != null;
            sql.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseLayerException(Globals.Errors.SQLERROR);
        } catch (NullPointerException ex) {
            throw new DatabaseLayerException(Globals.Errors.DATABASE);
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }
}
