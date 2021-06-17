import dtos.impl.StudentDTOimpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.assertEquals;

public class StudentDTOimplTest {
    public static StudentDTOimpl student;

    @BeforeClass
    public static void setup(){
        student = new StudentDTOimpl();
    }

    @AfterClass
    public static void tearDown(){
        student = null;
    }

    @Test
    public void idTest(){
        assertEquals(0, student.getID());
        student.setID(1);
        assertEquals(1, student.getID());
    }

    @Test
    public void DesFromDBTest(){
        assertEquals(null, student.getDesFromDB());
        student.setDesFromDB("test");
        assertEquals("test", student.getDesFromDB());
    }

    @Test
    public void SkillFromDBTest(){
        assertEquals(null, student.getSkillFromDB());
        student.setSkillFromDB("test");
        assertEquals("test", student.getSkillFromDB());
    }

    @Test
    public void RefFromDBTest(){
        assertEquals(null, student.getRefFromDB());
        student.setRefFromDB("test");
        assertEquals("test", student.getRefFromDB());
    }

    @Test
    public void FachfromDBTest(){
        assertEquals(null, student.getFachfromDB());
        student.setFachfromDB("test");
        assertEquals("test", student.getFachfromDB());
    }

    @Test
    public void GangfromDBTest(){
        assertEquals(null, student.getsGangfromDB());
        student.setsGangfromDB("test");
        assertEquals("test", student.getsGangfromDB());
    }

    @Test
    public void SemesterTest(){
        assertEquals(null, student.getSemester());
        Date d = new Date(0);
        student.setSemester(d);
        assertEquals(d, student.getSemester());
    }

    @Test
    public void Geb_dateTest(){
        assertEquals(null, student.getGeb_date());
        Date d = new Date(0);
        student.setGeb_date(d);
        assertEquals(d, student.getGeb_date());
    }
}
