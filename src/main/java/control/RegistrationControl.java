package control;

import control.exceptions.DatabaseUserException;
import daos.EmployerDAO;
import daos.StudentDAO;
import db.exceptions.DatabaseLayerException;
import globals.Globals;

public class RegistrationControl {
    public void registerStudentWithJDBC(String firstname, String lastname, String email, String password) throws DatabaseUserException {
        StudentDAO dao = new StudentDAO();

        try {
            dao.checkOnExistingUser(email);
            dao.setStudentByFirstnameLastnameEmailPassword(firstname, lastname, email , password );
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
        EmployerDAO dao = new EmployerDAO();
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
