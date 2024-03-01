package app.dao;

import app.Person;
import app.dao.interface_dao.InterfaceHobbyDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class HobbyDAO implements InterfaceHobbyDAO {



    private static HobbyDAO instance;
    private EntityManagerFactory emf;

    private HobbyDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static HobbyDAO getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            synchronized (PersonDAO.class) {
                if (instance == null) {
                    instance = new HobbyDAO(emf);
                }
            }
        }
        return instance;
    }

    @Override
    public List<Person> findAllPersonsByHobby(String hobbyName) {
        try (EntityManager em = emf.createEntityManager()) {
            List<Person> persons = em.createQuery("SELECT p FROM Person p " +
                            "JOIN p.hobbySet h " +
                            "WHERE h.Name = :hobbyName", Person.class)
                    .setParameter("hobbyName", hobbyName)
                    .getResultList();
            return persons;
        }
    }

    @Override
    public Long countPersonsByHobby(String hobbyName) {
        try (EntityManager em = emf.createEntityManager()) {
            Long count = em.createQuery("SELECT COUNT(p) " +
                            "FROM Person p " +
                            "JOIN p.hobbySet h " +
                            "WHERE h.Name = :hobbyName", Long.class)
                    .setParameter("hobbyName", hobbyName)
                    .getSingleResult();
            return count;
        }
    }

    @Override
    public List<Object[]> listOfHobbiesWithParticipantsCount() {
        try (EntityManager em = emf.createEntityManager()) {
            List<Object[]> hobbiesWithParticipantsList = em.createQuery(
                            "SELECT h, COUNT(DISTINCT p.id) " +
                                    "FROM Hobby h " +
                                    "JOIN h.person p " +
                                    "JOIN FETCH h.person GROUP BY h")
                    .getResultList();
            return hobbiesWithParticipantsList;
        }
    }


}
