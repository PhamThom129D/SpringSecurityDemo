package com.codegym.hospital.service;

import com.codegym.hospital.model.user.Patients;

public interface IPatientService {
    Patients getPatientByUserId(Long userId);
    void save(Patients patient);
}
