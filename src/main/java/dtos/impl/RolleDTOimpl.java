package dtos.impl;

import dtos.RolleDTO;

public class RolleDTOimpl implements RolleDTO {

    private String bezeichnung;

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    @Override
    public String getBezeichhnung() {
        return this.bezeichnung;
    }

    @Override
    public String toString() {
        return "RolleDTOImpl{" + "bezeichnung='" + bezeichnung + '\'' +  '}';
    }
}
