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

    public void updateStudentData(int id, String vorname, String nachname, String fachbereich, LocalDate semester, String studiengang, LocalDate gebTag) throws DatabaseLayerException{
        ResultSet set = null;
        Date semesterAsDate = null;
        Date gebTagAsDate = null;

        ZoneId defaultZoneId = ZoneId.systemDefault();
        if (semester != null){
            semesterAsDate = Date.from(semester.atStartOfDay(defaultZoneId).toInstant());
        }
        if (gebTag != null) {
            gebTagAsDate = Date.from(gebTag.atStartOfDay(defaultZoneId).toInstant());
        }

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
}
