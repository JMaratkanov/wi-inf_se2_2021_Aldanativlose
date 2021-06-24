package daos;

import db.JDBCConnection;
import db.exceptions.DatabaseLayerException;
import globals.Globals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainDAO {
    public ResultSet executeQueryStatement(PreparedStatement sql) throws DatabaseLayerException {
        ResultSet set;

        try {
            assert sql != null;
            set = sql.executeQuery();
            return set;
        } catch (SQLException ex) {
            DatabaseLayerException e = new DatabaseLayerException(Globals.Errors.SQLERROR);
            throw e;
        } catch (NullPointerException ex) {
            DatabaseLayerException e = new DatabaseLayerException(Globals.Errors.DATABASE);
            throw e;
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }

    public void executeUpdateStatement(PreparedStatement sql) {

    }
}
