package org.swiss;

public class Employee {
    Integer id;
    String firstName;
    String lastName;
    double salary;
    Integer managerId;

    public Employee(Integer id, String firstName, String lastName, double salary, Integer managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
    }
}
