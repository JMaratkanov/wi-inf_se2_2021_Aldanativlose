package daos;

import db.JDBCConnection;
import db.exceptions.DatabaseLayerException;
import dtos.StellenanzeigeDTO;
import dtos.UserDTO;
import dtos.impl.StellenanzeigeDTOimpl;
import dtos.impl.StudentDTOimpl;
import globals.Globals;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StellenanzeigeDAO extends UserDAO{

    public List<StellenanzeigeDTOimpl> getAll() throws DatabaseLayerException {
        ArrayList<StellenanzeigeDTOimpl> liste = new ArrayList<>();

        ResultSet set;

        try {
            PreparedStatement sql = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("SELECT inserat.id, inserat.title, inserat.standort, date_von, stunden_pro_woche, inserat_typ, status, verguetung_pro_stunde, inserat.ansprechpartner, inserat.branche_id, firmenname, inserat.content, beschreibung_kurz, kontaktemail, tel FROM collhbrs.inserat JOIN collhbrs.unternehmen_profil ON inserat.unternehmen_profil_id = unternehmen_profil.id ORDER BY status DESC, standort ASC");
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
                        // inserat.branche_id, firmenname, beschreibung_kurz, kontaktemail, tel
                        result = new StellenanzeigeDTOimpl();
                        result.setID(set.getInt(1));
                        result.setTitle(set.getString(2));
                        result.setStandort(set.getString(3));
                        result.setDateVon(set.getDate(4));
                        result.setStundenProWoche(set.getInt(5));
                        result.setInseratTyp(getInseratTypByID(set.getInt(6)));
                        result.setStatus(set.getInt(7));
                        result.setStundenlohn(set.getInt(8));
                        result.setAnsprechpartner(set.getString(9));
                        //result.set Branche 10
                        result.setFirmenname(set.getString(11));
                        result.setContent(set.getString(12));
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

    public List<StellenanzeigeDTOimpl> getAllAdsOf1Employer(int userID) throws DatabaseLayerException {
        ArrayList<StellenanzeigeDTOimpl> liste = new ArrayList<>();

        ResultSet set;

        try {
            PreparedStatement sql = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("SELECT inserat.id, inserat.title, inserat.standort, date_von, stunden_pro_woche, inserat_typ, status, verguetung_pro_stunde, inserat.ansprechpartner, inserat.branche_id, firmenname, inserat.content, beschreibung_kurz, kontaktemail, tel FROM collhbrs.inserat JOIN collhbrs.unternehmen_profil ON inserat.unternehmen_profil_id = unternehmen_profil.id WHERE inserat.unternehmen_profil_id = 95 ORDER BY status DESC, standort ASC");
                //sql.setInt(getPersonalIdByUserId(userID, "Unternehmer"), 1);
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
                    // inserat.branche_id, firmenname, beschreibung_kurz, kontaktemail, tel
                    result = new StellenanzeigeDTOimpl();
                    result.setID(set.getInt(1));
                    result.setTitle(set.getString(2));
                    result.setStandort(set.getString(3));
                    result.setDateVon(set.getDate(4));
                    result.setStundenProWoche(set.getInt(5));
                    result.setInseratTyp(getInseratTypByID(set.getInt(6)));
                    result.setStatus(set.getInt(7));
                    result.setStundenlohn(set.getInt(8));
                    result.setAnsprechpartner(set.getString(9));
                    //result.set Branche 10
                    result.setFirmenname(set.getString(11));
                    result.setContent(set.getString(12));
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

    public void newadtodao(String title, String standort, LocalDate dateVon, LocalDate dateBis, int stunden_pro_woche, double verguetung_pro_stunde, int inserat_typ, String ansprechpartner, int branche_id, String content, int userID) throws DatabaseLayerException {
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
                sql.setInt(9, 95);//getPersonalIdByUserId(userID, "Unternehmer"));
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
                        "UPDATE collhbrs.bewerbung SET status = 0, visible = false WHERE inserat_id = ?");
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
    public void deleteAd(int inseratID) throws DatabaseLayerException {
        PreparedStatement sql = null;
        PreparedStatement sql2 = null;
        try {
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement(
                        "DELETE from collhbrs.bewerbung WHERE inserat_id = ?");

                sql.setInt(1, inseratID);
                sql2 = JDBCConnection.getInstance().getPreparedStatement(
                        "DELETE from collhbrs.inserat WHERE inserat.id = ?");
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

    public List<StellenanzeigeDTOimpl> getLatest() throws DatabaseLayerException {
        ArrayList<StellenanzeigeDTOimpl> liste = new ArrayList<>();
        ResultSet set;

        try {
            PreparedStatement sql = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("SELECT inserat.id, inserat.title, inserat.standort, date_von, stunden_pro_woche, inserat_typ, status FROM collhbrs.inserat WHERE status = 1 ORDER BY inserat.id DESC LIMIT 5");
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

    /*
    public List<StellenanzeigeDTOimpl> getInserat() throws DatabaseLayerException {
        ArrayList<StellenanzeigeDTOimpl> liste = new ArrayList<>();

        ResultSet set;

        try {
            PreparedStatement sql = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("SELECT title, content, standort FROM collhbrs.inserat ");
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
                    result.setTitle(set.getString(1));
                    result.setContent(set.getString(2));
                    result.setStandort(set.getString(3));
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

     */

    /*
    // nicht beachten. für public inseratview
    public StellenanzeigeDTOimpl getInseratById(int id) throws DatabaseLayerException {
        ResultSet set;

        try {
            PreparedStatement sql = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("SELECT * FROM collhbrs.inserat WHERE collhbrs.inserat.id = ?");
                sql.setInt(1, id);
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            //TODO Select abfrage anpassen sodass alle Daten mit einer bestimmten ID ausgelesen werden
            assert sql != null;
            set = sql.executeQuery();

        } catch (SQLException ex) {
            throw new DatabaseLayerException(Globals.Errors.SQLERROR);
        } catch (NullPointerException ex) {
            throw new DatabaseLayerException(Globals.Errors.DATABASE);
        }

        StellenanzeigeDTOimpl inserat;

        try {
            if (set.next()) {
                // "title","content","standort","date_von","date_bis","status","stunden_pro_woche","verguetung_pro_stunde",
                // "unternehmen_profil_id","kenntnisse","inserat_typ","ansprechpartner","branche_id","id","user_id","gps_lat","gps_long"
                inserat = new StellenanzeigeDTOimpl();
                inserat.setTitle( set.getString(1));
                inserat.setContent( set.getString(2));
                inserat.setStandort( set.getString(3));
                inserat.setDateVon( set.getDate(4));
                //inserat.setBis( set.getDate(5));
                inserat.setStatus( set.getInt(6));
                inserat.setStundenProWoche( set.getInt(7));
                inserat.setStundenlohn( set.getInt(8));
                inserat.setUnternehmen_ID( set.getInt(9));
                //inserat.setKenntnisse( set.getInt(10));
                inserat.setInseratTyp( getInseratTypByID(set.getInt(11)) );
                inserat.setAnsprechpartner( set.getString(12));
                //inserat.set Branche


                //TODO rest des dtos füllen
                return inserat;

            } else {
                throw new DatabaseLayerException(Globals.Errors.NOUSERFOUND);
            }
        } catch (SQLException ex) {
            throw new DatabaseLayerException(Globals.Errors.DATABASE);
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }
     */
}
