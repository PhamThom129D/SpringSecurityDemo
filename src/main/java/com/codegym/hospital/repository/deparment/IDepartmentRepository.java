package com.codegym.hospital.repository.deparment;

import com.codegym.hospital.model.deparment.Departments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface IDepartmentRepository extends JpaRepository<Departments, Long> {
    Optional<Departments> findByName(String name);
    boolean existsByName(String name);


}
