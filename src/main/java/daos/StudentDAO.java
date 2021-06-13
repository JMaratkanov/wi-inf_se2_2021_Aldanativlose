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

public class StudentDAO extends UserDAO{
    public void setStudentByEmailAndPassword(String email, String password)  throws DatabaseLayerException {
        try {
            PreparedStatement sql = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("INSERT INTO collhbrs.user(email, password, rolle) VALUES (?, ?, ?)");
                sql.setString(1, email);
                sql.setString(2, password);
                sql.setInt(3, 1);
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

    public int getStudentIdByUserId(int id) throws DatabaseLayerException {
        ResultSet set = null;
        int studentProfilId = 0;

        try {
            Statement statement = null;
            try {
                statement = JDBCConnection.getInstance().getStatement();
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            set = statement.executeQuery(
                    "SELECT student_profil "
                            + "FROM collhbrs.user "
                            + "WHERE collhbrs.user.id = \'" + id+ "\'");

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

    public UserDTO getFullStudentDTOByStudentID(int id) throws DatabaseLayerException {
        ResultSet set = null;

        try {
            Statement statement = null;
            try {
                statement = JDBCConnection.getInstance().getStatement();
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            //TODO Select abfrage anpassen sodass alle Daten eines Studenten mit einer bestimmten ID ausgelesen werden
            set = statement.executeQuery(
                    "SELECT * "
                            + "FROM collhbrs.student_profil "
                            + "WHERE collhbrs.student_profil.id = \'" + id+ "\'");

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
                user.setFirstname(set.getString(2));
                user.setLastname(set.getString(3));

                //TODO rest des dtos füllen
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
}
