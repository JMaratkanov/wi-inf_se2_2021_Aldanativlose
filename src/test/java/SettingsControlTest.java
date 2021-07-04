import control.SettingsControl;
import control.exceptions.DatabaseUserException;
import daos.EmployerDAO;
import daos.StudentDAO;
import daos.UserDAO;
import db.exceptions.DatabaseLayerException;
import dtos.UserDTO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SettingsControlTest {
    private static SettingsControl sc;
    private static StudentDAO student;
    private static UserDAO user;
    private static EmployerDAO employer;

    @BeforeClass
    public static void setUp(){
        sc = new SettingsControl();
        student = new StudentDAO();
        user = new UserDAO();
        employer = new EmployerDAO();
    }

    @AfterClass
    public static void tearDown(){
        sc = null;
        student = null;
        user = null;
        employer = null;
    }

    @Test
    public void updateStudentWithJDBCTest() throws DatabaseLayerException, DatabaseUserException {
        student.setStudentByFirstnameLastnameEmailPassword("Max", "Mustermann", "updateStudentWithJDBCTest", "123");
        int id = student.findUserByUserEmailAndPassword("updateStudentWithJDBCTest", "123").getId();
        sc.updateStudentWithJDBC(id, "test", "test", "test", "test", "test", null, "test", "test", null);
        UserDTO studentDTO = student.getFullStudentDTOByStudentID(student.getStudentIdByUserId(id));
        assertEquals(student.getStudentIdByUserId(id), studentDTO.getId());
        assertEquals("test", studentDTO.getFirstName());
        assertEquals("test", studentDTO.getLastName());

        student.deleteStudentProfil(student.findUserByUserEmailAndPassword("updateStudentWithJDBCTest", "123").getId());
    }

    @Test
    public void getStudentWithJDBCByIDTest() throws DatabaseLayerException, DatabaseUserException {
        student.setStudentByFirstnameLastnameEmailPassword("Max", "Mustermann", "getStudentWithJDBCByIDTest", "123");
        int id = student.findUserByUserEmailAndPassword("getStudentWithJDBCByIDTest", "123").getId();
        UserDTO studentDTO = sc.getStudentWithJDBCByID(id);
        assertEquals(student.getStudentIdByUserId(id), studentDTO.getId());
        assertEquals("Max", studentDTO.getFirstName());
        assertEquals("Mustermann", studentDTO.getLastName());

        student.deleteStudentProfil(student.findUserByUserEmailAndPassword("getStudentWithJDBCByIDTest", "123").getId());

        assertEquals("Nutzer konnte nicht gefunden werden! Bitte überprüfen Sie Ihre Eingaben!",
                assertThrows(DatabaseUserException.class, ()-> sc.getStudentWithJDBCByID(0)).getReason());
    }

    @Test
    public void checkIfOldPasswordCorrectTest() throws DatabaseLayerException {
        employer.setEmployer("checkIfOldPasswordCorrectTest", "germany", "strasse", "5", "Ort", "12345", "checkIfOldPasswordCorrectTest@ag.com", "123");
        int id = employer.findUserByUserEmailAndPassword("checkIfOldPasswordCorrectTest@ag.com", "123").getId();
        assertEquals(true, sc.checkIfOldPasswordCorrect(id,"123"));
        assertEquals(false, sc.checkIfOldPasswordCorrect(id,"1234"));

        employer.deleteEmployerProfil(user.findUserByUserEmailAndPassword("checkIfOldPasswordCorrectTest@ag.com", "123").getId());
    }

    @Test
    public void updatePasswordTest() throws DatabaseLayerException, DatabaseUserException {
        employer.setEmployer("updatePasswordSCTest", "germany", "strasse", "5", "Ort", "12345", "updatePasswordSCTest@ag.com", "123");
        int id = employer.findUserByUserEmailAndPassword("updatePasswordSCTest@ag.com", "123").getId();

        sc.updatePassword(id, "1234");
        assertEquals("1234", user.getUserPasswordById(id));

        employer.deleteEmployerProfil(user.findUserByUserEmailAndPassword("updatePasswordSCTest@ag.com", "1234").getId());
    }
}
