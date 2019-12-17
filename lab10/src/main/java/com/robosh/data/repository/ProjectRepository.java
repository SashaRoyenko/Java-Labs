package com.robosh.data.repository;

import com.robosh.data.entity.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

  @Query(value = "Select * from project where name Like %?1%", nativeQuery = true)
  List<Project> findByName(String name);
}
