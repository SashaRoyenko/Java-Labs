package com.robosh.service;

import com.robosh.data.entity.Project;
import com.robosh.data.exception.ResourceNotFoundException;
import com.robosh.data.repository.ProjectRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectService {

  private ProjectRepository projectRepository;

  public Project create(Project employee){
    return projectRepository.save(employee);
  }

  public List<Project> findAll() {
    return projectRepository.findAll();
  }

  public Project findById(Long id) {
    return projectRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Project", "id", id)
    );
  }

  public ResponseEntity deleteById(Long id) {
    projectRepository.deleteById(id);
    return ResponseEntity.ok().build();
  }
}
