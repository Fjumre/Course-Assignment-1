package app.dao;

import app.Person;
import app.dao.interface_dao.InterfaceAddressDAO;
import app.model.Address;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AddressDAO implements InterfaceAddressDAO {

    private static AddressDAO instance;
    private EntityManagerFactory emf;

    private AddressDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static AddressDAO getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            synchronized (AddressDAO.class) {
                if (instance == null) {
                    instance = new AddressDAO(emf);
                }
            }
        }
        return instance;
    }

    @Override
    public List<Person> findPersonsFromACity(String city) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery(
                            "SELECT p " +
                                    "FROM Person p " +
                                    "JOIN p.address a " +
                                    "JOIN a.zipCode z " +
                                    "WHERE z.cityName = :city", Person.class)
                    .setParameter("city", city)
                    .getResultList();
        }
    }


    @Override
    public List<Object[]> allZipCodesAndCities() {
        try (EntityManager em = emf.createEntityManager()) {
            List<Object[]> zipAndCityList = em.createQuery("SELECT z.cityName, z.zip " +
                            "FROM ZipCode z", Object[].class)
                    .getResultList();
            return zipAndCityList;
        }
    }

    @Override
    public Map<Person, Long> getPersonsWithHobbyCountByAddress(String address) {
        try (EntityManager em = emf.createEntityManager()) {
            List<Person> people = em.createQuery("SELECT p " +
                            "FROM Person p " +
                            "WHERE p.address = :address", Person.class)
                    .setParameter("address", address)
                    .getResultList();

            return people.stream()
                    .collect(Collectors.toMap
                            (person -> person,
                                    person -> (long) person
                                            .getHobbySet().size()));
        }
    }
}
