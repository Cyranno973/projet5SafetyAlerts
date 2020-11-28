package com.steve.safetyAlerts.dao;

import com.steve.safetyAlerts.model.FireStation;

public interface FirestationDao {
    boolean createFireStation(FireStation fireStation);
    boolean updateFireStation(FireStation fireStation);
    boolean deleteFireStation(FireStation fireStation);
}
