package com.steve.safetyAlerts.daoService;

import com.steve.safetyAlerts.model.MedicalRecord;
import com.steve.safetyAlerts.model.Person;
import com.steve.safetyAlerts.repository.DataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
class MedicalRecordDaoImplTest {

    List<String> medications = new ArrayList<String>(
            Arrays.asList("a", "b", "c", "d"));
    List<String> allergies = new ArrayList<String>(
            Arrays.asList("e", "f", "g", "h"));

    MedicalRecord medicalrecord = new MedicalRecord("Roqsdqsgzazeer", "Boyd", "03/06/1984", medications, allergies);

    @Autowired
    MedicalRecordDaoImpl medicalRecordDao;

    @Autowired
    DataRepository dataRepository;

    @BeforeEach
    void init(){
        dataRepository.init();
        dataRepository.setCommit(false);
    }

    @Test
    void createMedicalRecord() {

        assertThat(medicalRecordDao.createMedicalRecord(medicalrecord)).isTrue();
        assertThat(dataRepository.getAllMedicalRecord().contains(medicalrecord)).isTrue();
    }

    @Test
    void updateMedicalRecord() {

        assertThat(medicalRecordDao.updateMedicalRecord(medicalrecord)).isFalse();
        medicalRecordDao.createMedicalRecord(medicalrecord);
        assertThat(medicalRecordDao.updateMedicalRecord(medicalrecord)).isTrue();
    }


    @Test
    void deleteMedicalRecord() {

        assertThat(medicalRecordDao.createMedicalRecord(medicalrecord)).isTrue();
        assertThat(dataRepository.getAllMedicalRecord().contains(medicalrecord)).isTrue();
        assertThat(medicalRecordDao.deleteMedicalRecord(medicalrecord)).isTrue();
        assertThat(dataRepository.getAllMedicalRecord().contains(medicalrecord)).isFalse();
    }
}
