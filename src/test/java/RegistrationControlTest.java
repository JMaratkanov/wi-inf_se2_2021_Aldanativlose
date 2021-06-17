import control.RegistrationControl;
import control.exceptions.DatabaseUserException;
import daos.EmployerDAO;
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

    @BeforeClass
    public static void setup(){
        rc = new RegistrationControl();
        employer = new EmployerDAO();
        user = new UserDAO();
    }

    @Test
    public void registerStudentWithJDBCTest(){
        try {
            rc.registerStudentWithJDBC("Max", "Mustermann", "registerstudentwithjdbctest@ag.com", "123");
            assertEquals("registerstudentwithjdbctest@ag.com", user.findUserByUserEmailAndPassword("registerstudentwithjdbctest@ag.com", "123").getEmail());
        }
        catch(DatabaseUserException e){
            assertEquals(true, false);
        }
        catch(DatabaseLayerException e){
            System.out.println(e.getReason());
            assertEquals(true, false);
        }

        assertEquals("There is already a user with this email!", assertThrows(DatabaseUserException.class,
                () -> rc.registerStudentWithJDBC("Max", "Mustermann", "registerstudentwithjdbctest@ag.com", "123")).getReason());
        //Todo
        // Delete registerstudentwithjdbctest@ag.com from Database after each run
    }

    //Todo
    // Tests mit SQLerror und Databaseerror

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

        assertEquals("There is already a user with this email!", assertThrows(DatabaseUserException.class,
                () -> rc.registerEmployerWithJDBC("registerEmployerWithJDBCTest", "germany", "strasse", "5", "Ort", "12345", "registerEmployerWithJDBCTest@ag.com", "123")).getReason());
        //Todo
        // Delete registeremployerwithjdbctest@ag.com from Database after each run
    }

    @AfterClass
    public static void teardown(){
        rc = null;
        employer = null;
    }
}
