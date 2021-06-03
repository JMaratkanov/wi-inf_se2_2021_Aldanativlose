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

        //TODO
        // Sollten hier noch die auskommentieren Test-Fälle vor dem Try-Catch Block eingefügt werden?
        // Dann müsste allerdings auch ein UserDTO übergeben werden statt lediglich der E-Mail und dem Passwort
        //return new RegistrationResult(0);

        /*
        if(user.getId() == 0) {
            return new RegistrationResult(1);
        } else if(user.getFirstName() == null) {
            return new RegistrationResult(2);
        } else if(user.getLastName() == null) {
            return new RegistrationResult(3);
        } else if(user.getFirstName().length() < 2) {
            return new RegistrationResult(4);
        } else if(user.getLastName().length() < 2) {
            return new RegistrationResult(5);
        } else {
            return new RegistrationResult(0);
        }
        // Wenn die Tests erfolgreich waren, würde hier dann der User wahrscheinlich in die Datenbank geschrieben werden.
        */
    }
}
