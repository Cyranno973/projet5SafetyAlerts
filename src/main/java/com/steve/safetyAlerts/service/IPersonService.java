package com.steve.safetyAlerts.service;

import com.steve.safetyAlerts.dto.ChildInfo;
import com.steve.safetyAlerts.dto.PersonInfo;

import java.util.Collection;

public interface IPersonService {

    Collection<String> getCommunityEmail(String city);

    Collection<String> getPhoneAlert(String station);

    Collection<ChildInfo> getChildAlert(String address);

    PersonInfo getPersonInfo(String firstName, String lastName);
}
