package dtos.impl;

import dtos.InseratDTO;

import java.time.LocalDate;

public class InseratDTOimpl implements InseratDTO {
    private int Id;
    private String Title;
    private String Content;
    private LocalDate CreateTime;
    private LocalDate LastUpdate;
    private String Standort;
    private LocalDate DateVon;
    private LocalDate DateBis;
    private int Status;
    private float HoursPerMonth;
    private float SalaryPerMonth;
    private int UnternehmerProfilID;
    private int InseratID;
    private int KenntnisseID;

    public int getId() {
        return this.Id;
    }

    @Override
    public String getTitle() {
        return this.Title;
    }

    @Override
    public String getContent() {
        return this.Content;
    }

    @Override
    public LocalDate getCreateTime() {
        return this.CreateTime;
    }

    @Override
    public LocalDate getLastUpdate() {
        return this.LastUpdate;
    }

    @Override
    public String getStandort() {
        return this.Standort;
    }

    @Override
    public LocalDate getDateVon() {
        return this.DateVon;
    }

    @Override
    public LocalDate getDateBis() {
        return this.DateBis;
    }

    @Override
    public int getStatus() {
        return this.Status;
    }

    @Override
    public float getHoursPerMonth() {
        return this.HoursPerMonth;
    }

    @Override
    public float getSalaryPerMonth() {
        return this.SalaryPerMonth;
    }

    @Override
    public int getUnternehmerProfilID() {
        return this.UnternehmerProfilID;
    }

    @Override
    public int getKenntnisseID() {
        return this.KenntnisseID;
    }

    @Override
    public int getInseratID() {
        return this.InseratID;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setCreateTime(LocalDate createTime) {
        CreateTime = createTime;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        LastUpdate = lastUpdate;
    }

    public void setStandort(String standort) {
        Standort = standort;
    }

    public void setDateVon(LocalDate dateVon) {
        DateVon = dateVon;
    }

    public void setDateBis(LocalDate dateBis) {
        DateBis = dateBis;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public void setHoursPerMonth(float hoursPerMonth) {
        HoursPerMonth = hoursPerMonth;
    }

    public void setSalaryPerMonth(float salaryPerMonth) {
        SalaryPerMonth = salaryPerMonth;
    }

    public void setUnternehmerProfilID(int unternehmerProfilID) {
        UnternehmerProfilID = unternehmerProfilID;
    }

    public void setInseratID(int inseratID) {
        InseratID = inseratID;
    }

    public void setKenntnisseID(int kenntnisseID) {
        KenntnisseID = kenntnisseID;
    }

}
