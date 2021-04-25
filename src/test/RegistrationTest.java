package test;

import control.RegistrationControl;
import control.factory.Factory;
import dtos.UserDTO;
import dtos.impl.UserDTOimpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationTest {
    private UserDTO user1 = null;
    RegistrationControl rc = new RegistrationControl();

    @BeforeEach
    void setup() {
        user1 = Factory.createUser(1, "Max", "Mustermann");
    }

    @AfterEach
    void teardown() {
        user1 = null;
    }

    @Test
    void testRegistrationControl() {
        assertEquals("Registrierung erfolgreich", rc.registerUser(user1), "Fehler bei der Registrierung");
    }
}
