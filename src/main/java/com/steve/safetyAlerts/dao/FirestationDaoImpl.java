package com.steve.safetyAlerts.dao;

import com.steve.safetyAlerts.model.FireStation;
import com.steve.safetyAlerts.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirestationDaoImpl implements FirestationDao{
    @Autowired
    private DataRepository dataRepository;

    @Override
    public boolean createFireStation(FireStation fireStation) {
        // ajout de la nouvelle fireStation en memoire java
        dataRepository.database.getFireStations().add(fireStation);
        // commit pour appliquer les changements dans le json
        dataRepository.commit();
        return true;
    }

    @Override
    public boolean updateFireStation(FireStation fireStation) {
        if (dataRepository.database.getFireStations().remove(fireStation)) {

            this.createFireStation(fireStation);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteFireStation(FireStation fireStation) {
        // suppression de la fireStation en memeoire
        boolean result = dataRepository.database.getFireStations().remove(fireStation);
        // commit pour appliquer les changements dans le json
        dataRepository.commit();
        return result;

    }
}

