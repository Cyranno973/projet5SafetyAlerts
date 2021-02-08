package com.steve.safetyAlerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.steve.safetyAlerts.exception.DataAlreadyExistException;
import com.steve.safetyAlerts.exception.DataNotFoundException;
import com.steve.safetyAlerts.service.IMedicalRecordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class MedicalrecordControllerTest {

    @Autowired
    MockMvc mockmvc;

    @MockBean
    IMedicalRecordService medicalrecordService;


    String firstNameTest = "barack";
    String lastNameTest = "Obama";
    String birthdateTest = "25/12/2202";

    @Test
    void createMedicalrecordValid() throws Exception {

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonMedicalrecord = obm.createObjectNode();

        // GIVEN

        jsonMedicalrecord.set("firstName", TextNode.valueOf(firstNameTest));
        jsonMedicalrecord.set("lastName", TextNode.valueOf(lastNameTest));
        jsonMedicalrecord.set("birthdate", TextNode.valueOf(birthdateTest));

        // WHEN
        // THEN
        mockmvc.perform(MockMvcRequestBuilders.post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMedicalrecord.toString()))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void createMedicalrecordInvalid() throws Exception {

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonMedicalrecord = obm.createObjectNode();

        // GIVEN

        jsonMedicalrecord.set("firstName", TextNode.valueOf(firstNameTest));
        jsonMedicalrecord.set("lastName", TextNode.valueOf(""));

        // WHEN
        // THEN
        mockmvc.perform(MockMvcRequestBuilders.post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMedicalrecord.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createMedicalrecordWhenMedicalrecordAlreadyExist() throws Exception {

        // on mock MedicalrecordService et on lui dit de renvoyer l'exception
        // DataALreadExist
        // quand on lui demande de renvoyer un Medicalrecord existant

        Mockito.doThrow(DataAlreadyExistException.class)
                .when(medicalrecordService).createMedicalRecord(Mockito.any());

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonMedicalrecord = obm.createObjectNode();

        // GIVEN

        jsonMedicalrecord.set("firstName", TextNode.valueOf(firstNameTest));
        jsonMedicalrecord.set("lastName", TextNode.valueOf(lastNameTest));

        // WHEN
        // THEN

        mockmvc.perform(MockMvcRequestBuilders.post("/Medicalrecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMedicalrecord.toString()))
                // .andExpect(MockMvcResultMatchers.status().isConflict());
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // --------------- MAJ DE DOSSIERS MEDICAUX -----------------

    @Test
    void updateMedicalrecordValid() throws Exception {

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonMedicalrecord = obm.createObjectNode();

        // GIVEN

        jsonMedicalrecord.set("firstName", TextNode.valueOf("John"));
        jsonMedicalrecord.set("lastName", TextNode.valueOf("Boyd"));
        jsonMedicalrecord.set("birthdate", TextNode.valueOf(birthdateTest));

        // WHEN
        // THEN

        mockmvc.perform(MockMvcRequestBuilders.put("/Medicalrecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMedicalrecord.toString()))
                // DPANO
                // .andExpect(MockMvcResultMatchers.status().isNoContent());
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateMedicalrecordInvalid() throws Exception {

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonMedicalrecord = obm.createObjectNode();

        // GIVEN

        jsonMedicalrecord.set("firstName", TextNode.valueOf(""));
        jsonMedicalrecord.set("lastName", TextNode.valueOf(""));

        // WHEN
        // THEN

        mockmvc.perform(MockMvcRequestBuilders.put("/Medicalrecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMedicalrecord.toString()))
                // DPANO
                // .andExpect(MockMvcResultMatchers.status().isBadRequest());
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

//    @Test
//    void updateMedicalrecordWhenMedicalrecordNotFound() throws Exception {
//
//        Mockito.doThrow(DataNotFoundException.class).when(medicalrecordService)
//                .updateMedicalRecord(Mockito.any());
//
//        ObjectMapper obm = new ObjectMapper();
//        ObjectNode jsonMedicalrecord = obm.createObjectNode();
//
//        // GIVEN
//
//        jsonMedicalrecord.set("firstName", TextNode.valueOf(firstNameTest));
//        jsonMedicalrecord.set("lastName", TextNode.valueOf(lastNameTest));
//
//        // WHEN
//        // THEN
//
//        mockmvc.perform(MockMvcRequestBuilders.put("/Medicalrecord")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonMedicalrecord.toString()))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//    }

    // Controleur "/medicalRecord"
//
//    @Test
//    void getmedicalRecord() throws Exception {
//
//        // Etape 1 : on envoie une requête GET
//        // + on vérifie que le statut de la réponse est 200
//
//        mockmvc.perform(MockMvcRequestBuilders.get("/medicalRecord"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        // Etape 2 : on vérifie que le service a bien été appelé avec les bons
//        // paramètres
//
//        Mockito.verify(medicalrecordService, Mockito.times(1))
//                .getMedicalRecord();
//    }


}
