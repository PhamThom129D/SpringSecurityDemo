package com.codegym.hospital.service.implement;

import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    @Value("${twilio.service_sid}")
    private String serviceSid;

    public void sendOtp(String phoneNumber) {
        Verification verification = Verification.creator(
                serviceSid,
                phoneNumber,
                "sms"
        ).create();
        System.out.println("OTP sent: " + verification.getStatus());
    }

    public boolean verifyOtp(String phoneNumber, String code) {
        VerificationCheck verificationCheck = VerificationCheck.creator(serviceSid)
                .setTo(phoneNumber)
                .setCode(code)
                .create();
        return "approved".equals(verificationCheck.getStatus());
    }
}

