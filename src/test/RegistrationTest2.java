package test;

import control.builder.UserBuilder;
import control.RegistrationControl;

import dtos.UserDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationTest2 {
    private UserDTO user1 = null;
    private UserDTO user2 = null;
    private UserDTO user3 = null;
    private UserDTO user4 = null;
    private UserDTO user5 = null;
    private UserDTO user6 = null;
    RegistrationControl rc = null;

    @BeforeEach
    void setup() {
        /*
        How to use this builder: Create instance -> Use instance to build an empty user (has neither attributes set ...
        like when you construct a UserDTO with "new UserDTOimpl()"
        Default creates User with ID: 1337 FN: Obi-Wan LN: Kenobi */

        UserBuilder b = new UserBuilder();
        user1 = b.buildEmptyUser().withDefaultID().withDefaultFirstName().withDefaultLastName().done();
        user2 = b.buildEmptyUser().withThisID(2).withThisFirstName("Toni").done();
        user3 = b.buildEmptyUser().withThisID(3).withThisLastName("Müller").done();
        user4 = b.buildEmptyUser().withThisFirstName("Eren").withThisLastName("Jäger").done();
        user5 = b.buildEmptyUser().withThisID(4).withThisFirstName("A").withThisLastName("Meyer").done();
        user6 = b.buildEmptyUser().withThisID(5).withThisFirstName("Alexander").withThisLastName("M").done();
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
        assertEquals("Nachname ist zu kurz!", rc.registerUser(user6), "Fehler im RegistrationControl");
    }

}
