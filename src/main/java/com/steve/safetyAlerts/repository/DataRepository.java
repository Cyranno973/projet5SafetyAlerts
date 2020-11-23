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
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Person> getPersonsByCity(String city) {
        return getAllPersons().stream()
                .filter(person -> person.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }

    /**
     * @return Retourne la list de toute les personnes
     */
    public List<Person> getAllPersons() {
        return database.getPersons();
    }

    /**
     * @param lastName
     * @return Retourne la list de toute les personnesdu meme nom
     */
    public List<Person> getPersonByLastName(String lastName) {
        return getAllPersons().stream()
                .filter(person -> person.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    /**
     * @param address
     * @return Retourne la list de toute les address
     */
    public List<Person> getPersonsByAddress(String address) {
        return database.getPersons().stream()
                .filter(person -> person.getAddress().equalsIgnoreCase(address))
                .collect(Collectors.toList());
    }

    /**
     * @param station
     * @return Retourne la list de toute les address trouver par station
     */
    public List<FireStation> getAddressFireStationByStation(String station) {
        return database.getFirestations().stream()
                .filter(fireStation -> fireStation.getStation().equalsIgnoreCase(station))
                .collect(Collectors.toList());
    }

    /**
     * @param address
     * @return Retourne la list de toute les address trouver par station
     */
    public FireStation getStationFireStationByAddress(String address) {
        for (FireStation fireStation : database.getFirestations()) {
            if (fireStation.getAddress().equalsIgnoreCase(address)) {
                return fireStation;
            }
        }
        return null;
    }

    /**
     * @param firstName, lastName
     * @return Retourne le medicalrecod d'une personnne au travers de son prénom et nom
     */
    public MedicalRecord getMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
        MedicalRecord medicalRecordResult = new MedicalRecord();
        for (MedicalRecord medicalRecord : database.getMedicalrecords()) {
            if (medicalRecord.getFirstName().equalsIgnoreCase(firstName) && medicalRecord.getLastName().equalsIgnoreCase(lastName)) {
                medicalRecordResult = medicalRecord;
            }
        }
        return medicalRecordResult;
    }

    public List<String> getListFireStation(List<String> stations) {
        return database.getFirestations().stream()
                .filter(fireStation -> stations.contains(fireStation.getStation()))
                .map(FireStation::getAddress)            //.map(fireStation -> fireStation.getAddress())
                .collect(Collectors.toList());
    }

//    public List<String> getListFireStationss(List<String> stations) {
//        List<String> adress = new ArrayList<>();
//        for (FireStation fireStation : database.getFirestations()){
//            if (stations.contains(fireStation.getStation())){
//                adress.add(fireStation.getAddress());
//            }
//        }
//        return adress;
//
//    }

    public List<String> getListFireStationAddress(String station) {
        List<String> lol = database.getFirestations().stream()
                .filter(fireStation -> fireStation.getStation().equals(station))
                .map(FireStation::getAddress)
                .collect(Collectors.toList());
        System.out.println(lol);
        return lol;
    }

    public List<Person> getListPersonByName(String firstName, String lastName) {
        List<Person> personList = new ArrayList<>();
        for (Person person : database.getPersons()) {
            if ((firstName == null || (person.getFirstName().equalsIgnoreCase(firstName))) && (lastName == null || person.getLastName().equalsIgnoreCase(lastName))) {
                personList.add(person);
            }
        }
        return personList;
    }
}

