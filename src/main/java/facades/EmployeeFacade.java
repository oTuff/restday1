package facades;

import entities.Employee;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class EmployeeFacade {

    private static EmployeeFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private EmployeeFacade() {
    }


    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static EmployeeFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new EmployeeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Employee getEmployeeById(long id) {
        EntityManager em = emf.createEntityManager();
        Employee employee = em.find(Employee.class, id);
//        if (rm == null)
//            throw new RenameMeNotFoundException("The Person entity with ID: "+id+" Was not found");
        return employee;
    }

    public List<Employee> getEmployeesByName(String name) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e where e.name = :name", Employee.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

     public List<Employee> getAllEmployees() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e", Employee.class);
        return query.getResultList();
    }

    public List<Employee> getEmployeesWithHighestSalary() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Employee> query = em.createQuery("select e from Employee e where e.salary = (select max(e2.salary) from Employee e2)", Employee.class);
        return query.getResultList();
    }

    public Employee createEmployee(Employee employee) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
            return employee;
        } finally {
            em.close();
        }
    }

//    public static void main(String[] args) {
//        emf = EMF_Creator.createEntityManagerFactory();
//        PersonFacade fe = getPersonFacade(emf);
//        fe.getAll().forEach(dto->System.out.println(dto));
//    }

}
