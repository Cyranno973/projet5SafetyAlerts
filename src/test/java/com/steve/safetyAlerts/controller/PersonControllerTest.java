package com.steve.safetyAlerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.steve.safetyAlerts.exception.DataAlreadyExistException;
import com.steve.safetyAlerts.exception.DataNotFoundException;
import com.steve.safetyAlerts.service.IPersonService;
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

import java.util.Set;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    IPersonService iPersonService;

    String firstNameTest = "john";
    String lastNameTest = "wick";
    String addressTest = "24 dupont";
    String cityTest = "atlanta";
    String zipTest = "87500";
    String phoneTest = "07454545";
    String emailTest = "jogn@gmail.com";

    @Test
    void createPersonValid() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonPerson = objectMapper.createObjectNode();
        jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
        jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));
        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/person")      //requete
                .contentType(MediaType.APPLICATION_JSON).content(jsonPerson.toString())) // contenu
                .andExpect(MockMvcResultMatchers.status().isCreated()); // test
    }

    @Test
    void createPersonInvalid() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonPerson = objectMapper.createObjectNode();
        jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
        jsonPerson.set("lasName", TextNode.valueOf(""));
        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/person")      //requete
                .contentType(MediaType.APPLICATION_JSON).content(jsonPerson.toString())) // contenu
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); // test
    }

    @Test
    void createPersonWhenPersonAlreadyExist() throws Exception {
        //given
        // on mock IPersonService on lui dit de renvoyer l'exception DataAlreadyExist uniquement quand on lui demande de creer une personne
        Mockito.doThrow(DataAlreadyExistException.class).when(iPersonService).createPerson(Mockito.any());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonPerson = objectMapper.createObjectNode();
        jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
        jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/person")      //requete
                .contentType(MediaType.APPLICATION_JSON).content(jsonPerson.toString())) // contenu
                .andExpect(MockMvcResultMatchers.status().isConflict()); // test
    }

    @Test
    void updatePersonValid() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonPerson = objectMapper.createObjectNode();
        jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
        jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));
        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/person")      //requete
                .contentType(MediaType.APPLICATION_JSON).content(jsonPerson.toString())) // contenu
                .andExpect(MockMvcResultMatchers.status().isNoContent()); // test
    }

    @Test
    void updatePersonInvalid() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonPerson = objectMapper.createObjectNode();
        jsonPerson.set("firstName", TextNode.valueOf(""));
        jsonPerson.set("lasName", TextNode.valueOf(""));
        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/person")      //requete
                .contentType(MediaType.APPLICATION_JSON).content(jsonPerson.toString())) // contenu
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); // test
    }

    @Test
    void updatePersonWhenPersonNotFound() throws Exception {
        //given
        // on mock IPersonService on lui dit de renvoyer l'exception DataAlreadyExist uniquement quand on lui demande de creer une personne
        Mockito.doThrow(DataNotFoundException.class).when(iPersonService).updatePerson(Mockito.any());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonPerson = objectMapper.createObjectNode();
        jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
        jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/person")      //requete
                .contentType(MediaType.APPLICATION_JSON).content(jsonPerson.toString())) // contenu
                .andExpect(MockMvcResultMatchers.status().isNotFound()); // test
    }

    @Test
    void deletePersonValid() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonPerson = objectMapper.createObjectNode();
        jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
        jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));
        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/person")      //requete
                .contentType(MediaType.APPLICATION_JSON).content(jsonPerson.toString())) // contenu
                .andExpect(MockMvcResultMatchers.status().isResetContent()); // test
    }

    @Test
    void deletePersonInvalid() throws Exception {
        //given
        // on mock IPersonService on lui dit de renvoyer l'exception DataAlreadyExist uniquement quand on lui demande de creer une personne
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonPerson = objectMapper.createObjectNode();
        jsonPerson.set("firstName", TextNode.valueOf(""));
        jsonPerson.set("lastName", TextNode.valueOf(""));
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/person")      //requete
                .contentType(MediaType.APPLICATION_JSON).content(jsonPerson.toString())) // contenu
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); // test
    }

    @Test
    void deletePersonWhenPersonNotFound() throws Exception {
        //given
        // on mock IPersonService on lui dit de renvoyer l'exception DataAlreadyExist uniquement quand on lui demande de creer une personne
        Mockito.doThrow(DataNotFoundException.class).when(iPersonService).deletePerson(Mockito.any());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonPerson = objectMapper.createObjectNode();
        jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
        jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/person")      //requete
                .contentType(MediaType.APPLICATION_JSON).content(jsonPerson.toString())) // contenu
                .andExpect(MockMvcResultMatchers.status().isNotFound()); // test
    }

    @Test
    void getCommunityEmail() throws Exception {
        //given
        Set<String> listEmails = Set.of("ja@a.com", "b@b.com", "c@C.fr");

        //etape 1 on moque le comportement de ipersonService pour envoyer des valeur d'email
        Mockito.when(iPersonService.getCommunityEmail("Culver")).thenReturn(listEmails);

        //etape2 on envoie une reque GET avec param Culver comme value un 200 isOk()
        mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail")
                .param("city", "Culver"))//requete
                .andExpect(MockMvcResultMatchers.status().isOk()); // test
        //etap 3 on verifie que le serivce a bien été appele avec le bon parametre

        Mockito.verify(iPersonService, Mockito.times(1)).getCommunityEmail("Culver");
    }
}
