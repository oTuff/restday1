package facades;

import dtos.PersonDTO;
import entities.Person;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;

//import errorhandling.RenameMeNotFoundException;
import errorhandling.PersonNotFoundException;
import utils.EMF_Creator;

/**
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }


    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public PersonDTO create(PersonDTO personDTO) {
        Person person = new Person(personDTO.getName(), personDTO.getAge());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }

    public PersonDTO update(PersonDTO personDTO) {
        Person person = new Person(personDTO.getName(), personDTO.getAge());
        person.setId(personDTO.getId());
        EntityManager em = getEntityManager();
        Person fromDB = em.find(Person.class, personDTO.getId());
        if (fromDB == null) {
            throw new EntityNotFoundException("this person does not exist");
        }
        try {
            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }

    public PersonDTO getById(long id) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        Person rm = em.find(Person.class, id);
        if (rm == null)
            throw new PersonNotFoundException("The Person entity with ID: "+id+" Was not found");
        return new PersonDTO(rm);
    }

    //TODO Remove/Change this before use
    public long getRenameMeCount() {
        EntityManager em = getEntityManager();
        try {
            long renameMeCount = (long) em.createQuery("SELECT COUNT(r) FROM Person r").getSingleResult();
            return renameMeCount;
        } finally {
            em.close();
        }
    }

    public List<PersonDTO> getAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT r FROM Person r", Person.class);
        List<Person> rms = query.getResultList();
        return PersonDTO.getDtos(rms);
    }

    public void delete(long id) {
        EntityManager em = emf.createEntityManager();
        em.remove(id);
    }

    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade fe = getPersonFacade(emf);
//        fe.getAll().forEach(dto->System.out.println(dto));
//        PersonDTO personDTO = new PersonDTO("Oskar", 21);
//        personDTO.setId(2L);
//        fe.update(personDTO);
    }

}
