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
    private static StudentDAO student;

    @BeforeClass
    public static void setup(){
        user = new UserDAO();
        employer = new EmployerDAO();
        student = new StudentDAO();
    }

    @AfterClass
    public static void tearDown(){
        user = null;
        employer = null;
        student = null;
    }

    @Test
    public void findUserByUseridAndPasswordTest() throws DatabaseLayerException {
        employer.setEmployer("findUserByUseridAndPasswordTest", "germany", "strasse", "5", "Ort", "12345", "findUserByUseridAndPasswordTest@ag.com", "123");

        assertEquals("findUserByUseridAndPasswordTest@ag.com", user.findUserByUserEmailAndPassword("findUserByUseridAndPasswordTest@ag.com", "123").getEmail());

        employer.deleteEmployerProfil(user.findUserByUserEmailAndPassword("findUserByUseridAndPasswordTest@ag.com", "123").getId());

        assertEquals("Nutzer konnte nicht gefunden werden, sind sie bereits registriert?", assertThrows(DatabaseLayerException.class, () -> user.findUserByUserEmailAndPassword("nicht Vorhanden", "123")).getReason());
    }

    @Test
    public void checkOnExistingUserTest() throws DatabaseLayerException {
        employer.setEmployer("checkOnExistingUserTest", "germany", "strasse", "5", "Ort", "12345", "checkOnExistingUserTest@ag.com", "123");

        assertEquals("User existiert bereits!", assertThrows(DatabaseLayerException.class, () -> user.checkOnExistingUser("checkOnExistingUserTest@ag.com")).getReason());

        employer.deleteEmployerProfil(user.findUserByUserEmailAndPassword("checkOnExistingUserTest@ag.com", "123").getId());
    }

    @Test
    public void updatePasswordTest() throws DatabaseLayerException {
        employer.setEmployer("updatePasswordTest", "germany", "strasse", "5", "Ort", "12345", "updatePasswordTest@ag.com", "123");
        int id = employer.findUserByUserEmailAndPassword("updatePasswordTest@ag.com", "123").getId();

        user.updatePassword(id, "1234");
        assertEquals("1234", user.getUserPasswordById(id));

        employer.deleteEmployerProfil(user.findUserByUserEmailAndPassword("updatePasswordTest@ag.com", "1234").getId());
    }

    @Test
    public void getUserPasswordByIdTest() throws DatabaseLayerException {
        employer.setEmployer("getUserPasswordByIdTest", "germany", "strasse", "5", "Ort", "12345", "getUserPasswordByIdTest@ag.com", "123");
        int id = employer.findUserByUserEmailAndPassword("getUserPasswordByIdTest@ag.com", "123").getId();
        assertEquals("123", user.getUserPasswordById(id));

        employer.deleteEmployerProfil(user.findUserByUserEmailAndPassword("getUserPasswordByIdTest@ag.com", "123").getId());
    }
}
