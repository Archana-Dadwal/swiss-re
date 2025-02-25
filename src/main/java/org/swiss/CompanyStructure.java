package org.swiss;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CompanyStructure {

    private static Map<Integer, Employee> empDetail = new HashMap<>();
    private static Map<Integer, List<Employee>> managerToSubordinateDetail = new HashMap<>();

    static public void addEmployee(Employee emp) {
        empDetail.put(emp.id, emp);
        managerToSubordinateDetail.computeIfAbsent(emp.managerId, k -> new ArrayList<>()).add(emp);
    }

    /**
     * This method will analyze the salary
     * which managers earn less than they should, and by how much
     * which managers earn more than they should, and by how much
     */
    public static void analyzeSalaries() {
        for (Integer managerId : managerToSubordinateDetail.keySet()) {
            Employee manager = empDetail.get(managerId);
            if (manager == null) continue;

            List<Employee> subordinates = managerToSubordinateDetail.get(managerId);
            double avgSalary = subordinates.stream().mapToDouble(e -> e.salary).average().orElse(0);
            double minRequiredSalary = avgSalary * 1.2;
            double maxAllowedSalary = avgSalary * 1.5;

            if (manager.salary < minRequiredSalary || manager.salary > maxAllowedSalary) {
                System.out.println("Manager " + manager.firstName + " " + manager.lastName +
                        " does not meet salary constraints (Salary: " + manager.salary + ")");
            }
        }
    }

    /**
     * This method will identify
     * which employees have a reporting line which is too long(more than 4), and by how much
     */
    public static void identifyLongReportingLine() {
        for (Employee emp : empDetail.values()) {
            int count = countManagers(emp.id);
            if (count > 4) {
                System.out.println("Employee " + emp.firstName + " " + emp.lastName +
                        " has more than 4 managers between them and the CEO.");
            }
        }
    }

    /**
     * This is helper method to count the mangers count for a specific emp id
     * @param empId
     * @return
     */
    private static int countManagers(int empId) {
        int count = 0;
        Integer managerId = empDetail.get(empId).managerId;
        while (managerId != null && empDetail.containsKey(managerId)) {
            count++;
            Employee emp = empDetail.get(managerId);
            // this is to eliminate the scenario's where emp id is same as manager id
            if (Objects.nonNull(emp) && Objects.nonNull(emp.managerId)) {
                if (emp.managerId.equals(emp.id))
                    break;
            }
            managerId = emp.managerId;

        }
        return count;
    }

    /**
     * This will help us to load the csv and create the desired maps
     * @param filePath
     */
    private static void loadEmployeesFromCSV(String filePath) {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 5) continue;
                int id = Integer.parseInt(values[0].trim());
                String firstName = values[1].trim();
                String lastName = values[2].trim();
                double salary = Double.parseDouble(values[3].trim());
                Integer managerId = values[4].trim().isEmpty() ? null : Integer.parseInt(values[4].trim());
                addEmployee(new Employee(id, firstName, lastName, salary, managerId));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main method to invoke the required methods
     * @param args
     * placed a dummy 1000 records file in the resources folder for testing, which needs to be given as arg while invoking main
     * local s/m path C://Users/DELL/IdeaProjects/swiss-re/src/main/resources/large_employee_data.csv
     */
    public static void main(String[] args) {

        if (args.length > 0) {
            loadEmployeesFromCSV(args[0]);
            System.out.println("::::::::Analyzing Salary mismatches ::::::::::");
            analyzeSalaries();
            System.out.println("::::::::Analyzing Long Reporting Hierarchy ::::::::::");
            identifyLongReportingLine();
        } else
            System.out.println("Please enter a valid CSV file path in the program arguments");

    }


}






