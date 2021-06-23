package control;

import control.exceptions.DatabaseUserException;
import globals.Globals;

public class MainControl {
    public void checkReasonAndThrowEx(String reason) throws DatabaseUserException {
        // Analyse und Umwandlung der technischen Errors in 'lesbaren' Darstellungen
        // Durchreichung und Behandlung der Fehler (Chain Of Responsibility Pattern (SE-1))
        if (reason.equals(Globals.Errors.NOUSERFOUND)) {
            throw new DatabaseUserException("No User could be found! Please check your credentials!");
        } else if ( reason.equals((Globals.Errors.SQLERROR))) {
            throw new DatabaseUserException("There were problems with the SQL code. Please contact the developer!");
        } else if ( reason.equals((Globals.Errors.DATABASE ) )) {
            throw new DatabaseUserException("A failure occured while trying to connect to database with JDBC. " +
                    "Please contact the admin");
        } else {
            throw new DatabaseUserException("A failure occured while");
        }
    }
}
