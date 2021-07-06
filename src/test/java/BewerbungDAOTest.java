import daos.*;
import db.JDBCConnection;
import db.exceptions.DatabaseLayerException;
import dtos.impl.ApplSetForEmployerDTO;
import dtos.impl.BewerbungDTOimpl;
import dtos.impl.StellenanzeigeDTOimpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BewerbungDAOTest {

    private static BewerbungDAO bewerbung;
    private static StudentDAO student;
    private static StellenanzeigeDAO stellenanzeige;
    private static EmployerDAO employer;

    @BeforeClass
    public static void setup(){
        bewerbung = new BewerbungDAO();
        student = new StudentDAO();
        stellenanzeige = new StellenanzeigeDAO();
        employer = new EmployerDAO();
    }

    @AfterClass
    public static void tearDown(){
        bewerbung = null;
        student = null;
        stellenanzeige = null;
        employer = null;
    }

    @Test
    public void getAllTest() throws DatabaseLayerException, SQLException {
        student.setStudentByFirstnameLastnameEmailPassword("Max", "Mustermann", "getAllTest", "123");

        List<BewerbungDTOimpl> list = bewerbung.getAll(student.findUserByUserEmailAndPassword("getAllTest", "123").getId());
        assertEquals(0, list.size());
        assertEquals(list, bewerbung.getAll(student.findUserByUserEmailAndPassword("getAllTest", "123").getId()));
        employer.setEmployer("getAllTest", "germany", "strasse", "5", "Ort", "12345", "getAllTest@ag.com", "123");

        LocalDate date = LocalDate.now();
        stellenanzeige.newadtodao("BewerbungGetAllTest", "Bonn", date, date, 1, 1.0, 1, "demo", 1, "test", employer.findUserByUserEmailAndPassword("getAllTest@ag.com", "123").getId());

        int inseratId = 0;
        List<StellenanzeigeDTOimpl> stellenanzeigeDAOimplList = stellenanzeige.getAll();
        for(int i = 0; i < stellenanzeigeDAOimplList.size(); i++){
            if(stellenanzeigeDAOimplList.get(i).getTitle().equals("BewerbungGetAllTest")){
                inseratId = stellenanzeigeDAOimplList.get(i).getID();
                break;
            }
        }

        student.bewerbungDurchfuehren(inseratId, student.findUserByUserEmailAndPassword("getAllTest", "123").getId());

        list = bewerbung.getAll(student.findUserByUserEmailAndPassword("getAllTest", "123").getId());
        assertEquals(1, list.size());
        assertEquals("BewerbungGetAllTest", list.get(0).getName());

        stellenanzeige.cancelAd(inseratId);
        student.deleteStudentProfil(student.findUserByUserEmailAndPassword("getAllTest", "123").getId());

        deleteInserat("BewerbungGetAllTest");
        employer.deleteEmployerProfil(employer.findUserByUserEmailAndPassword("getAllTest@ag.com", "123").getId());
    }

    private void deleteInserat(String inserat) throws DatabaseLayerException, SQLException {
        PreparedStatement sql = JDBCConnection.getInstance().getPreparedStatement("DELETE FROM collhbrs.inserat WHERE collhbrs.inserat.title='" + inserat + "'");
        sql.executeUpdate();
    }


    @Test
    public void getAllApllicantsByEmployerIDTest() throws DatabaseLayerException, SQLException {
        student.setStudentByFirstnameLastnameEmailPassword("Max", "Mustermann", "getAllApllicantsByEmployerIDTest", "123");
        employer.setEmployer("getAllApllicantsByEmployerIDTest", "germany", "strasse", "5", "Ort", "12345", "getAllApllicantsByEmployerIDTest@ag.com", "123");

        List<ApplSetForEmployerDTO> list = bewerbung.getAllApllicantsByEmployerID(employer.findUserByUserEmailAndPassword("getAllApllicantsByEmployerIDTest@ag.com", "123").getId());
        assertEquals(0, list.size());
        assertEquals(list, bewerbung.getAllApllicantsByEmployerID(employer.findUserByUserEmailAndPassword("getAllApllicantsByEmployerIDTest@ag.com", "123").getId()));

        LocalDate date = LocalDate.now();
        stellenanzeige.newadtodao("getAllApllicantsByEmployerIDTest", "Bonn", date, date, 1, 1.0, 1, "demo", 1, "test", employer.findUserByUserEmailAndPassword("getAllApllicantsByEmployerIDTest@ag.com", "123").getId());

        int inseratId = 0;
        List<StellenanzeigeDTOimpl> stellenanzeigeDAOimplList = stellenanzeige.getAll();
        for(int i = 0; i < stellenanzeigeDAOimplList.size(); i++){
            if(stellenanzeigeDAOimplList.get(i).getTitle().equals("getAllApllicantsByEmployerIDTest")){
                inseratId = stellenanzeigeDAOimplList.get(i).getID();
                break;
            }
        }

        student.bewerbungDurchfuehren(inseratId, student.findUserByUserEmailAndPassword("getAllApllicantsByEmployerIDTest", "123").getId());

        list = bewerbung.getAllApllicantsByEmployerID(employer.findUserByUserEmailAndPassword("getAllApllicantsByEmployerIDTest@ag.com", "123").getId());
        assertEquals(1, list.size());

        stellenanzeige.cancelAd(inseratId);
        student.deleteStudentProfil(student.findUserByUserEmailAndPassword("getAllApllicantsByEmployerIDTest", "123").getId());

        deleteInserat("getAllApllicantsByEmployerIDTest");
        employer.deleteEmployerProfil(employer.findUserByUserEmailAndPassword("getAllApllicantsByEmployerIDTest@ag.com", "123").getId());

    }
/*
    @Test
    public void apllicationEditTest() throws DatabaseLayerException, SQLException {
        student.setStudentByFirstnameLastnameEmailPassword("Max", "Mustermann", "getAllApllicantsByEmployerIDTest", "123");
        employer.setEmployer("getAllApllicantsByEmployerIDTest", "germany", "strasse", "5", "Ort", "12345", "getAllApllicantsByEmployerIDTest@ag.com", "123");

        List<ApplSetForEmployerDTO> list = bewerbung.getAllApllicantsByEmployerID(employer.findUserByUserEmailAndPassword("getAllApllicantsByEmployerIDTest@ag.com", "123").getId());
        assertEquals(0, list.size());
        assertEquals(list, bewerbung.getAllApllicantsByEmployerID(employer.findUserByUserEmailAndPassword("getAllApllicantsByEmployerIDTest@ag.com", "123").getId()));

        LocalDate date = LocalDate.now();
        stellenanzeige.newadtodao("getAllApllicantsByEmployerIDTest", "Bonn", date, date, 1, 1.0, 1, "demo", 1, "test", employer.findUserByUserEmailAndPassword("getAllApllicantsByEmployerIDTest@ag.com", "123").getId());

        int inseratId = 0;
        List<StellenanzeigeDTOimpl> stellenanzeigeDAOimplList = stellenanzeige.getAll();
        for(int i = 0; i < stellenanzeigeDAOimplList.size(); i++){
            if(stellenanzeigeDAOimplList.get(i).getTitle().equals("getAllApllicantsByEmployerIDTest")){
                inseratId = stellenanzeigeDAOimplList.get(i).getID();
                break;
            }
        }

        student.bewerbungDurchfuehren(inseratId, student.findUserByUserEmailAndPassword("getAllApllicantsByEmployerIDTest", "123").getId());

        list = bewerbung.getAllApllicantsByEmployerID(employer.findUserByUserEmailAndPassword("getAllApllicantsByEmployerIDTest@ag.com", "123").getId());

        assertEquals("Offen", list.get(0).getStatus());
        bewerbung.apllicationEdit(list.get(0).getID(), 2);
        assertEquals("Bewerbung abgelehnt", list.get(0).getStatus());


        stellenanzeige.cancelAd(inseratId);
        student.deleteStudentProfil(student.findUserByUserEmailAndPassword("getAllApllicantsByEmployerIDTest", "123").getId());

        deleteInserat("getAllApllicantsByEmployerIDTest");
        employer.deleteEmployerProfil(employer.findUserByUserEmailAndPassword("getAllApllicantsByEmployerIDTest@ag.com", "123").getId());

    }

 */
}
