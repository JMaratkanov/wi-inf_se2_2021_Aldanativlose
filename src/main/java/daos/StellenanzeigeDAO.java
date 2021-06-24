package daos;

import com.vaadin.flow.component.datepicker.DatePicker;
import db.JDBCConnection;
import db.exceptions.DatabaseLayerException;
import dtos.impl.StellenanzeigeDTOimpl;
import globals.Globals;

import java.sql.*;
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
            DatabaseLayerException e = new DatabaseLayerException("Fehler im SQL-Befehl!");
            e.setReason(Globals.Errors.SQLERROR);
            throw e;
        } catch (NullPointerException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Fehler bei Datenbankverbindung!");
            e.setReason(Globals.Errors.DATABASE);
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
                DatabaseLayerException e = new DatabaseLayerException("Probleme mit der Datenbank");
                e.setReason(Globals.Errors.DATABASE);
                throw e;

            } finally {
                JDBCConnection.getInstance().closeConnection();
            }

        return liste;
    }

    public void newadtodao(String title, String standort, LocalDate date_von, LocalDate date_bis, String stunden_pro_woche, int verguetung_pro_stunde, String inserat_typ, String ansprechpartner, String branche_id, String content) throws DatabaseLayerException {
        try {
            PreparedStatement sql = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("INSERT INTO collhbrs.inserat(title, content, standort, date_von, date_bis, status, stunden_pro_woche, verguetung_pro_stunde, unternehmen_profil_id, inserat_typ, ansprechpartner, branche_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"); //date_von, date_bis,
                sql.setString(1, title);
                sql.setString(2, content);
                sql.setString(3, standort);
                sql.setDate(4, new java.sql.Date(1));
                sql.setDate(5, new java.sql.Date(1));
                sql.setInt(6, 1);
                sql.setString(7, stunden_pro_woche);
                sql.setInt(8, verguetung_pro_stunde);
                sql.setInt(9, 95);
                sql.setString(10, inserat_typ);
                sql.setString(11, ansprechpartner);
                sql.setString(12, branche_id);



            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            assert sql != null;
            sql.executeUpdate();

        } catch (SQLException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Fehler im SQL-Befehl!");
            e.setReason(Globals.Errors.SQLERROR);
            throw e;
        } catch (NullPointerException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Fehler bei Datenbankverbindung!");
            e.setReason(Globals.Errors.DATABASE);
            throw e;
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }
}
