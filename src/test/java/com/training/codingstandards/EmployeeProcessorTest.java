package com.training.codingstandards;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EmployeeProcessorTest {

    @Test
    void processCreatesPayrollRowForEachEmployee() {
        Employee employee = new Employee("1001", "Asha Raman", "asha.raman@example.com",
                "Engineering", 92000, 6, "IN", "lead.eng@example.com");

        EmployeeProcessor processor = new EmployeeProcessor();
        List<EmployeeProcessor.PayrollRow> rows = processor.process(Arrays.asList(employee));

        assertNotNull(rows);
        assertEquals(1, rows.size());
        assertEquals("1001", rows.get(0).empId);
        assertEquals("Engineering", rows.get(0).department);
    }

    @Test
    void appliesEngineeringBonusAndCountryTaxUsingValueEquality() {
        Employee employee = new Employee("1007", "Grace Nakamura", "grace@example.com",
                new String("Engineering"), 115000, 12, new String("JP"), "lead@example.com");

        EmployeeProcessor.PayrollRow row = new EmployeeProcessor().process(List.of(employee)).get(0);

        assertEquals(20700, row.bonus, 0.001);
        assertEquals(23000, row.tax, 0.001);
        assertEquals(112700, row.netPay, 0.001);
        assertEquals("L5", row.grade);
    }

    @Test
    void returnsEmptyRowsForNullInput() {
        assertEquals(List.of(), new EmployeeProcessor().process(null));
    }

    @Test
    void coversDepartmentBonusAndTaxRules() {
        List<Employee> employees = List.of(
                employee("Engineering", 120000, 12, "US"),
                employee("Engineering", 100000, 12, "IN"),
                employee("Engineering", 92000, 6, "SG"),
                employee("Engineering", 80000, 6, "JP"),
                employee("Engineering", 70000, 2, "AE"),
                employee("Finance", 90000, 6, "IN"),
                employee("Finance", 70000, 6, "US"),
                employee("Finance", 70000, 3, "SG"),
                employee("Sales", 70000, 5, "NG"),
                employee("Sales", 70000, 1, "AE"),
                employee("HR", 70000, 4, "JP"),
                employee("HR", 70000, 2, "CA"));

        List<EmployeeProcessor.PayrollRow> rows = new EmployeeProcessor().process(employees);

        assertEquals(12, rows.size());
        assertEquals(18000, rows.get(0).bonus, 0.001);
        assertEquals(20000, rows.get(1).tax, 0.001);
        assertEquals(9200, rows.get(2).bonus, 0.001);
        assertEquals(6400, rows.get(3).bonus, 0.001);
        assertEquals(3500, rows.get(4).bonus, 0.001);
        assertEquals(8100, rows.get(5).bonus, 0.001);
        assertEquals(4900, rows.get(6).bonus, 0.001);
        assertEquals(2800, rows.get(7).bonus, 0.001);
        assertEquals(7700, rows.get(8).bonus, 0.001);
        assertEquals(4200, rows.get(9).bonus, 0.001);
        assertEquals(3500, rows.get(10).bonus, 0.001);
        assertEquals(2100, rows.get(11).bonus, 0.001);
    }

    private Employee employee(String department, double salary, int years, String country) {
        return new Employee("id-" + department + years + salary, "Name", "name@example.com",
                new String(department), salary, years, new String(country), "manager@example.com");
    }
}
