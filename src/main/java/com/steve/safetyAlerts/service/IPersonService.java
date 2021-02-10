package com.steve.safetyAlerts.service;

import com.steve.safetyAlerts.dto.ChildInfo;
import com.steve.safetyAlerts.dto.PersonInfo;
import com.steve.safetyAlerts.model.Person;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IPersonService {

    Collection<String> getCommunityEmail(String city);

    List<ChildInfo> getChildAlert(String address);

    List<PersonInfo> getPersonInfo(String firstName, String lastName);

    boolean createPerson(Person person);

    boolean updatePerson(Person person);

    boolean deletePerson(Person person);

}
