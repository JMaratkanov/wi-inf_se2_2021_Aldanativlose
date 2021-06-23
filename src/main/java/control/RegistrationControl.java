package control;

import control.exceptions.DatabaseUserException;
import daos.EmployerDAO;
import daos.StudentDAO;
import db.exceptions.DatabaseLayerException;
import globals.Globals;

public class RegistrationControl extends MainControl{
    public void registerStudentWithJDBC(String firstname, String lastname, String email, String password) throws DatabaseUserException {
        StudentDAO dao = new StudentDAO();
        email = email.toLowerCase();

        try {
            dao.checkOnExistingUser(email);
            dao.setStudentByFirstnameLastnameEmailPassword(firstname, lastname, email , password );
        }
        catch ( DatabaseLayerException e) {
            checkReasonAndThrowEx(e.getReason());
        }
    }

    public void registerEmployerWithJDBC(String companyName, String country, String street, String number, String place, String plz,
                                         String email, String password) throws DatabaseUserException {
        EmployerDAO dao = new EmployerDAO();
        email = email.toLowerCase();

        try {
            dao.checkOnExistingUser(email);
            dao.setEmployer( companyName, country, street, number, place, plz, email , password );
        }
        catch ( DatabaseLayerException e) {
            checkReasonAndThrowEx(e.getReason());
        }
    }
}
