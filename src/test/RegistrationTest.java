package test;

import control.RegistrationControl;
import control.builder.UserBuilder;
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
    private UserDTO user6 = null;
    RegistrationControl rc = null;
    UserBuilder ub = null;

    /* test */
    
    @BeforeEach
    void setup() {
        user1 = Factory.createUser(1, "Max", "Mustermann");
        user2 = Factory.createUserIF(2, "Toni");
        user3 = Factory.createUserIL(3, "Müller");
        user4 = Factory.createUserFL("Eren", "Jäger");
        user5 = Factory.createUser(4, "A", "Meyer");
        user6 = Factory.createUser(5, "Alexander", "M");
        rc = new RegistrationControl();
        ub = new UserBuilder();
    }

    @AfterEach
    void teardown() {
        user1 = null;
        user2 = null;
        user3 = null;
        user4 = null;
        user5 = null;
        user6 = null;
        rc = null;
    }

    @Test
    void testRegistrationControl() {
        assertEquals("Registrierung erfolgreich!", rc.registerUser(user1).getMeldung(), "Fehler im RegistrationControl");
        assertEquals("Kein Nachname angegeben!", rc.registerUser(user2).getMeldung(), "Fehler im RegistrationControl");
        assertEquals("Kein Vorname angegeben!", rc.registerUser(user3).getMeldung(), "Fehler im RegistrationControl");
        assertEquals("Keine ID angegeben!", rc.registerUser(user4).getMeldung(), "Fehler im RegistrationControl");
        assertEquals("Vorname ist zu kurz!", rc.registerUser(user5).getMeldung(), "Fehler im RegistrationControl");
        assertEquals("Nachname ist zu kurz!", rc.registerUser(user6).getMeldung(), "Fehler im RegistrationControl");
    }

    @Test
    void testRegistrationControlBuilder() {
        /*
        How to use this builder: Create instance -> Use instance to build an empty user (has neither attributes set ...
        like when you construct a UserDTO with "new UserDTOimpl()"
        Default creates User with ID: 1337 FN: Obi-Wan LN: Kenobi
        */

        user1 = ub.buildEmptyUser().withDefaultID().withDefaultFirstName().withDefaultLastName().done();
        user2 = ub.buildEmptyUser().withThisID(2).withThisFirstName("Toni").done();
        user3 = ub.buildEmptyUser().withThisID(3).withThisLastName("Müller").done();
        user4 = ub.buildEmptyUser().withThisFirstName("Eren").withThisLastName("Jäger").done();
        user5 = ub.buildEmptyUser().withThisID(4).withThisFirstName("A").withThisLastName("Meyer").done();
        user6 = ub.buildEmptyUser().withThisID(5).withThisFirstName("Alexander").withThisLastName("M").done();

        assertEquals("Registrierung erfolgreich!", rc.registerUser(user1).getMeldung(), "Fehler im RegistrationControl");
        assertEquals("Kein Nachname angegeben!", rc.registerUser(user2).getMeldung(), "Fehler im RegistrationControl");
        assertEquals("Kein Vorname angegeben!", rc.registerUser(user3).getMeldung(), "Fehler im RegistrationControl");
        assertEquals("Keine ID angegeben!", rc.registerUser(user4).getMeldung(), "Fehler im RegistrationControl");
        assertEquals("Vorname ist zu kurz!", rc.registerUser(user5).getMeldung(), "Fehler im RegistrationControl");
        assertEquals("Nachname ist zu kurz!", rc.registerUser(user6).getMeldung(), "Fehler im RegistrationControl");
    }
}
