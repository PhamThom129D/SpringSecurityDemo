package com.codegym.hospital.service;

import com.codegym.hospital.model.user.Role;

import java.util.List;

public interface IRoleService {
    Role findRoleByName(String roleName);
    List<Role> findAllRoles();
}
