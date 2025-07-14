package com.codegym.hospital.service;

import com.codegym.hospital.model.user.Doctors;

import java.util.List;

public interface IDoctorService  {
    void save(Doctors doctor);
    Doctors findByUserID(Long userId);
}
