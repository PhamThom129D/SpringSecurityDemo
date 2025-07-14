package com.codegym.hospital.service.impl.user;

import com.codegym.hospital.model.user.Role;
import com.codegym.hospital.repository.user.IRoleRepository;
import com.codegym.hospital.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    @Override
    public Map<String, String> getRoleDisplayNames() {
        return Map.of(
                "ADMIN", "Quản trị viên",
                "RECEPTIONIST", "Nhân viên tiếp nhận",
                "DOCTOR", "Bác sĩ",
                "PATIENT", "Bệnh nhân"
        );
    }
}
