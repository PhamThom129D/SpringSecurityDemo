package com.codegym.hospital.service.implement;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmailOtpService {

    private final JavaMailSender mailSender;
    private final Map<String, String> otpStore = new ConcurrentHashMap<>();
    private final Map<String, Long> otpExpiry = new ConcurrentHashMap<>();
    private final long OTP_VALID_DURATION = 5 * 60 * 1000; // 5 phút

    public EmailOtpService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w+$");
    }

    public void sendOtpToEmail(String email) {
        String otp = generateOtp();
        otpStore.put(email, otp);
        otpExpiry.put(email, System.currentTimeMillis() + OTP_VALID_DURATION);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Mã OTP đăng nhập của bạn");
        message.setText("Mã OTP của bạn là: " + otp + "\nHiệu lực trong 5 phút.");
        mailSender.send(message);
    }

    public boolean verifyOtp(String email, String code) {
        if (!otpStore.containsKey(email)) return false;

        String storedOtp = otpStore.get(email);
        long expiryTime = otpExpiry.getOrDefault(email, 0L);

        if (System.currentTimeMillis() > expiryTime) {
            otpStore.remove(email);
            otpExpiry.remove(email);
            return false;
        }

        if (storedOtp.equals(code)) {
            otpStore.remove(email);
            otpExpiry.remove(email);
            return true;
        }

        return false;
    }

    private String generateOtp() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999));
    }
}
