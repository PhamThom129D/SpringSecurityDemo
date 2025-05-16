package com.codegym.hospital.repository.user;

import com.codegym.hospital.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByPhonenumber(String phonenumber);

    User findByEmail(String email);

    List<User> findByStatus(String status);

}
