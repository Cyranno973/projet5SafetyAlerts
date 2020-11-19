package com.steve.safetyAlerts.service;

import com.steve.safetyAlerts.dto.ChildInfo;
import com.steve.safetyAlerts.dto.FirePerson;
import com.steve.safetyAlerts.dto.PersonInfo;

import java.util.List;
import java.util.Set;

public interface IPersonService {

    Set<String> getCommunityEmail(String city);

    List<String> getPhoneAlert(String station);

    List<ChildInfo> getChildAlert(String address);

    PersonInfo getPersonInfo(String firstName, String lastName);

    List<FirePerson> getFire(String address);

    List<FirePerson> getFloodStation(List<String> stationNumber);
}
