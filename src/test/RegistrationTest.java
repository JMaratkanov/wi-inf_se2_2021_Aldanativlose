package test;

import control.RegistrationControl;
import dtos.UserDTO;
import dtos.impl.UserDTOimpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationTest {
    private UserDTO a = null;
    private UserDTO b = null;
    private UserDTO c = null;
    private UserDTO d = null;
    private UserDTO e = null;

    RegistrationControl rc = new RegistrationControl();

    @BeforeEach
    void setup() {
    }

    @Test
    void testRegistrationControl() {
        assertEquals("Registrierung erfolgreich", rc.registerUser(a), "Fehler bei der Registrierung");
    }
}
