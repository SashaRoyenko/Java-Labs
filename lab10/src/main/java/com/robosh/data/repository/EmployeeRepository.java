package com.robosh.data.repository;

import com.robosh.data.entity.Employee;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("from Employee e " +
        "where " +
        "   concat(e.lastName, ' ', ' ', e.firstName) like concat('%', :name, '%')")
    List<Employee> findByName(@Param("name") String name);
}
