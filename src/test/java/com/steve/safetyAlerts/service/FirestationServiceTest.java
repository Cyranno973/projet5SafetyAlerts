package com.steve.safetyAlerts.service;

import com.steve.safetyAlerts.daoService.FirestationDaoImpl;
import com.steve.safetyAlerts.dto.FirePerson;
import com.steve.safetyAlerts.exception.DataAlreadyExistException;
import com.steve.safetyAlerts.exception.DataNotFoundException;
import com.steve.safetyAlerts.exception.InvalidArgumentException;
import com.steve.safetyAlerts.model.FireStation;
import com.steve.safetyAlerts.model.MedicalRecord;
import com.steve.safetyAlerts.model.Person;
import com.steve.safetyAlerts.repository.DataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class FirestationServiceTest {

    @Autowired
    IFireStationService fireStationService;

    @MockBean
    FirestationDaoImpl firestationDaoMock;

    @MockBean
    DataRepository dataRepository;

    @MockBean
    DataNotFoundException dataNotFoundException;

    @MockBean
    InvalidArgumentException invalidArgumentException;

    FireStation firestation1 = new FireStation("WhiteHouse", "1");
    FireStation firestation2 = new FireStation("BlackHouse", "2");
    FireStation firestationVide = new FireStation("", "");

    Person obama = new Person("Barack", "obama", "WhiteHouse", "Washinton DC", "1232111","06755" , "obama@mohamed.com");
    Person biden = new Person("joe", "biden", "BlackHouse", "Washinton DC", "1232111","06754" , "biden@mohamed.com");
    Person trump = new Person("Donald", "trump", "CasinoHouse", "Washinton DC", "1232111", "06753","trump@mohamed.com");

    List<String> medication = List.of("a,b,c,d");
    List<String> allergies = List.of("q,s,d,d");

    MedicalRecord medicalrecordObama = new MedicalRecord("Barack", "obama",
            "03/06/1984", medication, allergies);


    @Test
    public void createExistingFirestationTest() throws Exception {

        // GIVEN
        List<FireStation> listFirestation = new ArrayList<FireStation>();
        listFirestation.add(firestation1);

        // WHEN
        Mockito.when(dataRepository.getListFirestation()).thenReturn(listFirestation);

        // THEN
        // On crée une station qui existe
        try {
            Assertions.assertFalse(fireStationService.createFireStation(firestation1));
            verify(firestationDaoMock, Mockito.times(0)).createFireStation(firestation1);
        } catch (DataAlreadyExistException eExp) {
            assert (eExp.getMessage().contains("existe déja"));
        }
    }

    @Test
    public void createInvalidFirestationTest() throws Exception {

        // GIVEN
        List<FireStation> listFirestation = new ArrayList<FireStation>();
        listFirestation.add(firestationVide);

        // WHEN
        Mockito.when(dataRepository.getListFirestation()).thenReturn(listFirestation);

        // THEN
        // On crée une station qui existe
        try {
            Assertions.assertFalse(fireStationService.createFireStation(firestationVide));
            verify(firestationDaoMock, Mockito.times(0)).createFireStation(firestationVide);
        } catch (InvalidArgumentException eExp) {
            assert (eExp.getMessage().contains("adresse ou station vide"));
        }
    }


    @Test
    public void createValidFirestationTest() throws Exception {

        //given
        List<FireStation> listFirestation = new ArrayList<FireStation>();
        //when
        Mockito.when(dataRepository.getListFirestation()).thenReturn(listFirestation);

        //then
        Assertions.assertTrue(fireStationService.createFireStation(firestation1));
        verify(firestationDaoMock, Mockito.times(1)).createFireStation(firestation1);

    }

    @Test
    public void updateExistingFirestationTest() throws Exception {
        // GIVEN

        // WHEN

        Mockito.when(
                firestationDaoMock.updateFireStation(any(FireStation.class)))
                .thenReturn(true);

        // THEN
        // On MAJ la station
        Assertions.assertTrue(
                fireStationService.updateFireStation(firestation1));
        verify(firestationDaoMock, Mockito.times(1))
                .updateFireStation(firestation1);

    }

    @Test
    public void updateNonExistingFirestationTest() throws Exception {
        // GIVEN
        // WHEN
        Mockito.when(firestationDaoMock.updateFireStation(any(FireStation.class))).thenReturn(false);
        // THEN
        // On crée une station qui existe
        try {
            Assertions.assertFalse(fireStationService.updateFireStation(firestation1));
            verify(firestationDaoMock, Mockito.times(1)).updateFireStation(firestation1);
        } catch (DataNotFoundException dnfe) {
            assert (dnfe.getMessage().contains("n'existe pas"));
        }
    }

    @Test
    public void deleteExistingFirestationTest() throws Exception {

        Mockito.when(
                firestationDaoMock.deleteFireStation(any(FireStation.class)))
                .thenReturn(true);

        // THEN
        // On MAJ la station
        Assertions.assertTrue(
                fireStationService.deleteFireStation(firestation1));
        verify(firestationDaoMock, Mockito.times(1))
                .deleteFireStation(firestation1);


    }

    @Test
    public void deleteNonExistingFirestationTest() throws Exception {

        // GIVEN
        // WHEN
        Mockito.when(firestationDaoMock.deleteFireStation(any(FireStation.class))).thenReturn(false);
        // THEN
        // On crée une station qui existe
        try {
            Assertions.assertFalse(fireStationService.deleteFireStation(firestation1));
            verify(firestationDaoMock, Mockito.times(1)).deleteFireStation(firestation1);
        } catch (DataNotFoundException dnfe) {
            assert (dnfe.getMessage().contains("n'existe pas"));
        }
    }

    @Test
    public void getFirestationTest() throws Exception {

        List<FireStation> listFirestation = new ArrayList<FireStation>();

        // GIVEN
        listFirestation.add(firestation1);
        listFirestation.add(firestation2);

        // WHEN
        Mockito.when(dataRepository.getListFirestation())
                .thenReturn(listFirestation);

        // THEN
        assertThat(fireStationService.getFirestation().size()).isEqualTo(2);
    }

    @Test
    void getPhoneAlert() {
        String station = "1";
        //given
        Mockito.when(dataRepository.getFireStationByStation(station)).thenReturn(List.of(firestation1));
        Mockito.when(dataRepository.getAllPersons()).thenReturn(List.of(obama,biden,trump));
        //when
        List<String> phones = fireStationService.getPhoneAlert(station);
        //then
        assertThat(phones).isEqualTo(List.of(obama.getPhone()));
    }

    @Test
    void getFire() {

        String address = "WhiteHouse";
        medicalrecordObama.setBirthdate(LocalDate.now().minusYears(30).format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        //given
        Mockito.when(dataRepository.getPersonsByAddress(address)).thenReturn(List.of(obama));
        Mockito.when(dataRepository.getMedicalRecordByFirstNameAndLastName(obama.getFirstName(), obama.getLastName())).thenReturn(medicalrecordObama);
        Mockito.when(dataRepository.getStationFireStationByAddress(address)).thenReturn(firestation1);
        //when
        List<FirePerson> firestations = fireStationService.getFire(address);
        //then
        assertThat(firestations).hasSize(1);
        FirePerson firePerson = firestations.get(0);
        assertThat(firePerson.getFirstname()).isEqualTo("Barack");
        assertThat(firePerson.getLastName()).isEqualTo("obama");
        assertThat(firePerson.getPhone()).isEqualTo("06755");
        assertThat(firePerson.getAge()).isEqualTo(30);
        assertThat(firePerson.getStation()).isEqualTo("1");
        assertThat(firePerson.getAllergies()).isEqualTo(medicalrecordObama.getAllergies());
        assertThat(firePerson.getMedications()).isEqualTo(medicalrecordObama.getMedications());
    }

    @Test
    void getFloodStation() {


    }

    @Test
    void getCoverageByFireStation() {

    }
}
