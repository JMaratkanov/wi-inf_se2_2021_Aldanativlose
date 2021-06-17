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

public class UserDAOTest {
    private static UserDAO user;
    private static StudentDAO student;
    private static EmployerDAO employer;

    @BeforeClass
    public static void setup(){
        user = new UserDAO();
        student = new StudentDAO();
        employer = new EmployerDAO();
    }

    @AfterClass
    public static void tearDown(){
        user = null;
        student = null;
    }

    @Test
    public void findUserByUseridAndPasswordTest(){
        try{
            assertEquals("demo", user.findUserByUserEmailAndPassword("demo", "demo").getEmail());
            assertEquals(1, user.findUserByUserEmailAndPassword("demo", "demo").getId());
        }
        catch(DatabaseLayerException e){
            assertEquals(true, false);
        }
        assertEquals("Nutzer konnte nicht gefunden werden, sind sie bereits registriert?", assertThrows(DatabaseLayerException.class, () -> user.findUserByUserEmailAndPassword("nicht Vorhanden", "123")).getReason());
    }

    /*
    // Erst wieder aktivieren wenn das TO-DO erledigt ist!
    // Sonst läuft die Datenbank voll
    @Test
    public void setStudentByEmailAndPasswordTest(){
        assertEquals("sql error", assertThrows(DatabaseLayerException.class, () -> student.setStudentByFirstnameLastnameEmailPassword("Max", "Mustermann", "demo", "demo")).getReason());
        try{
            student.setStudentByFirstnameLastnameEmailPassword("Max", "Mustermann", "TestStudent", "123");
            UserDTO userDTO = user.findUserByUseridAndPassword("TestStudent", "123");
            assertEquals("TestStudent", userDTO.getEmail());
        }
        catch(DatabaseLayerException e){
            System.out.println(e.getReason());
            assertEquals(true, false);
        }
        //Todo
        // Delete TestStudent from Database after each run
    }
     */

    @Test
    public void checkOnExistingUserTest(){
        assertEquals("Dieser Nutzer existiert bereits, loggen sie sich mit ihrer Email und Passwort ein", assertThrows(DatabaseLayerException.class, () -> user.checkOnExistingUser("demo")).getReason());

        try {

            user.checkOnExistingUser("demo");
            //user.checkOnExistingUser("tst");
        }
        catch(DatabaseLayerException e){
            System.out.println(e);
            assertEquals(true, true);
        }
    }

    @Test
    public void setEmployerTest(){
        assertEquals("sql error", assertThrows(DatabaseLayerException.class,
                () -> employer.setEmployer("Test AG", "germany", "strasse", "5", "Ort", "12345", "Test@ag.com", "password")).getReason());
        try{
            employer.setEmployer("TestCorrect", "germany", "strasse", "5", "Ort", "12345", "TestCorrect@ag.com", "password");
            UserDTO userDTO = user.findUserByUserEmailAndPassword("TestCorrect@ag.com", "password");
            assertEquals("TestCorrect@ag.com", userDTO.getEmail());
        }
        catch(DatabaseLayerException e){
            assertEquals(true, false);
        }

        //Todo
        // Delete TestCorrect@ag.com from Database after each run
    }
}
