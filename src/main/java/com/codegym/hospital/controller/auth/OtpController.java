package com.codegym.hospital.controller.auth;

import com.codegym.hospital.model.auth.OtpVerification;
import com.codegym.hospital.model.user.User;
import com.codegym.hospital.repository.user.IUserRepository;
import com.codegym.hospital.service.impl.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequestMapping(value = "/api/otp" ,produces = "text/plain;charset=UTF-8")
public class OtpController {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/send")
    @ResponseBody
    public ResponseEntity<String> sendOtp(@RequestParam("email") String email, HttpSession session) {
        User userOpt = userRepository.findByEmail(email);
        if (userOpt == null) {
            return ResponseEntity.badRequest().body("Email chưa đăng ký tài khoản!");
        }

        OtpVerification lastOtp = otpService.getLatestOtpByUser(userOpt);
        if (lastOtp != null && lastOtp.getCreatedAt().isAfter(LocalDateTime.now().minusMinutes(3))) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Vui lòng đợi 3 phút để gửi lại OTP.");
        }

        OtpVerification otp = otpService.createOtpForUser(userOpt);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userOpt.getEmail());
        message.setSubject("[HospitalCare] Xác thực đăng nhập - Mã OTP của bạn");

        String emailBody = "Chào bạn " + userOpt.getFullname() + ", \n\n"
                + "Bạn hoặc ai đó đã yêu cầu đăng nhập vào hệ thống của chúng tôi.\n"
                + "Mã OTP của bạn là: " + otp.getOtpCode() + "\n\n"
                + "Vui lòng nhập mã này để hoàn tất quá trình đăng nhập. Mã OTP có hiệu lực trong vòng 5 phút.\n\n"
                + "Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email này hoặc liên hệ với chúng tôi để được hỗ trợ.\n\n"
                + "Trân trọng,\n"
                + "Đội ngũ hỗ trợ";

        message.setText(emailBody);
        mailSender.send(message);

        session.setAttribute("otpUserId", userOpt.getId());
        return ResponseEntity.ok("OK");
    }


    @PostMapping("/verify")
    @ResponseBody
    public ResponseEntity<String> verifyOtp(@RequestParam String email,
                                            @RequestParam String code,
                                            HttpSession session) {
        if (session.getAttribute("otpUserId") == null) {
            return ResponseEntity.badRequest().body("Vui lòng gửi mã OTP trước.");
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy người dùng.");
        }

        if (otpService.verifyLatestOtpForUser(user, code)) {
            session.setAttribute("loggedInUser", user);
            return ResponseEntity.ok("OK");
        }

        return ResponseEntity.badRequest().body("Mã OTP không đúng hoặc đã hết hạn.");
    }


    @GetMapping("/home")
    public String home() {
        return "home";
    }

}
