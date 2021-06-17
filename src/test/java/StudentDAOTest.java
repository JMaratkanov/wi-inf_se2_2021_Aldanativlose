import daos.EmployerDAO;
import daos.StudentDAO;
import daos.UserDAO;
import db.exceptions.DatabaseLayerException;
import dtos.UserDTO;
import dtos.impl.StudentDTOimpl;
import dtos.impl.UserDTOimpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.management.Notification;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StudentDAOTest {
    private static StudentDAO student;
    private static UserDAO user;

    @BeforeClass
    public static void setup(){
        student = new StudentDAO();
        user = new UserDAO();
    }

    @AfterClass
    public static void tearDown(){
        student = null;
        user = null;
    }


    // Erst wieder aktivieren wenn das TO-DO erledigt ist!
    // Sonst läuft die Datenbank voll
    @Test
    public void setStudentByEmailAndPasswordTest(){
        assertEquals("sql error", assertThrows(DatabaseLayerException.class, () -> student.setStudentByFirstnameLastnameEmailPassword("Max", "Mustermann", "demo", "demo")).getReason());
        try{
            student.setStudentByFirstnameLastnameEmailPassword("Max", "Mustermann", "setStudentByEmailAndPasswordTest", "123");
            UserDTO userDTO = student.findUserByUserEmailAndPassword("setStudentByEmailAndPasswordTest", "123");
            assertEquals("setStudentByEmailAndPasswordTest", userDTO.getEmail());
            }
        catch(DatabaseLayerException e){
            System.out.println(e.getReason());
            assertEquals(true, false);
        }
        assertEquals("sql error", assertThrows(DatabaseLayerException.class, () ->  student.setStudentByFirstnameLastnameEmailPassword("Max", "Mustermann", "setStudentByEmailAndPasswordTest", "123")).getReason());

        //Todo
        // Delete setStudentByEmailAndPasswordTest from Database after each run
    }


    @Test
    public void getStudentIdByUserIdTest(){
        try{
            assertEquals(34, student.getStudentIdByUserId(199));
        }
        catch (DatabaseLayerException e){
            assertEquals(true, false);
        }
    }

    @Test
    public void getFullStudentDTOByStudentIDTest(){
        try{
            student.setStudentByFirstnameLastnameEmailPassword("Max", "Mustermann", "getFullStudentDTOByStudentIDTest", "123");
            int id = student.getStudentIdByUserId(student.findUserByUserEmailAndPassword("getFullStudentDTOByStudentIDTest", "123").getId());
            UserDTO studentDTO = student.getFullStudentDTOByStudentID(id);
            assertEquals(id, studentDTO.getId());
            assertEquals("Max", studentDTO.getFirstName());
            assertEquals("Mustermann", studentDTO.getLastName());
        }
        catch (DatabaseLayerException e){
            System.out.println(e.getReason());
            assertEquals(true, false);
        }
        assertEquals("Nutzer konnte nicht gefunden werden, sind sie bereits registriert?",
                assertThrows(DatabaseLayerException.class, ()-> student.getFullStudentDTOByStudentID(0)).getReason());

        //Todo
        // Delete getFullStudentDTOByStudentIDTest from Database after each run
    }

    @Test
    public void updateStudentDataTest(){
        //keine Fehlermeldung, wenn student nicht gefunden wird
        //assertEquals("", assertThrows(DatabaseLayerException.class, ()-> student.updateStudentData(999, "", "", "", "", "" ,null ,"" ,"" ,null)));
        try{
            student.setStudentByFirstnameLastnameEmailPassword("Max", "Mustermann", "updateStudentDataTest", "123");
            int id = user.findUserByUserEmailAndPassword("updateStudentDataTest", "123").getId();
            student.updateStudentData(id, "test", "test", "test", "test", "test", null, "test", "test", null);
            UserDTO studentDTO = student.getFullStudentDTOByStudentID(student.getStudentIdByUserId(id));
            assertEquals(student.getStudentIdByUserId(id), studentDTO.getId());
            assertEquals("test", studentDTO.getFirstName());
            assertEquals("test", studentDTO.getLastName());
        }
        catch (DatabaseLayerException e) {
            System.out.println(e.getReason());
            assertEquals(true, false);
        }

        //Todo
        // Delete updateStudentDataTest from Database after each run
    }

    //Todo
    // public void deleteStudentProfilTest()
}
