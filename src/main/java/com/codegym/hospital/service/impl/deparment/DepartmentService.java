package com.codegym.hospital.service.impl.deparment;

import com.codegym.hospital.model.deparment.Departments;
import com.codegym.hospital.repository.deparment.IDepartmentRepository;
import com.codegym.hospital.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class DepartmentService implements IDepartmentService {
    @Autowired
    private IDepartmentRepository departmentRepository;
    @Override
    public List<Departments> getAllDepartments() {
        return departmentRepository.findAll();
    }
}
