package com.robosh.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.robosh.data.entity.Project;
import com.robosh.data.enums.Role;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeDto {

  private Long id;

  @NotBlank
  private String firstName;

  @NotBlank
  private String lastName;

  @JsonFormat(pattern = "dd.MM.yyyy")
  private LocalDate birthDate;

  @Enumerated
  private Role role;

//  private String projectName;

  private List<Project> project;

}
