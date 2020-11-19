package com.steve.safetyAlerts.dto;

import java.util.List;

public class Foyer {
    private String address;
    private List<FirePerson> firePersons ;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<FirePerson> getFirePersons() {
        return firePersons;
    }

    public void setFirePersons(List<FirePerson> personInfos) {
        this.firePersons = personInfos;
    }
}
