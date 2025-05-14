package com.codegym.hospital.service;

import com.codegym.hospital.model.user.Role;

public interface IRoleService {
    Role findRoleByName(String roleName);
}
