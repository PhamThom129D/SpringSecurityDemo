package com.codegym.hospital.repository.user;

import com.codegym.hospital.model.auth.OtpVerification;
import com.codegym.hospital.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOtpVerificationRepository extends JpaRepository<OtpVerification, Integer> {
    OtpVerification findTopByUserAndIsVerifiedFalseOrderByCreatedAtDesc(User user);
    OtpVerification findTopByUserOrderByCreatedAtDesc(User user);
}

