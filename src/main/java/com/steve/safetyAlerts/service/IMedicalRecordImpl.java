package com.steve.safetyAlerts.service;

import com.steve.safetyAlerts.dao.IMedicalRecordDao;
import com.steve.safetyAlerts.exception.DataAlreadyExistException;
import com.steve.safetyAlerts.exception.DataNotFoundException;
import com.steve.safetyAlerts.model.MedicalRecord;
import com.steve.safetyAlerts.model.Person;
import com.steve.safetyAlerts.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IMedicalRecordImpl implements IMedicalRecord {
    @Autowired
    private DataRepository dataRepository;
    @Autowired
    private IMedicalRecordDao medicalRecordDao;

    @Override
    public void createMedicalRecord(MedicalRecord medicalRecord) {
        Person person = dataRepository.getPersonByName(medicalRecord.getFirstName(),medicalRecord.getLastName());
        if (!dataRepository.database.getMedicalrecords().contains(medicalRecord) && (person != null)){

            medicalRecordDao.createMedicalRecord(medicalRecord);
        }else {
            throw new DataAlreadyExistException("La personne n'existe pas ou le medicalRecord existe ");
        }
    }

    @Override
    public void updateMedicalRecord(MedicalRecord medicalRecord) {
        if (!medicalRecordDao.updateMedicalRecord(medicalRecord)){
            throw new DataNotFoundException("La personne "+medicalRecord.getLastName()+" "+medicalRecord.getFirstName()+" n'existe pas ");
        }
    }

    @Override
    public void deleteMedicalRecord(MedicalRecord medicalRecord) {
        if (!medicalRecordDao.deleteMedicalRecord(medicalRecord)){
            throw new DataNotFoundException("La personne "+medicalRecord.getLastName()+" "+medicalRecord.getFirstName()+" n'a pas de dossier medical ");
        }
    }
}
