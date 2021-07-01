package daos;

import db.JDBCConnection;
import db.exceptions.DatabaseLayerException;
import dtos.impl.StellenanzeigeDTOimpl;
import globals.Globals;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StellenanzeigeDAO {

    public List<StellenanzeigeDTOimpl> getAll() throws DatabaseLayerException {
        ArrayList<StellenanzeigeDTOimpl> liste = new ArrayList<>();

        ResultSet set;

        try {
            PreparedStatement sql = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("SELECT id, title, standort, date_von, stunden_pro_woche, inserat_typ, status FROM collhbrs.inserat ORDER BY status DESC, standort ASC");
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            assert sql != null;
            set = sql.executeQuery();

        } catch (SQLException ex) {
            throw new DatabaseLayerException(Globals.Errors.SQLERROR);
        } catch (NullPointerException ex) {
            throw new DatabaseLayerException(Globals.Errors.DATABASE);
        }

        StellenanzeigeDTOimpl result;

        boolean flipflop;
            try {
                do {
                    flipflop = set.next();
                    if (flipflop) {
                        result = new StellenanzeigeDTOimpl();
                        result.setID(set.getInt(1));
                        result.setTitle(set.getString(2));
                        result.setStandort(set.getString(3));
                        result.setDateVon(set.getDate(4));
                        result.setStundenProWoche(set.getInt(5));
                        result.setInseratTyp(getInseratTypByID(set.getInt(6)));
                        result.setStatus(set.getInt(7));
                        liste.add(result);
                    }
                }while(flipflop);
            } catch (SQLException ex) {
                throw new DatabaseLayerException(Globals.Errors.DATABASE);
            } finally {
                JDBCConnection.getInstance().closeConnection();
            }

        return liste;
    }

    public void newadtodao(String title, String standort, LocalDate dateVon, LocalDate dateBis, int stunden_pro_woche, double verguetung_pro_stunde, int inserat_typ, String ansprechpartner, int branche_id, String content) throws DatabaseLayerException {
        Date date_von = null; //java.sql.Date.valueOf(dateVon);
        Date date_bis = null; //java.sql.Date.valueOf(dateBis);
        if (dateVon != null){
            date_von = Date.valueOf(dateVon);
        }
        if (dateBis != null) {
            date_bis = Date.valueOf(dateBis);
        }
        try {
            PreparedStatement sql = null;
            try {


                sql = JDBCConnection.getInstance().getPreparedStatement("INSERT INTO collhbrs.inserat(title, content, standort, date_von, date_bis, status, stunden_pro_woche, verguetung_pro_stunde, unternehmen_profil_id, inserat_typ, ansprechpartner, branche_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"); //date_von, date_bis,
                sql.setString(1, title);
                sql.setString(2, content);
                sql.setString(3, standort);
                sql.setDate(4, date_von);
                sql.setDate(5, date_bis);
                sql.setInt(6, 1);
                sql.setInt(7, stunden_pro_woche);
                sql.setDouble(8, verguetung_pro_stunde);
                sql.setInt(9, 95);
                sql.setInt(10, inserat_typ);
                sql.setString(11, ansprechpartner);
                sql.setInt(12, branche_id);

                //TODO Hardcoded vals richtig übergeben
                /*
                sql.setInt(4, 1); //,status);                      - Was macht das @Simon?
                sql.setInt(5, 95);  //,unternehmen_profil_id);     - get from session- later
                sql.setInt(9, 2);//  inserat_typ);                 - Mit switch case den String aus dem Select d. view die ID in Tabelle inserattyp mappen
                sql.setDouble(10, 9.); //verguetung_pro_stunde
                sql.setDouble(11, 7.); //stunden_pro_woche
                sql.setInt(12, 4); //branche_id);                  - same

                 */

            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            assert sql != null;
            sql.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseLayerException(Globals.Errors.SQLERROR);
        } catch (NullPointerException ex) {
            throw new DatabaseLayerException(Globals.Errors.DATABASE);
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }

    public String getInseratTypByID(int ID) {
        switch (ID){
            case 0: return "keine Angabe";
            case 1: return "Teilzeit";
            case 2: return "Vollzeit";
            case 3: return "Praktikum";
            case 4: return "Bachelorarbeit";
            case 5: return "Masterarbeit";
            default: return "FEHLER IN StellenanzeigeDAO/getInseratTypByID";
        }
    }

    public void cancelAd(int inseratID) throws DatabaseLayerException {
        PreparedStatement sql = null;
        PreparedStatement sql2 = null;
        try {
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement(
                        "UPDATE collhbrs.inserat SET status = 0 WHERE id = ?");
                sql.setInt(1, inseratID);

                sql2 = JDBCConnection.getInstance().getPreparedStatement(
                        "UPDATE collhbrs.bewerbung SET status = 0 WHERE inserat_id = ?");
                sql2.setInt(1, inseratID);

            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }
            assert sql != null;
            sql.executeUpdate();
            assert sql2 != null;
            sql2.executeUpdate();

        } catch (SQLException ex) {
            throw new DatabaseLayerException(Globals.Errors.SQLERROR);
        } catch (NullPointerException ex) {
            throw new DatabaseLayerException(Globals.Errors.DATABASE);
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }
}
