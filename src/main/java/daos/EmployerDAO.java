package daos;

import db.JDBCConnection;
import db.exceptions.DatabaseLayerException;
import globals.Globals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployerDAO extends UserDAO{
    public void setEmployer(String companyName, String country, String street, String hNr, String place, String plz, String email, String password)  throws DatabaseLayerException {
        ResultSet set;
        try {
            checkOnExistingUser(email);

            PreparedStatement sql = null;
            PreparedStatement sql2 = null;
            try {
                sql = JDBCConnection.getInstance().getPreparedStatement("INSERT INTO collhbrs.unternehmen_profil(firmenname, land, strasse, hausnr, ort, plz) VALUES (?, ?, ?, ?, ?, ?) returning id");
                sql.setString(1, companyName);
                sql.setString(2, country);
                sql.setString(3, street);
                sql.setString(4, hNr);
                sql.setString(5, place);
                sql.setString(6, plz);
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            assert sql != null;
            set = sql.executeQuery();
            int id = 0;
            if(set.next()) {
                id = set.getInt(1);
            }

            try {
                sql2 = JDBCConnection.getInstance().getPreparedStatement("INSERT INTO collhbrs.user(email, password, rolle, unternehmen_profil) VALUES (?, ?, ?, ?)");
                sql2.setString(1, email);
                sql2.setString(2, password);
                sql2.setInt(3, 2);
                sql2.setInt(4, id);
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

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

    public int getEmployerIdByUserId(int id) throws DatabaseLayerException {
        return getPersonalIdByUserId(id, "Unternehmer");
    }

    public void deleteEmployerProfil(int id) throws DatabaseLayerException {
        PreparedStatement sql = null;
        try {
            int employerID = getEmployerIdByUserId(id);
            sql = JDBCConnection.getInstance().getPreparedStatement(
                    "DELETE FROM collhbrs.unternehmen_profil WHERE collhbrs.unternehmen_profil.id = ?");
            sql.setInt(1, employerID);
        } catch (DatabaseLayerException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            throw new DatabaseLayerException(Globals.Errors.SQLERROR);
        }
        assert sql != null;
        executeSQLUpdateCommand(sql);
    }
}
