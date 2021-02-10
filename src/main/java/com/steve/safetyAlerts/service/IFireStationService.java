package com.steve.safetyAlerts.service;

import com.steve.safetyAlerts.dto.Coverage;
import com.steve.safetyAlerts.dto.FirePerson;
import com.steve.safetyAlerts.dto.Foyer;
import com.steve.safetyAlerts.model.FireStation;

import java.util.List;

public interface IFireStationService {
    boolean createFireStation(FireStation fireStation);

    boolean updateFireStation(FireStation fireStation);

    boolean deleteFireStation(FireStation fireStation);

    List<String> getFirestation();

    List<Coverage> getCoverageByFireStation(String stationNumber);

    List<FirePerson> getFire(String address);

    List<String> getPhoneAlert(String station);

    List<Foyer> getFloodStation(List<String> stationNumbers);


}
