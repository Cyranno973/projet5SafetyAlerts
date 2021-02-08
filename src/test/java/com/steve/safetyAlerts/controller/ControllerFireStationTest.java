package com.steve.safetyAlerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.steve.safetyAlerts.exception.DataAlreadyExistException;
import com.steve.safetyAlerts.exception.DataNotFoundException;
import com.steve.safetyAlerts.exception.InvalidArgumentException;
import com.steve.safetyAlerts.service.IFireStationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc

public class ControllerFireStationTest {
    @Mock
    FireStationController fireStationController = new FireStationController();

    @Autowired
    MockMvc mockMvc;
    @MockBean
    IFireStationService firestationService;

    String StationTest = "14";
    String AddressTest = "24 boulevard magenta";

    // --------------- CREATION DE CASERNES -----------------

    @Test
    void createFireStationValid() throws Exception {

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonFireStation = obm.createObjectNode();

        // GIVEN

        jsonFireStation.set("station", TextNode.valueOf(StationTest));
        jsonFireStation.set("address", TextNode.valueOf(AddressTest));

        // WHEN
        // THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFireStation.toString()))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void createFireStationInvalid() throws Exception {

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonFireStation = obm.createObjectNode();

        //GIven
        jsonFireStation.set("station", TextNode.valueOf(""));
        jsonFireStation.set("address", TextNode.valueOf(AddressTest));

        Mockito.doThrow(InvalidArgumentException.class)
                .when(firestationService).createFireStation(Mockito.any());
        //When
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFireStation.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createFireStationWhenFireStationAlreadyExist() throws Exception {

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonFireStation = obm.createObjectNode();

        //Given
        jsonFireStation.set("station", TextNode.valueOf(StationTest));
        jsonFireStation.set("address", TextNode.valueOf(AddressTest));

        Mockito.doThrow(DataAlreadyExistException.class)
                .when(firestationService).createFireStation(Mockito.any());
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFireStation.toString()))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void updateFireStationValid() throws Exception {

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonFireStation = obm.createObjectNode();

        // GIVEN

        jsonFireStation.set("station", TextNode.valueOf(StationTest));
        jsonFireStation.set("address", TextNode.valueOf(AddressTest));

        mockMvc.perform(MockMvcRequestBuilders.put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFireStation.toString()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    // @Test
    void updateFireStationInvalid() throws Exception {

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonFireStation = obm.createObjectNode();

        // GIVEN

        // jsonFireStation.set("station", TextNode.valueOf(""));
        // jsonFireStation.set("address", TextNode.valueOf(""));

        // WHEN
        // THEN
        mockMvc.perform(MockMvcRequestBuilders.put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFireStation.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updateFireStationWhenFireStationNotFound() throws Exception {

        Mockito.doThrow(DataNotFoundException.class).when(firestationService)
                .updateFireStation(Mockito.any());

        // GIVEN

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonFireStation = obm.createObjectNode();

        // WHEN
        // THEN

        jsonFireStation.set("station", TextNode.valueOf(StationTest));
        jsonFireStation.set("address", TextNode.valueOf(AddressTest));

        mockMvc.perform(MockMvcRequestBuilders.put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFireStation.toString()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // --------------- SUPPRESSION DE CASERNES -----------------

    @Test
    void deleteFireStationValid() throws Exception {

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonFireStation = obm.createObjectNode();

        // GIVEN

        jsonFireStation.set("station", TextNode.valueOf(StationTest));
        jsonFireStation.set("address", TextNode.valueOf(AddressTest));

        // WHEN
        // THEN
        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFireStation.toString()))
                .andExpect(MockMvcResultMatchers.status().isResetContent());
    }

    @Test
    void deleteFireStationValidByAddress() throws Exception {

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonFireStation = obm.createObjectNode();

        // GIVEN

        jsonFireStation.set("station", TextNode.valueOf(""));
        jsonFireStation.set("address", TextNode.valueOf(AddressTest));

        // WHEN
        // THEN
        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFireStation.toString()))
                .andExpect(MockMvcResultMatchers.status().isResetContent());
    }

    @Test
    void deleteFireStationValidByStation() throws Exception {

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonFireStation = obm.createObjectNode();

        // GIVEN

        jsonFireStation.set("station", TextNode.valueOf(StationTest));
        jsonFireStation.set("address", TextNode.valueOf(""));

        // WHEN
        // THEN
        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFireStation.toString()))
                .andExpect(MockMvcResultMatchers.status().isResetContent());
    }

