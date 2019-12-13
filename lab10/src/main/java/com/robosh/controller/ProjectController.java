package com.robosh.controller;

import com.robosh.data.entity.Project;
import com.robosh.service.ProjectService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
@AllArgsConstructor
public class ProjectController {

  private ProjectService projectService;

  @GetMapping
  public List<Project> getAllProjects() {
    return projectService.findAll();
  }


  @GetMapping("/{id}")
  public Project getProjectById(@PathVariable Long id) {
    return projectService.findById(id);
  }

  @PostMapping
  public Project createProject(@RequestBody Project project) {
    return projectService.create(project);
  }


  @DeleteMapping("/{id}")
  public ResponseEntity deleteProjectById(@PathVariable Long id) {
    return projectService.deleteById(id);
  }

}
