package com.employee.repository;

import com.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

        public Employee findByFirstName(String firstName);
        public Employee findByFirstNameAndLastName(String firstName, String lastName);
        public Employee findByFirstNameOrLastName(String firstName, String lastName);
}
