package com.steve.safetyAlerts.service;

import com.steve.safetyAlerts.dao.FirestationDao;
import com.steve.safetyAlerts.exception.DataAlreadyExistException;
import com.steve.safetyAlerts.exception.DataNotFoundException;
import com.steve.safetyAlerts.model.FireStation;
import com.steve.safetyAlerts.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IFireStationServiceImpl implements IFireStationService {

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private FirestationDao fireStationDao;


    @Override
    public boolean createFireStation(FireStation fireStation) {
        // verification que la fireStation n'existe pas dans la DAO(datarepository)
        if (!dataRepository.getAllPersons().contains(fireStation)) {
            fireStationDao.createFireStation(fireStation);
            return true;
        } else {
            throw new DataAlreadyExistException("La fireStation " + fireStation.getStation() + " " + fireStation.getAddress() + "existe déja");
        }
    }

    @Override
    public boolean updateFireStation(FireStation fireStation) {
        if (!fireStationDao.updateFireStation(fireStation)) {
            throw new DataNotFoundException("La fireStation " + fireStation.getStation() + " " + fireStation.getAddress() + "n'existe pas");
        }
        return true;
    }

    @Override
    public boolean deleteFireStation(FireStation fireStation) {
        if (!fireStationDao.deleteFireStation(fireStation)) {
            throw new DataNotFoundException("La fireStation " + fireStation.getStation() + " " + fireStation.getAddress() + "n'existe pas");
        }
        return true;
    }
}
