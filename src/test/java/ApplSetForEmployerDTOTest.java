import dtos.impl.ApplSetForEmployerDTO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ApplSetForEmployerDTOTest {
    public static ApplSetForEmployerDTO asfe;

    @BeforeClass
    public static void setup(){
        asfe = new ApplSetForEmployerDTO();
    }

    @AfterClass
    public static void tearDown(){
        asfe = null;
    }

    @Test
    public void stelleTest(){
        assertEquals(null, asfe.getStelle());
        asfe.setStelle("test");
        assertEquals("test", asfe.getStelle());
    }

    @Test
    public void studentnameTest(){
        assertEquals(null, asfe.getStudentname());
        asfe.setStudentname("test");
        assertEquals("test", asfe.getStudentname());
    }

    @Test
    public void student_vornameTest(){
        assertEquals(null, asfe.getStudent_vorname());
        asfe.setStudent_vorname("test");
        assertEquals("test", asfe.getStudent_vorname());
    }

    @Test
    public void studIDTest(){
        assertEquals(0, asfe.getStudID());
        asfe.setStudID(1);
        assertEquals(1, asfe.getStudID());
    }

    @Test
    public void statusTest(){
        assertEquals(null, asfe.getStatus());
        asfe.setStatus(0);
        assertEquals("Ausschreibung beendet", asfe.getStatus());
        asfe.setStatus(1);
        assertEquals("Offen", asfe.getStatus());
        asfe.setStatus(2);
        assertEquals("Bewerbung abgelehnt", asfe.getStatus());
        asfe.setStatus(3);
        assertEquals("Zum Vorstellungsgespräch eingeladen", asfe.getStatus());
    }

    @Test
    public void sichtbarTest(){
        assertEquals(true, asfe.getSichtbar());
        asfe.setSichtbar(false);
        assertEquals(false, asfe.getSichtbar());
    }
}
