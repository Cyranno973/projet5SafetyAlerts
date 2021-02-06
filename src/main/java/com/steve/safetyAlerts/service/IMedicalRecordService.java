package com.steve.safetyAlerts.service;

import com.steve.safetyAlerts.model.MedicalRecord;

public interface IMedicalRecordService {
    void createMedicalRecord(MedicalRecord medicalRecord);
    void updateMedicalRecord(MedicalRecord medicalRecord);
    void deleteMedicalRecord(MedicalRecord medicalRecord);
}
