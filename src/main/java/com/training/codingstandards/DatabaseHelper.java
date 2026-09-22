package com.training.codingstandards;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHelper {

    private static final String URL = environmentVariable("DB_URL");
    private static final String USER = environmentVariable("DB_USER");
    private static final String PASSWORD = environmentVariable("DB_PASSWORD");

    public Employee findEmployee(String empId) {
        String sql = "SELECT emp_id, name FROM employees WHERE emp_id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, empId);
            try (ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                Employee employee = new Employee();
                employee.empId = rs.getString("emp_id");
                employee.name = rs.getString("name");
                return employee;
            }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to find employee", e);
        }
        return null;
    }

    private static String environmentVariable(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(name + " environment variable is required");
        }
        return value;
    }

    public void auditExport(String userInputPath) {
        if (userInputPath == null || !Files.exists(Path.of(userInputPath))) {
            throw new IllegalArgumentException("Export path does not exist");
        }
    }
}
