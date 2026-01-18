package com.employee.controller;

import com.employee.entity.Employee;
import com.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {
    @Autowired
    private EmployeeService service;

//    @PostMapping("save")
//   public ResponseEntity<String> saveEmployee(@RequestBody EmployeeBean bean ){
//        System.out.println(bean);
//        String message = service.saveEmployeeDetails(bean);
//        return new ResponseEntity<String>(message, HttpStatus.OK);
//    }

    @PostMapping("save")
    public ResponseEntity<String> saveEmployee(@RequestBody Employee employee ){
        String message = service.saveEmployeeDetails(employee);
        return new ResponseEntity<String>(message, HttpStatus.CREATED);
    }

    @GetMapping("fetchall")
    public ResponseEntity<List<Employee>> fetchAllEmployees(){
        List<Employee> list = service.fetchAllEmployees();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("fetch/{id}")
    public ResponseEntity<Employee> fetchEmployeeById(@PathVariable Integer id){
            Employee employee = service.fetchEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @GetMapping("fetch/name/{fname}")
    public ResponseEntity<Employee> fetchEmployeeByFirstName(@PathVariable String fname){
        Employee employee = service.fetchEmployeeByFirstName(fname);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @GetMapping("fetch/name/{fname}/{lname}")
    public ResponseEntity<Employee> fetchEmployeeByFirstNameAndLastName(@PathVariable String fname, @PathVariable String lname){
        Employee employee = service.fetchEmployeeByFirstNameAndLastName(fname, lname);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    //http://localhost:8088/employee/fetchbypagination?page=1&size=2&sortBy=firstName&ascending=true
//    @GetMapping("fetchbypagination")
//    public Page<Employee> getAllEmployeesByPagination(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "5") int size,
//            @RequestParam(defaultValue = "id") String sortBy,
//            @RequestParam(defaultValue = "true") boolean ascending
//    ) {
//        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//        return service.findAllByPagination(pageable);
//    }

    @GetMapping("fetchbypagination")
    public ResponseEntity<Page<Employee>> getAllEmployeesByPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending){
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort );
        Page<Employee> pageList = service.findAllByPagination(pageable);
        return new ResponseEntity<>(pageList, HttpStatus.OK);
    }


    @PutMapping("update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee emp ){

        Employee employeeInDb = service.fetchEmployeeById(id);
        employeeInDb.setFirstName(emp.getFirstName());
        employeeInDb.setLastName(emp.getLastName());
        employeeInDb.setEmail(emp.getEmail());
        employeeInDb.setPhone(emp.getPhone());
        service.saveEmployeeDetails(employeeInDb);
        return new ResponseEntity<>(employeeInDb, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer id) {
        service.deleteEmployeeById(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Employee> patchEmployee(
            @PathVariable Integer id,
            @RequestBody Employee employee) {

        Employee updatedEmployee = service.updateEmployeePartially(id, employee);
        return ResponseEntity.ok(updatedEmployee);
    }

}
