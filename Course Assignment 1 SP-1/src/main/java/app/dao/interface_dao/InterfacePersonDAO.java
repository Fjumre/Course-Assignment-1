package app.dao.interface_dao;

import app.Person;
import app.model.Address;
import app.model.Hobby;
import app.model.Password;
import app.model.PhoneNumber;

import java.util.List;
import java.util.Map;

public interface InterfacePersonDAO {


    void create(Person person);

    Person read(int id);

    Person update(Person person);

    void delete(int id);

    void addHobbyToPerson(long personId, Hobby hobby);

    void deleteHobbyFromPerson(long personId, Hobby hobby);

    void updateAddressFromPerson(long personId, Address address);

    void updatePhoneNumberFromPerson(long personId, PhoneNumber phoneNumber);

    void updatePasswordFromPerson(long personId, Password password);

    Person findAllInfo(long id);

    List<PhoneNumber> findAllPhoneNumbersForPerson(String firstName, String lastName);

    Person findAllInfoByPhoneNumber(int PhoneNumber);


}
