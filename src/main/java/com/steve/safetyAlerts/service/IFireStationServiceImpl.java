package com.steve.safetyAlerts.service;

import com.steve.safetyAlerts.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IFireStationServiceImpl implements IFireStationService {

    @Autowired
    private DataRepository dataRepository;

}
