package control;

import control.exceptions.DatabaseUserException;
import daos.StudentDAO;
import db.exceptions.DatabaseLayerException;
import dtos.UserDTO;
import globals.Globals;

import java.time.LocalDate;

public class SettingsControl {
    private UserDTO userDTO = null;

    public void updateStudentWithJDBC(int id, String vorname, String nachname,  String referenzen, String kenntnisse, String kurzbeschreibung, LocalDate semester, String studiengang, String fachbereich, LocalDate geb_date) throws DatabaseUserException {
        StudentDAO dao = new StudentDAO();
        try {
            dao.updateStudentData(id, vorname, nachname, referenzen, kenntnisse, kurzbeschreibung, semester, studiengang, fachbereich, geb_date);
        }
        catch ( DatabaseLayerException e) {

            // Analyse und Umwandlung der technischen Errors in 'lesbaren' Darstellungen
            // Durchreichung und Behandlung der Fehler (Chain Of Responsibility Pattern (SE-1))
            String reason = e.getReason();

            if ( reason.equals((Globals.Errors.SQLERROR))) {
                throw new DatabaseUserException("There were problems with the SQL code. Please contact the developer!");
            } else if ( reason.equals((Globals.Errors.DATABASE ) )) {
                throw new DatabaseUserException("A failure occured while trying to connect to database with JDBC." +
                        "Please contact the admin");
            } else {
                throw new DatabaseUserException("A failure occured while");
            }
        }
    }

    public UserDTO getStudentWithJDBCByID(int ID) throws DatabaseUserException {
        UserDTO userTmp = null;
        StudentDAO dao = new StudentDAO();

        try {
            userDTO = dao.getFullStudentDTOByStudentID( dao.getStudentIdByUserId(ID) );
        }
        catch ( DatabaseLayerException e) {

            String reason = e.getReason();

            if (reason.equals(Globals.Errors.NOUSERFOUND)) {
                throw new DatabaseUserException("No User could be found! Please check your credentials!");
            }
            else if ( reason.equals((Globals.Errors.SQLERROR))) {
                throw new DatabaseUserException("There were problems with the SQL code. Please contact the developer!");
            }
            else if ( reason.equals((Globals.Errors.DATABASE ) )) {
                throw new DatabaseUserException("A failure occured while trying to connect to database with JDBC. " +
                        "Please contact the admin");
            }
            else {
                throw new DatabaseUserException("A failure occured while");
            }
        }
        return userDTO;
    }
}
