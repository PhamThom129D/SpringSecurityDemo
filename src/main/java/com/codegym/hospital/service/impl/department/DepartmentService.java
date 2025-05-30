package com.codegym.hospital.service.impl.department;

import com.codegym.hospital.model.deparment.Departments;
import com.codegym.hospital.repository.deparment.IDeparmentRepository;
import com.codegym.hospital.service.IDeparmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService implements IDeparmentService {
    @Autowired
    private IDeparmentRepository deparmentRepository;
    @Override
    public Departments findById(Long id) {
        return deparmentRepository.findById(id).get();
    }

    @Override
    public List<Departments> findAll() {
        return deparmentRepository.findAll();
    }
}
