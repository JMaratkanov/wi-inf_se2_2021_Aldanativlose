package db;

import db.exceptions.DatabaseLayerException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCConnection {
    //Hier die URL einfügen
    private String url = "jdbc:postgresql://dumbo.inf.h-brs.de/nthiel2s";
    //private String url = "jdbc:postgresql://dumbo.inf.h-brs.de/demouser";

    private static JDBCConnection connection = null;
    private Connection conn;

    //Hier User und PW der DB einfügen
    private String login = "nthiel2s";
    private String password = "nthiel2s";

    public static JDBCConnection getInstance() throws DatabaseLayerException {
        if ( connection == null ) connection = new JDBCConnection();
        return connection;
    }

    private JDBCConnection() throws DatabaseLayerException {
        this.initConnection();
    }

    public void initConnection() throws DatabaseLayerException {
        try {
            DriverManager.registerDriver( new org.postgresql.Driver() ); 
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.openConnection();
    }

    //Hier user und pw der DB einfügen
    public void openConnection() throws DatabaseLayerException {
        try {
            Properties props = new Properties();
            props.setProperty("user", "nthiel2s" );
            props.setProperty("password", "nthiel2s" );

            this.conn = DriverManager.getConnection(this.url, props);

        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
            throw new DatabaseLayerException( "Fehler! Verbindung prüfen" );
        }
    }

    public Statement getStatement() throws DatabaseLayerException {
        try {
            if ( this.conn.isClosed() ) this.openConnection();
            return this.conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public PreparedStatement getPreparedStatement( String sql  ) throws DatabaseLayerException {
        try {
            if ( this.conn.isClosed() )  this.openConnection();
            return this.conn.prepareStatement(sql);
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public void closeConnection(){
        try {
            this.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

