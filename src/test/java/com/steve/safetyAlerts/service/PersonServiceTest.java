package com.steve.safetyAlerts.service;

import com.steve.safetyAlerts.dao.MedicalRecordDao;
import com.steve.safetyAlerts.dao.PersonDao;
import com.steve.safetyAlerts.exception.DataNotFoundException;
import com.steve.safetyAlerts.exception.InvalidArgumentException;
import com.steve.safetyAlerts.model.Person;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PersonServiceTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    IPersonService personService;

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

    List<String>medication = List.of("a,b,c,d");
    List<String>allergies = List.of("q,s,d,d");

}
