package com.training.codingstandards;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvEmployeeReader {

    public List<Employee> read(String csvPath) {
        List<Employee> employees = new ArrayList<>();
        try (Reader reader = createReader(csvPath);
             CSVParser parser = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build().parse(reader)) {
            for (CSVRecord record : parser) {
                Employee employee = new Employee(
                        record.get("empId"),
                        record.get("name"),
                        record.get("email"),
                        record.get("department"),
                        Double.parseDouble(record.get("salary")),
                        Integer.parseInt(record.get("yearsOfService")),
                        record.get("country"),
                        record.get("managerEmail"));
                employees.add(employee);
            }
        } catch (IOException | IllegalArgumentException e) {
            throw new IllegalStateException("Unable to read employee CSV", e);
        }
        return employees;
    }

    private Reader createReader(String csvPath) throws IOException {
        if (csvPath == null) {
            var inputStream = CsvEmployeeReader.class.getResourceAsStream("/employees.csv");
            if (inputStream == null) {
                throw new IOException("Bundled employees.csv was not found");
            }
            return new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        }
        return new InputStreamReader(new FileInputStream(csvPath), StandardCharsets.UTF_8);
    }
}
