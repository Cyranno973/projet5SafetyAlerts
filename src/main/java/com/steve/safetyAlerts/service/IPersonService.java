package com.steve.safetyAlerts.service;

import com.steve.safetyAlerts.dto.*;
import com.steve.safetyAlerts.model.Person;

import java.util.List;
import java.util.Set;

public interface IPersonService {

    Set<String> getCommunityEmail(String city);

    List<ChildInfo> getChildAlert(String address);

    List<PersonInfo> getPersonInfo(String firstName, String lastName);

    boolean createPerson(Person person);

    boolean updatePerson(Person person);

    boolean deletePerson(Person person);

}
