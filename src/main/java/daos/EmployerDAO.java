package daos;

import db.JDBCConnection;
import db.exceptions.DatabaseLayerException;
import globals.Globals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployerDAO extends UserDAO{
    public void setEmployer(String companyName, String country, String street, String hNr, String place, String plz, String email, String password)  throws DatabaseLayerException {
        ResultSet set = null;
        try {
            PreparedStatement sql = null;
            PreparedStatement sql2 = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("INSERT INTO collhbrs.unternehmen_profil(firmenname, land, strasse, hausnr, ort, plz) VALUES (?, ?, ?, ?, ?, ?) returning id");
                sql.setString(1, companyName);
                sql.setString(2, country);
                sql.setString(3, street);
                sql.setString(4, hNr);
                sql.setString(5, place);
                sql.setString(6, plz);
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            assert sql != null;
            set = sql.executeQuery();
            int id = 0;
            if(set.next()) {
                id = set.getInt(1);
            }

            try {
                sql2 = JDBCConnection.getInstance().getPreparedStatement("INSERT INTO collhbrs.user(email, password, rolle, unternehmen_profil) VALUES (?, ?, ?, ?)");
                sql2.setString(1, email);
                sql2.setString(2, password);
                sql2.setInt(3, 2);
                sql2.setInt(4, id);
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            assert sql2 != null;
            sql2.executeUpdate();

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

    public int getEmployerIdByUserId(int id) throws DatabaseLayerException {
        ResultSet set = null;
        int studentProfilId = 0;

        try {
            PreparedStatement statement = null;
            try {
                statement = JDBCConnection.getInstance().getPreparedStatement("SELECT unternehmen_profil FROM collhbrs.user WHERE collhbrs.user.id = ?");
                statement.setInt(1, id);
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            assert statement != null;
            set = statement.executeQuery();

            if(set.next()) {
                studentProfilId = set.getInt(1);
            }

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
        return studentProfilId;
    }

    public void deleteEmployerProfil(int id) throws DatabaseLayerException {
        try {
            PreparedStatement sql = null;
            try {
                int studentID = getEmployerIdByUserId(id);
                sql = JDBCConnection.getInstance().getPreparedStatement(
                        "DELETE FROM collhbrs.unternehmen_profil WHERE collhbrs.unternehmen_profil.id = ?");
                sql.setInt(1, studentID);
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
