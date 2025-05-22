package com.codegym.hospital.service.impl.user;

import com.codegym.hospital.model.user.Doctors;
import com.codegym.hospital.service.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService implements IDoctorService {
    @Autowired
    public IDoctorService doctorService;


    @Override
    public List<Doctors> getAllDoctor() {
        return doctorService.getAllDoctor();
    }
}
