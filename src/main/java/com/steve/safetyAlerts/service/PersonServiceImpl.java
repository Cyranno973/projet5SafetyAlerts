package com.steve.safetyAlerts.service;

import com.steve.safetyAlerts.dto.ChildInfo;
import com.steve.safetyAlerts.model.FireStation;
import com.steve.safetyAlerts.model.MedicalRecord;
import com.steve.safetyAlerts.model.Person;
import com.steve.safetyAlerts.repository.DataRepository;
import com.steve.safetyAlerts.utility.CalculateAge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class PersonServiceImpl implements IPersonService {
    private int age;

    @Autowired
    private DataRepository dataRepository;

    @Override
    public Collection<String> getCommunityEmail(String city) {
        Collection<String> collectionEmails = new HashSet<String>();

        for (Person person : dataRepository.getPersonsByCity(city)) {
            collectionEmails.add(person.getEmail());
        }
        return collectionEmails;
    }

    @Override
    public Collection<String> getPhoneAlert1() {
        Collection<String> collectionPhoneNumber = new ArrayList<>();
        for (Person person : dataRepository.getAllPersons()) {
            collectionPhoneNumber.add(person.getPhone());
        }
        return collectionPhoneNumber;
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
}
