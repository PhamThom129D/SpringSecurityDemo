package com.codegym.hospital.repository;

import com.codegym.hospital.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
