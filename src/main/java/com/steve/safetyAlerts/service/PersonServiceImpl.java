package com.steve.safetyAlerts.service;

import com.steve.safetyAlerts.dao.PersonDao;
import com.steve.safetyAlerts.dto.*;
import com.steve.safetyAlerts.exception.DataAlreadyExistException;
import com.steve.safetyAlerts.exception.DataNotFoundException;
import com.steve.safetyAlerts.model.FireStation;
import com.steve.safetyAlerts.model.MedicalRecord;
import com.steve.safetyAlerts.model.Person;
import com.steve.safetyAlerts.repository.DataRepository;
import com.steve.safetyAlerts.utility.CalculateAge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonServiceImpl implements IPersonService {
    private int age;

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private PersonDao personDao;


    @Override
    public boolean createPerson(Person person) {
        // verification que la person n'existe pas dans la DAO(datarepository)
        if (!dataRepository.getAllPersons().contains(person)) {
            personDao.createPerson(person);
            return true;
        } else {
            throw new DataAlreadyExistException("La personne " + person.getFirstName() + " " + person.getLastName() + "existe déja");
        }
    }

    @Override
    public boolean updatePerson(Person person) {
        if (!personDao.updatePerson(person)) {
            throw new DataNotFoundException("La personne " + person.getFirstName() + " " + person.getLastName() + "n'existe pas");
        }
        return true;
    }

    @Override
    public boolean deletePerson(Person person) {
        if (!personDao.deletePerson(person)) {
            throw new DataNotFoundException("La personne " + person.getFirstName() + " " + person.getLastName() + "n'existe pas");
        }
        return true;
    }

    @Override
    public Set<String> getCommunityEmail(String city) {
        Set<String> listEmails = new HashSet<String>();

        for (Person person : dataRepository.getPersonsByCity(city)) {
            listEmails.add(person.getEmail());
        }
        return listEmails;
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
    public List<ChildInfo> getChildAlert(String address) {
        List<Person> personList = dataRepository.getPersonsByAddress(address);
        List<ChildInfo> childInfoCollection = new ArrayList<>();
        List<String> familyList = new ArrayList<>();
        for (Person person : personList) {
            MedicalRecord medicalRecord = dataRepository.getMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());
            int age = CalculateAge.personAge(medicalRecord.getBirthdate());
            ChildInfo childInfo = new ChildInfo();
            if (age <= 18) {
                childInfo.setFirstName(person.getFirstName());
                childInfo.setLastName(person.getLastName());
                childInfo.setAge(age);
                childInfoCollection.add(childInfo);

            } else {
                familyList.add(person.getFirstName());
            }
            childInfo.setFamilyMember(familyList);
        }
        return childInfoCollection;
    }

    @Override
    public List<PersonInfo> getPersonInfo(String firstName, String lastName) {

        List<PersonInfo> personInfoList = new ArrayList<>();

        for (Person person : dataRepository.getListPersonByName(firstName, lastName)) {

            PersonInfo personInfo = new PersonInfo();
            personInfo.setFirstName(person.getFirstName());
            personInfo.setLastName(person.getLastName());
            personInfo.setAddres(person.getAddress());
            personInfo.setEmail(person.getEmail());

            MedicalRecord medicalRecord = dataRepository.getMedicalRecordByFirstNameAndLastName(personInfo.getFirstName(), personInfo.getLastName());

            age = CalculateAge.personAge(medicalRecord.getBirthdate());

            personInfo.setAge(age);
            personInfo.setAllergies(medicalRecord.getAllergies());
            personInfo.setMedications(medicalRecord.getMedications());
            personInfoList.add(personInfo);
//
//            else {
//                Homonyme homonyme = new Homonyme(person.getFirstName(), person.getLastName());
//                personInfo.getHomonymes().add(homonyme);
//            }
        }
        return personInfoList;
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
    public List<Foyer> getFloodStation(List<String> stationNumbers) {
        List<String> listAddress = dataRepository.getListFireStation(stationNumbers);
        List<Foyer> foyerList = new ArrayList<>();
        for (String address : listAddress) {
            List<FirePerson> firePersonList = getFire(address);
            Foyer foyer = new Foyer();
            foyer.setAddress(address);
            foyer.setFirePersons(firePersonList);
            foyerList.add(foyer);
        }
        return foyerList;
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

}


