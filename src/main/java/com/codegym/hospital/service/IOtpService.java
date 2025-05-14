package com.codegym.hospital.service;

import com.codegym.hospital.model.auth.OtpVerification;
import com.codegym.hospital.model.user.User;

public interface IOtpService {
    String generateOtp();
    OtpVerification createOtpForUser(User user);
    boolean verifyLatestOtpForUser(User user, String otpCode);

}
