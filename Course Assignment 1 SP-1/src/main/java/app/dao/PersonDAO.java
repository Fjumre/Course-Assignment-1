package app.dao;

import app.*;

import app.dao.interface_dao.InterfacePersonDAO;
import app.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;


public class PersonDAO implements InterfacePersonDAO {

    private static PersonDAO instance;
    private EntityManagerFactory emf;

    private PersonDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static PersonDAO getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            synchronized (PersonDAO.class) {
                if (instance == null) {
                    instance = new PersonDAO(emf);
                }
            }
        }
        return instance;
    }

    @Override
    public void create(Person person) {
        try (EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();


            Hobby hobby = findHobbyById(em, 3);
            person.getHobbySet().add(hobby);
            em.persist(person);
            em.getTransaction().commit();
        }
    }

    @Override
    public Person read(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            Person person = em.find(Person.class, id);
            return person;
        }
    }

    @Override
    public Person update(Person person) {
        try (EntityManager em = emf.createEntityManager()) {


            em.getTransaction().begin();
            Person updatedPerson = em.merge(person);
            em.getTransaction().commit();
            return updatedPerson;
        }
    }

    @Override
    public void delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Person person = em.find(Person.class, id);
            if (person != null) {
                em.remove(person);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public void addHobbyToPerson(long personId, Hobby hobby) {
        try (EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();
            Person person = em.find(Person.class, personId);
            if (person != null) {
                person.getHobbySet().add(hobby);
                em.persist(person);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public void deleteHobbyFromPerson(long personId, Hobby hobby) {
        try (EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();
            Person person = em.find(Person.class, personId);
            if (person != null) {

                Hobby doneHobby = em.merge(hobby);
                person.getHobbySet().remove(doneHobby);
                em.persist(person);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public void updateAddressFromPerson(long personId, Address address) {
        try (EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();
            Person person = em.find(Person.class, personId);
            if (person != null) {
                Address currentAddress = person.getAddress();
                currentAddress.setAddress(address.getAddress());
                currentAddress.setZipCode(address.getZipCode());
            }
            em.getTransaction().commit();

        }
    }

    @Override
    public void updatePhoneNumberFromPerson(long personId, PhoneNumber phoneNumber) {
        try (EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();
            Person person = em.find(Person.class, personId);
            if (person != null) {
                PhoneNumber currentPhoneNumber = (PhoneNumber) person.getPhoneNumberSet();
                currentPhoneNumber.setPhoneNumber(phoneNumber.getPhoneNumber());
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public void updatePasswordFromPerson(long personId, Password password) {
        try (EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();
            Person person = em.find(Person.class, personId);
            if (person != null) {
                Password currentPassword = person.getPassword();
                currentPassword.setPassword(password.getPassword());
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public Person findAllInfo(long id) {
        try (EntityManager em = emf.createEntityManager()) {

            Person person = em.createQuery("SELECT p FROM Person p " +
                            "LEFT JOIN FETCH p.hobbySet " +
                            "LEFT JOIN FETCH p.address a " +
                            "LEFT JOIN FETCH a.zipCode " +
                            "LEFT JOIN FETCH p.phoneNumberSet " +
                            "WHERE p.id = :id", Person.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return person;
        }
    }

    @Override
    public List<PhoneNumber> findAllPhoneNumbersForPerson(String firstName, String lastName) {
        try (EntityManager em = emf.createEntityManager()) {
            List<Person> foundPerson = em.createQuery(
                            "SELECT p.phoneNumberSet FROM Person p " +
                                    "WHERE p.firstName = :firstname " +
                                    "AND p.lastName = :lastname", Person.class)
                    .setParameter("firstname", firstName)
                    .setParameter("lastname", lastName)
                    .getResultList();

            // Extracting the phone numbers from the fetched person
            List<PhoneNumber> phoneNumbers = new ArrayList<>();
            for (Person person : foundPerson) {
                phoneNumbers.addAll(person.getPhoneNumberSet());
            }
            return phoneNumbers;
        }
    }

    @Override
    public Person findAllInfoByPhoneNumber(int PhoneNumber) {
        try (EntityManager em = emf.createEntityManager()) {

            return em.createQuery("SELECT p FROM Person p " +
                            "LEFT JOIN FETCH p.hobbySet " +
                            "LEFT JOIN FETCH p.address a " +
                            "LEFT JOIN FETCH a.zipCode " +
                            "JOIN p.phoneNumberSet ph " +
                            "WHERE ph.PhoneNumber = :phoneNumber", Person.class)
                    .setParameter("phoneNumber", PhoneNumber)
                    .getSingleResult();
        }
    }


    public ZipCode findZipCodeByZip(EntityManager em, int zip) {
        return em.find(ZipCode.class, zip);
    }

    public Hobby findHobbyById(EntityManager em, long id){
        return em.find(Hobby.class, id);
    }
}