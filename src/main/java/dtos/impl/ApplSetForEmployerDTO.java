package dtos.impl;

public class ApplSetForEmployerDTO {
    public String stelle;
    public String student_vorname;
    public String student_name;
    public String status;
    public int StudID;
    public boolean sichtbar = true;
    public int ID;

    public int getID() { return ID; }

    public void setID(int ID) { this.ID = ID; }


    //Lebenslauf

    public String getStelle() {
        return stelle;
    }

    public void setStelle(String stelle) {
        this.stelle = stelle;
    }
    public String getStudentname() {
        return student_name;
    }

    public void setStudentname(String studentname) {
        student_name = studentname;
    }

    public String getStudent_vorname() {
        return student_vorname;
    }

    public void setStudent_vorname(String student_vorname) {
        this.student_vorname = student_vorname;
    }

    public int getStudID() {
        return StudID;
    }

    public void setStudID(int x) {
        this.StudID = x;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(int statusID) {
        switch(statusID){
            case 0:this.status = "Ausschreibung beendet";
                break;
            case 1:this.status = "Offen";
                break;
            case 2:this.status = "Bewerbung abgelehnt";
                break;
            case 3:this.status = "Zum Vorstellungsgespräch eingeladen";
                break;
        }
    }

    public void setSichtbar(boolean sichtbar) { this.sichtbar = sichtbar; }

    public boolean getSichtbar(){ return sichtbar; }


}
