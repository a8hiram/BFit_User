package com.shcollege.bfituser;

public class UserDetails {

    String gender,dob,weight,height;

    public UserDetails() {
    }

    public UserDetails(String gender, String dob, String weight, String height) {
        this.gender = gender;
        this.dob = dob;
        this.weight = weight;
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
