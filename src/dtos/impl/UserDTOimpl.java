package dtos.impl;

import dtos.UserDTO;

public class UserDTOimpl implements UserDTO {
    private int id;
    private String firstname;
    private String lastname;
    private String eMail;
    //private List<RolleDTO> roles;

    public UserDTOimpl(int id, String firstname, String lastname, String eMail) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.eMail = eMail;
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

    public String getEMail() {
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
