package com.codegym.hospital.service;

import com.codegym.hospital.model.auth.OtpVerification;
import com.codegym.hospital.model.user.User;

import javax.servlet.http.HttpSession;

public interface IEmailService {
    String generateOtp();
    OtpVerification createOtpForUser(HttpSession session,User user);
    boolean verifyLatestOtpForUser(HttpSession session, String otpCode);
    void sendMessageEmailToUser(String emailAddress, String subject, String messageBody);
    boolean canSendOtpAgain(HttpSession session);
    void sendOtpEmail(User user , String otpCode);

    void sendApprovalNotificationEmail(String email);
    void sendRejectNotificationEmail(String email);

}
