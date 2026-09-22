package com.training.codingstandards;

import java.util.Date;
import java.util.Objects;

/** Employee record loaded from CSV. */
public class Employee {

    String empId;
    String name;
    String email;
    String department;
    double salary;
    int yearsOfService;
    String country;
    String managerEmail;
    Date lastProcessed;

    public Employee() {
    }

    public Employee(String empId, String name, String email, String department, double salary,
                    int yearsOfService, String country, String managerEmail) {
        this.empId = empId;
        this.name = name;
        this.email = email;
        this.department = department;
        this.salary = salary;
        this.yearsOfService = yearsOfService;
        this.country = country;
        this.managerEmail = managerEmail;
        this.lastProcessed = new Date();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) obj;
        return Objects.equals(empId, other.empId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empId);
    }
}
