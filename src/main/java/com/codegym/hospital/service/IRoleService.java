package com.codegym.hospital.service;

import com.codegym.hospital.model.user.Role;

import java.util.List;
import java.util.Map;

public interface IRoleService {
    Role findRoleByName(String roleName);
    List<Role> findAllRoles();

    Map<String, String> getRoleDisplayNames();
}
