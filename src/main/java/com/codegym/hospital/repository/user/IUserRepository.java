package com.codegym.hospital.repository.user;

import com.codegym.hospital.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByPhonenumber(String phonenumber);

    User findByEmail(String email);

}
