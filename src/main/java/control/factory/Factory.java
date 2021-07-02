package control.factory;

import dtos.impl.UserDTOimpl;

public class Factory {
    public static UserDTOimpl createUser(int id, String firstname, String lastname) {
        UserDTOimpl x = new UserDTOimpl();
        x.setId(id);
        x.setFirstname(firstname);
        x.setLastname(lastname);
        return x;

    }

    public static UserDTOimpl createUserIF(int id, String firstname) {
        UserDTOimpl x = new UserDTOimpl();
        x.setId(id);
        x.setFirstname(firstname);
        return x;
    }

    public static UserDTOimpl createUserIL(int id, String lastname) {
        UserDTOimpl x = new UserDTOimpl();
        x.setId(id);
        x.setLastname(lastname);
        return x;
    }

    public static UserDTOimpl createUserFL(String firstname, String lastname) {
        UserDTOimpl x = new UserDTOimpl();
        x.setFirstname(firstname);
        x.setLastname(lastname);
        return x;
    }
}
