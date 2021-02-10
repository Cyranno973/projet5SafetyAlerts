package com.steve.safetyAlerts.service;

import com.steve.safetyAlerts.model.MedicalRecord;

import java.util.List;

public interface IMedicalRecordService {
    void createMedicalRecord(MedicalRecord medicalRecord);

    void updateMedicalRecord(MedicalRecord medicalRecord);

    void deleteMedicalRecord(MedicalRecord medicalRecord);

    List<String> getMedicalrecord();
}
