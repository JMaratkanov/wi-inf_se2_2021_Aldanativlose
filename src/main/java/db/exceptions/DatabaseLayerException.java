package db.exceptions;

public class DatabaseLayerException extends Exception {

    //reason sollte nicht null sein
    private String reason = null;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public DatabaseLayerException( String reason ) {
        this.reason = reason;
    }

}
