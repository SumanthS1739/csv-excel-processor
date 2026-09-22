package com.training.codingstandards;

import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExcelReportWriterTest {

    @Test
    void writesPayrollRowsToExcel() throws Exception {
        EmployeeProcessor.PayrollRow row = new EmployeeProcessor.PayrollRow();
        row.empId = "1001";
        row.name = "Asha Raman";
        row.email = "asha@example.com";
        row.department = "Engineering";
        row.baseSalary = 92000;
        row.bonus = 9200;
        row.tax = 18400;
        row.netPay = 82800;
        row.grade = "L3";
        row.hashedId = "hash";
        row.token = "token";
        Path output = Files.createTempFile("payroll", ".xlsx");

        new ExcelReportWriter().write(List.of(row), output.toString());

        try (var workbook = WorkbookFactory.create(output.toFile())) {
            assertEquals("Payroll", workbook.getSheetAt(0).getSheetName());
            assertEquals("Asha Raman", workbook.getSheetAt(0).getRow(1).getCell(1).getStringCellValue());
        }
        Files.deleteIfExists(output);
    }
}