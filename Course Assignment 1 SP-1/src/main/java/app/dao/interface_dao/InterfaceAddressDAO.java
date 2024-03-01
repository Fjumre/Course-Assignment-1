package app.dao.interface_dao;

import app.Person;
import app.model.Address;

import java.util.List;
import java.util.Map;

public interface InterfaceAddressDAO {


    List<Person> findPersonsFromACity(String city);

    List<Object[]> allZipCodesAndCities();

    Map<Person, Long> getPersonsWithHobbyCountByAddress(String address);
}
