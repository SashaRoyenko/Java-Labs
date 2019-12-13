package com.robosh.data.mapper;

import com.robosh.data.dto.EmployeeDto;
import com.robosh.data.entity.Employee;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface EmployeeMapper {

  @Mappings({
      @Mapping(source = "projectId", target = "project.id")
  })
  Employee toEmployee(EmployeeDto employeeDto);

  @InheritInverseConfiguration
  EmployeeDto toEmployeeDto(Employee employee);

  List<Employee> toEmployeeList(List<EmployeeDto> employeeDtoList);

  List<EmployeeDto> toEmployeeDtoList(List<Employee> employeeList);

}
