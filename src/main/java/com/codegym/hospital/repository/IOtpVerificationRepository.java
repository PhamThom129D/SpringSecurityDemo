package com.codegym.hospital.repository;

import com.codegym.hospital.model.OtpVerification;
import com.codegym.hospital.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOtpVerificationRepository extends JpaRepository<OtpVerification, Integer> {
    OtpVerification findTopByUserAndIsVerifiedFalseOrderByCreatedAtDesc(User user);
}

