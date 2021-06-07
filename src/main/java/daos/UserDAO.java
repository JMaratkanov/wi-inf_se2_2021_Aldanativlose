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

import com.vaadin.flow.component.html.Pre;
import control.exceptions.DatabaseUserException;
import db.JDBCConnection;
import db.exceptions.DatabaseLayerException;
import dtos.RolleDTO;
import dtos.UserDTO;
import dtos.impl.UserDTOimpl;
import globals.Globals;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
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
        } catch (NullPointerException ex) {
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
                user.setEmail(set.getString(2));
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

    public void setStudentByEmailAndPassword(String email, String password)  throws DatabaseLayerException{
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


    public void setEmployer(String companyName, String country, String street, String hNr, String place, String plz, String email, String password)  throws DatabaseLayerException{
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

            sql2.executeUpdate();

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