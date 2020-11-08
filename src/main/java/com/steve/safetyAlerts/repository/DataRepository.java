package com.steve.safetyAlerts.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steve.safetyAlerts.model.Database;
import com.steve.safetyAlerts.model.FireStation;
import com.steve.safetyAlerts.model.MedicalRecord;
import com.steve.safetyAlerts.model.Person;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

@Repository
public class DataRepository {

    // Il permet de mapper le JSON en objet JAVA
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static Database database;

    public DataRepository() throws IOException {
        //etape lire data.json
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("data.json");

        //mapper le json et le database
        database = objectMapper.readerFor(Database.class).readValue(inputStream);
    }

    //methode pour recuperer les personnes par ville
    public Collection<Person> getPersonsByCity(String city) {
        Collection<Person> personCollection = new ArrayList<>();
        for (Person person : database.getPersons()) {
            if (person.getCity().equalsIgnoreCase(city)) {
                personCollection.add(person);
            }
        }
        return personCollection;
    }

    public Collection<Person> getAllPersons() {
        Collection<Person> personCollection = new ArrayList<>();
        for (Person person : database.getPersons()) {
            personCollection.add(person);
        }
        return personCollection;
    }

    public Collection<Person> getPersonsByAddress(String address) {
        Collection<Person> personCollection = new ArrayList<>();
        for (Person person : database.getPersons()) {
            if (person.getAddress().equalsIgnoreCase(address)) {
                personCollection.add(person);
            }
        }
//        System.out.println(personCollection);
        return personCollection;
    }

    public Collection<FireStation> getAddressFireStationByStation(String station) {
        Collection<FireStation> fireStationCollection = new ArrayList<>();
        for (FireStation fireStation : database.getFirestations()) {
            if (fireStation.getStation().equalsIgnoreCase(station)) {
                fireStationCollection.add(fireStation);
            }
        }
        return fireStationCollection;
    }

    public Collection<MedicalRecord> getAllMedicalRecord() {
        Collection<MedicalRecord> medicalRecordCollection = new ArrayList<>();
        for (MedicalRecord medicalRecord : database.getMedicalrecords()) {
//            medicalRecordCollection.add(medicalRecord);
        }
        return medicalRecordCollection;
    }

    public Collection<MedicalRecord> getMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
        Collection<MedicalRecord> medicalRecordCollection = new ArrayList<>();
        for (MedicalRecord medicalRecord : database.getMedicalrecords()) {
            if (medicalRecord.getFirstName().equalsIgnoreCase(firstName) && medicalRecord.getLastName().equalsIgnoreCase(lastName)) {
                medicalRecordCollection.add(medicalRecord);
            }
        }
//        System.out.println(medicalRecordCollection);
        return medicalRecordCollection;
    }


    public static void main(String[] args) throws IOException {
        DataRepository dataRepository = new DataRepository();
        System.out.println(dataRepository.database);
    }
}

