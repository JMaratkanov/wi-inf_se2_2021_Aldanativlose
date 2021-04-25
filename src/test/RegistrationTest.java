package test;

import control.RegistrationControl;
import control.factory.Factory;
import dtos.UserDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationTest {
    private UserDTO user1 = null;
    private UserDTO user2 = null;
    private UserDTO user3 = null;
    private UserDTO user4 = null;
    private UserDTO user5 = null;
    RegistrationControl rc = null;

    @BeforeEach
    void setup() {
        user1 = Factory.createUser(1, "Max", "Mustermann");
        user2 = Factory.createUserIF(2, "Toni");
        user3 = Factory.createUserIL(3, "Müller");
        user4 = Factory.createUserFL("Eren", "Jäger");
        user5 = Factory.createUser(4, "A", "Meyer");
        rc = new RegistrationControl();
    }

    @AfterEach
    void teardown() {
        user1 = null;
        user2 = null;
        user3 = null;
        user4 = null;
        rc = null;
    }

    @Test
    void testRegistrationControl() {
        assertEquals("Registrierung erfolgreich!", rc.registerUser(user1), "Fehler im RegistrationControl");
        assertEquals("Kein Nachname angegeben!", rc.registerUser(user2), "Fehler im RegistrationControl");
        assertEquals("Kein Vorname angegeben!", rc.registerUser(user3), "Fehler im RegistrationControl");
        assertEquals("Keine ID angegeben!", rc.registerUser(user4), "Fehler im RegistrationControl");
        assertEquals("Vorname ist zu kurz!", rc.registerUser(user5), "Fehler im RegistrationControl");
    }
}
