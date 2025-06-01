package com.codegym.hospital.service.impl.user;

import com.codegym.hospital.model.user.Patients;
import com.codegym.hospital.repository.user.IPatientRepository;
import com.codegym.hospital.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PatientService implements IPatientService {
    @Autowired
    public IPatientRepository patientRepository;

    @Override
    public Patients getPatientByUserId(Long userId) {
        return patientRepository.findByUserId(userId);
    }

    @Override
    public void save(Patients patient) {
        patientRepository.save(patient);
    }
}
