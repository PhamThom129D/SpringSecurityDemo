package com.codegym.hospital.service;

import com.codegym.hospital.model.user.User;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    String registerUser(User user);
    User loginUser(User user);
    User isPhoneNumberExist(String phoneNumber);
    User isEmailExist(String email);
    List<User> getUserByStatus(String status);
    void saveUser(User user);
    Optional<User> getUserById(Long id);
    void deleteUser(Long id);
    String uploadFile(MultipartFile file) throws IOException;
    void createUserWithDetail(User user, MultipartFile avatarFile) throws IOException;
    User prepareUserForUpdate(Long id, Model model);
}
