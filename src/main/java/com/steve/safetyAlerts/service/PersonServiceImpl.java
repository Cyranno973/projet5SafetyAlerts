package com.steve.safetyAlerts.service;

import com.steve.safetyAlerts.controller.PersonController;
import com.steve.safetyAlerts.daoService.PersonDao;
import com.steve.safetyAlerts.dto.ChildInfo;
import com.steve.safetyAlerts.dto.PersonInfo;
import com.steve.safetyAlerts.exception.DataAlreadyExistException;
import com.steve.safetyAlerts.exception.DataNotFoundException;
import com.steve.safetyAlerts.model.MedicalRecord;
import com.steve.safetyAlerts.model.Person;
import com.steve.safetyAlerts.repository.DataRepository;
import com.steve.safetyAlerts.utility.CalculateAge;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonServiceImpl implements IPersonService {
    private int age;
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(PersonServiceImpl.class);

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private PersonDao personDao;

    @Override
    public boolean createPerson(Person person) {
        // verification que la person n'existe pas dans la DAO(datarepository)
        if (!dataRepository.getAllPersons().contains(person)) {
            personDao.createPerson(person);
            logger.info("createPerson : laperson à été creer");
            return true;
        } else {
            logger.error("La personne " + person.getFirstName() + " " + person.getLastName() + "existe déja");
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
    public Collection<String> getCommunityEmail(String city) {
        Collection<String> listEmails = new HashSet<>();

        for (Person person : dataRepository.getPersonsByCity(city)) {
            logger.info("getcomminty : ajouts des email dans une liste");
            listEmails.add(person.getEmail());
        }
        return listEmails;
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
}


