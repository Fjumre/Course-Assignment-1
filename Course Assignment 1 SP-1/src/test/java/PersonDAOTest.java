import app.*;
import app.config.HibernateConfig;
import app.dao.AddressDAO;
import app.dao.HobbyDAO;
import app.dao.PersonDAO;
import app.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static junit.framework.TestCase.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersonDAOTest {


    private static EntityManagerFactory emfTest;
    private static PersonDAO personDAO;
    private static HobbyDAO hobbyDAO;
    private static AddressDAO addressDAO;

    @BeforeEach
    void eachSetUp(){
        try (EntityManager em = emfTest.createEntityManager()) {
            em.getTransaction().begin();

            LocalDate birthday = LocalDate.of(1962, 3, 23);
            Person person = new Person("Kurt", "Vestergaard", birthday, "KurtVestergaard@email.com", Person.Gender.MALE);

            ZipCode zipCode = personDAO.findZipCodeByZip(em, 7950);
            Address address = new Address("Hovedgaden 72");
            address.addZipCode(zipCode);

            PhoneNumber phoneNumber = new PhoneNumber(53332556);
            Password password = new Password("yesman");
            Hobby hobby = new Hobby("3D-udskrivning", "https://en.wikipedia.org/wiki/3D_printing", "Generel", "Indendørs");


            person.addAddress(address);
            person.addPassword(password);
            person.addPhoneNumber(phoneNumber);
            person.addHobby(hobby);


            em.persist(person);
            em.getTransaction().commit();
        }
    }

    @AfterEach
    void eachTearDown(){
            try (EntityManager em = emfTest.createEntityManager()) {
                em.getTransaction().begin();

                 List<Person> persons = em.createQuery("SELECT p FROM Person p", Person.class).getResultList();
                for (Person person : persons) {
                     Person managedPerson = em.merge(person);
                    em.remove(managedPerson);
                }


                em.getTransaction().commit();
            }
        }


    @BeforeAll
    static void setUp() {
        emfTest = HibernateConfig.getEntityManagerFactoryConfigTest();
        personDAO = PersonDAO.getInstance(emfTest);
    }

    @AfterAll
    static void tearDown() {
        if (emfTest != null) {
            emfTest.close();
        }
    }

    @Test
    void testCreate() {
        try (EntityManager em = emfTest.createEntityManager()) {
            LocalDate birthday = LocalDate.of(1974, 1, 11);
            Person person = new Person("Jens", "Hansen", birthday, "JensHansen@email.com", Person.Gender.MALE);

            ZipCode zipCode = personDAO.findZipCodeByZip(em, 7900);
            Address address = new Address("Gaden 72");
            address.addZipCode(zipCode);

            PhoneNumber phoneNumber = new PhoneNumber(52344556);
            Password password = new Password("Jens1234");
            Hobby hobby = new Hobby("3D-udskrivning", "https://en.wikipedia.org/wiki/3D_printing", "Generel", "Indendørs");


            person.addAddress(address);
            person.addPassword(password);
            person.addPhoneNumber(phoneNumber);
            person.addHobby(hobby);

            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();

            assertNotNull(person);


        }
    }

    @Test
    void testRead() {
        try (EntityManager em = emfTest.createEntityManager()) {

            LocalDate birthday = LocalDate.of(1974, 1, 11);
            Person person = new Person("Jens", "Hansen", birthday, "JensHansen@email.com", Person.Gender.MALE);

            ZipCode zipCode = personDAO.findZipCodeByZip(em, 7900);
            Address address = new Address("Gaden 72");
            address.addZipCode(zipCode);

            PhoneNumber phoneNumber = new PhoneNumber(52344556);
            Password password = new Password("Jens1234");
            Hobby hobby = new Hobby("3D-udskrivning", "https://en.wikipedia.org/wiki/3D_printing", "Generel", "Indendørs");


            Person findPerson = em.find(Person.class, person.getId());
            System.out.println(findPerson);
        }
    }

    @Test
    void testUpdate() {
        try (EntityManager em = emfTest.createEntityManager()) {
            LocalDate birthday = LocalDate.of(1974, 1, 11);
            Person person = new Person("Jens", "Hansen", birthday, "JensHansen@email.com", Person.Gender.MALE);

            ZipCode zipCode = personDAO.findZipCodeByZip(em, 7900);
            Address address = new Address("Gaden 72");
            address.addZipCode(zipCode);

            PhoneNumber phoneNumber = new PhoneNumber(52344556);
            Password password = new Password("Jens1234");
            Hobby hobby = new Hobby("3D-udskrivning", "https://en.wikipedia.org/wiki/3D_printing", "Generel", "Indendørs");

            person.addAddress(address);
            person.addPassword(password);
            person.addPhoneNumber(phoneNumber);
            person.addHobby(hobby);

            Hobby hobby2 = new Hobby("Computer programmering", "https://en.wikipedia.org/wiki/Computer_programming", "Generel", "Indendørs");

            person.addHobby(hobby2);

            personDAO.update(person);
        }
    }

    @Test
    void testDelete() {
        try (EntityManager em = emfTest.createEntityManager()) {
            LocalDate birthday = LocalDate.of(1974, 1, 11);
            Person person = new Person("Jens", "Hansen", birthday, "JensHansen@email.com", Person.Gender.MALE);

            ZipCode zipCode = personDAO.findZipCodeByZip(em, 7900);
            Address address = new Address("Gaden 72");
            address.addZipCode(zipCode);

            PhoneNumber phoneNumber = new PhoneNumber(52344556);
            Password password = new Password("Jens1234");
            Hobby hobby = new Hobby("3D-udskrivning", "https://en.wikipedia.org/wiki/3D_printing", "Generel", "Indendørs");

            person.addAddress(address);
            person.addPassword(password);
            person.addPhoneNumber(phoneNumber);
            person.addHobby(hobby);

            personDAO.delete((int) person.getId());

            Person deletedPerson= em.find(Person.class, person.getId());

            assertNull(deletedPerson);
        }
    }


    @Test
    void testAddHobbyToPerson(){
        try (EntityManager em = emfTest.createEntityManager()) {
            LocalDate birthday = LocalDate.of(1974, 1, 11);
            Person person = new Person("Jens", "Hansen", birthday, "JensHansen@email.com", Person.Gender.MALE);

            ZipCode zipCode = personDAO.findZipCodeByZip(em, 7900);
            Address address = new Address("Gaden 72");
            address.addZipCode(zipCode);

            PhoneNumber phoneNumber = new PhoneNumber(52344556);
            Password password = new Password("Jens1234");
            Hobby hobby = new Hobby("3D-udskrivning", "https://en.wikipedia.org/wiki/3D_printing", "Generel", "Indendørs");

            person.addAddress(address);
            person.addPassword(password);
            person.addPhoneNumber(phoneNumber);
            person.addHobby(hobby);

            em.persist(person);
            em.getTransaction().commit();

            Hobby hobby2 = new Hobby("Computer programmering", "https://en.wikipedia.org/wiki/Computer_programming", "Generel", "Indendørs");

            em.persist(hobby2);
            personDAO.addHobbyToPerson(person.getId(), hobby2);
            em.getTransaction().commit();

            Person updatedPerson = em.find(Person.class, person.getId());
            assertTrue(updatedPerson.getHobbySet().contains(hobby2), "Hobby should be added to the person");


        }
    }

    @Test
    void testDeleteHobbyFromPerson(){
        try (EntityManager em = emfTest.createEntityManager()) {

            em.getTransaction().begin();

            long personId=1;
            long hobbyId=1;

            Person findPerson= em.find(Person.class, personId);
            Hobby deletehobby = em.find(Hobby.class, hobbyId);


           // findPerson.removeHobby(deletehobby);
            personDAO.deleteHobbyFromPerson(findPerson.getId(), deletehobby);
            em.merge(findPerson);
            em.getTransaction().commit();

            Person updatedPerson = em.find(Person.class, personId);
            assertFalse(updatedPerson.getHobbySet().contains(deletehobby));
        }

    }

    @Test
    void testFindAllInfo() {

            long personId=1;

            personDAO.findAllInfo(personId);

    }

    @Test
    void testFindAllPhoneNumbersForPerson() {
        String firstName = "Kurt";
        String lastName = "Vestergaard";

        List<PhoneNumber> phoneNumbers = personDAO.findAllPhoneNumbersForPerson(firstName, lastName);

        boolean foundPhoneNumber = phoneNumbers.stream()
                .anyMatch(phoneNumber -> phoneNumber.getPhoneNumber() == 53332556);

        assertTrue(foundPhoneNumber);
    }

    @Test
    void testFindAllPersonsByHobby(){

        String hobbyName= "3D-udskrivning";

        List<Person> personList= hobbyDAO.findAllPersonsByHobby(hobbyName);

        boolean foundPersons = personList.stream()
                .anyMatch(person-> "Kurt".equals(person.getFirstName()));

        assertTrue(foundPersons);
    }

    @Test
    void testCountPersonsByHobby(){

        String hobbyName= "3D-udskrivning";

        Long count = hobbyDAO.countPersonsByHobby(hobbyName);

        assertTrue(count>0);
    }

    @Test
    void testListOfHobbiesWithParticipantsCount(){
        List<Object[]> hobbiesWithParticipantinsList = hobbyDAO.listOfHobbiesWithParticipantsCount();
        for (Object[] hobbiesAndParticipants : hobbiesWithParticipantinsList) {
            Hobby hobby = (Hobby) hobbiesAndParticipants[0];
            Long count = (Long) hobbiesAndParticipants[1];
            System.out.println("Hobby: " + hobby + ", Participants: " + count);
        }
    }


    @Test
    void testFindPersonsFromACity(){

        String city="Erslev";

        List<Person> personsFromCity= addressDAO.findPersonsFromACity(city);

        boolean foundPersonsFromCity = personsFromCity.stream()
                .anyMatch(person-> "Kurt".equals(person.getFirstName()));

        assertTrue(foundPersonsFromCity);

    }

    @Test
    void testAllZipCodesAndCities(){

    }

    @Test
    void testGetPersonsWithHobbyCountByAddress(){

        String address= "Hovedgaden 72";

        List<Person> foundPersons= (List<String>) addressDAO.getPersonsWithHobbyCountByAddress(address);
    }

    @Test
    void testFindAllInfoByPhoneNumber(){

        int phoneNumber= 53332556;

        Person foundInfo= personDAO.findAllInfoByPhoneNumber(phoneNumber);

        assertEquals("Kurt", foundInfo.getFirstName());
    }


}