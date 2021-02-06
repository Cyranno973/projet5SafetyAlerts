package com.steve.safetyAlerts.dao;

import com.steve.safetyAlerts.model.MedicalRecord;
import com.steve.safetyAlerts.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordDaoImpl implements MedicalRecordDao {

    @Autowired
    private DataRepository dataRepository;

    @Override
    public boolean createMedicalRecord(MedicalRecord medicalRecord) {
        dataRepository.database.getMedicalrecords().add(medicalRecord);
        dataRepository.commit();
        return true;
    }

    @Override
    public boolean updateMedicalRecord(MedicalRecord medicalRecord) {
        for (MedicalRecord mr : dataRepository.database.getMedicalrecords()) {
            if (mr.getFirstName().equalsIgnoreCase(medicalRecord.getFirstName()) && (mr.getLastName().equalsIgnoreCase(medicalRecord.getLastName()))) {
                boolean result = deleteMedicalRecord(mr);
                if (result) {
                    result = createMedicalRecord(medicalRecord);
                    dataRepository.commit();
                    return result;
                }
            }
        }
        return false;
    }

    @Override
    public boolean deleteMedicalRecord(MedicalRecord medicalRecord) {
        boolean result = dataRepository.database.getMedicalrecords().remove(medicalRecord);
        dataRepository.commit();
        return result;
    }
}
