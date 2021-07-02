import daos.BewerbungDAO;
import daos.StellenanzeigeDAO;
import daos.StudentDAO;
import daos.UserDAO;
import db.exceptions.DatabaseLayerException;
import dtos.impl.BewerbungDTOimpl;
import dtos.impl.StellenanzeigeDTOimpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BewerbungDAOTest {

    private static BewerbungDAO bewerbung;
    private static StudentDAO student;
    private static StellenanzeigeDAO stellenanzeige;

    @BeforeClass
    public static void setup(){
        bewerbung = new BewerbungDAO();
        student = new StudentDAO();
        stellenanzeige = new StellenanzeigeDAO();
    }

    @AfterClass
    public static void tearDown(){
        bewerbung = null;
        student = null;
        stellenanzeige = null;
    }

    //Achtun, Testdaten löschen sich nicht selbstständig
    @Test
    public void getAllTest() throws DatabaseLayerException {
        student.setStudentByFirstnameLastnameEmailPassword("Max", "Mustermann", "getAllTest", "123");

        List<BewerbungDTOimpl> list = bewerbung.getAll(student.findUserByUserEmailAndPassword("getAllTest", "123").getId());
        assertEquals(0, list.size());
        assertEquals(list, bewerbung.getAll(student.findUserByUserEmailAndPassword("getAllTest", "123").getId()));

        LocalDate date = LocalDate.now();
        stellenanzeige.newadtodao("BewerbungGetAllTest", "Bonn", date, date, 1, 1.0, 1, "demo", 1, "test");
        int inseratId = 0;
        List<StellenanzeigeDTOimpl> stellenanzeigeDAOimplList = stellenanzeige.getAll();
        for(int i = 0; i < stellenanzeigeDAOimplList.size(); i++){
            if(stellenanzeigeDAOimplList.get(i).getTitle().equals("BewerbungGetAllTest")){
                inseratId = stellenanzeigeDAOimplList.get(i).getID();
                break;
            }
        }

        student.bewerbungDurchführen(inseratId, student.findUserByUserEmailAndPassword("getAllTest", "123").getId());

        list = bewerbung.getAll(student.findUserByUserEmailAndPassword("getAllTest", "123").getId());
        assertEquals(1, list.size());
        assertEquals("BewerbungGetAllTest", list.get(0).getName());

        stellenanzeige.cancelAd(inseratId);
        //student.deleteStudentProfil(student.findUserByUserEmailAndPassword("getAllTest", "123").getId());
        // TODO delete Inserat
        //Testdaten löschen
    }

    /*
    @Test
    public void getAllApllicantsByEmployerIDTest(){

    }
     */
}
