package dtos.impl;

import dtos.UserDTO;

public class UserDTOimpl implements UserDTO {
    private int id;
    private String firstname;
    private String lastname;

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstname;
    }

    public String getLastName() {
        return this.lastname;
    }
}
