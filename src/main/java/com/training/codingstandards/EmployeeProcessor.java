package com.training.codingstandards;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeeProcessor {

    public List<PayrollRow> process(List<Employee> employees) {
        List<PayrollRow> rows = new ArrayList<>();
        if (employees == null) {
            return rows;
        }

        for (Employee employee : employees) {
            PayrollRow row = new PayrollRow();
            row.empId = employee.empId;
            row.name = employee.name;
            row.department = employee.department;
            row.email = employee.email;
            row.baseSalary = employee.salary;
            row.hashedId = SecurityUtil.hashIdentifier(employee.empId + employee.email);

            row.bonus = calculateBonus(employee);
            row.tax = calculateTax(employee.salary, employee.country);
            row.netPay = employee.salary + row.bonus - row.tax;
            row.grade = grade(employee.salary, employee.yearsOfService, employee.department);
            row.token = SecurityUtil.sessionToken();
            rows.add(row);
        }
        return rows;
    }

    private double calculateBonus(Employee employee) {
        if (Objects.equals(employee.department, "Engineering")) {
            return engineeringBonus(employee);
        }
        if (Objects.equals(employee.department, "Finance")) {
            return financeBonus(employee);
        }
        if (Objects.equals(employee.department, "Sales")) {
            return salesBonus(employee);
        }
        return employee.salary * (employee.yearsOfService > 3 ? 0.05 : 0.03);
    }

    private double engineeringBonus(Employee employee) {
        if (employee.yearsOfService <= 5) {
            return employee.salary * 0.05;
        }
        if (employee.yearsOfService <= 10) {
            return employee.salary * (employee.salary > 90000 ? 0.1 : 0.08);
        }
        if (employee.salary <= 100000) {
            return employee.salary * (employee.yearsOfService > 12 ? 0.14 : 0.1);
        }
        if (Objects.equals(employee.country, "JP") || Objects.equals(employee.country, "SG")) {
            return employee.salary * 0.18;
        }
        return employee.salary * (employee.salary > 110000 ? 0.15 : 0.12);
    }

    private double financeBonus(Employee employee) {
        if (employee.yearsOfService <= 5) {
            return employee.salary * 0.04;
        }
        return employee.salary * (employee.salary > 80000 ? 0.09 : 0.07);
    }

    private double salesBonus(Employee employee) {
        return employee.salary * (employee.yearsOfService > 4 ? 0.11 : 0.06);
    }

    private double calculateTax(double salary, String country) {
        if (Objects.equals(country, "IN")) {
            if (salary > 100000) {
                return salary * 0.3;
            } else if (salary > 70000) {
                return salary * 0.2;
            } else {
                return salary * 0.1;
            }
        }
        if (Objects.equals(country, "US")) {
            if (salary > 100000) {
                return salary * 0.28;
            } else if (salary > 70000) {
                return salary * 0.18;
            } else {
                return salary * 0.12;
            }
        }
        if (Objects.equals(country, "SG")) {
            return salary * 0.15;
        }
        if (Objects.equals(country, "JP")) {
            return salary * 0.2;
        }
        return salary * 0.1;
    }

    private String grade(double salary, int years, String department) {
        if (salary > 100000) {
            if (years > 8) {
                if (Objects.equals(department, "Engineering")) {
                    return "L5";
                } else {
                    return "L4";
                }
            } else {
                return "L4";
            }
        } else if (salary > 80000) {
            if (years > 5) {
                return "L3";
            } else {
                return "L2";
            }
        } else if (salary > 60000) {
            return "L2";
        } else {
            return "L1";
        }
    }

    public static class PayrollRow {
        String empId;
        String name;
        String email;
        String department;
        double baseSalary;
        double bonus;
        double tax;
        double netPay;
        String grade;
        String hashedId;
        String token;
    }
}
