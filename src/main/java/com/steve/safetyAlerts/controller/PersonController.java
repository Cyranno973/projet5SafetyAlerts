package com.steve.safetyAlerts.controller;

import com.steve.safetyAlerts.dto.ChildInfo;
import com.steve.safetyAlerts.dto.PersonInfo;
import com.steve.safetyAlerts.model.Person;
import com.steve.safetyAlerts.service.IPersonService;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
public class PersonController {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(PersonController.class);
    // appel d'un service pour remonter les info (city) pour les reponses d'API
    @Autowired
    private IPersonService personService;

    @GetMapping(path = "communityEmail")
    public Collection<String> getCommunityEmail(@RequestParam String city) {
        logger.info("getCommunityEmail : appel du controller communityEmail");
        return personService.getCommunityEmail(city);
    }

    @GetMapping(path = "childAlert")
    public List<ChildInfo> getChildAlert(@RequestParam String address) {
        logger.info("getChildAlert : appel du controller childAlert");
        return personService.getChildAlert(address);
    }

    @GetMapping("personInfo")
    public List<PersonInfo> getPersonInfo(@RequestParam(required = false) String firstName, @RequestParam @Valid String lastName) {
        logger.info("getPersonInfo : appel du controller personInfo");
        return personService.getPersonInfo(firstName, lastName);
    }

    @PostMapping(path = "person")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPerson(@RequestBody @Valid Person person) {
        logger.info("createPerson : appel du controller person");
        personService.createPerson(person);
    }

    @PutMapping(path = "person")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePerson(@RequestBody @Valid Person person) {
        logger.info("updatePerson : appel du controller person");
        personService.updatePerson(person);
    }

    @DeleteMapping(path = "person")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deletePerson(@RequestBody @Valid Person person) {
        logger.info("deletePerson : appel du controller person");
        personService.deletePerson(person);
    }
}

