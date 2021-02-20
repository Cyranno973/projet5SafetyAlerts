package com.steve.safetyAlerts.controller;

import com.steve.safetyAlerts.model.MedicalRecord;
import com.steve.safetyAlerts.service.IMedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MedicalRecordController {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(PersonController.class);


    @Autowired
    private IMedicalRecordService medicalRecordService;

    @PostMapping(path = "medicalRecord")
    @ResponseStatus(HttpStatus.CREATED)
    public void createFireStation(@RequestBody @Valid MedicalRecord medicalRecord) {
        logger.info("createFireStation : appel du controller medicalRecord");
        medicalRecordService.createMedicalRecord(medicalRecord);
    }


    @PutMapping(path = "medicalRecord")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMedicalRecord(@RequestBody @Valid MedicalRecord medicalRecord) {
        logger.info("updateMedicalRecord : appel du controller medicalRecord");
        medicalRecordService.updateMedicalRecord(medicalRecord);
    }

    @DeleteMapping(path = "medicalRecord")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteMedicalRecord(@RequestBody @Valid MedicalRecord firestation) {
        logger.info("deleteMedicalRecord : appel du controller medicalRecord");
        medicalRecordService.deleteMedicalRecord(firestation);
    }

    @GetMapping(path = "medicalRecord")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getmedicalRecord() {
        List<String> medicalrecord = medicalRecordService.getMedicalrecord();
        logger.info("getmedicalRecord : appel du controller medicalRecord");
        return medicalrecord;
    }
}
