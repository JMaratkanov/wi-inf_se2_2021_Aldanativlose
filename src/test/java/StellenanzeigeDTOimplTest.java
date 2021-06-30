import dtos.impl.StellenanzeigeDTOimpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class StellenanzeigeDTOimplTest {
    public static StellenanzeigeDTOimpl stellenanzeige;

    @BeforeClass
    public static void setup(){
        stellenanzeige = new StellenanzeigeDTOimpl();
    }

    @AfterClass
    public static void tearDown(){
        stellenanzeige = null;
    }

    @Test
    public void idTest(){
        assertEquals(0, stellenanzeige.getID());
        stellenanzeige.setID(1);
        assertEquals(1, stellenanzeige.getID());
    }

    @Test
    public void titleTest(){
        assertEquals(null, stellenanzeige.getTitle());
        stellenanzeige.setTitle("test");
        assertEquals("test", stellenanzeige.getTitle());
    }

    @Test
    public void contentTest(){
        assertEquals(null, stellenanzeige.getContent());
        stellenanzeige.setContent("test");
        assertEquals("test", stellenanzeige.getContent());
    }

    @Test
    public void unternehmen_IDTest(){
        assertEquals(0, stellenanzeige.getUnternehmen_ID());
        stellenanzeige.setUnternehmen_ID(1);
        assertEquals(1, stellenanzeige.getUnternehmen_ID());
    }

    @Test
    public void createdTest(){
        assertEquals(null, stellenanzeige.getCreated());
        LocalTime lt = LocalTime.now();
        stellenanzeige.setCreated(lt);
        assertEquals(lt, stellenanzeige.getCreated());
    }

    @Test
    public void updatedTest(){
        assertEquals(null, stellenanzeige.getUpdated());
        LocalTime lt = LocalTime.now();
        stellenanzeige.setUpdated(lt);
        assertEquals(lt, stellenanzeige.getUpdated());
    }

    @Test
    public void standortTest(){
        assertEquals(null, stellenanzeige.getStandort());
        stellenanzeige.setStandort("test");
        assertEquals("test", stellenanzeige.getStandort());
    }

    @Test
    public void vonTest(){
        assertEquals(null, stellenanzeige.getVon());
        LocalTime lt = LocalTime.now();
        stellenanzeige.setVon(lt);
        assertEquals(lt, stellenanzeige.getVon());
    }

    @Test
    public void bisTest(){
        assertEquals(null, stellenanzeige.getBis());
        LocalTime lt = LocalTime.now();
        stellenanzeige.setBis(lt);
        assertEquals(lt, stellenanzeige.getBis());
    }

    @Test
    public void statusTest(){
        assertEquals(0, stellenanzeige.getStatus());
        stellenanzeige.setStatus(1);
        assertEquals(1, stellenanzeige.getStatus());
    }

    @Test
    public void stunden_p_monatTest(){
        assertEquals(0.0, stellenanzeige.getStunden_p_monat(), 0.001);
        stellenanzeige.setStunden_p_monat(1.0);
        assertEquals(1.0, stellenanzeige.getStunden_p_monat(), 0.001);
    }

    @Test
    public void stundenlohnTest(){
        assertEquals(0.0, stellenanzeige.getStundenlohn(), 0.001);
        stellenanzeige.setStundenlohn(1.0);
        assertEquals(1.0, stellenanzeige.getStundenlohn(), 0.001);
    }

    @Test
    public void inseratTypTest(){
        assertEquals(0, stellenanzeige.getInseratTyp());
        stellenanzeige.setInseratTyp(1);
        assertEquals(1, stellenanzeige.getInseratTyp());
    }

    @Test
    public void kenntnisseTest(){
        assertEquals(0, stellenanzeige.getKenntnisse());
        stellenanzeige.setKenntnisse(1);
        assertEquals(1, stellenanzeige.getKenntnisse());
    }
}
