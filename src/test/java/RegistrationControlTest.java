import control.RegistrationControl;
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

public class RegistrationControlTest{
    private static RegistrationControl rc;
    private static EmployerDAO employer;
    private static UserDAO user;
    private static StudentDAO student;

    @BeforeClass
    public static void setup(){
        rc = new RegistrationControl();
        employer = new EmployerDAO();
        user = new UserDAO();
        student = new StudentDAO();
    }

    @Test
    public void registerStudentWithJDBCTest(){
        try {
            rc.registerStudentWithJDBC("Max", "Mustermann", "registerstudentwithjdbctest", "123");
            assertEquals("registerstudentwithjdbctest", user.findUserByUserEmailAndPassword("registerstudentwithjdbctest", "123").getEmail());
            assertThrows(DatabaseUserException.class,
                    () -> rc.registerStudentWithJDBC("Max", "Mustermann", "registerstudentwithjdbctest", "123"));


            student.deleteStudentProfil(student.findUserByUserEmailAndPassword("registerstudentwithjdbctest", "123").getId());
        }
        catch(DatabaseUserException e){
            assertEquals(true, false);
        }
        catch(DatabaseLayerException e){
            System.out.println(e.getReason());
            assertEquals(true, false);
        }
    }

    @Test
    public void registerEmployerWithJDBCTest(){
        try {
            rc.registerEmployerWithJDBC("registeremployerwithjdbctest", "germany", "strasse", "5", "Ort", "12345", "registeremployerwithjdbctest@ag.com", "123");
            assertEquals("registeremployerwithjdbctest@ag.com", employer.findUserByUserEmailAndPassword("registeremployerwithjdbctest@ag.com", "123").getEmail());

        } catch (DatabaseUserException e) {
            assertEquals(true, false);
        } catch (DatabaseLayerException e) {
            assertEquals(true, false);
        }

        assertThrows(DatabaseUserException.class, () -> rc.registerEmployerWithJDBC("registerEmployerWithJDBCTest", "germany", "strasse", "5", "Ort", "12345", "registerEmployerWithJDBCTest@ag.com", "123"));
        //Todo
        // Delete registeremployerwithjdbctest@ag.com from Database after each run
    }

    @AfterClass
    public static void teardown(){
        rc = null;
        employer = null;
        user = null;
        student = null;
    }
}
