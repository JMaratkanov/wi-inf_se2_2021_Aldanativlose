package dtos.impl;

public class ApplSetForEmployerDTO {
    public String stelle;
    public String student_vorname;
    public String student_name;
    public int StudID;
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
}
