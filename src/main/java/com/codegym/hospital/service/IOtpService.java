package com.codegym.hospital.service;

import com.codegym.hospital.model.OtpVerification;
import com.codegym.hospital.model.User;

public interface IOtpService {
    String generateOtp();
    OtpVerification createOtpForUser(User user);
    OtpVerification verifyLatestOtpForUser(User user);
}
