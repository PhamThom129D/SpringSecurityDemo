package com.codegym.hospital.repository.user;

import com.codegym.hospital.model.user.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IDoctorRepository extends JpaRepository<Doctors,Long> {
    Doctors findByUser_Id(Long userId);
}
