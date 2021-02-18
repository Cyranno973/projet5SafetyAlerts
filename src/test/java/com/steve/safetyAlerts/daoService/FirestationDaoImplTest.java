package com.steve.safetyAlerts.daoService;

import com.steve.safetyAlerts.model.FireStation;
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

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
class FirestationDaoImplTest {

    FireStation fireStation = new FireStation("5zaerue de la Vigne","45");
    FireStation fireStationAddress = new FireStation("5zaerue de la Vigne","");
    FireStation fireStationStation = new FireStation("","45");

    @Autowired
    FirestationDaoImpl firestationDao;

    @Autowired
    DataRepository dataRepository;

    @BeforeEach
    void init(){
        dataRepository.init();
        dataRepository.setCommit(false);
    }

    @Test
    void createFireStation() {
        assertThat(firestationDao.createFireStation(fireStation)).isTrue();
        assertThat(dataRepository.getListFirestation().contains(fireStation)).isTrue();
    }

    @Test
    void updateFireStation() {
        assertThat(firestationDao.updateFireStation(fireStation)).isFalse();
        firestationDao.createFireStation(fireStation);
        assertThat(firestationDao.updateFireStation(fireStation)).isTrue();

    }

    @Test
    void deleteFireStation() {
        assertThat(firestationDao.createFireStation(fireStation)).isTrue();
        assertThat(dataRepository.getListFirestation().contains(fireStation)).isTrue();
        assertThat(firestationDao.deleteFireStation(fireStation)).isTrue();
        assertThat(dataRepository.getListFirestation().contains(fireStation)).isFalse();
    }

    @Test
    void deleteFireStationAddressNull() {
        assertThat(firestationDao.createFireStation(fireStation)).isTrue();
        assertThat(dataRepository.getListFirestation().contains(fireStation)).isTrue();
        assertThat(firestationDao.deleteFireStation(fireStationAddress)).isTrue();
        assertThat(dataRepository.getListFirestation().contains(fireStation)).isFalse();
    }

    @Test
    void deleteFireStationStationNull() {
        assertThat(firestationDao.createFireStation(fireStation)).isTrue();
        assertThat(dataRepository.getListFirestation().contains(fireStation)).isTrue();
        assertThat(firestationDao.deleteFireStation(fireStationStation)).isTrue();
        assertThat(dataRepository.getListFirestation().contains(fireStation)).isFalse();
    }
}
