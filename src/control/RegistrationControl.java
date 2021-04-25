package control;

import dtos.UserDTO;

public class RegistrationControl {
    public String registerUser(UserDTO user) {
        if(user.getId() == 0) {
            return "Keine ID angegeben!";
        } else if(user.getFirstName() == null) {
            return "Kein Vorname angegeben!";
        } else if(user.getLastName() == null) {
            return "Kein Nachname angegeben!";
        } else if(user.getFirstName().length() < 2) {
            return "Vorname ist zu kurz!";
        } else if(user.getLastName().length() < 2) {
            return "Nachname ist zu kurz!";
        } else {
            return "Registrierung erfolgreich!";
        }

    }
}
