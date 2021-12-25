package com.example.payroll;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
class EmployeeController {

    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    // Get All employees
    @GetMapping("/employees")
    List<Employee> all() {
        return repository.findAll();
    }

    // get one employee
    @GetMapping("/employees/{id}")
    Employee one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    // new employee
    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee newEmployee) {
        log.info("Added new employee: {" + "name: {} role: {}}",newEmployee.getName(), newEmployee.getRole());
        return repository.save(newEmployee);
    }

    // update employee
    @PutMapping("/employees/{id}")
    Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });
    }

    // delete employee
    @DeleteMapping("/employees/{id}")
    boolean deleteEmployee(@PathVariable Long id) {
        Optional<Employee> employee = repository.findById(id);
        if (employee.isEmpty()){
            log.warn("Could not find Employee ID: " + id);
            return false;
        }
        repository.deleteById(id);
        log.info("Deleted: " + employee.get());
        return true;
    }

}