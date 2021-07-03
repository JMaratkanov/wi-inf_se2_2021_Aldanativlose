package control;

import control.exceptions.DatabaseUserException;
import globals.Globals;

public class MainControl {
    public void checkReasonAndThrowEx(String reason) throws DatabaseUserException {
        // Analyse und Umwandlung der technischen Errors in 'lesbaren' Darstellungen
        // Durchreichung und Behandlung der Fehler (Chain Of Responsibility Pattern (SE-1))
        switch (reason) {
            case Globals.Errors.NOUSERFOUND:
                throw new DatabaseUserException("Nutzer konnte nicht gefunden werden! Bitte überprüfen Sie Ihre Eingaben!");
            case Globals.Errors.SQLERROR:
                throw new DatabaseUserException("Es gab Probleme bei im SQL Code. Bitte kontaktieren Sie die Entwickler!");
            case Globals.Errors.DATABASE:
                throw new DatabaseUserException("Beim Versuch, eine Verbindung zur Datenbank mit JDBC herzustellen, ist ein Fehler aufgetreten. Bitte kontaktieren Sie den den Administrator");
            case Globals.Errors.EXISTINGUSER:
                throw new DatabaseUserException("Es existiert bereits ein Nutzer mit dieser E-Mail-Adresse!");
            case Globals.Errors.DOUBLEAPPLICATION:
                throw new DatabaseUserException("Sie haben sich bereits auf diese Stellenanzeige beworben!");
            default:
                throw new DatabaseUserException("Ein unbekannter Fehler ist aufgetreten" +
                        "");
        }
    }
}
