package com.employee.service;

import com.employee.entity.Employee;
import com.employee.exception.ResourceNotFoundException;
import com.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

//    @Autowired
//    private ModelMapper modelMapper;

    @Autowired
    private EmployeeRepository repository;

//    public String saveEmployeeDetails( EmployeeBean bean){
//        System.out.println("I am in service "+ bean);
//
////        Employee employee = new Employee();
////        employee.setFirstName(bean.getFirstName());
////        employee.setLastName(bean.getLastName());
////        employee.setEmail(bean.getEmail());
////        employee.setPhone(bean.getPhone());
//
//        ModelMapper modelMapper = new ModelMapper();
//        Employee employee = modelMapper.map(bean, Employee.class);
//        Employee savedEntity = repository.save(employee);
//        System.out.println("Saved the data :: "+ savedEntity);
//        return "Success";
//    }

    public String saveEmployeeDetails( Employee employee){
        Employee savedEntity = repository.save(employee);
        System.out.println("Saved the data :: "+ savedEntity);
        return "Success";
    }

    public List<Employee> fetchAllEmployees() {

      List<Employee>  employees = repository.findAll();

        return employees;
    }

    public Employee fetchEmployeeById(Integer id){
       Employee employee = repository.findById(id).get();
       return employee;
    }

    public Employee fetchEmployeeByFirstName(String fname) {
        Employee employee = repository.findByFirstName(fname);
        return employee;
    }

    public Employee fetchEmployeeByFirstNameAndLastName(String fname, String lname) {
       // Employee employee = repository.findByFirstNameAndLastName(fname, lname);
        Employee employee = repository.findByFirstNameOrLastName(fname, lname);
        return employee;
    }

    public Page<Employee> findAllByPagination(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public void deleteEmployeeById(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
        repository.deleteById(id);
    }

    public Employee updateEmployeePartially(Integer id, Employee updatedEmployee) {

        Employee existingEmployee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        if (updatedEmployee.getFirstName() != null) {
            existingEmployee.setFirstName(updatedEmployee.getFirstName());
        }
        if (updatedEmployee.getLastName() != null) {
            existingEmployee.setLastName(updatedEmployee.getLastName());
        }
        if (updatedEmployee.getEmail() != null) {
            existingEmployee.setEmail(updatedEmployee.getEmail());
        }
        if (updatedEmployee.getPhone() != null) {
            existingEmployee.setPhone(updatedEmployee.getPhone());
        }

        return repository.save(existingEmployee);
    }
}
