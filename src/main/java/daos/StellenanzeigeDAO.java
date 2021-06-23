package daos;

import db.JDBCConnection;
import db.exceptions.DatabaseLayerException;
import dtos.impl.StellenanzeigeDTOimpl;
import globals.Globals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public void newadtodao(String title, String content, String standort) throws DatabaseLayerException {
        try {
            PreparedStatement sql = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("INSERT INTO collhbrs.inserat(title, content, standort, status, unternehmen_profil_id) VALUES (?, ?, ?, ?, ?)");
                sql.setString(1, title);
                sql.setString(2, content);
                sql.setString(3, standort);
                sql.setInt(4, 1);
                sql.setInt(5, 95);


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
