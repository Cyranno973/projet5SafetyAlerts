package com.steve.safetyAlerts.daoService;

import com.steve.safetyAlerts.model.Person;
import com.steve.safetyAlerts.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonDaoImpl implements PersonDao {
    @Autowired
    private DataRepository dataRepository;

    @Override
    public boolean createPerson(Person person) {
        // ajout de la nouvelle person en memoire java
        dataRepository.database.getPersons().add(person);
        // commit pour appliquer les changements dans le json
        dataRepository.commit();
        return true;
    }

    @Override
    public boolean updatePerson(Person person) {
        if (dataRepository.database.getPersons().remove(person)) {

            this.createPerson(person);
            return true;
        }
        return false;
    }

    @Override
    public boolean deletePerson(Person person) {
        // suppression de la person en memeoire
        boolean result = dataRepository.database.getPersons().remove(person);
        // commit pour appliquer les changements dans le json
        dataRepository.commit();
        return result;

    }
}
