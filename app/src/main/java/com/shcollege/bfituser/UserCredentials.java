package com.shcollege.bfituser;

public class UserCredentials {
    public String firstname, lastname, email, password, gender, dob, weight, height;
    public UserCredentials() {

    }

    public UserCredentials(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }


}
