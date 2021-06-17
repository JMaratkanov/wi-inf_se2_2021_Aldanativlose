import dtos.impl.InseratDTOimpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class InseratDTOimplTest {
    public static InseratDTOimpl inserat;

    @BeforeClass
    public static void setup(){
        inserat = new InseratDTOimpl();
    }

    @AfterClass
    public static void tearDown(){
        inserat = null;
    }

    @Test
    public void idTest(){
        assertEquals(0, inserat.getId());
        inserat.setId(1);
        assertEquals(1, inserat.getId());
    }

    @Test
    public void TitleTest(){
        assertEquals(null, inserat.getTitle());
        inserat.setTitle("test");
        assertEquals("test", inserat.getTitle());
    }

    @Test
    public void ContentTest(){
        assertEquals(null, inserat.getContent());
        inserat.setContent("test");
        assertEquals("test", inserat.getContent());
    }

    @Test
    public void CreateTimeTest(){
        assertEquals(null, inserat.getCreateTime());
        LocalDate d = LocalDate.of(1,1,1);
        inserat.setCreateTime(d);
        assertEquals(d, inserat.getCreateTime());
    }

    @Test
    public void LastUpdateTest(){
        assertEquals(null, inserat.getLastUpdate());
        LocalDate d = LocalDate.of(1,1,1);
        inserat.setLastUpdate(d);
        assertEquals(d, inserat.getLastUpdate());
    }

    @Test
    public void StandortTest(){
        assertEquals(null, inserat.getStandort());
        inserat.setStandort("test");
        assertEquals("test", inserat.getStandort());
    }

    @Test
    public void DateVonTest(){
        assertEquals(null, inserat.getDateVon());
        LocalDate d = LocalDate.of(1,1,1);
        inserat.setDateVon(d);
        assertEquals(d, inserat.getDateVon());
    }

    @Test
    public void DateBisTest(){
        assertEquals(null, inserat.getDateBis());
        LocalDate d = LocalDate.of(1,1,1);
        inserat.setDateBis(d);
        assertEquals(d, inserat.getDateBis());
    }

    @Test
    public void StatusTest(){
        assertEquals(0, inserat.getStatus());
        inserat.setStatus(1);
        assertEquals(1, inserat.getStatus());
    }

    @Test
    public void HoursPerMonthTest(){
        assertEquals(0.0f, inserat.getHoursPerMonth(),0.0001);
        inserat.setHoursPerMonth(1.0f);
        assertEquals(1.0f, inserat.getHoursPerMonth(),0.0001);
    }

    @Test
    public void SalaryPerMonthTest(){
        assertEquals(0.0f, inserat.getSalaryPerMonth(),0.0001);
        inserat.setSalaryPerMonth(1.0f);
        assertEquals(1.0f, inserat.getSalaryPerMonth(),0.0001);
    }

    @Test
    public void UnternehmerProfilIDTest(){
        assertEquals(0, inserat.getUnternehmerProfilID());
        inserat.setUnternehmerProfilID(1);
        assertEquals(1, inserat.getUnternehmerProfilID());
    }

    @Test
    public void getKenntnisseIDTest(){
        assertEquals(0, inserat.getKenntnisseID());
        inserat.setKenntnisseID(1);
        assertEquals(1, inserat.getKenntnisseID());
    }

    @Test
    public void getInseratIDTest(){
        assertEquals(0, inserat.getInseratID());
        inserat.setInseratID(1);
        assertEquals(1, inserat.getInseratID());
    }
}
