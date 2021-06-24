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
    public void updateStudentWithJDBCTest(){
        try{
            student.setStudentByFirstnameLastnameEmailPassword("Max", "Mustermann", "updateStudentWithJDBCTest", "123");
            int id = student.findUserByUserEmailAndPassword("updateStudentWithJDBCTest", "123").getId();
            sc.updateStudentWithJDBC(id, "test", "test", "test", "test", "test", null, "test", "test", null);
            UserDTO studentDTO = student.getFullStudentDTOByStudentID(student.getStudentIdByUserId(id));
            assertEquals(student.getStudentIdByUserId(id), studentDTO.getId());
            assertEquals("test", studentDTO.getFirstName());
            assertEquals("test", studentDTO.getLastName());

            student.deleteStudentProfil(student.findUserByUserEmailAndPassword("updateStudentWithJDBCTest", "123").getId());

        }
        catch (DatabaseLayerException e) {
            System.out.println(e.getReason());
            assertEquals(true, false);
        }
        catch (DatabaseUserException e) {
            System.out.println(e.getReason());
            assertEquals(true, false);
        }
    }

    @Test
    public void getStudentWithJDBCByIDTest(){
        try{
            student.setStudentByFirstnameLastnameEmailPassword("Max", "Mustermann", "getStudentWithJDBCByIDTest", "123");
            int id = student.findUserByUserEmailAndPassword("getStudentWithJDBCByIDTest", "123").getId();
            UserDTO studentDTO = sc.getStudentWithJDBCByID(id);
            assertEquals(student.getStudentIdByUserId(id), studentDTO.getId());
            assertEquals("Max", studentDTO.getFirstName());
            assertEquals("Mustermann", studentDTO.getLastName());

            student.deleteStudentProfil(student.findUserByUserEmailAndPassword("getStudentWithJDBCByIDTest", "123").getId());
        }
        catch (DatabaseLayerException e){
            System.out.println(e.getReason());
            assertEquals(true, false);
        }
        catch (DatabaseUserException e) {
            System.out.println(e.getReason());
            assertEquals(true, false);
        }

        assertEquals("No User could be found! Please check your credentials!",
                assertThrows(DatabaseUserException.class, ()-> sc.getStudentWithJDBCByID(0)).getReason());
    }

    @Test
    public void checkIfOldPasswordCorrectTest(){
        try{
            employer.setEmployer("checkIfOldPasswordCorrectTest", "germany", "strasse", "5", "Ort", "12345", "checkIfOldPasswordCorrectTest@ag.com", "123");
            int id = employer.findUserByUserEmailAndPassword("checkIfOldPasswordCorrectTest@ag.com", "123").getId();
            assertEquals(true, sc.checkIfOldPasswordCorrect(id,"123"));
            assertEquals(false, sc.checkIfOldPasswordCorrect(id,"1234"));

        }
        catch (DatabaseLayerException e){
            System.out.println(e.getReason());
            assertEquals(true, false);
        }

        //Todo
        // Delete checkIfOldPasswordCorrectTest@ag.com from Database after each run
    }

    @Test
    public void updatePasswordTest(){
        try{
            employer.setEmployer("updatePasswordSCTest", "germany", "strasse", "5", "Ort", "12345", "updatePasswordSCTest@ag.com", "123");
            int id = employer.findUserByUserEmailAndPassword("updatePasswordSCTest@ag.com", "123").getId();

            sc.updatePassword(id, "1234");
            assertEquals("1234", user.getUserPasswordById(id));

        }
        catch (DatabaseLayerException e){
            System.out.println(e.getReason());
            assertEquals(true, false);
        }
        catch (DatabaseUserException e) {
            System.out.println(e.getReason());
            assertEquals(true, false);
        }

        //Todo
        // Delete updatePasswordSCTest@ag.com from Database after each run
    }
}
