package com.robosh.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.robosh.data.entity.Employee;
import com.robosh.data.entity.Project;
import com.robosh.data.enums.Role;
import com.robosh.data.repository.EmployeeRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private EmployeeRepository employeeRepository;

  @Test
  @Transactional
  @Rollback
  public void shouldExtractEmployeeProjectsLazy() throws NoSuchFieldException {
    Employee savedEmployee = employeeRepository.save(getEmployee());
    Optional<Employee> databaseEmployee = employeeRepository.findById(savedEmployee.getId());
    System.err.println(employeeRepository.findAll());
    System.err.println("Lazy fetch start");
    assertEquals(2, databaseEmployee.get().getProject().size());
    System.err.println("Lazy fetch finish");

  }


//  public void shouldSaveEmployee(){
//    employeeService.create(getEmployee());
//  }

  private Employee getEmployee(){
    return Employee.builder()
        .firstName("Oleksandr")
        .lastName("Roienko")
        .birthDate(LocalDate.of(2000, 4, 24))
        .role(Role.JUNIOR)
        .project(getProjectList())
        .build();
  }

  private List<Project> getProjectList(){
    return new ArrayList<Project>(){{
      add(new Project("Detroit"));
      add(new Project("Pubg"));
    }};
  }
}