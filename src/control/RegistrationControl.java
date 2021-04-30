package control;

import dtos.UserDTO;

public class RegistrationControl {
    public RegistrationResult registerUser(UserDTO user) {
        if(user.getId() == 0) {
            return new RegistrationResult(1);
        } else if(user.getFirstName() == null) {
            return new RegistrationResult(2);
        } else if(user.getLastName() == null) {
            return new RegistrationResult(3);
        } else if(user.getFirstName().length() < 2) {
            return new RegistrationResult(4);
        } else if(user.getLastName().length() < 2) {
            return new RegistrationResult(5);
        } else {
            return new RegistrationResult(0);
        }

        // Wenn die Tests erfolgreich waren, würde hier dann der User wahrscheinlich in die Datenbank geschrieben werden.
    }
}
