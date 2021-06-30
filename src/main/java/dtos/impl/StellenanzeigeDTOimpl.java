package dtos.impl;

import dtos.StellenanzeigeDTO;

import java.time.LocalTime;
import java.util.Date;

public class StellenanzeigeDTOimpl implements StellenanzeigeDTO {
   int ID;
   int unternehmen_ID;

   String title;
   String content;
   String standort;
   Date DateVon;
   LocalTime bis;
   int status;
   int stundenProWoche; //fließkomma rly?:D
   double stundenlohn;
   String inseratTyp;
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

    public String getStandort() {
        return standort;
    }

    public void setStandort(String standort) {
        this.standort = standort;
    }

    public Date getDateVon() {
        return DateVon;
    }

    public void setDateVon(Date dateVon) {
        this.DateVon = dateVon;
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

    public int getStundenProWoche() {
        return stundenProWoche;
    }

    public void setStundenProWoche(int stundenProWoche) {
        this.stundenProWoche = stundenProWoche;
    }

    public double getStundenlohn() {
        return stundenlohn;
    }

    public void setStundenlohn(double stundenlohn) {
        this.stundenlohn = stundenlohn;
    }

    public String getInseratTyp() {
        return inseratTyp;
    }

    public void setInseratTyp(String inseratTyp) {
        this.inseratTyp = inseratTyp;
    }

    public int getKenntnisse() {
        return kenntnisse;
    }

    public void setKenntnisse(int kenntnisse) {
        this.kenntnisse = kenntnisse;
    }
}
