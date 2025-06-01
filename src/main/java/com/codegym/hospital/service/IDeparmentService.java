package com.codegym.hospital.service;

import com.codegym.hospital.model.deparment.Departments;

import java.util.List;

public interface IDeparmentService {
    Departments findById(Long id);
    List<Departments> findAll();
    void save(Departments department);
    boolean existsByName(String name);
}
