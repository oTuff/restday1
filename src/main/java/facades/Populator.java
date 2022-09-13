/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PersonDTO;
import entities.Employee;
import entities.Person;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
//        PersonFacade fe = PersonFacade.getPersonFacade(emf);
//        fe.create(new PersonDTO(new Person("Oscar", 22)));
//        fe.create(new PersonDTO(new Person("First 2", 2)));
//        fe.create(new PersonDTO(new Person("First 3", 3)));
        EmployeeFacade ef = EmployeeFacade.getInstance(emf);
        ef.createEmployee(new Employee("Oscar","gade 2", 100));
    }
    
    public static void main(String[] args) {
        populate();
    }
}
