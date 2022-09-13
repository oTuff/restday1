package dtos;

import entities.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDTO {
    private long id;
    private String name;
    private String address;

    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.address = employee.getAddress();
    }

    public static List<EmployeeDTO> getDtos(List<Employee> employeeList) {
        List<EmployeeDTO> employeeDTOList = new ArrayList();
        employeeList.forEach(employee -> employeeDTOList.add(new EmployeeDTO(employee)));
        return employeeDTOList;
    }

        public long getId () {
            return id;
        }

        public void setId ( long id){
            this.id = id;
        }

        public String getName () {
            return name;
        }

        public void setName (String name){
            this.name = name;
        }

        public String getAddress () {
            return address;
        }

        public void setAddress (String address){
            this.address = address;
        }
    }
