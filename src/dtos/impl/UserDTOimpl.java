package dtos.impl;

import java.util.List;

public class UserDTOimpl {
    private int id;
    private String firstname;
    private String lastname;
    private String eMail;
    //private List<RolleDTO> roles;

    public int getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstname;
    }

    public String getLastName() {
        return this.lastname;
    }

    public String geteMail() {
        return this.eMail;
    }
    /*
    @Override
    public List<RolleDTO> getRoles() {
        return this.roles;
    }
     */

    @Override
    public String toString() {
        return "UserDTOImpl{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", E-Mail='" + eMail +
                //", roles=" + roles +
                '}';
    }
}
