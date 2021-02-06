package com.steve.safetyAlerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.steve.safetyAlerts.exception.DataAlreadyExistException;
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

    String StationTest = "99";
    String AddressTest = "999 Paris St";

    // --------------- CREATION DE CASERNES -----------------

    @Test
    void createFireStationValid() throws Exception {

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonFirestation = obm.createObjectNode();

        // GIVEN

        jsonFirestation.set("station", TextNode.valueOf(StationTest));
        jsonFirestation.set("address", TextNode.valueOf(AddressTest));

        // WHEN
        // THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFirestation.toString()))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void createFireStationInvalid() throws Exception {
        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonFirestation = obm.createObjectNode();

        //GIven
        jsonFirestation.set("station", TextNode.valueOf(""));
        jsonFirestation.set("address", TextNode.valueOf(AddressTest));

        //When
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFirestation.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createFireStationWhenFireStationAlreadyExist() throws Exception {

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonFirestation = obm.createObjectNode();

        //Given
        jsonFirestation.set("station", TextNode.valueOf(StationTest));
        jsonFirestation.set("address", TextNode.valueOf(AddressTest));

        Mockito.doThrow(DataAlreadyExistException.class)
                .when(firestationService).createFireStation(Mockito.any());
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFirestation.toString()))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }
}
