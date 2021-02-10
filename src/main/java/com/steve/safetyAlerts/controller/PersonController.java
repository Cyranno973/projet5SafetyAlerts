package com.steve.safetyAlerts.controller;

import com.steve.safetyAlerts.dto.ChildInfo;
import com.steve.safetyAlerts.dto.PersonInfo;
import com.steve.safetyAlerts.model.Person;
import com.steve.safetyAlerts.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
public class PersonController {

    // appel d'un service pour remonter les info (city) pour les reponses d'API
    @Autowired
    private IPersonService personService;

    @GetMapping(path = "communityEmail")
    public Collection<String> getCommunityEmail(@RequestParam String city) {
        return personService.getCommunityEmail(city);
    }

    @GetMapping(path = "childAlert")
    public List<ChildInfo> getChildAlert(@RequestParam String address) {
        return personService.getChildAlert(address);
    }

    @GetMapping("personInfo")
    public List<PersonInfo> getPersonInfo(@RequestParam(required = false) String firstName, @RequestParam @Valid String lastName) {
        return personService.getPersonInfo(firstName, lastName);
    }

    @PostMapping(path = "person")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPerson(@RequestBody @Valid Person person) {
        personService.createPerson(person);
    }

    @PutMapping(path = "person")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePerson(@RequestBody @Valid Person person) {
        personService.updatePerson(person);
    }

    @DeleteMapping(path = "person")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deletePerson(@RequestBody @Valid Person person) {
        personService.deletePerson(person);
    }
}

