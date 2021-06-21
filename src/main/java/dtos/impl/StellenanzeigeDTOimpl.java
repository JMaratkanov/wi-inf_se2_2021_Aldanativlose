package dtos.impl;

import dtos.StellenanzeigeDTO;
import java.time.LocalTime;

public class StellenanzeigeDTOimpl implements StellenanzeigeDTO {
   int ID;
   int unternehmen_ID;

   String title;
   String content;
   LocalTime created;
   LocalTime updated;
   String standort;
   LocalTime von;
   LocalTime bis;
   int status;
   double stunden_p_monat; //fließkomma rly?:D
   double stundenlohn;
   int inseratTyp;
   int kenntnisse; //int []?


    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUnternehmen_ID() {
        return unternehmen_ID;
    }

    public void setUnternehmen_ID(int unternehmen_ID) {
        this.unternehmen_ID = unternehmen_ID;
    }

    public LocalTime getCreated() {
        return created;
    }

    public void setCreated(LocalTime created) {
        this.created = created;
    }

    public LocalTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalTime updated) {
        this.updated = updated;
    }

    public String getStandort() {
        return standort;
    }

    public void setStandort(String standort) {
        this.standort = standort;
    }

    public LocalTime getVon() {
        return von;
    }

    public void setVon(LocalTime von) {
        this.von = von;
    }

    public LocalTime getBis() {
        return bis;
    }

    public void setBis(LocalTime bis) {
        this.bis = bis;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getStunden_p_monat() {
        return stunden_p_monat;
    }

    public void setStunden_p_monat(double stunden_p_monat) {
        this.stunden_p_monat = stunden_p_monat;
    }

    public double getStundenlohn() {
        return stundenlohn;
    }

    public void setStundenlohn(double stundenlohn) {
        this.stundenlohn = stundenlohn;
    }

    public int getInseratTyp() {
        return inseratTyp;
    }

    public void setInseratTyp(int inseratTyp) {
        this.inseratTyp = inseratTyp;
    }

    public int getKenntnisse() {
        return kenntnisse;
    }

    public void setKenntnisse(int kenntnisse) {
        this.kenntnisse = kenntnisse;
    }
}
