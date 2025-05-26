package com.codegym.hospital.repository.deparment;

import com.codegym.hospital.model.deparment.Departments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface IDepartmentRepository extends JpaRepository<Departments, Long> {
}
