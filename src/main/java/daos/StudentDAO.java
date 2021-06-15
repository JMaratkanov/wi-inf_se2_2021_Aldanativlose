package daos;

import db.JDBCConnection;
import db.exceptions.DatabaseLayerException;
import dtos.UserDTO;
import dtos.impl.StudentDTOimpl;
import globals.Globals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class StudentDAO extends UserDAO{
    public void setStudentByFirstnameLastnameEmailPassword(String firstname, String lastname, String email, String password)  throws DatabaseLayerException {
        ResultSet set = null;
        String kurzbeschreibung = "Bitte trage hier eine Kurzbeschreibung ein!";
        try {
            PreparedStatement sql = null;
            PreparedStatement sql2 = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("INSERT INTO collhbrs.student_profil(vorname, nachname, kurzbeschreibung) VALUES (?, ?, ?) returning id");
                sql.setString(1, firstname);
                sql.setString(2, lastname);
                sql.setString(3, kurzbeschreibung);
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            set = sql.executeQuery();
            int id = 0;
            if(set.next()) {
                id = set.getInt(1);
            }

            try {
                sql2 = JDBCConnection.getInstance().getPreparedStatement("INSERT INTO collhbrs.user(email, password, rolle, student_profil) VALUES (?, ?, ?, ?)");
                sql2.setString(1, email);
                sql2.setString(2, password);
                sql2.setInt(3, 1);
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

        StudentDTOimpl user = null;

        try {
            if (set.next()) {
                // Durchführung des Object-Relational-Mapping (ORM)
                //User wird mit diesen Werten in die Session gesetzt
                user = new StudentDTOimpl();
                user.setId( set.getInt(1));
                user.setFirstname(set.getString(2));
                user.setLastname(set.getString(3));
                //user.setRefFromDB(set.getString(4));
                //user.setSkillFromDB(set.getString(6));
                user.setDesFromDB(set.getString(7));
                user.setsGangfromDB(set.getString(9));
                user.setFachfromDB(set.getString(10));

                //user.setSemFromDB((LocalDate) set.getDate(8));  cast nicht möglich
                //user.setGebFromDB((LocalDate) set.getDate(11));

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
