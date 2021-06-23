package control;

import control.exceptions.DatabaseUserException;
import daos.UserDAO;
import db.exceptions.DatabaseLayerException;
import dtos.UserDTO;
import globals.Globals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.UserRepository;


@Component
public class LoginControl extends MainControl{

    @Autowired
    private UserRepository repository;

    private UserDTO userDTO = null;

    public boolean authenticate(String email, String password ) throws DatabaseUserException {
        // Standard: User wird mit Spring JPA ausgelesen (Was sind die Vorteile?)
        // UserDTO tmpUser = this.getUserWithJPA( username , password );
        email = email.toLowerCase();

        if(email.contains(";") || email.contains(",") || email.contains(":")){ //Check for Illegal Chars
            DatabaseUserException x = new DatabaseUserException("Illegal Char Detected");
            x.setReason(Globals.Errors.ILLEGALCHAR);
            throw x; }
        // Alternative: Auslesen des Users mit JDBC (Was sind die Vorteile bzw. Nachteile?)
         UserDTO tmpUser = this.getUserWithJDBC( email , password );

        if ( tmpUser == null ) {
            // ggf. hier ein Loggin einfügen
            return false;
        }
        this.userDTO = tmpUser;
        return true;
    }

    public UserDTO getCurrentUser(){
        return this.userDTO;
    }

    private UserDTO getUserWithJDBC( String email , String password ) throws DatabaseUserException {
        UserDTO userTmp = null;
        UserDAO dao = new UserDAO();
        try {
            userDTO = dao.findUserByUserEmailAndPassword( email , password );
        }
        catch ( DatabaseLayerException e) {
            checkReasonAndThrowEx(e.getReason());
        }
        return userDTO;
    }

    private UserDTO getUserWithJPA( String username , String password ) throws DatabaseUserException {
        UserDTO userTmp;
        username = username.toLowerCase();

        try {
            userTmp = repository.findUserByUseridAndPassword(username, password);
        } catch ( org.springframework.dao.DataAccessResourceFailureException e ) {
            // Analyse und Umwandlung der technischen Errors in 'lesbaren' Darstellungen (ToDo!)
            throw new DatabaseUserException("A failure occured while trying to connect to database with JPA");
        }
        return userTmp;
    }
}
