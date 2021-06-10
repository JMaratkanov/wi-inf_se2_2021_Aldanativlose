package control;

import control.exceptions.DatabaseUserException;
import daos.UserDAO;
import db.JDBCConnection;
import db.exceptions.DatabaseLayerException;
import dtos.UserDTO;
import globals.Globals;

import java.sql.SQLException;
import java.sql.Statement;

public class RegistrationControl {
    public void registerStudentWithJDBC(String email, String password) throws DatabaseUserException {
        UserDAO dao = new UserDAO();
        try {
            dao.checkOnExistingUser(email);
            dao.setStudentByEmailAndPassword( email , password );
        }
        catch ( DatabaseLayerException e) {

            // Analyse und Umwandlung der technischen Errors in 'lesbaren' Darstellungen
            // Durchreichung und Behandlung der Fehler (Chain Of Responsibility Pattern (SE-1))
            String reason = e.getReason();

            if ( reason.equals((Globals.Errors.SQLERROR))) {
                throw new DatabaseUserException("There were problems with the SQL code. Please contact the developer!");
            } else if ( reason.equals((Globals.Errors.DATABASE ) )) {
                throw new DatabaseUserException("A failure occured while trying to connect to database with JDBC." +
                        "Please contact the admin");
            } else if (reason.equals(Globals.Errors.EXISTINGUSER)) {
                throw new DatabaseUserException("There is already a user with this email!");
            } else {
                throw new DatabaseUserException("A failure occured while");
            }
        }
    }

    public void registerEmployerWithJDBC(String companyName, String country, String street, String number, String place, String plz,
                                         String email, String password) throws DatabaseUserException {
        UserDAO dao = new UserDAO();
        try {
            dao.checkOnExistingUser(email);
            dao.setEmployer( companyName, country, street, number, place, plz, email , password );
        }
        catch ( DatabaseLayerException e) {

            // Analyse und Umwandlung der technischen Errors in 'lesbaren' Darstellungen
            // Durchreichung und Behandlung der Fehler (Chain Of Responsibility Pattern (SE-1))
            String reason = e.getReason();

            if ( reason.equals((Globals.Errors.SQLERROR))) {
                throw new DatabaseUserException("There were problems with the SQL code. Please contact the developer!");
            } else if ( reason.equals((Globals.Errors.DATABASE ) )) {
                throw new DatabaseUserException("A failure occured while trying to connect to database with JDBC." +
                        "Please contact the admin");
            } else if (reason.equals(Globals.Errors.EXISTINGUSER)) {
                throw new DatabaseUserException("There is already a user with this email!");
            } else {
                throw new DatabaseUserException("A failure occured while");
            }
        }
    }
}
