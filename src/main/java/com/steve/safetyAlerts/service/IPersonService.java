package com.steve.safetyAlerts.service;

import com.steve.safetyAlerts.dto.*;

import java.util.List;
import java.util.Set;

public interface IPersonService {

    Set<String> getCommunityEmail(String city);

    List<String> getPhoneAlert(String station);

    List<ChildInfo> getChildAlert(String address);

    List<PersonInfo> getPersonInfo(String firstName, String lastName);

    List<FirePerson> getFire(String address);

    List<Foyer> getFloodStation(List<String> stationNumbers);

    Coverage fireStation(String stationNumber);
}
