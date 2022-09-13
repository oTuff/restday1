package facades;
import entities.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeFacadeTest {

    private static EntityManagerFactory emf;
    private static EmployeeFacade facade;

    @BeforeAll
    static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = EmployeeFacade.getInstance(emf);
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
//            em.createNamedQuery("employee.deleteAllRows").executeUpdate();
//            em.getTransaction().commit();
//
//            em.getTransaction().begin();
            em.persist(new Employee("Oscar", "hovedgade 1", 100));
            em.persist(new Employee("Alexander", "hovedgade 1", 90));

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    @AfterEach
    public void tearDown(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNamedQuery("employee.deleteAllRows").executeUpdate();
        em.getTransaction().commit();
        em.close();

    }

    @Test
    void createEmployee() {
        for(Employee e: facade.getAllEmployees()){
            System.out.println(e);
        }
        facade.createEmployee(new Employee("William","Lynbyvej 19", 75));
        int actual = facade.getAllEmployees().size();
        int expected = 3;
    }

    @Test
    void getEmployeeById() {
        Employee employee = new Employee("Oscar", "Gade 1",22);
        facade.createEmployee(employee);
        long id = employee.getId();
        Employee actual = facade.getEmployeeById(id);
//        for(Employee e: facade.getAllEmployees()){
//            System.out.println(e);
//        }
        Employee expected = employee;
        assertEquals(actual,expected);
    }

    @Test
    void getEmployeesByName() {
//        for(Employee e: facade.getAllEmployees()){
//            System.out.println(e);
//        }
        String actual = facade.getEmployeesByName("Oscar").get(0).getName();
        String expected = "Oscar";
        assertEquals(actual,expected);
    }

    @Test
    void getAllEmployees() {
//        for(Employee e: facade.getAllEmployees()){
//            System.out.println(e);
//        }
        int actual = facade.getAllEmployees().size();
        int expected = 2;
        assertEquals(actual,expected);
    }

    @Test
    void getEmployeesWithHighestSalary() {
//        for(Employee e: facade.getAllEmployees()){
//            System.out.println(e);
//        }
        int actual = facade.getEmployeesWithHighestSalary().get(0).getSalary();
        int expected = 100;
        assertEquals(actual,expected);
    }
}