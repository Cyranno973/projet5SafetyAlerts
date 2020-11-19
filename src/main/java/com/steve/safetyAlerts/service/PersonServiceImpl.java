package com.steve.safetyAlerts.service;

import com.steve.safetyAlerts.dto.ChildInfo;
import com.steve.safetyAlerts.dto.Homonyme;
import com.steve.safetyAlerts.dto.PersonInfo;
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
    public Collection<String> getPhoneAlert(String station) {
        Collection<String> collectionStationNumber = new ArrayList<>();

        for (FireStation fireStation : dataRepository.getAddressFireStationByStation(station)) {
            for (Person person : dataRepository.getAllPersons()) {
                if (fireStation.getAddress().equalsIgnoreCase(person.getAddress())) {
                    collectionStationNumber.add(person.getPhone());
                }
            }
        }
        return collectionStationNumber;
    }

    @Override
    public Collection<ChildInfo> getChildAlert(String address) {

        Collection<ChildInfo> childInfoCollection = new ArrayList<>();
        List<String> personList = new ArrayList<>();
        for (Person person : dataRepository.getPersonsByAddress(address)) {
            for (MedicalRecord medicalRecord : dataRepository.getMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName())) {
                ChildInfo childInfo = new ChildInfo();

                if (person != null && person.getLastName().equals(person.getLastName())) {
//                        System.out.println("Meme nom "+medicalRecord.getLastName());
                    String member = person.getFirstName() + " " + person.getLastName();
                    personList.add(member);
                    childInfo.setFamilyMember(personList);
                }
                age = CalculateAge.personAge(medicalRecord.getBirthdate());
//                    System.out.println(medicalRecord.getFirstName()+age);


                if (age <= 18) {

//                    System.out.println("ici 1 :"+medicalRecord.getFirstName());
                    childInfo.setFirstName(medicalRecord.getFirstName());
                    childInfo.setLastName(medicalRecord.getLastName());
                    childInfo.setAge(age);
                    childInfoCollection.add(childInfo);
                }
            }
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

                MedicalRecord medicalRecord = dataRepository.getMedicalRecordByFirstNameAndLastNamessss(firstName, lastName);

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
}


