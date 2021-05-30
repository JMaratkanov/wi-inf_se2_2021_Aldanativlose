/*
package daos;

import db.JDBCConnection;
import db.exceptions.DatabaseLayerException;
import dtos.RolleDTO;
import dtos.UserDTO;
import dtos.impl.UserDTOimpl;
import globals.Globals;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class UserDAO {

    public UserDTO findUserByUseridAndPassword(String id, String password) throws DatabaseLayerException {
        ResultSet set = null;

        try {
            Statement statement = null;
            try {
                statement = JDBCConnection.getInstance().getStatement();
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            //TODO SQL Query hier einfügen
            set = statement.executeQuery("");
            set = statement.executeQuery(
                    "SELECT * "
                            + "FROM collhbrs.user "
                            + "WHERE collhbrs.user.email = \'" + id + "\'"
                            + " AND collhbrs.user.password = \'" + password + "\'");
        } catch (SQLException ex) {
            DatabaseLayerException e = new DatabaseLayerException("SQL-Fehler");
            e.setReason(Globals.Errors.SQLERROR);
            throw e;
        }
        catch (NullPointerException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Datenbankverbindungs-Fehler");
            e.setReason(Globals.Errors.DATABASE);
            throw e;
        }

        UserDTOimpl user = null;

        try {
            if (set.next()) {
                // Durchführung des Object-Relational-Mapping (ORM)

                user = new UserDTOimpl();
                user.setId( set.getInt(1));
                user.setFirstname( set.getString(4) );
                user.setLastname(set.getString(5));

                // Beziehe die Rollen eines Users:
                RolleDAO rolleDAO = new RolleDAO();
                List<RolleDTO> rollen = rolleDAO.getRolesOfUser(user);

                // Einsetzen der Rollen in ein User-Object
                user.setRoles(rollen);

                return user;

            } else {
                // Error Handling
                DatabaseLayerException e = new DatabaseLayerException("USER NICHT GEFUNDEN");
                e.setReason(Globals.Errors.NOUSERFOUND);
                throw e;
            }
        } catch (SQLException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Irgendwo in der DB ist kacke am dampfen");
            e.setReason(Globals.Errors.DATABASE);
            throw e;

        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }


}
*/
package daos;

import db.JDBCConnection;
import db.exceptions.DatabaseLayerException;
import dtos.RolleDTO;
import dtos.UserDTO;
import dtos.impl.UserDTOimpl;
import globals.Globals;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class UserDAO {

    public UserDTO findUserByUseridAndPassword(String id, String password) throws DatabaseLayerException {
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
                            + "WHERE collhbrs.user.email = \'" + id + "\'"
                            + " AND collhbrs.user.password = \'" + password + "\'");

        } catch (SQLException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Fehler im SQL-Befehl!");
            e.setReason(Globals.Errors.SQLERROR);
            throw e;
        }
        catch (NullPointerException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Fehler bei Datenbankverbindung!");
            e.setReason(Globals.Errors.DATABASE);
            throw e;
        }

        UserDTOimpl user = null;

        try {
            if (set.next()) {
                // Durchführung des Object-Relational-Mapping (ORM)

                user = new UserDTOimpl();
                user.setId( set.getInt(1));
                //TODO
                // Haben setFirstname und setLastname für den Zugriff auf unsere Datenbank ausgenommen, da wir Vor- und Nachname
                // bisher nicht in der User Tabelle vorhanden ist. Ggf.: muss das hier noch angepasst werden!
                //user.setFirstname( set.getString(4) );
                //user.setLastname(set.getString(5));


                return user;

            } else {
                // Error Handling
                DatabaseLayerException e = new DatabaseLayerException("No User Could be found");
                e.setReason(Globals.Errors.NOUSERFOUND);
                throw e;
            }
        } catch (SQLException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Probleme mit der Datenbank");
            e.setReason(Globals.Errors.DATABASE);
            throw e;

        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }

    public void setUserByEmailAndPassword(String email, String password)  throws DatabaseLayerException{
        try {
            Statement statement = null;
            try {
                statement = JDBCConnection.getInstance().getStatement();
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            statement.executeQuery(
                    "INSERT INTO collhbrs.user(id, email, password, userid)"
                        + "VALUES ( 10,  \'" + email + "\', \'" + password + "\', 'test2')");

        } catch (SQLException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Fehler im SQL-Befehl!");
            e.setReason(Globals.Errors.SQLERROR);
            throw e;
        }
        catch (NullPointerException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Fehler bei Datenbankverbindung!");
            e.setReason(Globals.Errors.DATABASE);
            throw e;
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }
}