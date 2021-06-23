package daos;

import db.JDBCConnection;
import db.exceptions.DatabaseLayerException;
import dtos.UserDTO;
import dtos.impl.StudentDTOimpl;
import globals.Globals;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;

public class StudentDAO extends UserDAO{
    public void setStudentByFirstnameLastnameEmailPassword(String firstname, String lastname, String email, String password)  throws DatabaseLayerException {
        ResultSet set = null;
        String kurzbeschreibung = "Bitte trage hier eine Kurzbeschreibung ein!";
        try {
            PreparedStatement sql = null;
            PreparedStatement sql2 = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("INSERT INTO collhbrs.student_profil(vorname, nachname, kurzbeschreibung,referenzen, kenntnisse, studiengang, fachbereich, geb_date, semester) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) returning id");
                sql.setString(1, firstname);
                sql.setString(2, lastname);
                sql.setString(3, kurzbeschreibung);
                sql.setString(4, "Bitte was eingeben");
                sql.setString(5, "Bitte was eingeben");
                sql.setString(6, "Bitte was eingeben");
                sql.setString(7, "Bitte was eingeben");
                sql.setDate(8, new java.sql.Date(1)); //1970 ;D
                sql.setDate(9, new java.sql.Date(1));
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
            PreparedStatement statement = null;
            try {
                statement = JDBCConnection.getInstance().getPreparedStatement("SELECT student_profil FROM collhbrs.user WHERE collhbrs.user.id = ?");
                statement.setInt(1, id);
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

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
                user.setRefFromDB(set.getString(4));
                user.setSkillFromDB(set.getString(6));
                user.setDesFromDB(set.getString(7));
                user.setSemester(set.getDate(8));
                user.setsGangfromDB(set.getString(9));
                user.setFachfromDB(set.getString(10));
                user.setGeb_date(set.getDate(11));

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

    public void updateStudentData(int id, String vorname, String nachname,  String referenzen, String kenntnisse, String kurzbeschreibung, LocalDate semester, String studiengang, String fachbereich, LocalDate geb_date) throws DatabaseLayerException{
        Date semesterAsDate = null;
        Date gebTagAsDate = null;
        int student_profil_id = getStudentIdByUserId(id);

        ZoneId defaultZoneId = ZoneId.systemDefault();
        if (semester != null){
            semesterAsDate = Date.valueOf(semester);
        }
        if (geb_date != null) {
            gebTagAsDate = Date.valueOf(geb_date);
        }

        try {
            PreparedStatement sql = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement(
                        "UPDATE collhbrs.student_profil " +
                                "SET vorname = (?), nachname = (?), referenzen = (?), kenntnisse = (?), kurzbeschreibung = (?), " +
                                "semester = (?), studiengang = (?), fachbereich = (?), geb_date = (?)" +
                                "WHERE id=(?)");
                sql.setString(1, vorname);
                sql.setString(2, nachname);
                sql.setString(3, referenzen);
                sql.setString(4, kenntnisse);
                sql.setString(5, kurzbeschreibung);
                sql.setDate(6, semesterAsDate);
                sql.setString(7, studiengang);
                sql.setString(8, fachbereich);
                sql.setDate(9, gebTagAsDate);
                sql.setInt(10, student_profil_id);
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

    public void deleteStudentProfil(int id) throws DatabaseLayerException {
        try {
            PreparedStatement sql = null;
            try {
                int studentID = getStudentIdByUserId(id);
                sql = JDBCConnection.getInstance().getPreparedStatement(
                        "DELETE FROM collhbrs.student_profil WHERE collhbrs.student_profil.id = ?");
                sql.setInt(1, studentID);
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
