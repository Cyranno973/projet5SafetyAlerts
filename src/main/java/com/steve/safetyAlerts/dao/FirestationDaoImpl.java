package com.steve.safetyAlerts.dao;

import com.steve.safetyAlerts.model.FireStation;
import com.steve.safetyAlerts.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FirestationDaoImpl implements FirestationDao {
    @Autowired
    private DataRepository dataRepository;

    @Override
    public boolean createFireStation(FireStation fireStation) {
        // ajout de la nouvelle fireStation en memoire java
        boolean result = dataRepository.database.getFireStations().add(fireStation);
        // commit pour appliquer les changements dans le json
        dataRepository.commit();
        return result;
    }

    @Override
    public boolean updateFireStation(FireStation fireStation) {
        for (FireStation fs : dataRepository.database.getFireStations()) {

            if (fs.getAddress().contentEquals(fireStation.getAddress())) {
                boolean result = deleteFireStation(fs);
                if (result) {
                    result = createFireStation(fireStation);
                    dataRepository.commit();
                    return result;
                }
            }
        }

        return false;
    }

    @Override
    public boolean deleteFireStation(FireStation fireStation) {
        List<FireStation> fireStationListDeleted = new ArrayList<>();


        // suppressiion by station et address
        if (!"".equals(fireStation.getStation()) && (fireStation.getStation() != null) && (!"".equals(fireStation.getAddress()) && (fireStation.getAddress() != null))) {

            for (FireStation fs : dataRepository.database.getFireStations()) {
                if (fs.getStation().contentEquals(fireStation.getStation()) && (fs.getAddress().contentEquals(fireStation.getAddress()))) {
                    fireStationListDeleted.add(fs);
                }
            }
        }else {

            // suppressiion by address uniquement
            if (!"".equals(fireStation.getAddress()) && (fireStation.getAddress() != null)) {

                for (FireStation fs : dataRepository.database.getFireStations()) {
                    if (fs.getAddress().contentEquals(fireStation.getAddress())) {
                        fireStationListDeleted.add(fs);
                    }
                }
            }


            // suppressiion by station uniquement

            if (!"".equals(fireStation.getStation()) && (fireStation.getStation() != null)) {

                for (FireStation fs : dataRepository.database.getFireStations()) {
                    if (fs.getStation().contentEquals(fireStation.getStation())) {
                        fireStationListDeleted.add(fs);
                    }
                }
            }


        }

        // suppression de la fireStation en memeoire
        boolean result = dataRepository.database.getFireStations().removeAll(fireStationListDeleted);
        // commit pour appliquer les changements dans le json
        dataRepository.commit();
        return result;

    }
}

