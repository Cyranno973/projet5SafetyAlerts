package com.steve.safetyAlerts.dao;

import com.steve.safetyAlerts.model.Person;

public interface PersonDao {
    boolean createPerson(Person person);
    boolean updatePerson(Person person);
    boolean deletePerson(Person person);
}
