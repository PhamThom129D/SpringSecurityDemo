package com.codegym.hospital.service.impl.user;

import com.codegym.hospital.model.user.Role;
import com.codegym.hospital.repository.user.IRoleRepository;
import com.codegym.hospital.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public Role findRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }
}
