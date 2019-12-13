package com.robosh.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.robosh.data.enums.Role;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
@Entity
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "first_name", nullable = false)
  @NotBlank
  private String firstName;

  @Column(name = "second_name", nullable = false)
  @NotBlank
  private String secondName;

  @Column(name = "birth_date")
  @DateTimeFormat(iso = ISO.DATE, pattern = "dd.MM.yyyy")
  @JsonFormat(pattern = "dd.MM.yyyy")
  private LocalDate birthDate;

  @Enumerated
  private Role role;

  @OneToOne
  private Project project;
}
