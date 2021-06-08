package control;

import control.exceptions.DatabaseUserException;
import daos.UserDAO;
import db.exceptions.DatabaseLayerException;
import dtos.UserDTO;
import globals.Globals;

import java.time.LocalDate;

public class SettingsControl {
    private UserDTO userDTO = null;

    public void updateStudentWithJDBC(int id, String vorname, String nachname,  String description, String skills, String references, String fachbereich, LocalDate semester, String studiengang, LocalDate gebTag) throws DatabaseUserException {
        UserDAO dao = new UserDAO();
        try {
            dao.updateUserData(id, vorname, nachname, fachbereich, semester, studiengang, gebTag);
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

    public UserDTO getStudentWithJDBC(int ID) throws DatabaseUserException {
        UserDTO userTmp = null;
        UserDAO dao = new UserDAO();
        try {
            userDTO = dao.getFullStudentDTO( ID );
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
