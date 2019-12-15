package com.robosh.service;

import com.robosh.data.dto.EmployeeDto;
import com.robosh.data.entity.Employee;
import com.robosh.data.exception.ResourceNotFoundException;
import com.robosh.data.mapper.EmployeeMapper;
import com.robosh.data.repository.EmployeeRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeService {

  private EmployeeRepository employeeRepository;
  private ModelMapper modelMapper;
  private EmployeeMapper employeeMapper;

  public EmployeeDto create(EmployeeDto employee) {
    return employeeMapper
        .toEmployeeDto(employeeRepository.save(employeeMapper.toEmployee(employee)));
  }

  public Employee create(Employee employee) {
    return employeeRepository.save(employee);
  }

//  public List<Employee> findAll() {
//    return employeeRepository.findAll();
//  }

  public List<EmployeeDto> findAll() {
    return employeeMapper.toEmployeeDtoList(employeeRepository.findAll());
  }

  public EmployeeDto findById(Long id) {
    return employeeMapper.toEmployeeDto(employeeRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Employee", "id", id)
    ));
  }

  public EmployeeDto update(EmployeeDto updatedEmployeeDto) {
    Employee updatedEmployee = employeeMapper.toEmployee(updatedEmployeeDto);
    Employee currentEmployee = employeeMapper.toEmployee(findById(updatedEmployee.getId()));
    modelMapper.map(currentEmployee, updatedEmployee);
    employeeRepository.save(updatedEmployee);
    return employeeMapper.toEmployeeDto(updatedEmployee);
  }

  public ResponseEntity deleteById(Long id) {
    findById(id);
    employeeRepository.deleteById(id);
    return ResponseEntity.ok().build();
  }

  public List<EmployeeDto> findByName(String name) {
    return employeeMapper.toEmployeeDtoList(employeeRepository.findByName(name));
  }

}
