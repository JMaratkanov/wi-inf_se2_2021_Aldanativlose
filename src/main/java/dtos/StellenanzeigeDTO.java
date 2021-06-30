package dtos;

import java.util.Date;

public interface StellenanzeigeDTO {
    public int getID();
    public String getTitle();
    public String getContent();
    public int getStundenProWoche();
    public Date getDateVon();
    public String getInseratTyp();
    public String getStatus();
}
