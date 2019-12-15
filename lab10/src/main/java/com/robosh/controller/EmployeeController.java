package com.robosh.controller;

import com.robosh.data.dto.EmployeeDto;
import com.robosh.data.entity.Employee;
import com.robosh.service.EmployeeService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@Controller
//@RestController
//@RequestMapping("/employee")
//@AllArgsConstructor
public class EmployeeController {

  private EmployeeService employeeService;

  @GetMapping
  public List<EmployeeDto> getAllEmployees() {
    return employeeService.findAll();
  }


//  @GetMapping("/{id}")
//  public Employee getEmployeeById(@PathVariable Long id) {
//    return employeeService.findById(id);
//  }

  @PostMapping
  public EmployeeDto createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
    return employeeService.create(employeeDto);
  }

  @PutMapping
  public EmployeeDto updateEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
    return employeeService.update(employeeDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteEmployeeById(@PathVariable Long id) {
    return employeeService.deleteById(id);
  }

}
