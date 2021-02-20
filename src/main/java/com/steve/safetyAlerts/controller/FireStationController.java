package com.steve.safetyAlerts.controller;

import com.steve.safetyAlerts.dto.Coverage;
import com.steve.safetyAlerts.dto.FirePerson;
import com.steve.safetyAlerts.dto.Foyer;
import com.steve.safetyAlerts.model.FireStation;
import com.steve.safetyAlerts.service.IFireStationService;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FireStationController {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(PersonController.class);


    @Autowired
    private IFireStationService fireStationService;

    @PostMapping(path = "firestation")
    @ResponseStatus(HttpStatus.CREATED)
    public void createFireStation(@RequestBody @Valid FireStation firestation) {
        logger.info("createFireStation : appel du controller firestation");

        fireStationService.createFireStation(firestation);
    }


    @PutMapping(path = "firestation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateFireStation(@RequestBody @Valid FireStation firestation) {
        logger.info("updateFireStation : appel du controller firestation");
        fireStationService.updateFireStation(firestation);
    }

    @DeleteMapping(path = "firestation")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteFireStation(@RequestBody @Valid FireStation firestation) {
        logger.info("deleteFireStation : appel du controller firestation");
        fireStationService.deleteFireStation(firestation);
    }

    @GetMapping("firestation")
    public List<Coverage> getCoverageByFireStation(@RequestParam String stationNumber) {
        logger.info("getCoverageByFireStation : appel du controller firestation");
        return fireStationService.getCoverageByFireStation(stationNumber);
    }

    @GetMapping(path = "firestationlist")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getFirestation() {
        logger.info("getFirestation : appel du controller firestationlist");
        return fireStationService.getFirestation();
    }

    @GetMapping("fire")
    public List<FirePerson> getFire(@RequestParam String address) {
        logger.info("getFire : appel du controller fire");
        return fireStationService.getFire(address);
    }

    @GetMapping(path = "phoneAlert")
    public List<String> getPhoneAlert(@RequestParam String station) {
        logger.info("getPhoneAlert : appel du controller phoneAlert");
        return fireStationService.getPhoneAlert(station);
    }

    @GetMapping("flood/stations")
    public List<Foyer> getFlood(@RequestParam List<String> stationNumbers) {
        logger.info("getFlood : appel du controller flood/stations");
        return fireStationService.getFloodStation(stationNumbers);
    }

}
