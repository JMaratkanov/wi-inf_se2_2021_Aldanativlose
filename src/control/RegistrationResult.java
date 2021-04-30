package control;

public class RegistrationResult {
    //Das ist eine erweiterbare Wrapperklasse die Ergebnisattribute eines Registrierungsvorgangs kapseln kann

    int status;
    String meldung;

    public RegistrationResult(int a){
        status = a;

        switch(a){
            case 0:  meldung = "Registrierung erfolgreich!"; break;
            case 1:  meldung = "Keine ID angegeben!"; break;
            case 2:  meldung = "Kein Vorname angegeben!"; break;
            case 3:  meldung = "Kein Nachname angegeben!"; break;
            case 4:  meldung = "Vorname ist zu kurz!"; break;
            case 5:  meldung = "Nachname ist zu kurz!"; break;
            default: throw new IllegalStateException("Unexpected value: " + a);
        }
    }

    public int getStatus() {
        return status;
    }

    public String getMeldung(){ return meldung; }
}
