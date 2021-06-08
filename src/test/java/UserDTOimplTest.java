import dtos.impl.UserDTOimpl;
import org.junit.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class UserDTOimplTest {

    private static UserDTOimpl udi;

    public UserDTOimplTest(){

    }

    @Before
    public void setUpTest(){
        udi = new UserDTOimpl();
    }

    @Test
    public void testEmail(){
        assertEquals(null, udi.getEmail());
        udi.setEmail("MaxMuster@hbrs.de");
        assertEquals("MaxMuster@hbrs.de", udi.getEmail());
        udi.setEmail("test@hbrs.de");
        assertEquals("test@hbrs.de", udi.getEmail());
    }

    @Test
    public void testPassword(){
        assertEquals(null, udi.getPassword());
        udi.setPassword("123");
        assertEquals("123", udi.getPassword());
        udi.setPassword("abc");
        assertEquals("abc", udi.getPassword());
        udi.setPassword("");
        assertEquals("", udi.getPassword());
    }

    /*
    @Test
    public void testS_ID(){
        assertEquals(0, udi.getS_ID());
        udi.setS_ID(1);
        assertEquals(1, udi.getS_ID());
        udi.setS_ID(123);
        assertEquals(123, udi.getS_ID());
    }

    @Test
    public void testU_ID(){
        assertEquals(0, udi.getU_ID());
        udi.setU_ID(1);
        assertEquals(1, udi.getU_ID());
        udi.setU_ID(123);
        assertEquals(123, udi.getU_ID());
    }
     */

    @Test
    public void testPasswordResetKey(){
        assertEquals(null, udi.getPasswordResetKey());
        udi.setPasswordResetKey("abc");
        assertEquals("abc", udi.getPasswordResetKey());
        udi.setPasswordResetKey("123");
        assertEquals("123", udi.getPasswordResetKey());
    }

    @Test
    public void testCreateTime(){
        assertEquals(null, udi.getCreateTime());
        LocalDate createTime = LocalDate.now();
        udi.setCreateTime(createTime);
        assertEquals(createTime, udi.getCreateTime());
        LocalDate createTime2 = LocalDate.now();
        udi.setCreateTime(createTime2);
        assertEquals(createTime2, udi.getCreateTime());
    }

    @Test
    public void testUpdateTime(){
        assertEquals(null, udi.getUpdateTime());
        LocalDate updateTime = LocalDate.now();
        udi.setUpdateTime(updateTime);
        assertEquals(updateTime, udi.getUpdateTime());
        LocalDate updateTime2 = LocalDate.now();
        udi.setUpdateTime(updateTime2);
        assertEquals(updateTime2, udi.getUpdateTime());
    }

    @Test
    public void testStatus(){
        assertEquals(0, udi.getStatus());
        udi.setStatus(1);
        assertEquals(1, udi.getStatus());
        udi.setStatus(123);
        assertEquals(123, udi.getStatus());
    }

    @Test
    public void testFirstname(){
        assertEquals(null, udi.getFirstname());
        udi.setFirstname("peter");
        assertEquals("peter", udi.getFirstname());
        udi.setFirstname("Bob");
        assertEquals("Bob", udi.getFirstname());
    }

    @Test
    public void testLastname(){
        System.out.println(udi.getLastname());
        assertEquals(null, udi.getLastname());
        udi.setLastname("lustig");
        assertEquals("lustig", udi.getLastname());
        udi.setLastname("Braun");
        assertEquals("Braun", udi.getLastname());
    }

    @Test
    public void testId(){
        assertEquals(0, udi.getId());
        udi.setId(5);
        assertEquals(5, udi.getId());
        udi.setId(123);
        assertEquals(123, udi.getId());
    }

    @Test
    public void testToString(){
        assertEquals("UserDTOImpl{id=0, firstname='null', lastname='null'}", udi.toString());
        udi.setId(1);
        assertEquals("UserDTOImpl{id=1, firstname='null', lastname='null'}", udi.toString());
        udi.setFirstname("Peter");
        assertEquals("UserDTOImpl{id=1, firstname='Peter', lastname='null'}", udi.toString());
        udi.setLastname("Lustig");
        assertEquals("UserDTOImpl{id=1, firstname='Peter', lastname='Lustig'}", udi.toString());
    }

    @After
    public void tearDownTest(){
        udi = null;
    }
}
