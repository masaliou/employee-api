package com.mirahtec.employeeapi.service;

import com.mirahtec.employeeapi.model.Employee;
import com.mirahtec.employeeapi.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé avec l'id : " + id));
    }

    public Employee createEmployee(Employee employee) {
        if (repository.existsByEmail(employee.getEmail())) {
            throw new RuntimeException("Email déjà utilisé : " + employee.getEmail());
        }
        return repository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee updated) {
        Employee existing = getEmployeeById(id);
        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setEmail(updated.getEmail());
        existing.setDepartment(updated.getDepartment());
        existing.setPosition(updated.getPosition());
        existing.setSalary(updated.getSalary());
        return repository.save(existing);
    }

    public void deleteEmployee(Long id) {
        getEmployeeById(id);
        repository.deleteById(id);
    }
}