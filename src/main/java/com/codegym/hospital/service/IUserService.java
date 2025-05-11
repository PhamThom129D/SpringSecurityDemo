package com.codegym.hospital.service;

import com.codegym.hospital.model.User;

public interface IUserService {
    User registerUser(User user);
    User loginUser(User user);
    User isPhoneNumberExist(String phoneNumber);
}
