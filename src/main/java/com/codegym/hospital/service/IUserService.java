package com.codegym.hospital.service;

import com.codegym.hospital.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    String registerUser(User user);
    User loginUser(User user);
    User isPhoneNumberExist(String phoneNumber);
    User isEmailExist(String email);
    List<User> getUserByStatus(String status);
    Page<User> findPendingUsers(String keyword, Pageable pageable);
}
