package app.dao.interface_dao;

import app.Person;

import java.util.List;

public interface InterfaceHobbyDAO {



    List<Person> findAllPersonsByHobby(String hobbyName);

    Long countPersonsByHobby(String hobbyName);

    List<Object[]> listOfHobbiesWithParticipantsCount();
}
