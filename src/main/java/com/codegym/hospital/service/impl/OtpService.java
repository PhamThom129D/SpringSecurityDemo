package com.codegym.hospital.service.impl;

import com.codegym.hospital.model.auth.OtpVerification;
import com.codegym.hospital.model.user.User;
import com.codegym.hospital.repository.user.IOtpVerificationRepository;
import com.codegym.hospital.service.IOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService implements IOtpService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private IOtpVerificationRepository otpRepository;

    @Override
    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(999999);
        return String.valueOf(otp);
    }

    @Override
    public OtpVerification createOtpForUser(User user) {
        String otpCode = generateOtp();
        OtpVerification otp = new OtpVerification();
        otp.setUser(user);
        otp.setOtpCode(otpCode);
        otp.setCreatedAt(LocalDateTime.now());
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(3));
        otp.setVerified(false);

        return otpRepository.save(otp);
    }

    @Override
    public boolean verifyLatestOtpForUser(User user, String otpCode) {
        OtpVerification latestOtp = otpRepository.findTopByUserAndIsVerifiedFalseOrderByCreatedAtDesc(user);
        if (latestOtp == null) {
            return false;
        }

        if (latestOtp.getOtpCode().equals(otpCode) && latestOtp.getExpiresAt().isAfter(LocalDateTime.now())) {
            latestOtp.setVerified(true);
            otpRepository.save(latestOtp);
            return true;
        }

        return false;
    }

    public OtpVerification getLatestOtpByUser(User user) {
        return otpRepository.findTopByUserOrderByCreatedAtDesc(user);
    }

}
