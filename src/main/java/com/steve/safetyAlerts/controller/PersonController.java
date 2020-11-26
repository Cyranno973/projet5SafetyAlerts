package com.steve.safetyAlerts.controller;

import com.steve.safetyAlerts.dto.*;
import com.steve.safetyAlerts.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
public class PersonController {

    // appel d'un service pour remonter les info (city) pour les reponses d'API
    @Autowired
    private IPersonService personService ;
    // private IFireStationService fireStationService;

    @GetMapping(path="phoneAlert")
    public List<String> getPhoneAlert(@RequestParam String station){
        return personService.getPhoneAlert(station);
    }

    @GetMapping(path="communityEmail")
    public Set<String> getCommunityEmail(@RequestParam String city){
        return personService.getCommunityEmail(city);
    }

    @GetMapping(path="childAlert")
    public List<ChildInfo> getChildAlert(@RequestParam String address){
        return personService.getChildAlert(address);
    }

    @GetMapping("personInfo")
    public List<PersonInfo> getPersonInfo(@RequestParam(required = false) String firstName, @RequestParam @Valid String lastName){
        return personService.getPersonInfo(firstName,lastName);
    }
    @GetMapping("fire")
    public List<FirePerson> getFire(@RequestParam String address){
        return personService.getFire(address);
    }

    @GetMapping("flood")
    public List<Foyer> getFlood(@RequestParam List<String> stationNumbers){ return personService.getFloodStation(stationNumbers); }

    @GetMapping("firestation")
    public List<Coverage> getCoverageByFireStation(@RequestParam String stationNumber){ return personService.getCoverageByFireStation(stationNumber); }
}

