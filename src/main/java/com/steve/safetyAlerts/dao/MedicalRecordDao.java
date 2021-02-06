package com.steve.safetyAlerts.dao;

import com.steve.safetyAlerts.model.MedicalRecord;

public interface MedicalRecordDao {

    boolean createMedicalRecord(MedicalRecord medicalRecord);
    boolean updateMedicalRecord(MedicalRecord medicalRecord);
    boolean deleteMedicalRecord(MedicalRecord medicalRecord);
}
