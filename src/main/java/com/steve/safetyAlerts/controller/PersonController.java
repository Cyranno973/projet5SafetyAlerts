package com.steve.safetyAlerts.controller;

import com.steve.safetyAlerts.dto.ChildInfo;
import com.steve.safetyAlerts.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class PersonController {

    // appel d'un service pour remonter les info (city) pour les reponses d'API
    @Autowired
    private IPersonService personService ;
    // private IFireStationService fireStationService;

    @GetMapping(path="phoneAlert1")
    public Collection<String> getPhoneAlert(){
        return personService.getPhoneAlert1();
    }

    @GetMapping(path="phoneAlert")
    public Collection<String> getPhoneAlert(@RequestParam String station){
        return personService.getPhoneAlert(station);
    }
    @GetMapping(path="communityEmail")
    public Collection<String> getCommunityEmail(@RequestParam String city){
        return personService.getCommunityEmail(city);
    }
    @GetMapping(path="childAlert")
    public Collection<ChildInfo> getChildAlert(@RequestParam String address){
        return personService.getChildAlert(address);
    }
}

