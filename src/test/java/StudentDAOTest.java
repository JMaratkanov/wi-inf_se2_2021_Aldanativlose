
import daos.StudentDAO;
import daos.UserDAO;
import db.exceptions.DatabaseLayerException;
import dtos.UserDTO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;

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


    @Test
    public void setStudentByEmailAndPasswordTest() throws DatabaseLayerException {
        assertEquals("There is already a user with this email address!", assertThrows(DatabaseLayerException.class, () -> student.setStudentByFirstnameLastnameEmailPassword("sqlerror1", "Mustermann", "demo", "demo")).getReason());

        student.setStudentByFirstnameLastnameEmailPassword("Max", "Mustermann", "setStudentByEmailAndPasswordTest", "123");
        UserDTO userDTO = student.findUserByUserEmailAndPassword("setStudentByEmailAndPasswordTest", "123");
        assertEquals("setStudentByEmailAndPasswordTest", userDTO.getEmail());
        assertEquals("There is already a user with this email address!", assertThrows(DatabaseLayerException.class, () ->  student.setStudentByFirstnameLastnameEmailPassword("sqlerror2", "Mustermann", "setStudentByEmailAndPasswordTest", "123")).getReason());

        student.deleteStudentProfil(student.findUserByUserEmailAndPassword("setStudentByEmailAndPasswordTest", "123").getId());
    }


    @Test
    public void getStudentIdByUserIdTest() throws DatabaseLayerException {
        assertEquals(34, student.getStudentIdByUserId(199));
    }

    @Test
    public void getFullStudentDTOByStudentIDTest() throws DatabaseLayerException {
        student.setStudentByFirstnameLastnameEmailPassword("Max", "Mustermann", "getFullStudentDTOByStudentIDTest", "123");
        int id = student.getStudentIdByUserId(student.findUserByUserEmailAndPassword("getFullStudentDTOByStudentIDTest", "123").getId());
        UserDTO studentDTO = student.getFullStudentDTOByStudentID(id);
        assertEquals(id, studentDTO.getId());
        assertEquals("Max", studentDTO.getFirstName());
        assertEquals("Mustermann", studentDTO.getLastName());
        student.deleteStudentProfil(student.findUserByUserEmailAndPassword("getFullStudentDTOByStudentIDTest", "123").getId());
        assertEquals("No User could be found! Please check your credentials!",
                assertThrows(DatabaseLayerException.class, ()-> student.getFullStudentDTOByStudentID(0)).getReason());
    }

    @Test
    public void updateStudentDataTest() throws DatabaseLayerException {
        student.setStudentByFirstnameLastnameEmailPassword("Max", "Mustermann", "updateStudentDataTest", "123");
        int id = user.findUserByUserEmailAndPassword("updateStudentDataTest", "123").getId();
        LocalDate testDate = LocalDate.of(1,1,1);
        student.updateStudentData(id, "test", "test", "test", "test", "test", testDate, "test", "test", testDate);
        UserDTO studentDTO = student.getFullStudentDTOByStudentID(student.getStudentIdByUserId(id));
        assertEquals(student.getStudentIdByUserId(id), studentDTO.getId());
        assertEquals("test", studentDTO.getFirstName());
        assertEquals("test", studentDTO.getLastName());
        student.deleteStudentProfil(student.findUserByUserEmailAndPassword("updateStudentDataTest", "123").getId());
    }

    @Test
    public void deleteStudentProfilTest() throws DatabaseLayerException {
        student.setStudentByFirstnameLastnameEmailPassword("Max", "Mustermann", "deleteStudentProfilTest", "123");
        int id = user.findUserByUserEmailAndPassword("deleteStudentProfilTest", "123").getId();

        student.deleteStudentProfil(id);
    }

    /*
    Wird in BewerbungDAOTest getAllTest() abgedeckt
    @Test
    public void bewerbungDurchführenTest(){}
     */
}
