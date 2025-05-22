package com.codegym.hospital.service.impl.user;

import com.codegym.hospital.model.user.Role;
import com.codegym.hospital.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private IRoleService roleService;

    @Override
    public Role findRoleByName(String roleName) {
        return null;
    }
}
