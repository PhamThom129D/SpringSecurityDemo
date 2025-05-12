package com.codegym.hospital.service.implement;

import com.codegym.hospital.model.OtpVerification;
import com.codegym.hospital.model.User;
import com.codegym.hospital.repository.IOtpVerificationRepository;
import com.codegym.hospital.service.IOtpService;
import org.hibernate.Hibernate;
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
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    @Override
    public OtpVerification createOtpForUser(User user) {
        String otpCode = generateOtp();
        OtpVerification otp = new OtpVerification();
        otp.setUser(user);
        otp.setOtpCode(otpCode);
        otp.setCreatedAt(LocalDateTime.now());
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        otp.setVerified(false);

        return otpRepository.save(otp);
    }

    @Override
    public OtpVerification verifyLatestOtpForUser(User user) {
        OtpVerification latestOtp = otpRepository.findTopByUserAndIsVerifiedFalseOrderByCreatedAtDesc(user);

        if (latestOtp != null && latestOtp.getExpiresAt().isAfter(LocalDateTime.now())) {
            latestOtp.setVerified(true);
            otpRepository.save(latestOtp);
            return latestOtp;
        }

        return null;
    }
}
