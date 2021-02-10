package com.steve.safetyAlerts.service;

import com.steve.safetyAlerts.daoService.FirestationDao;
import com.steve.safetyAlerts.dto.Coverage;
import com.steve.safetyAlerts.dto.FirePerson;
import com.steve.safetyAlerts.dto.Foyer;
import com.steve.safetyAlerts.exception.DataAlreadyExistException;
import com.steve.safetyAlerts.exception.DataNotFoundException;
import com.steve.safetyAlerts.exception.InvalidArgumentException;
import com.steve.safetyAlerts.model.FireStation;
import com.steve.safetyAlerts.model.MedicalRecord;
import com.steve.safetyAlerts.model.Person;
import com.steve.safetyAlerts.repository.DataRepository;
import com.steve.safetyAlerts.utility.CalculateAge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class IFireStationServiceImpl implements IFireStationService {

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private FirestationDao fireStationDao;

    @Autowired
    private IFireStationService fireStationService;


    @Override
    public boolean createFireStation(FireStation fireStation) {

        // verification que la fireStation n'existe pas dans la DAO(datarepository)
        if (!StringUtils.isEmpty(fireStation.getStation()) && !StringUtils.isEmpty(fireStation.getAddress())) {

            if (!dataRepository.database.getFireStations().contains(fireStation)) {
                fireStationDao.createFireStation(fireStation);
                return true;
            } else {
                throw new DataAlreadyExistException("La fireStation " + fireStation.getStation() + " " + fireStation.getAddress() + "existe déja");
            }
        }
        throw new InvalidArgumentException(" fireStation non creer" + fireStation.getStation() + " " + fireStation.getAddress() + " adresse ou station vide ");
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

    @Override
    public List<Coverage> getCoverageByFireStation(String stationNumber) {
        List<FireStation> listFireStation = dataRepository.getFireStationByStation(stationNumber);
        List<Coverage> coverageList = new ArrayList<>();
        int adultCount = 0;
        int childCount = 0;

        for (FireStation fireStation : listFireStation) {
            List<Person> personListByAddress = dataRepository.getPersonsByAddress(fireStation.getAddress());
            for (Person person : personListByAddress) {
                Coverage coverage = new Coverage();
                coverage.setLastname(person.getLastName());
                coverage.setFirstName(person.getFirstName());
                coverage.setAddress(person.getAddress());
                coverage.setPhone(person.getPhone());
                MedicalRecord medicalRecord = dataRepository.getMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                int age = CalculateAge.personAge(medicalRecord.getBirthdate());

                if (age <= 18) {
                    childCount++;
                } else {
                    adultCount++;
                }
                coverage.setNombreAdulte(adultCount);
                ;
                coverageList.add(coverage);
            }
        }
        return coverageList;
    }

    public List<String> getFirestation() {
        List<String> listFirestations = new ArrayList<>();

        for (FireStation fireStation : dataRepository.getListFirestation()) {
            listFirestations.add(fireStation.toString());
        }
        return listFirestations;
    }

    @Override
    public List<FirePerson> getFire(String address) {
        List<FirePerson> firePersonList = new ArrayList<>();
        for (Person person : dataRepository.getPersonsByAddress(address)) {
            FirePerson firePerson = new FirePerson();
            firePerson.setFirstname(person.getFirstName());
            firePerson.setLastName(person.getLastName());
            firePerson.setPhone(person.getPhone());
            MedicalRecord medicalRecord = dataRepository.getMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());
            firePerson.setAllergies(medicalRecord.getAllergies());
            firePerson.setMedications(medicalRecord.getMedications());
            firePerson.setAge(CalculateAge.personAge(medicalRecord.getBirthdate()));
            firePerson.setStation(dataRepository.getStationFireStationByAddress(address).getStation());
            firePersonList.add(firePerson);
        }
        return firePersonList;
    }

    @Override
    public List<String> getPhoneAlert(String station) {
        List<String> ListStationNumber = new ArrayList<>();
        for (FireStation fireStation : dataRepository.getFireStationByStation(station)) {
            for (Person person : dataRepository.getAllPersons()) {
                if (fireStation.getAddress().equalsIgnoreCase(person.getAddress())) {
                    ListStationNumber.add(person.getPhone());
                }
            }
        }
        return ListStationNumber;
    }

    @Override
    public List<Foyer> getFloodStation(List<String> stationNumbers) {
        List<String> listAddress = dataRepository.getListFireStation(stationNumbers);
        List<Foyer> foyerList = new ArrayList<>();
        for (String address : listAddress) {
            List<FirePerson> firePersonList = fireStationService.getFire(address);
            Foyer foyer = new Foyer();
            foyer.setAddress(address);
            foyer.setFirePersons(firePersonList);
            foyerList.add(foyer);
        }
        return foyerList;
    }
}
