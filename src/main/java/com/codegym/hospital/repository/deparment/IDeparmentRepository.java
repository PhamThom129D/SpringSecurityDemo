package com.codegym.hospital.repository.deparment;

import com.codegym.hospital.model.deparment.Departments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDeparmentRepository extends JpaRepository<Departments, Long> {
}
