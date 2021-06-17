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
    private static EmployerDAO employer;

    @BeforeClass
    public static void setup(){
        user = new UserDAO();
        employer = new EmployerDAO();
    }

    @AfterClass
    public static void tearDown(){
        user = null;
        employer = null;
    }

    @Test
    public void findUserByUseridAndPasswordTest(){
        try{
            employer.setEmployer("findUserByUseridAndPasswordTest", "germany", "strasse", "5", "Ort", "12345", "findUserByUseridAndPasswordTest@ag.com", "123");

            assertEquals("findUserByUseridAndPasswordTest@ag.com", user.findUserByUserEmailAndPassword("findUserByUseridAndPasswordTest@ag.com", "123").getEmail());
        }
        catch(DatabaseLayerException e){
            assertEquals(true, false);
        }
        assertEquals("Nutzer konnte nicht gefunden werden, sind sie bereits registriert?", assertThrows(DatabaseLayerException.class, () -> user.findUserByUserEmailAndPassword("nicht Vorhanden", "123")).getReason());

        //Todo
        // Delete findUserByUseridAndPasswordTest@ag.com from Database after each run
    }

    @Test
    public void checkOnExistingUserTest(){
        assertEquals("Dieser Nutzer existiert bereits, loggen sie sich mit ihrer Email und Passwort ein", assertThrows(DatabaseLayerException.class, () -> user.checkOnExistingUser("demo")).getReason());
    }

    @Test
    public void updatePasswordTest(){
        try{
            employer.setEmployer("updatePasswordTest", "germany", "strasse", "5", "Ort", "12345", "updatePasswordTest@ag.com", "123");
            int id = employer.findUserByUserEmailAndPassword("updatePasswordTest@ag.com", "123").getId();

            user.updatePassword(id, "1234");
            assertEquals("1234", user.getUserPasswordById(id));

        }
        catch (DatabaseLayerException e){
            System.out.println(e.getReason());
            assertEquals(true, false);
        }

        //Todo
        // Delete updatePasswordTest@ag.com from Database after each run
    }

    @Test
    public void getUserPasswordByIdTest(){
        try {
            employer.setEmployer("getUserPasswordByIdTest", "germany", "strasse", "5", "Ort", "12345", "getUserPasswordByIdTest@ag.com", "123");
            int id = employer.findUserByUserEmailAndPassword("getUserPasswordByIdTest@ag.com", "123").getId();
            assertEquals("123", user.getUserPasswordById(id));
        }
        catch (DatabaseLayerException e){
            System.out.println(e.getReason());
            assertEquals(true, false);
        }
        //Todo
        // Delete getUserPasswordByIdTest@ag.com from Database after each run
    }
}
