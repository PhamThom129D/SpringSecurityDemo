package com.codegym.hospital.service.impl;

import com.codegym.hospital.model.auth.OtpVerification;
import com.codegym.hospital.model.user.User;
import com.codegym.hospital.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class EmailService implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;


    @Override
    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(999999);
        return String.valueOf(otp);
    }

    @Override
    public OtpVerification createOtpForUser(HttpSession session, User user) {
        String otpCode = generateOtp();
        OtpVerification otp = new OtpVerification(LocalDateTime.now(), LocalDateTime.now().plusMinutes(3), false, otpCode, user.getId());
        session.setAttribute("otpVerification", otp);
        return otp;
    }

    @Override
    public boolean verifyLatestOtpForUser(HttpSession session, String otpCode) {
        OtpVerification otp = (OtpVerification) session.getAttribute("otpVerification");
        if (otp == null || otp.getExpiresAt().isBefore(LocalDateTime.now())) {
            session.removeAttribute("otpVerification");
            return false;
        }
        return otp.getOtpCode().equals(otpCode);
    }

    @Override
    public void sendMessageEmailToUser(String emailAddress, String subject, String messageBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailAddress);
        message.setSubject(subject);
        message.setText(messageBody);
        mailSender.send(message);

    }

    @Override
    public boolean canSendOtpAgain(HttpSession session) {
        OtpVerification existingOtp = (OtpVerification) session.getAttribute("otpVerification");
        if (existingOtp == null) {
            return true;
        }

        return LocalDateTime.now().isAfter( existingOtp.getCreatedAt().plusMinutes(3));
    }


    @Override
    public void sendOtpEmail(User user, String otpCode) {
        String subject = "[HospitalCare] Xác thực đăng nhập - Mã OTP của bạn";

        String body = "Chào bạn " + user.getFullname() + ", \n\n"
                + "Bạn hoặc ai đó đã yêu cầu đăng nhập vào hệ thống của chúng tôi.\n"
                + "Mã OTP của bạn là: " + otpCode + "\n\n"
                + "Vui lòng nhập mã này để hoàn tất quá trình đăng nhập. Mã OTP có hiệu lực trong vòng 5 phút.\n\n"
                + "Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email này hoặc liên hệ với chúng tôi để được hỗ trợ.\n\n"
                + "Trân trọng,\n"
                + "Đội ngũ hỗ trợ";

        sendMessageEmailToUser(user.getEmail(), subject, body);
    }


}
