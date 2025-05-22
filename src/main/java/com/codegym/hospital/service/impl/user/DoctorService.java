package com.codegym.hospital.service.impl.user;

import com.codegym.hospital.model.user.Doctors;
import com.codegym.hospital.repository.user.IDoctorRepository;
import com.codegym.hospital.service.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DoctorService implements IDoctorService {
    @Autowired
    public IDoctorRepository doctorRepository;


    @Override
    public void save(Doctors doctor) {
        doctorRepository.save(doctor);
    }
}
