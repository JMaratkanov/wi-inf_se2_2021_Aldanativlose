package control.builder;

public interface Builder {
    public Builder withDefaultID();
    public Builder withDefaultFirstName();
    public Builder withDefaultLastName();
    public Builder withThisID(int a);
    public Builder withThisFirstName(String a);
    public Builder withThisLastName(String a);

}
