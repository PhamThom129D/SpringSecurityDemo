package com.codegym.hospital.service;

import com.codegym.hospital.model.user.User;

public interface IUserService {
    String registerUser(User user);
    User loginUser(User user);
    User isPhoneNumberExist(String phoneNumber);
    User isEmailExist(String email);
}
