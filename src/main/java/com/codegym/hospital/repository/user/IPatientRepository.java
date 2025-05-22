package com.codegym.hospital.repository.user;

import com.codegym.hospital.model.user.Patients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPatientRepository extends JpaRepository<Patients,Long> {
}
