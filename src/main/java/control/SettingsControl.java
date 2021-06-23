package control;

import control.exceptions.DatabaseUserException;
import daos.StudentDAO;
import daos.UserDAO;
import db.exceptions.DatabaseLayerException;
import dtos.UserDTO;
import globals.Globals;
import java.time.LocalDate;

public class SettingsControl extends MainControl{
    private UserDTO userDTO = null;

    public void updateStudentWithJDBC(int id, String vorname, String nachname,  String referenzen, String kenntnisse, String kurzbeschreibung, LocalDate semester, String studiengang, String fachbereich, LocalDate geb_date) throws DatabaseUserException {
        StudentDAO dao = new StudentDAO();
        try {
            dao.updateStudentData(id, vorname, nachname, referenzen, kenntnisse, kurzbeschreibung, semester, studiengang, fachbereich, geb_date);
        }
        catch ( DatabaseLayerException e) {
            checkReasonAndThrowEx(e.getReason());
        }
    }

    public void deleteStudentWithJDBC(int id) throws DatabaseUserException {
        StudentDAO dao = new StudentDAO();
        try {
            dao.deleteStudentProfil(id);
        } catch (DatabaseLayerException e) {
            checkReasonAndThrowEx(e.getReason());
        }
    }

    public UserDTO getStudentWithJDBCByID(int ID) throws DatabaseUserException {
        StudentDAO dao = new StudentDAO();
        try {
            userDTO = dao.getFullStudentDTOByStudentID(dao.getStudentIdByUserId(ID));
        } catch ( DatabaseLayerException e) {
            checkReasonAndThrowEx(e.getReason());
        }
        return userDTO;
    }

    public boolean checkIfOldPasswordCorrect(int ID, String alt) {
        UserDAO tmp = new UserDAO();
        String pwFromDB = "";
        try {
            pwFromDB = tmp.getUserPasswordById(ID);
        } catch (DatabaseLayerException e) {
            e.printStackTrace();
            //this exception is impossible -> wenn user nicht vorhanden wäre wäre er nicht auf dieser seite
        }
        return alt.equals(pwFromDB);
    }

    public void updatePassword(int id, String password) throws DatabaseUserException {
        UserDAO dao = new UserDAO();
        try {
            dao.updatePassword(id, password);
        }
        catch ( DatabaseLayerException e) {
            checkReasonAndThrowEx(e.getReason());
        }
    }
}
