package com.codegym.hospital.service.implement;

import com.codegym.hospital.model.Role;
import com.codegym.hospital.model.User;
import com.codegym.hospital.repository.IRoleRepository;
import com.codegym.hospital.repository.IUserRepository;
import com.codegym.hospital.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passEncoder;
    @Override
    public String registerUser(User user) {
        user.setPassword(passEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName(user.getRole().getName());
        user.setRole(role);
        String message;
        switch (role.getName()){
            case "PATIENT":
                user.setStatus("active");
                message = "Đăng ký thành công. Vui lòng đăng nhập lại!";
                break;
            case "DOCTOR" :
                case "RECEPTIONIST":
                    user.setStatus("pending");
                    message = "Đăng ký thành công. Chờ duyệt!";
                    break;
            default:
                user.setStatus("inactive");
                return null;
        }
        userRepository.save(user);
        return message;
    }
    @Override
    public User loginUser(User user) {
        User userFromDb = isPhoneNumberExist(user.getPhonenumber());
        if (userFromDb != null && passEncoder.matches(user.getPassword(), userFromDb.getPassword())) {
            return userFromDb;
        }
        return null;
    }

    @Override
    public User isPhoneNumberExist(String phoneNumber) {
        return userRepository.findByPhonenumber(phoneNumber);
    }
}
