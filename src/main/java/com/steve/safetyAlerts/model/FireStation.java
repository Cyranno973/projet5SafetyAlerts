package com.steve.safetyAlerts.model;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class FireStation {
    @NotBlank
    private String address;
    @NotBlank
    private String station;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "FireStation{" +
                "address='" + address + '\'' +
                ", station='" + station + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FireStation)) return false;
        FireStation that = (FireStation) o;
        return Objects.equals(getAddress(), that.getAddress()) &&
                Objects.equals(getStation(), that.getStation());
    }
}
