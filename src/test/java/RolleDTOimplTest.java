import dtos.impl.RolleDTOimpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RolleDTOimplTest {
    public static RolleDTOimpl rolle;

    @BeforeClass
    public static void setup(){
        rolle = new RolleDTOimpl();
    }

    @AfterClass
    public static void tearDown(){
        rolle = null;
    }

    @Test
    public void BezeichnungTest(){
        rolle.setBezeichnung("test");
        assertEquals("test", rolle.getBezeichnung());
    }

    @Test
    public void toStringTest(){
        rolle.setBezeichnung("test");
        assertEquals("RolleDTOImpl{bezeichnung='" + rolle.getBezeichnung() + '\'' +  '}', rolle.toString());
    }
}
