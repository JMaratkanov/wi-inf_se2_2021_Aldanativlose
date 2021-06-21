package daos;

import db.JDBCConnection;
import db.exceptions.DatabaseLayerException;
import dtos.impl.StellenanzeigeDTOimpl;
import globals.Globals;

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
            Statement statement = null;
            try {
                statement = JDBCConnection.getInstance().getStatement();
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            set = statement.executeQuery("SELECT title, content, standort " + "FROM collhbrs.inserat ");

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
}
