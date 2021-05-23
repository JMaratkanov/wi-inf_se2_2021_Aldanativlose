package dtos;

import java.time.LocalDate;

public interface InseratDTO {

     public int getId();

     public String getTitle();

     public String getContent();

     public LocalDate getCreateTime();

     public LocalDate getLastUpdate();

     public String getStandort();

     public LocalDate getDateVon();

     public LocalDate getDateBis();

     public int getStatus();

     public float getHoursPerMonth();

     public float getSalaryPerMonth();

     public int getUnternehmerProfilID();

     public int getKenntnisseID();

     public int getInseratID();
}
