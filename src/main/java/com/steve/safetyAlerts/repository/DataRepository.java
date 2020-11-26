package com.steve.safetyAlerts.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steve.safetyAlerts.exception.DataRepositoryException;
import com.steve.safetyAlerts.model.Database;
import com.steve.safetyAlerts.model.FireStation;
import com.steve.safetyAlerts.model.MedicalRecord;
import com.steve.safetyAlerts.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DataRepository {

    // Il permet de mapper le JSON en objet JAVA
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static Database database;
    public String JSON_FILE = "data.json";
    private static final Logger logger = LogManager.getLogger(DataRepository.class);

    public DataRepository() throws IOException {
        this.init();
    }

    /**
     * Chargement du Fichier data.json en memoire dans l'objet Database
     */
    public void init() {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(JSON_FILE)) {
            database = objectMapper.readerFor(Database.class).readValue(inputStream);
            logger.info("OK - FILE_OPEN : "+ JSON_FILE);

        } catch (FileNotFoundException fnfe) {

            logger.info("KO - FILE_NOT_FOUND : "+ JSON_FILE);
            throw new DataRepositoryException("KO - FILE_NOT_FOUND : ", fnfe);

        } catch (IOException ioe) {

            logger.info("KO - I/O ERREUR : "+ JSON_FILE);
            throw new DataRepositoryException("KO - I/O ERREUR : ", ioe);

        }
    }
    public void commit (){

    }
    public void setCommit(){

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
    public List<FireStation> getFireStationByStation(String station) {
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
                .map(FireStation::getAddress)
                .collect(Collectors.toList());
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

