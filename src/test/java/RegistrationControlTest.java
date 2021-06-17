import control.RegistrationControl;
import control.exceptions.DatabaseUserException;
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

    @BeforeClass
    public static void setup(){
        rc = new RegistrationControl();
    }

    @Test
    public void registerStudentWithJDBCTest(){
        assertEquals("There is already a user with this email!", assertThrows(DatabaseUserException.class,
                () -> rc.registerStudentWithJDBC("Max", "Mustermann", "demo", "demo")).getReason());
        try {
            rc.registerStudentWithJDBC("Max", "Mustermann", "TestStudent", "123");
            UserDAO ud = new UserDAO();
            UserDTO user = ud.findUserByUserEmailAndPassword("TestStudent", "123");
            assertEquals("TestStudent", user.getEmail());
        }
        catch(DatabaseUserException e){
            assertEquals(true, false);
        }
        catch(DatabaseLayerException e){
            assertEquals(true, false);
        }
        //Todo
        // Delete TestStudent from Database after each run
    }

    //Todo
    // Tests mit SQLerror und Databaseerror

    @Test
    public void registerEmployerWithJDBCTest(){
        assertEquals("There is already a user with this email!", assertThrows(DatabaseUserException.class,
                () -> rc.registerEmployerWithJDBC("Test AG", "germany", "strasse", "5", "Ort", "12345", "Test@ag.com", "password")).getReason());
        try {
            rc.registerEmployerWithJDBC("TestCorrect", "germany", "strasse", "5", "Ort", "12345", "TestCorrect@ag.com", "password");
            UserDAO ud = new UserDAO();
            UserDTO user = ud.findUserByUserEmailAndPassword("TestCorrect@ag.com", "password");
            assertEquals("TestCorrect@ag.com", user.getEmail());
        } catch (DatabaseUserException e) {
            assertEquals(true, false);
        } catch (DatabaseLayerException e) {
            assertEquals(true, false);
        }

        //Todo
        // Delete TestCorrect@ag.com from Database after each run
        //Email in unternehmen_profil redundant?
        //Adresse in unternehmen_profil redundant
    }

    @AfterClass
    public static void teardown(){
        rc = null;
    }
}
