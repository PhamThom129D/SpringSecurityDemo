package com.codegym.hospital.controller.auth;

import com.codegym.hospital.model.auth.OtpVerification;
import com.codegym.hospital.model.user.User;
import com.codegym.hospital.repository.user.IUserRepository;
import com.codegym.hospital.service.IOtpService;
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
    private IOtpService otpService;
    @Autowired
    private IUserRepository userRepository;


    @PostMapping("/send")
    @ResponseBody
    public ResponseEntity<String> sendOtp(@RequestParam("email") String email, HttpSession session) {
        User userOpt = userRepository.findByEmail(email);
        if (userOpt == null) {
            return ResponseEntity.badRequest().body("Email chưa đăng ký tài khoản!");
        }
        if(!otpService.canSendOtpAgain(session)){
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Vui lòng đợi ít nhất 3 phút để gửi lại mã OTP.");
        }
        OtpVerification otp = otpService.createOtpForUser(session,userOpt);
        otpService.sendOtpEmail(userOpt,otp.getOtpCode());
        return ResponseEntity.ok("Mã OTP đã được gửi thành công.");
    }


    @PostMapping("/verify")
    @ResponseBody
    public ResponseEntity<String> verifyOtp(@RequestParam String email,
                                            @RequestParam String code,
                                            HttpSession session) {
        OtpVerification otp = (OtpVerification) session.getAttribute("otpVerification");
        if (otp == null) {
            return ResponseEntity.badRequest().body("Vui lòng gửi mã OTP trước.");
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy người dùng.");
        }

        if (otpService.verifyLatestOtpForUser(session,code)) {
            session.removeAttribute("otpVerification");
            session.setAttribute("loggedInUser", user);
            return ResponseEntity.ok("OK");
        }

        return ResponseEntity.badRequest().body("Mã OTP không đúng hoặc đã hết hạn.");
    }


    @GetMapping("/home")
    public String home() {
        return "admin/home";
    }

}
