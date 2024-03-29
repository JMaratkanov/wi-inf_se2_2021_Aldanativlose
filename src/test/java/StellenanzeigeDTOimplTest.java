import dtos.impl.StellenanzeigeDTOimpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Date;
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
    public void standortTest(){
        assertEquals(null, stellenanzeige.getStandort());
        stellenanzeige.setStandort("test");
        assertEquals("test", stellenanzeige.getStandort());
    }

    @Test
    public void vonTest(){
        assertEquals(null, stellenanzeige.getDateVon());
        Date lt = new Date(1);
        stellenanzeige.setDateVon(lt);
        assertEquals(lt, stellenanzeige.getDateVon());
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
        assertEquals(null, stellenanzeige.getStatus());
        stellenanzeige.setStatus(0);
        assertEquals("Ausschreibung beendet", stellenanzeige.getStatus());
        stellenanzeige.setStatus(1);
        assertEquals("Offen", stellenanzeige.getStatus());
    }

    @Test
    public void stunden_p_monatTest(){
        assertEquals(0, stellenanzeige.getStundenProWoche(), 0.001);
        stellenanzeige.setStundenProWoche(1);
        assertEquals(1, stellenanzeige.getStundenProWoche(), 0.001);
    }

    @Test
    public void stundenlohnTest(){
        assertEquals(0.0, stellenanzeige.getStundenlohn(), 0.001);
        stellenanzeige.setStundenlohn(1.0);
        assertEquals(1.0, stellenanzeige.getStundenlohn(), 0.001);
    }

    @Test
    public void inseratTypTest(){
        assertEquals(null, stellenanzeige.getInseratTyp());
        stellenanzeige.setInseratTyp("Masterarbeit");
        assertEquals("Masterarbeit", stellenanzeige.getInseratTyp());
    }

    @Test
    public void kenntnisseTest(){
        assertEquals(0, stellenanzeige.getKenntnisse());
        stellenanzeige.setKenntnisse(1);
        assertEquals(1, stellenanzeige.getKenntnisse());
    }
}
