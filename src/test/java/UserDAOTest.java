import control.exceptions.DatabaseUserException;
import control.factory.Factory;
import daos.UserDAO;
import db.exceptions.DatabaseLayerException;
import dtos.UserDTO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserDAOTest {
    private static UserDAO user;

    @BeforeClass
    public static void setup(){
        user = new UserDAO();
    }

    @AfterClass
    public static void tearDown(){
        user = null;
    }

    @Test
    public void findUserByUseridAndPasswordTest(){
        try{
            assertEquals("demo", user.findUserByUseridAndPassword("demo", "demo").getEmail());
            assertEquals(199, user.findUserByUseridAndPassword("demo", "demo").getId());
        }
        catch(DatabaseLayerException e){
            assertEquals(true, false);
        }
        assertEquals("Nutzer konnte nicht gefunden werden, sind sie bereits registriert?", assertThrows(DatabaseLayerException.class, () -> user.findUserByUseridAndPassword("nicht Vorhanden", "123")).getReason());
    }

    @Test
    public void setStudentByEmailAndPasswordTest(){
        assertEquals("sql error", assertThrows(DatabaseLayerException.class, () -> user.setStudentByEmailAndPassword("demo", "demo")).getReason());
        try{
            user.setStudentByEmailAndPassword("TestStudent", "123");
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

    @Test
    public void checkOnExistingUserTest(){
        //src/main/java/daos/UserDAO.java
        //33.6%	67	4
        assertEquals("Dieser Nutzer existiert bereits, loggen sie sich mit ihrer Email und Passwort ein", assertThrows(DatabaseLayerException.class, () -> user.checkOnExistingUser("demo")).getReason());

        try {
            user.checkOnExistingUser("checkOnExistingUserTest");
        }
        catch(DatabaseLayerException e){
            System.out.println(e.getReason());
            assertEquals(true, false);
        }
    }

    @Test
    public void setEmployerTest(){
        assertEquals("sql error", assertThrows(DatabaseLayerException.class,
                () -> user.setEmployer("Test AG", "germany", "strasse", "5", "Ort", "12345", "Test@ag.com", "password")).getReason());
        try{
            user.setEmployer("TestCorrect", "germany", "strasse", "5", "Ort", "12345", "TestCorrect@ag.com", "password");
            UserDTO userDTO = user.findUserByUseridAndPassword("TestCorrect@ag.com", "password");
            assertEquals("TestCorrect@ag.com", userDTO.getEmail());
        }
        catch(DatabaseLayerException e){
            assertEquals(true, false);
        }

        //Todo
        // Delete TestCorrect@ag.com from Database after each run
    }
}
