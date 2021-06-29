import dtos.impl.BewerbungDTOimpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BewerbungDTOimplTest {
    public static BewerbungDTOimpl bewerbung;

    @BeforeClass
    public static void setup(){
        bewerbung = new BewerbungDTOimpl();
    }

    @AfterClass
    public static void tearDown(){
        bewerbung = null;
    }

    @Test
    public void idTest(){
        assertEquals(0, bewerbung.getID());
        bewerbung.setID(1);
        assertEquals(1, bewerbung.getID());
    }

    @Test
    public void nameTest(){
        assertEquals(null, bewerbung.getName());
        bewerbung.setName("test");
        assertEquals("test", bewerbung.getName());
    }

    @Test
    public void unternehmenTest(){
        assertEquals(null, bewerbung.getUnternehmen());
        bewerbung.setUnternehmen("test");
        assertEquals("test", bewerbung.getUnternehmen());
    }

    @Test
    public void statusTest(){
        assertEquals(null, bewerbung.getStatus());
        bewerbung.setStatus(0);
        assertEquals("abgelehnt", bewerbung.getStatus());
        bewerbung.setStatus(1);
        assertEquals("Stelle noch verfügbar", bewerbung.getStatus());
        bewerbung.setStatus(2);
        assertEquals("Ausschreibung beendet", bewerbung.getStatus());
        bewerbung.setStatusFromDB(0);
        assertEquals("abgelehnt", bewerbung.getStatus());
        bewerbung.setStatusFromDB(1);
        assertEquals("Stelle noch verfügbar", bewerbung.getStatus());
        bewerbung.setStatusFromDB(2);
        assertEquals("Ausschreibung beendet", bewerbung.getStatus());
    }

    @Test
    public void mehrTest(){
        assertEquals(null, bewerbung.getMehr());
        bewerbung.setMehr("test");
        assertEquals("test", bewerbung.getMehr());
    }

    @Test
    public void inseratIDTest(){
        assertEquals(0, bewerbung.getInseratID());
        bewerbung.setInseratID(1);
        assertEquals(1, bewerbung.getInseratID());
    }

    @Test
    public void unternehmenIDTest(){
        assertEquals(0, bewerbung.getUnternehmenID());
        bewerbung.setUnternehmenID(1);
        assertEquals(1, bewerbung.getUnternehmenID());
    }
}
