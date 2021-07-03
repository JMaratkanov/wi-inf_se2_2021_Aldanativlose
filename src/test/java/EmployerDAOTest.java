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
    private static UserDAO userDAO;
    private static EmployerDAO employerDAO;

    @BeforeClass
    public static void setup(){
        userDAO = new UserDAO();
        employerDAO = new EmployerDAO();
    }

    @AfterClass
    public static void tearDown(){
        userDAO = null;
        employerDAO = null;
    }


    @Test
    public void setEmployerTest() throws DatabaseLayerException {
        employerDAO.setEmployer("setEmployerTest", "germany", "strasse", "5", "Ort", "12345", "setEmployerTest@ag.com", "123");
        UserDTO userDTO = userDAO.findUserByUserEmailAndPassword("setEmployerTest@ag.com", "123");
        assertEquals("setEmployerTest@ag.com", userDTO.getEmail());

        assertEquals("There is already a user with this email address!", assertThrows(DatabaseLayerException.class,
                () -> employerDAO.setEmployer("setEmployerTestqwe", "germany", "strasse", "5", "Ort", "12345", "setEmployerTest@ag.com", "123")).getReason());

        employerDAO.deleteEmployerProfil(userDAO.findUserByUserEmailAndPassword("setEmployerTest@ag.com", "123").getId());
    }

    @Test
    public void deleteEmployerProfilTest() throws DatabaseLayerException {
        employerDAO.setEmployer("deleteEmployerProfil", "germany", "strasse", "5", "Ort", "12345", "deleteEmployerProfilTest@ag.com", "123");
        int id = userDAO.findUserByUserEmailAndPassword("deleteEmployerProfilTest@ag.com", "123").getId();

        employerDAO.deleteEmployerProfil(id);
    }
}