    // @Test
    void deleteFireStationInvalid() throws Exception {

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonFireStation = obm.createObjectNode();

        // GIVEN

        jsonFireStation.set("station", TextNode.valueOf(""));
        jsonFireStation.set("address", TextNode.valueOf(""));

        // WHEN
        // THEN
        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFireStation.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deleteFireStationWhenFireStationNotFound() throws Exception {

        Mockito.doThrow(DataNotFoundException.class).when(firestationService)
                .deleteFireStation(Mockito.any());

        // GIVEN

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonFireStation = obm.createObjectNode();

        // WHEN
        // THEN

        jsonFireStation.set("station", TextNode.valueOf(StationTest));
        jsonFireStation.set("address", TextNode.valueOf(AddressTest));

        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFireStation.toString()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void FireStationController() throws Exception {

        // Test 1 : on envoie une requête GET avec en paramètre une adresse
        // valide
        // + on vérifie que le statut de la réponse est 200

        mockMvc.perform(MockMvcRequestBuilders.get("/fire").param("address",
                "892 Downing Ct"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Test 2 : on vérifie que le service a bien été appelé avec les bons
        // paramètres

        Mockito.verify(firestationService, Mockito.times(1))
                .getFire("892 Downing Ct");

        // Test 3 : on envoie une requête GET avec en paramètre une adresse
        // non valide
        // + on vérifie que le retour est vide

        mockMvc.perform(MockMvcRequestBuilders.get("/fire").param("address",
                "999 Downing Ct"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));

    }

    @Test
    void getFireStationListPhone() throws Exception {

        // Test 1 : on envoie une requête GET avec en paramètre un n° de station
        // valide
        // + on vérifie que le statut de la réponse est 200

        mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert")
                .param("station", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Test 2 : on vérifie que le service a bien été appelé avec les bons
        // paramètres

        Mockito.verify(firestationService, Mockito.times(1))
                .getPhoneAlert("1");

        // Test 3 : on envoie une requête GET avec en paramètre une station
        // qui n'existe pas
        // + on vérifie que le retour est vide

        mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert")
                .param("station", "9"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));

    }

    @Test
    void getFireStationCoveragePerson() throws Exception {

        // Test 1 : on envoie une requête GET avec en paramètre un n° de station
        // valide
        // + on vérifie que le statut de la réponse est 200

        mockMvc.perform(MockMvcRequestBuilders.get("/firestation")
                .param("stationNumber", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Test 2 : on vérifie que le service a bien été appelé avec les bons
        // paramètres

        Mockito.verify(firestationService, Mockito.times(1))
                .getCoverageByFireStation("1");

        // Test 3 : on envoie une requête GET avec en paramètre une station
        // qui n'existe pas
        // + on vérifie que le retour est vide

        mockMvc.perform(MockMvcRequestBuilders.get("/firestation")
                .param("stationNumber", "9"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void getFireStationPersonAtAddress() throws Exception {

        List<String> stations = Arrays.asList("1", "2");

        // Test 1 : on envoie une requête GET avec en paramètre des n° de
        // station
        // valide
        // + on vérifie que le statut de la réponse est 200

        mockMvc.perform(MockMvcRequestBuilders.get("/flood/stations")
                .param("stationNumbers", "1", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Test 2 : on vérifie que le service a bien été appelé avec les bons
        // paramètres

        Mockito.verify(firestationService, Mockito.times(1))
                .getFloodStation(stations);

        // Test 3 : on envoie une requête GET avec en paramètre une station
        // qui n'existe pas
        // + on vérifie que le retour est vide

        mockMvc.perform(MockMvcRequestBuilders.get("/flood/stations")
                .param("stationNumbers", "0"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    // Controleur "/firestationlist"

    @Test
    void getFireStation() throws Exception {

        // Etape 1 : on envoie une requête GET
        // + on vérifie que le statut de la réponse est 200

        mockMvc.perform(MockMvcRequestBuilders.get("/firestationlist"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Etape 2 : on vérifie que le service a bien été appelé avec les bons
        // paramètres

        Mockito.verify(firestationService, Mockito.times(1)).getFirestation();
    }
}
