package daos;

import db.JDBCConnection;
import db.exceptions.DatabaseLayerException;
import dtos.UserDTO;
import dtos.impl.UserDTOimpl;
import globals.Globals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public UserDTO findUserByUserEmailAndPassword(String email, String password) throws DatabaseLayerException {
        ResultSet set;

        try {
            PreparedStatement sql = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("SELECT * FROM collhbrs.user WHERE collhbrs.user.email = ? AND collhbrs.user.password = ?");
                sql.setString(1, email);
                sql.setString(2, password);
            } catch (DatabaseLayerException | NullPointerException e) {
                e.printStackTrace();
            }

            assert sql != null;
            set = sql.executeQuery();

        } catch (SQLException ex) {
            throw new DatabaseLayerException(Globals.Errors.SQLERROR);
        } catch (NullPointerException ex) {
            throw new DatabaseLayerException(Globals.Errors.DATABASE);
        }

        UserDTOimpl user;

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
                throw new DatabaseLayerException(Globals.Errors.NOUSERFOUND);
            }
        } catch (SQLException ex) {
            throw new DatabaseLayerException(Globals.Errors.DATABASE);
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }

    // Prüft anhand einer E-Mail ob ein User mit dieser E-Mail schon existiert
    public void checkOnExistingUser(String email) throws DatabaseLayerException{
        ResultSet set;
        try {
            PreparedStatement sql = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("SELECT COUNT(*) FROM collhbrs.user WHERE email LIKE ?");
                sql.setString(1, email);
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            assert sql != null;
            set = sql.executeQuery();
            int erg = 0;
            if(set.next()) {
                erg = set.getInt(1);
            }
            if (erg == 1) {
                throw new DatabaseLayerException(Globals.Errors.EXISTINGUSER);
            }

        } catch (SQLException ex) {
            throw new DatabaseLayerException(Globals.Errors.SQLERROR);
        } catch (NullPointerException ex) {
            throw new DatabaseLayerException(Globals.Errors.DATABASE);
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

    public String getUserPasswordById(int id) throws DatabaseLayerException {
        ResultSet set;
        String userPW = "";

        try {
            PreparedStatement sql = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("SELECT password FROM collhbrs.user WHERE collhbrs.user.id = ?");
                sql.setInt(1, id);
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            assert sql != null;
            set = sql.executeQuery();

            if(set.next()) {
                userPW = set.getString(1);
            }

        } catch (SQLException ex) {
            throw new DatabaseLayerException(Globals.Errors.SQLERROR);
        } catch (NullPointerException ex) {
            throw new DatabaseLayerException(Globals.Errors.DATABASE);
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
        return userPW;
    }
}