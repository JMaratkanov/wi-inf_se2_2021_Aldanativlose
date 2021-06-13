import control.exceptions.DatabaseUserException;
import control.factory.Factory;
import daos.StudentDAO;
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
    private static StudentDAO student;

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
            assertEquals(1, user.findUserByUseridAndPassword("demo", "demo").getId());
        }
        catch(DatabaseLayerException e){
            assertEquals(true, false);
        }
        assertEquals("Nutzer konnte nicht gefunden werden, sind sie bereits registriert?", assertThrows(DatabaseLayerException.class, () -> user.findUserByUseridAndPassword("nicht Vorhanden", "123")).getReason());
    }

    @Test
    public void setStudentByEmailAndPasswordTest(){
        assertEquals("sql error", assertThrows(DatabaseLayerException.class, () -> student.setStudentByEmailAndPassword("demo", "demo")).getReason());
        try{
            student.setStudentByEmailAndPassword("TestStudent", "123");
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
        try {
            user.checkOnExistingUser("tst");
        }
        catch(DatabaseLayerException e){
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
