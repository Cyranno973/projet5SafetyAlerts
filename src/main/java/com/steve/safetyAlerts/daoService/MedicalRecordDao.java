package com.steve.safetyAlerts.daoService;

import com.steve.safetyAlerts.model.MedicalRecord;

public interface MedicalRecordDao {

    boolean createMedicalRecord(MedicalRecord medicalRecord);

    boolean updateMedicalRecord(MedicalRecord medicalRecord);

    boolean deleteMedicalRecord(MedicalRecord medicalRecord);
}
