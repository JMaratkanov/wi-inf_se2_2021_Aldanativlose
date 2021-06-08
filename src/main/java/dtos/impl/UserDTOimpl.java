package dtos.impl;

import dtos.RolleDTO;
import dtos.UserDTO;

import java.time.LocalDate;
import java.util.List;

public class UserDTOimpl implements UserDTO {
    private int id;
    private String email;
    private String password;
    private String PasswordResetKey;
    private LocalDate createTime;
    private LocalDate updateTime;
    private int status;
    private String firstname;
    private String lastname;
    private int role;

    public void setRole(int role) {
        this.role = role;
    }

    public int getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordResetKey() {
        return PasswordResetKey;
    }

    public void setPasswordResetKey(String passwordResetKey) {
        PasswordResetKey = passwordResetKey;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public LocalDate getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDate updateTime) {
        this.updateTime = updateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
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
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
