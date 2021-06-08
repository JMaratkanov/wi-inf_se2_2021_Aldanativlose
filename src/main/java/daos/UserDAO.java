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
import dtos.UserDTO;
import dtos.impl.UserDTOimpl;
import globals.Globals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


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
        } catch (NullPointerException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Fehler bei Datenbankverbindung!");
            e.setReason(Globals.Errors.DATABASE);
            throw e;
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }

    public void updateUserData(int id, String vorname, String nachname, String fachbereich, LocalDate semester, String studiengang, LocalDate gebTag) throws DatabaseLayerException{
        ResultSet set = null;

        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date semesterAsDate = Date.from(semester.atStartOfDay(defaultZoneId).toInstant());
        Date gebTagAsDate = Date.from(gebTag.atStartOfDay(defaultZoneId).toInstant());

        try {
            PreparedStatement sql = null;
            PreparedStatement sql2 = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("INSERT INTO collhbrs.student_profil(vorname, nachname, fachbereich, studiengang, semester) VALUES (?, ?, ?, ?, ?, ?) RETURNING id");
                sql.setString(1, vorname);
                sql.setString(2, nachname);
                sql.setString(3, fachbereich);
                sql.setString(4, studiengang);
                sql.setDate(5, (java.sql.Date) semesterAsDate);
                sql.setDate(6, (java.sql.Date) gebTagAsDate);
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            set = sql.executeQuery();
            int studentProfilId = 0;
            if(set.next()) {
                studentProfilId = set.getInt(1);
            }

            try {
                sql2 = JDBCConnection.getInstance().getPreparedStatement("UPDATE collhbrs.user(studenten_profil) VALUES (?) WHERE id=(?)");
                sql2.setInt(1, studentProfilId);
                sql2.setInt(2, id);
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

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

    public UserDTO getFullStudentDTO(int id) throws DatabaseLayerException {
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
                            + "FROM collhbrs.user "
                            + "WHERE collhbrs.user.id = \'" + Integer.toString(id) + "\'");

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