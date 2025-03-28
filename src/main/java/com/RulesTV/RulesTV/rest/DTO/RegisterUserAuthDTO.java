package com.RulesTV.RulesTV.rest.DTO;

public class RegisterUserAuthDTO {

    private String email;
    private String fullName;
    private String password;
    private String phone_number;

    public RegisterUserAuthDTO(String email, String fullName, String password, String phoneNumber) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.phone_number = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {return fullName;}

    public void setName(String fullName) {
        this.fullName = this.fullName;
    }

    public String getPhone_number() {return phone_number;}

    public void setPhone_number(String phone_number) {this.phone_number = phone_number;}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
