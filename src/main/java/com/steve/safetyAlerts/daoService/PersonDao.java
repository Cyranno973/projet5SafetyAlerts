package com.steve.safetyAlerts.daoService;

import com.steve.safetyAlerts.model.Person;

public interface PersonDao {

    boolean createPerson(Person person);

    boolean updatePerson(Person person);

    boolean deletePerson(Person person);
}
