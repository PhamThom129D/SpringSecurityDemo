package com.codegym.hospital.repository;

import com.codegym.hospital.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByPhonenumber(String phonenumber);
}
