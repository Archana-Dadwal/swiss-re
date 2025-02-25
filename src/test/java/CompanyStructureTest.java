import org.example.CompanyStructure;
import org.example.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompanyStructureTest {

    @BeforeEach
    void setUp() {
        CompanyStructure.addEmployee(new Employee(1, "John", "Doe", 120000, null));
        CompanyStructure.addEmployee(new Employee(2, "Jane", "Smith", 80000, 1));
        CompanyStructure.addEmployee(new Employee(3, "Alice", "Johnson", 75000, 2));
        CompanyStructure.addEmployee(new Employee(4, "Bob", "Brown", 72000, 3));
        CompanyStructure.addEmployee(new Employee(5, "Carol", "White", 70000, 4));
        CompanyStructure.addEmployee(new Employee(6, "David", "Miller", 68000, 5));
    }

    @Test
    void testAnalyzeSalaries() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        CompanyStructure.analyzeSalaries();
        String output = outContent.toString().trim();
        assertTrue(output.contains("Manager Jane Smith does not meet salary constraints"));
    }

    @Test
    void testIdentifyLongReportingLine() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        CompanyStructure.identifyLongReportingLine();
        String output = outContent.toString().trim();
        assertTrue(output.contains("Employee David Miller has more than 4 managers between them and the CEO."));
    }

}
