package dtos.impl;

import dtos.UserDTO;

public class UserDTOimpl implements UserDTO {
    private int id = 0;
    private String firstname;
    private String lastname;

    public UserDTOimpl createUser(int id, String firstname, String lastname) {
        UserDTOimpl x = new UserDTOimpl();
        x.setId(id);
        x.setFirstname(firstname);
        x.setLastname(lastname);
        return x;
    }

    public UserDTOimpl createUserIF(int id, String firstname) {
        UserDTOimpl x = new UserDTOimpl();
        x.setId(id);
        x.setFirstname(firstname);
        return x;
    }

    public UserDTOimpl createUserIL(int id, String lastname) {
        UserDTOimpl x = new UserDTOimpl();
        x.setId(id);
        x.setLastname(lastname);
        return x;
    }

    public UserDTOimpl createUserFL(String firstname, String lastname) {
        UserDTOimpl x = new UserDTOimpl();
        x.setFirstname(firstname);
        x.setLastname(lastname);
        return x;
    }

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

    @Override
    public String toString() {
        return "UserDTOImpl{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname +
                '}';
    }
}
