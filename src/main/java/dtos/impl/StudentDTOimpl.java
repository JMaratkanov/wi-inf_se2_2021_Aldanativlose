package dtos.impl;

import dtos.UserDTO;

public class StudentDTOimpl extends UserDTOimpl implements UserDTO {
    private int ID;
    private String desFromDB;
    private String skillFromDB;
    private String refFromDB;
    private String fachfromDB;
    private String sGangfromDB;
    private String semFromDB;
    private String gebFromDB;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDesFromDB() {
        return desFromDB;
    }

    public void setDesFromDB(String desFromDB) {
        this.desFromDB = desFromDB;
    }

    public String getSkillFromDB() {
        return skillFromDB;
    }

    public void setSkillFromDB(String skillFromDB) {
        this.skillFromDB = skillFromDB;
    }

    public String getRefFromDB() {
        return refFromDB;
    }

    public void setRefFromDB(String refFromDB) {
        this.refFromDB = refFromDB;
    }

    public String getFachfromDB() {
        return fachfromDB;
    }

    public void setFachfromDB(String fachfromDB) {
        this.fachfromDB = fachfromDB;
    }

    public String getsGangfromDB() {
        return sGangfromDB;
    }

    public void setsGangfromDB(String sGangfromDB) {
        this.sGangfromDB = sGangfromDB;
    }

    public String getSemFromDB() {
        return semFromDB;
    }

    public void setSemFromDB(String semFromDB) {
        this.semFromDB = semFromDB;
    }

    public String getGebFromDB() {
        return gebFromDB;
    }

    public void setGebFromDB(String gebFromDB) {
        this.gebFromDB = gebFromDB;
    }

}
