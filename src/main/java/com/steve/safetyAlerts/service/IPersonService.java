package com.steve.safetyAlerts.service;

import com.steve.safetyAlerts.dto.ChildInfo;

import java.util.Collection;

public interface IPersonService {

    Collection<String> getCommunityEmail(String city);

    Collection<String> getPhoneAlert1();
    Collection<String> getPhoneAlert(String station);

    Collection<ChildInfo> getChildAlert(String address);
}
