package com.robosh.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.robosh.data.enums.Role;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "first_name", nullable = false)
  @NotBlank
  private String firstName;

  @Column(name = "second_name", nullable = false)
  @NotBlank
  private String lastName;

  @Column(name = "birth_date")
  @DateTimeFormat(iso = ISO.DATE, pattern = "dd.MM.yyyy")
  @JsonFormat(pattern = "dd.MM.yyyy")
  private LocalDate birthDate;

  @Enumerated
  private Role role;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Project> project;

}
