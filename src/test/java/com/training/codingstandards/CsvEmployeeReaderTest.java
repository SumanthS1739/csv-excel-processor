package com.training.codingstandards;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CsvEmployeeReaderTest {

    @Test
    void readsBundledEmployeeCsv() {
        CsvEmployeeReader reader = new CsvEmployeeReader();
        List<Employee> employees = reader.read(null);

        assertFalse(employees.isEmpty());
        assertEquals(8, employees.size());
        assertEquals("1001", employees.get(0).empId);
    }

    @Test
    void rejectsMissingCsvFile() {
        CsvEmployeeReader reader = new CsvEmployeeReader();

        assertThrows(IllegalStateException.class,
                () -> reader.read(Path.of("missing-employees.csv").toString()));
    }
}
