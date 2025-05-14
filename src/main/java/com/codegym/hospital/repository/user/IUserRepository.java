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

        @Query("SELECT u FROM User u WHERE u.status = 'pending' AND " +
                "(LOWER(u.fullname) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                "OR LOWER(u.role.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
        Page<User> findPendingUsers(@Param("keyword") String keyword, Pageable pageable);



}
