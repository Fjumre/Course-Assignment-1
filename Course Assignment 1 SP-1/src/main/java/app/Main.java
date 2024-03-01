package app;

import app.config.HibernateConfig;
import app.dao.PersonDAO;
import app.model.Address;
import app.model.Password;
import app.model.PhoneNumber;
import app.model.ZipCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;

public class Main {

    public static PersonDAO personDAO;



    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();
        personDAO = PersonDAO.getInstance(emf);

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            LocalDate birthday = LocalDate.of(1974, 1, 11);
            Person person = new Person("Jens", "Hansen", birthday, "JensHansen@email.com", Person.Gender.MALE);

            ZipCode zipCode = personDAO.findZipCodeByZip(em, 7900);
            Address address = new Address("Gaden 72");
            address.addZipCode(zipCode);
            person.addAddress(address);

            PhoneNumber phoneNumber = new PhoneNumber(9417);
            person.addPhoneNumber(phoneNumber);
            Password password = new Password("Jens1234");
            person.addPassword(password);
            //Hobby hobby = new Hobby("3D-udskrivning", "https://en.wikipedia.org/wiki/3D_printing", "Generel", "Indendørs");

//            Hobby hobby = personDAO.findHobbyById(em, 1);
//            person.addHobby(hobby);
//
//
          personDAO.create(person);
//
//
//            em.persist(person);
//            em.getTransaction().commit();
        }
    }
}

//        List<Object[]> hobbiesWithParticipantinsList = personDAO.listOfHobbiesWithParticipantsCount();
//        for (Object[] hobbiesAndParticipants : hobbiesWithParticipantinsList) {
//            Hobby hobby = (Hobby) hobbiesAndParticipants[0];
//            int count = (int) hobbiesAndParticipants[1];
//            System.out.println("Hobby: " + hobby + ", Participants: " + count);
//        }
//
//
//        List<Object[]> zipCodesAndCities = personDAO.allZipCodesAndCities();
//        for (Object[] zipCodeAndCity : zipCodesAndCities) {
//            String cityName = (String) zipCodeAndCity[0];
//            int zip = (Integer) zipCodeAndCity[1];
//            System.out.println("City Name: " + cityName + ", Zip Code: " + zip);
//        }
//

