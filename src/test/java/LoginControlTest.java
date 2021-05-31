import control.LoginControl;
import control.exceptions.DatabaseUserException;
import control.factory.Factory;
import dtos.UserDTO;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginControlTest {
    private String emailCorrect;
    private String emailWrong;
    private String passwordCorrect;
    private String passwordWrong;
    private UserDTO user;
    private LoginControl lc;

    @BeforeEach
    void setup(){
        emailCorrect = "maxmuster@hbrs.de";
        emailWrong = "MaxMusr@hbrs.de";
        passwordCorrect = "123456";
        passwordWrong = "1234";
        lc = new LoginControl();
        user = Factory.createUser(1, "Max", "Mustermann");
    }

    @AfterEach
    void teardown() {
        emailCorrect = "";
        emailWrong = "";
        passwordCorrect = "";
        passwordWrong = "";
        user = null;
        lc = null;
    }
/*
    @Test
    public void testAuthentificate() throws DatabaseUserException {
        assertEquals(true, lc.authentificate(emailCorrect, passwordCorrect));
        assertEquals(false, lc.authentificate(emailCorrect, passwordWrong));
        assertEquals(false, lc.authentificate(emailWrong, passwordCorrect));
        assertEquals(false, lc.authentificate(emailWrong, passwordWrong));
    }

    @Test
    public void testGetCurrentUser() {
        assertEquals(user, lc.getCurrentUser());
    }
  */

}
