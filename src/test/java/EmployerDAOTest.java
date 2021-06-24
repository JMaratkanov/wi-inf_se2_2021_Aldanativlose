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
        }
        catch(DatabaseLayerException e){
            assertEquals(true, false);
        }

        assertEquals("sql error", assertThrows(DatabaseLayerException.class,
                () -> employer.setEmployer("setEmployerTest", "germany", "strasse", "5", "Ort", "12345", "setEmployerTest@ag.com", "123")).getReason());
        //Todo
        // Delete setEmployerTest@ag.com from Database after each run
    }
}
