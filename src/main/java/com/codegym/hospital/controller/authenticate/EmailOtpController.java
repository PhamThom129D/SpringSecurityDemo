package com.codegym.hospital.controller.authenticate;

import com.codegym.hospital.model.OtpVerification;
import com.codegym.hospital.model.User;
import com.codegym.hospital.repository.IUserRepository;
import com.codegym.hospital.service.implement.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/api/otp")
public class EmailOtpController {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private JavaMailSender mailSender;

    // Hiển thị form HTML (email + OTP)
    @GetMapping("/email")
    public String showEmailOtpForm() {
        return "auth/email_form";
    }

    @PostMapping("/send")
    @ResponseBody
    public String sendOtp(@RequestParam("email") String email, HttpSession session) {
        User userOpt = userRepository.findByEmail(email);
        if (userOpt == null) {
            return errorResponse("Email không tồn tại.");
        }

        OtpVerification otp = otpService.createOtpForUser(userOpt);

        // Gửi email chứa mã OTP
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userOpt.getEmail());
        message.setSubject("Mã OTP đăng nhập");
        message.setText("Mã OTP của bạn là: " + otp.getOtpCode());
        mailSender.send(message);

        // Lưu userId vào session (để verify OTP)
        session.setAttribute("otpUserId", userOpt.getId());
        return "OK";
    }

    // Xử lý Verify OTP (AJAX POST tới /api/otp/verify)
    @PostMapping("/verify")
    @ResponseBody
    public String verifyOtp(@RequestParam("email") String email,
                            @RequestParam("code") String otpCode,
                            HttpSession session) {
        Integer userId = (Integer) session.getAttribute("otpUserId");
        if (userId == null) {
            return errorResponse("Vui lòng gửi mã OTP trước.");
        }
        User userOpt = userRepository.findByEmail(email);
        if (userOpt == null) {
            return errorResponse("Không tìm thấy người dùng.");
        }
        OtpVerification verifiedOtp = otpService.verifyLatestOtpForUser(userOpt);
        if (verifiedOtp == null) {
            System.out.println("OTP không hợp lệ hoặc hết hạn.");
        } else {
            System.out.println("OTP hợp lệ? " + verifiedOtp.isVerified());
        }

        if (verifiedOtp != null && verifiedOtp.isVerified()) {
            session.setAttribute("loggedInUser", userOpt);
            System.out.println(111111);
            return "OK";
        } else {
            return errorResponse("Mã OTP không đúng hoặc đã hết hạn.");
        }

    }

    // Trang sau khi login thành công
    @GetMapping("/home")
    public String home() {
        return "home"; // ví dụ trả về trang home.html
    }

    @ResponseBody
    @ResponseStatus(code = org.springframework.http.HttpStatus.BAD_REQUEST)
    private String errorResponse(String message) {
        return message;
    }
}
