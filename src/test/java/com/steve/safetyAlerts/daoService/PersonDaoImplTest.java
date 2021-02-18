package com.steve.safetyAlerts.daoService;

import com.steve.safetyAlerts.model.Person;
import com.steve.safetyAlerts.repository.DataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
class PersonDaoImplTest {

    Person obama = new Person("azeae", "obama", "WhiteHouse", "Washinton DC", "1232111", "Obama@mohamed.com", "06755");

    @Autowired
    PersonDaoImpl personDao;

    @Autowired
    DataRepository dataRepository;

    @BeforeEach
    void init(){
        dataRepository.init();
        dataRepository.setCommit(false);
    }

    @Test
    void createPerson() {

        assertThat(dataRepository.getAllPersons().contains(obama)).isFalse();
        assertThat(personDao.createPerson(obama)).isTrue();
        assertThat(dataRepository.getAllPersons().contains(obama)).isTrue();
    }

    @Test
    void updatePerson() {
        assertThat(personDao.updatePerson(obama)).isFalse();
        personDao.createPerson(obama);
        assertThat(personDao.updatePerson(obama)).isTrue();
    }


    @Test
    void deletePerson() {
        assertThat(personDao.deletePerson(obama)).isFalse();
        assertThat(personDao.createPerson(obama)).isTrue();
        assertThat(dataRepository.getAllPersons().contains(obama)).isTrue();

        assertThat(personDao.deletePerson(obama)).isTrue();
        assertThat(dataRepository.getAllPersons().contains(obama)).isFalse();
    }
}
