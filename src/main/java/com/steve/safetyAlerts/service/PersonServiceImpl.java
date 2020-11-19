package com.steve.safetyAlerts.service;

import com.steve.safetyAlerts.dto.*;
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
        for (FireStation fireStation : dataRepository.getAddressFireStationByStation(station)) {
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
    public PersonInfo getPersonInfo(String firstName, String lastName) {

        final List<Person> personByLastName = dataRepository.getPersonByLastName(lastName);
        PersonInfo personInfo = new PersonInfo();
        for (Person person : personByLastName) {
            if (firstName.equalsIgnoreCase(person.getFirstName())) {

                personInfo.setFirstName(person.getFirstName());
                personInfo.setLastName(person.getLastName());
                personInfo.setAddres(person.getAddress());
                personInfo.setEmail(person.getEmail());

                MedicalRecord medicalRecord = dataRepository.getMedicalRecordByFirstNameAndLastName(firstName, lastName);

                age = CalculateAge.personAge(medicalRecord.getBirthdate());

                personInfo.setAge(age);
                personInfo.setAllergies(medicalRecord.getAllergies());
                personInfo.setMedications(medicalRecord.getMedications());
            } else {
                Homonyme homonyme = new Homonyme(person.getFirstName(), person.getLastName());
                personInfo.getHomonymes().add(homonyme);
            }
        }
        return personInfo;
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
       for (String address : listAddress){
          List<FirePerson> firePersonList = getFire(address);
           Foyer foyer = new Foyer();
           foyer.setAddress(address);
           foyer.setFirePersons(firePersonList);
           foyerList.add(foyer);
       }
        return foyerList;
    }
}


