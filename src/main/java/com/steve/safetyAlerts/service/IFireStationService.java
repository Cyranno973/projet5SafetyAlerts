package com.steve.safetyAlerts.service;

import com.steve.safetyAlerts.model.FireStation;
import com.steve.safetyAlerts.model.Person;

public interface IFireStationService {
    boolean createFireStation(FireStation fireStation);

    boolean updateFireStation(FireStation fireStation);

    boolean deleteFireStation(FireStation fireStation);

}
