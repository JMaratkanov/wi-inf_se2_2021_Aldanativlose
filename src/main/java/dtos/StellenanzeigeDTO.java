package dtos;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.Date;

public interface StellenanzeigeDTO {
    public int getID();
    public String getTitle();
    public String getContent();
    public int getStundenProWoche();
    public Date getDateVon();
    public String getInseratTyp();
    public String getStatus();
    // neu
    public String getAnsprechpartner();
    public String getFirmenname();
}
