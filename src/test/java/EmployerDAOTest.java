import daos.EmployerDAO;
import daos.UserDAO;
import db.exceptions.DatabaseLayerException;
import dtos.UserDTO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmployerDAOTest {
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
    public void setEmployerTest(){
        try{
            employer.setEmployer("setEmployerTest", "germany", "strasse", "5", "Ort", "12345", "setEmployerTest@ag.com", "123");
            UserDTO userDTO = user.findUserByUserEmailAndPassword("setEmployerTest@ag.com", "123");
            assertEquals("setEmployerTest@ag.com", userDTO.getEmail());

            assertEquals("Dieser Nutzer existiert bereits, loggen sie sich mit ihrer Email und Passwort ein", assertThrows(DatabaseLayerException.class,
                    () -> employer.setEmployer("setEmployerTestqwe", "germany", "strasse", "5", "Ort", "12345", "setEmployerTest@ag.com", "123")).getReason());

            employer.deleteEmployerProfil(user.findUserByUserEmailAndPassword("setEmployerTest@ag.com", "123").getId());
        }
        catch(DatabaseLayerException e){
            assertEquals(true, false);
        }

       //Todo
        // Delete setEmployerTest@ag.com from Database after each run
    }

    @Test
    public void deleteEmployerProfilTest(){
        try {
            employer.setEmployer("deleteEmployerProfil", "germany", "strasse", "5", "Ort", "12345", "deleteEmployerProfilTest@ag.com", "123");
            int id = user.findUserByUserEmailAndPassword("deleteEmployerProfilTest@ag.com", "123").getId();

            employer.deleteEmployerProfil(id);
        }
        catch (DatabaseLayerException e){
            System.out.println(e.getReason());
            assertEquals(true, false);
        }
    }
}
