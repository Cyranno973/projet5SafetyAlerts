package com.steve.safetyAlerts.daoService;

import com.steve.safetyAlerts.model.Person;
import com.steve.safetyAlerts.repository.DataRepository;
import com.steve.safetyAlerts.service.PersonServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonDaoImpl implements PersonDao {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(PersonDaoImpl.class);

    @Autowired
    private DataRepository dataRepository;

    @Override
    public boolean createPerson(Person person) {
        // ajout de la nouvelle person en memoire java
        dataRepository.getAllPersons().add(person);
        logger.info("createPerson : person ajouter a la liste des persons");
        // commit pour appliquer les changements dans le json
        dataRepository.commit();
        logger.info("createperson: commit de la person creer");
        return true;
    }

    @Override
    public boolean updatePerson(Person person) {
        if (dataRepository.getAllPersons().remove(person)) {
            this.createPerson(person);
            return true;
        }
        return false;
    }

    @Override
    public boolean deletePerson(Person person) {
        // suppression de la person en memeoire
        boolean result = dataRepository.getAllPersons().remove(person);
        // commit pour appliquer les changements dans le json
        dataRepository.commit();
        return result;

    }
}
