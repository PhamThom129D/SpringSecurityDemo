package com.codegym.hospital.repository.user;

import com.codegym.hospital.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
