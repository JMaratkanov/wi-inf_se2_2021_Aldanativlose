import control.LoginControl;
import control.factory.Factory;
import dtos.UserDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginControlTest {
    private String emailCorrect;
    private String emailWrong;
    private String pwCorrect;
    private String pwWrong;
    private UserDTO user;
    private LoginControl lc;

    @BeforeEach
    void setup(){
        emailCorrect = "maxmuster@hbrs.de";
        emailWrong = "MaxMusr@hbrs.de";
        pwCorrect = "123456";
        pwWrong = "1234";
        lc = new LoginControl();
        user = Factory.createUser(1, "Max", "Mustermann");
    }

    @AfterEach
    void teardown() {
        emailCorrect = "";
        emailWrong = "";
        pwCorrect = "";
        pwWrong = "";
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
