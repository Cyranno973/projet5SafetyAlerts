package com.steve.safetyAlerts.dto;

import com.steve.safetyAlerts.model.Person;

import java.util.List;

public class ChildInfo {
    private String firstName;
    private String lastName;
    private int age;
    private String address;
    private List<String> familyMember;

    public String getFirstName() {
        return firstName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(List<String> familyMember) {
        this.familyMember = familyMember;
    }
}
