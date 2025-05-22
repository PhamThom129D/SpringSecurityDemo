package com.codegym.hospital.service.impl.user;

import com.codegym.hospital.model.user.Patients;
import com.codegym.hospital.repository.user.IPatientRepository;
import com.codegym.hospital.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService implements IPatientService {
    @Autowired
    public IPatientRepository patientRepository;
}
