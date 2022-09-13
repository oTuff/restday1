/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Person;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tha
 */
public class PersonDTO {
    private long id;
    private String name;
    private int age;

    public PersonDTO(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static List<PersonDTO> getDtos(List<Person> persons){
        List<PersonDTO> personlist = new ArrayList();
        persons.forEach(person->personlist.add(new PersonDTO(person)));
        return personlist;
    }


    public PersonDTO(Person person) {
        if(person.getId() != null)
            this.id = person.getId();
        this.name = person.getName();
        this.age = person.getAge();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setDummyStr1(String dummyStr1) {
        this.name = dummyStr1;
    }

    public int getAge() {
        return age;
    }

    public void setDummyStr2(int dummyStr2) {
        this.age = dummyStr2;
    }

    @Override
    public String toString() {
        return "RenameMeDTO{" + "id=" + id + ", str1=" + name + ", str2=" + age + '}';
    }
}
