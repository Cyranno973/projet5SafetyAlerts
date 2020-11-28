package com.steve.safetyAlerts.controller;

import com.steve.safetyAlerts.model.FireStation;
import com.steve.safetyAlerts.service.IFireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class FireStationController {

    @Autowired
    private IFireStationService fireStationService;

    @PostMapping(path = "firestation")
    @ResponseStatus(HttpStatus.CREATED)
    public void createFireStation(@RequestBody @Valid FireStation firestation) {
        fireStationService.createFireStation(firestation);
    }


    @PutMapping(path = "firestation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateFireStation(@RequestBody @Valid FireStation firestation) {
        fireStationService.updateFireStation(firestation);
    }

    @DeleteMapping(path = "firestation")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteFireStation(@RequestBody @Valid FireStation firestation) {
        fireStationService.deleteFireStation(firestation);
    }

}
