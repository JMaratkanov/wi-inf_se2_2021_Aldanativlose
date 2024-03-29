package daos;

import db.JDBCConnection;
import db.exceptions.DatabaseLayerException;
import dtos.UserDTO;
import dtos.impl.StudentDTOimpl;
import globals.Globals;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class StudentDAO extends UserDAO{
    public void setStudentByFirstnameLastnameEmailPassword(String firstname, String lastname, String email, String password)  throws DatabaseLayerException {
        ResultSet set;
        String bwe = "Bitte etwas eingeben";
        String kurzbeschreibung = "Bitte trage hier eine Kurzbeschreibung ein!";

        try {
            checkOnExistingUser(email);

            PreparedStatement sql = null;
            PreparedStatement sql2 = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("INSERT INTO collhbrs.student_profil(vorname, nachname, kurzbeschreibung,referenzen, kenntnisse, studiengang, fachbereich, geb_date, semester) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) returning id");
                sql.setString(1, firstname);
                sql.setString(2, lastname);
                sql.setString(3, kurzbeschreibung);
                sql.setString(4, bwe);
                sql.setString(5, bwe);
                sql.setString(6, bwe);
                sql.setString(7, bwe);
                sql.setDate(8, new java.sql.Date(1)); //1970 ;D
                sql.setDate(9, new java.sql.Date(1));
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
                sql2 = JDBCConnection.getInstance().getPreparedStatement("INSERT INTO collhbrs.user(email, password, rolle, student_profil) VALUES (?, ?, ?, ?)");
                sql2.setString(1, email);
                sql2.setString(2, password);
                sql2.setInt(3, 1);
                sql2.setInt(4, id);
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            assert sql2 != null;
            sql2.executeUpdate();

        } catch (SQLException ex) {
            throw new DatabaseLayerException(Globals.Errors.SQLERROR);
        } catch (NullPointerException ex) {
            throw new DatabaseLayerException(Globals.Errors.DATABASE);
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }

    public int getStudentIdByUserId(int id) throws DatabaseLayerException {
        return getPersonalIdByUserId(id, "Student");
    }

    public UserDTO getFullStudentDTOByStudentID(int id) throws DatabaseLayerException {
        ResultSet set;

        try {
            PreparedStatement sql = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("SELECT * FROM collhbrs.student_profil WHERE collhbrs.student_profil.id = ?");
                sql.setInt(1, id);
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            assert sql != null;
            set = sql.executeQuery();

        } catch (SQLException ex) {
            throw new DatabaseLayerException(Globals.Errors.SQLERROR);
        } catch (NullPointerException ex) {
            throw new DatabaseLayerException(Globals.Errors.DATABASE);
        }

        StudentDTOimpl user;

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

                return user;
            } else {
                throw new DatabaseLayerException(Globals.Errors.NOUSERFOUND);
            }
        } catch (SQLException ex) {
            throw new DatabaseLayerException(Globals.Errors.DATABASE);
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }

    public void updateStudentData(int id, String vorname, String nachname,  String referenzen, String kenntnisse, String kurzbeschreibung, LocalDate semester, String studiengang, String fachbereich, LocalDate geb_date) throws DatabaseLayerException{
        Date semesterAsDate = null;
        Date gebTagAsDate = null;
        int student_profil_id = getStudentIdByUserId(id);

        if (semester != null){
            semesterAsDate = Date.valueOf(semester);
        }
        if (geb_date != null) {
            gebTagAsDate = Date.valueOf(geb_date);
        }

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
        } catch (SQLException ex) {
            throw new DatabaseLayerException(Globals.Errors.SQLERROR);
        }
        assert sql != null;
        executeSQLUpdateCommand(sql);
    }

    public void deleteStudentProfil(int id) throws DatabaseLayerException {
        PreparedStatement sql = null;
        try {
            int studentID = getStudentIdByUserId(id);
            sql = JDBCConnection.getInstance().getPreparedStatement(
                    "DELETE FROM collhbrs.student_profil WHERE collhbrs.student_profil.id = ?");
            sql.setInt(1, studentID);
        } catch (DatabaseLayerException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            throw new DatabaseLayerException(Globals.Errors.SQLERROR);
        }
        assert sql != null;
        executeSQLUpdateCommand(sql);
    }

    public void bewerbungDurchfuehren(int inseratID, int userID) throws DatabaseLayerException {
        int student_profil_id = getStudentIdByUserId(userID);

        PreparedStatement sql = null;
        try {
            sql = JDBCConnection.getInstance().getPreparedStatement(
                    "INSERT INTO collhbrs.bewerbung(inserat_id, student_profil) VALUES (?, ?)");
            sql.setInt(1, inseratID);
            sql.setInt(2, student_profil_id);
        } catch (DatabaseLayerException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            throw new DatabaseLayerException(Globals.Errors.SQLERROR);
        }
        assert sql != null;
        executeSQLUpdateCommand(sql);
    }

    public void checkBewerbungDoppelt(int inseratID, int userID) throws DatabaseLayerException {
        ResultSet set;
        int student_profil_id = getStudentIdByUserId(userID);
        try {
            PreparedStatement sql = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("SELECT COUNT(*) FROM collhbrs.bewerbung WHERE inserat_id = ? AND student_profil = ?");
                sql.setInt(1, inseratID);
                sql.setInt(2, student_profil_id);
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            assert sql != null;
            set = sql.executeQuery();
            int erg = 1;
            if(set.next()) {
                erg = set.getInt(1);
            }
            if(erg > 0) {
                throw new DatabaseLayerException(Globals.Errors.DOUBLEAPPLICATION);
            }

        } catch (SQLException ex) {
            throw new DatabaseLayerException(Globals.Errors.SQLERROR);
        } catch (NullPointerException ex) {
            throw new DatabaseLayerException(Globals.Errors.DATABASE);
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }
}
