import daos.StellenanzeigeDAO;
import daos.StudentDAO;
import daos.UserDAO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StellenanzeigeDAOTest {
    private static StellenanzeigeDAO stellenanzeige;

    @BeforeClass
    public static void setup(){
        stellenanzeige = new StellenanzeigeDAO();
    }

    @AfterClass
    public static void tearDown(){
        stellenanzeige = null;
    }

    @Test
    public void getInseratTypByIDTest(){
        assertEquals("keine Angabe", stellenanzeige.getInseratTypByID(0));
        assertEquals("Teilzeit", stellenanzeige.getInseratTypByID(1));
        assertEquals("Vollzeit", stellenanzeige.getInseratTypByID(2));
        assertEquals("Praktikum", stellenanzeige.getInseratTypByID(3));
        assertEquals("Bachelorarbeit", stellenanzeige.getInseratTypByID(4));
        assertEquals("Masterarbeit", stellenanzeige.getInseratTypByID(5));
        assertEquals("FEHLER IN StellenanzeigeDAO/getInseratTypByID", stellenanzeige.getInseratTypByID(6));
    }
}
