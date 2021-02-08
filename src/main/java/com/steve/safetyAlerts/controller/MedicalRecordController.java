package com.steve.safetyAlerts.controller;

import com.steve.safetyAlerts.model.MedicalRecord;
import com.steve.safetyAlerts.service.IMedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MedicalRecordController {

    @Autowired
    private IMedicalRecordService medicalRecordService;

    @PostMapping(path = "medicalRecord")
    @ResponseStatus(HttpStatus.CREATED)
    public void createFireStation(@RequestBody @Valid MedicalRecord medicalRecord) {
        medicalRecordService.createMedicalRecord(medicalRecord);
    }


    @PutMapping(path = "medicalRecord")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMedicalRecord(@RequestBody @Valid MedicalRecord medicalRecord) {
        medicalRecordService.updateMedicalRecord(medicalRecord);
    }

    @DeleteMapping(path = "medicalRecord")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteMedicalRecord(@RequestBody @Valid MedicalRecord firestation) {
        medicalRecordService.deleteMedicalRecord(firestation);
    }

//    @GetMapping(path = "medicalRecord")
//    @ResponseStatus(HttpStatus.OK)
//    public List<String> getmedicalRecord() { List<String> medicalrecord = medicalrecordService.getMedicalrecord(); return medicalrecord; }
}
