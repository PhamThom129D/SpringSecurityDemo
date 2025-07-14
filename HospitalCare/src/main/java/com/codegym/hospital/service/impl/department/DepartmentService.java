package com.codegym.hospital.service.impl.department;

import com.codegym.hospital.model.deparment.Departments;
import com.codegym.hospital.repository.deparment.IDepartmentRepository;
import com.codegym.hospital.service.IDeparmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService implements IDeparmentService {
    @Autowired
    private IDepartmentRepository departmentRepository;
    @Override
    public Departments findById(Long id) {
        return departmentRepository.findById(id).get();
    }

    @Override
    public List<Departments> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public void save(Departments department) {
        departmentRepository.save(department);
    }
    @Override
    public boolean existsByName(String name) {
        return departmentRepository.existsByName(name);
    }



}
