package daos;

import db.JDBCConnection;
import db.exceptions.DatabaseLayerException;
import dtos.UserDTO;
import dtos.impl.UserDTOimpl;
import globals.Globals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {

    public UserDTO findUserByUserEmailAndPassword(String email, String password) throws DatabaseLayerException {
        ResultSet set = null;

        try {
            Statement statement = null;
            try {
                statement = JDBCConnection.getInstance().getStatement();
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            set = statement.executeQuery(
                    "SELECT * "
                            + "FROM collhbrs.user "
                            + "WHERE collhbrs.user.email = \'" + email + "\'"
                            + " AND collhbrs.user.password = \'" + password + "\'");

        } catch (SQLException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Fehler im SQL-Befehl!");
            e.setReason(Globals.Errors.SQLERROR);
            throw e;
        } catch (NullPointerException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Fehler bei Datenbankverbindung!");
            e.setReason(Globals.Errors.DATABASE);
            throw e;
        }

        UserDTOimpl user = null;

        try {
            if (set.next()) {
                // Durchführung des Object-Relational-Mapping (ORM)

                //User wird mit diesen Werten in die Session gesetzt
                user = new UserDTOimpl();
                user.setId( set.getInt(1));
                user.setEmail(set.getString(2));
                user.setRole(set.getInt(5));
                //TODO
                // Haben setFirstname und setLastname für den Zugriff auf unsere Datenbank ausgenommen, da wir Vor- und Nachname
                // bisher nicht in der User Tabelle vorhanden ist. Ggf.: muss das hier noch angepasst werden!
                //user.setFirstname( set.getString(4));
                //user.setLastname(set.getString(5));

                return user;

            } else {
                // Error Handling
                DatabaseLayerException e = new DatabaseLayerException("No User Could be found");
                e.setReason(Globals.Errors.NOUSERFOUND);
                throw e;
            }
        } catch (DatabaseLayerException e) {
            throw e;
        } catch (SQLException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Probleme mit der Datenbank");
            e.setReason(Globals.Errors.DATABASE);
            throw e;

        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }

    // Prüft anhand einer E-Mail ob ein User mit dieser E-Mail schon existiert
    public void checkOnExistingUser(String email) throws DatabaseLayerException{
        ResultSet set = null;
        try {
            PreparedStatement sql = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("SELECT COUNT(*) FROM collhbrs.user WHERE email LIKE ?");
                sql.setString(1, email);
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            set = sql.executeQuery();
            int erg = 0;
            if(set.next()) {
                erg = set.getInt(1);
            }
            if (erg == 1) {
                DatabaseLayerException e = new DatabaseLayerException("User bereits vorhanden!");
                e.setReason(Globals.Errors.EXISTINGUSER);
                throw e;
            }

        } catch (DatabaseLayerException ex) {
            throw ex;
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

    public void updatePassword(int id, String password) throws DatabaseLayerException {
        try {
            PreparedStatement sql = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement(
                        "UPDATE collhbrs.user " +
                                "SET password = (?)" +
                                "WHERE id=(?)");
                sql.setString(1, password);
                sql.setInt(2, id);
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }
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