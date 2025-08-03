package com.example.coffeeshop.controllers;

import com.example.coffeeshop.models.Employee;
import com.example.coffeeshop.services.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    // Show the list of employees (HTML view)
    @GetMapping("/employees")
    public String home(Model model) {
        model.addAttribute("employees", service.getAllEmployees());
        return "employeeList";
    }

    // Show form to add a new employee (HTML view)
    @GetMapping("/employee/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "form";
    }

    // Save a new or updated employee (handle form submit with validation)
    @PostMapping("/employee/save")
    public String saveEmployee(@Valid @ModelAttribute Employee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("employee", employee);
            return "form"; // return back to the form to show validation errors
        }
        service.saveEmployee(employee);
        return "redirect:/employees";
    }

    // Find employee by ID (REST JSON)
    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable @Min(1) int id) {
        Employee employee = service.getEmployeeById(id);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employee);
    }

    // Delete employee by ID (REST JSON)
    @PostMapping("/employee/delete/{id}")
    public String deleteEmployee(@PathVariable int id) {
        Employee employee = service.getEmployeeById(id);
        if (employee != null) {
            service.deleteEmployee(id);
        }
        return "redirect:/employees";
    }

    // Update employee by ID (REST JSON)
    @PutMapping("/employee/update/{id}")
    public ResponseEntity<String> updateEmployee(
            @PathVariable int id,
            @Valid @RequestBody Employee employee) {

        Employee existingEmployee = service.getEmployeeById(id);
        if (existingEmployee == null) {
            return ResponseEntity.notFound().build();
        }

        employee.setId(id);
        service.saveEmployee(employee);
        return ResponseEntity.ok("Employee updated successfully");
    }

    // Optional: Show form to edit an existing employee (HTML view)
    @GetMapping("/employee/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Employee employee = service.getEmployeeById(id);
        if (employee == null) {
            // could redirect or show error page, redirecting to list for now
            return "redirect:/employees";
        }
        model.addAttribute("employee", employee);
        return "form";
    }
}
