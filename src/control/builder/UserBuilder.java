package control.builder;

import dtos.impl.UserDTOimpl;

public class UserBuilder implements Builder{
    private UserDTOimpl user = null;

    public UserBuilder buildEmptyUser(){
        user = new UserDTOimpl();
        return this;
    }

    @Override
    public UserBuilder withDefaultID() {
        user.setId(1337);
        return this;
    }

    @Override
    public UserBuilder withDefaultFirstName() {
        user.setFirstname("Obi-Wan");
        return this;
    }

    @Override
    public UserBuilder withDefaultLastName() {
        user.setLastname("Kenobi");
        return this;
    }

    @Override
    public UserBuilder withThisID(int a) {
        user.setId(a);
        return this;
    }

    @Override
    public UserBuilder withThisFirstName(String a) {
        user.setFirstname(a);
        return this;
    }

    @Override
    public UserBuilder withThisLastName(String a) {
        user.setLastname(a);
        return this;
    }

    public UserDTOimpl done(){
        return user;
    }
}
