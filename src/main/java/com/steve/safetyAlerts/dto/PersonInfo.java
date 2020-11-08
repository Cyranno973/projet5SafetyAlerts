package com.steve.safetyAlerts.dto;

import java.util.ArrayList;
import java.util.List;

public class PersonInfo {
    private String firstName;
    private String lastName;
    private String addres;
    private int age;
    private String email;
    private List<String> medications = new ArrayList<>();
    private List<String> allergies = new ArrayList<>();
    private List<Homonyme> homonymes = new ArrayList<>();

    public String getFirstName() {
        return firstName;
    }

    public List<Homonyme> getHomonymes() {
        return homonymes;
    }

    public void setHomonymes(List<Homonyme> homonymes) {
        this.homonymes = homonymes;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }
}
