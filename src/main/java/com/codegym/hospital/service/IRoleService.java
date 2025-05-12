package com.codegym.hospital.service;

import com.codegym.hospital.model.Role;

public interface IRoleService {
    Role findRoleByName(String roleName);
}
