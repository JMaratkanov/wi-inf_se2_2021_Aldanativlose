package dtos.impl;

import dtos.BewerbungDTO;

public class BewerbungDTOimpl implements BewerbungDTO {

    private String name;
    private String unternehmen;
    private String status;
    private String mehr;
    private int ID;
    private int statusFromDB;
    private int inseratID;
    private int unternehmenID;

    @Override
    public int getID() {
        return this.ID;
    }

    public void setID(int x) {
        this.ID = x;
    }

    public String getName() {
        return this.name;
    }

    public String getUnternehmen() {
        return this.unternehmen;
    }

    public String getStatus() {
        return this.status;
    }

    public String getMehr() {
        return this.mehr;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setUnternehmen(String unternehmen) {
        this.unternehmen = unternehmen;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMehr(String mehr) {
        this.mehr = mehr;
    }

    public void setStatusFromDB(int x) {
        this.statusFromDB = x;
    }

    public void setInseratID(int x) {
        this.inseratID = x;
    }

    public int getInseratID() {
        return this.inseratID;
    }

    //Comment
    public int getUnternehmenID() {
        return unternehmenID;
    }

    public void setUnternehmenID(int unternehmenID) {
        this.unternehmenID = unternehmenID;
    }
}
