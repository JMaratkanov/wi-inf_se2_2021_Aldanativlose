package control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import dtos.UserDTO;
import dtos.impl.UserDTOimpl;

//not implemented
public class LoginControl {

    //Platzhalter
    public boolean authentificate(String username, String password) {
        return true;
    }

    //Platzhalter
    public UserDTO getCurrentUser() {
        return new UserDTOimpl();
    }
}
