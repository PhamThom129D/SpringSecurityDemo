package com.codegym.hospital.controller.authenticate;


import com.codegym.hospital.service.implement.EmailOtpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
public class EmailOtpController {

    private final EmailOtpService emailOtpService;

    public EmailOtpController(EmailOtpService emailOtpService) {
        this.emailOtpService = emailOtpService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        if (!emailOtpService.isValidEmail(email)) {
            return ResponseEntity.badRequest().body("Email không hợp lệ!");
        }

        emailOtpService.sendOtpToEmail(email);
        return ResponseEntity.ok("OTP đã được gửi!");
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String code) {
        boolean verified = emailOtpService.verifyOtp(email, code);
        if (verified) {
            return ResponseEntity.ok("Xác thực thành công!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mã OTP không đúng hoặc đã hết hạn!");
        }
    }
}

