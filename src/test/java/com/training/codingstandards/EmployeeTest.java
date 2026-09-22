package com.training.codingstandards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EmployeeTest {

    @Test
    void employeesWithTheSameIdAreEqualByValue() {
        Employee first = new Employee(new String("1001"), "Asha", "asha@example.com",
                "Engineering", 92000, 6, "IN", "lead@example.com");
        Employee second = new Employee(new String("1001"), "Different", "other@example.com",
                "Sales", 54000, 1, "AE", "lead@example.com");

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first, null);
    }
}