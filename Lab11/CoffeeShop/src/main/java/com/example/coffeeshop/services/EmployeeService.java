package com.example.coffeeshop.services;

import com.example.coffeeshop.models.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private final List<Employee> employeeList = new ArrayList<>();
    private int nextId = 1;

    public List<Employee> getAllEmployees() {
        return employeeList;
    }

    public void saveEmployee(Employee employee) {
        if (employee.getId() == 0) {
            employee.setId(nextId++);
            employeeList.add(employee);
        } else {
            for (int i = 0; i < employeeList.size(); i++) {
                if (employeeList.get(i).getId() == employee.getId()) {
                    employeeList.set(i, employee);
                    break;
                }
            }
        }
    }

    public Employee getEmployeeById(int id) {
        return employeeList.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void deleteEmployee(int id) {
        employeeList.removeIf(e -> e.getId() == id);
    }
}
