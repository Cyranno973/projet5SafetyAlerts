package com.steve.safetyAlerts.service;

import com.steve.safetyAlerts.daoService.MedicalRecordDao;
import com.steve.safetyAlerts.daoService.PersonDao;
import com.steve.safetyAlerts.exception.DataAlreadyExistException;
import com.steve.safetyAlerts.exception.DataNotFoundException;
import com.steve.safetyAlerts.exception.InvalidArgumentException;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PersonServiceTest {

    @Autowired
    IPersonService personServiceTest;

    @MockBean
    DataRepository dataRepository;

    @MockBean
    IMedicalRecordService MedicalRecordService;

    @MockBean
    PersonDao personDaoMock;

    @MockBean
    MedicalRecordDao medicalRecordDaoMock;

    @MockBean
    DataNotFoundException dataNotFoundException;

    @MockBean
    InvalidArgumentException invalidArgumentException;

    Person obama = new Person("Barack", "obama", "WhiteHouse", "Washinton DC", "1232111", "Obama@mohamed.com", "06755");
    Person biden = new Person("joe", "biden", "WhiteHouse", "Washinton DC", "1232111", "Obama@mohamed.com", "06755");
    Person trump = new Person("Donald", "trump", "WhiteHouse", "Washinton DC", "1232111", "Obama@mohamed.com", "06755");

    List<String> medication = List.of("a,b,c,d");
    List<String> allergies = List.of("q,s,d,d");


    @Test
    public void createNoneExistingNPersonTest() {

        //Given
        List<Person> persons = new ArrayList<>();


        //When
        Mockito.when(dataRepository.getAllPersons()).thenReturn(persons);

        //then
        Assertions.assertTrue(personServiceTest.createPerson(obama));

        verify(personDaoMock, Mockito.times(1)).createPerson((obama));

    }

    @Test
    public void createExistingNPersonTest() throws Exception {

        //Given
        List<Person> persons = new ArrayList<>();
        persons.add(obama);

        //When
        Mockito.when(dataRepository.getAllPersons()).thenReturn(persons);

        //then
        try {
            Assertions.assertTrue(personServiceTest.createPerson(obama));
            verify(personDaoMock, Mockito.times(0)).createPerson(any());
        } catch (DataAlreadyExistException eExp) {
            assert (eExp.getMessage().contains("existe déja"));
        }

    }

    @Test
    public void updateExistingPersonTest() throws Exception {

        //when
        Mockito.when(personDaoMock.updatePerson(any(Person.class))).thenReturn(true);

        //then
        Assertions.assertTrue(personServiceTest.updatePerson(obama));

        verify(personDaoMock, Mockito.times(1)).updatePerson((obama));

    }

    @Test
    public void updateNoneExistingPersonTest() throws Exception {

        //when
        Mockito.when(personDaoMock.updatePerson(any(Person.class))).thenReturn(false);

        // THEN
        // On crée un personne qui existe
        try {
            Assertions.assertTrue(personServiceTest.updatePerson(obama));
            verify(personDaoMock, Mockito.times(1)).updatePerson(any());
        } catch (DataNotFoundException eExp) {
            assert (eExp.getMessage().contains("n'existe pas"));
        }
    }

    @Test
    public void deleteExistingPersonTest() {
        //when
        Mockito.when(personDaoMock.deletePerson(any(Person.class))).thenReturn(true);

        //then
        Assertions.assertTrue(personServiceTest.deletePerson(obama));

        verify(personDaoMock, Mockito.times(1)).deletePerson((obama));
    }

    @Test
    public void deleteNoneExistingPersonTest() throws Exception {

        //when
        Mockito.when(personDaoMock.deletePerson(any(Person.class))).thenReturn(false);

        // THEN
        // On crée un personne qui existe
        try {
            Assertions.assertTrue(personServiceTest.deletePerson(obama));
            verify(personDaoMock, Mockito.times(1)).deletePerson(any());
        } catch (DataNotFoundException eExp) {
            assert (eExp.getMessage().contains("n'existe pas"));
        }
    }

    @Test
    public void getPersonTest() throws Exception {
        //Given
        List<Person> persons = new ArrayList<>();
        //when
        Mockito.when(dataRepository.getAllPersons()).thenReturn(persons);

        //then
        Assertions.assertTrue(dataRepository.getAllPersons().add(obama));

        verify(dataRepository, Mockito.times(1)).getAllPersons();
    }

    @Test
    public void getValidCommunityEmailTest() throws Exception {

        //Given
        Collection<String> emails = new ArrayList<>();
        emails.add(obama.getEmail());
        System.out.println(emails);
        //when
        // Mockito.when(personServiceTest.getCommunityEmail(any(String.class))).thenReturn(emails);

        // emails = personServiceTest.getCommunityEmail(obama.getCity());

        //then
        // assertThat(personServiceTest.getCommunityEmail("Culver")).isEqualTo(emails);
    }


}
