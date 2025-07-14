package com.codegym.hospital.service.impl;

import com.codegym.hospital.model.auth.OtpVerification;
import com.codegym.hospital.model.user.User;
import com.codegym.hospital.service.IEmailService;
import com.codegym.hospital.service.IUserService;
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

    @Autowired
    private IUserService userService;


    @Override
    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        System.out.println(otp);
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

    @Override
    public void sendApprovalNotificationEmail(String email) {
        User user = userService.isEmailExist(email);
        String subject = "[HospitalCare] Thông báo phê duyệt tài khoản";

        String roleVN;
        switch (user.getRole().getName()) {
            case "DOCTOR":
                roleVN = "Bác sĩ";
                break;
            case "RECEPTIONIST":
                roleVN = "Nhân viên tiếp nhận";
                break;
            default:
                roleVN = "người dùng";
                break;
        }


        String loginUrl = "http://localhost:8080/authenticate/login";

        String body = "Kính gửi " + roleVN + " " + user.getFullname() + ",\n\n"
                + "Tài khoản của bạn đã được quản trị viên phê duyệt và kích hoạt thành công trên hệ thống HospitalCare.\n\n"
                + "Bạn hiện có thể đăng nhập vào hệ thống bằng tài khoản đã đăng ký tại địa chỉ:\n"
                + loginUrl + "\n\n"
                + "Nếu bạn gặp bất kỳ khó khăn nào trong quá trình sử dụng, vui lòng liên hệ bộ phận hỗ trợ.\n\n"
                + "Trân trọng,\n"
                + "Đội ngũ HospitalCare";

        sendMessageEmailToUser(user.getEmail(), subject, body);
    }

    @Override
    public void sendRejectNotificationEmail(String email, String reason) {
        User user = userService.isEmailExist(email);
        String subject = "[HospitalCare] Thông báo từ chối tài khoản";

        String roleVN;
        switch (user.getRole().getName()) {
            case "DOCTOR":
                roleVN = "Bác sĩ";
                break;
            case "RECEPTIONIST":
                roleVN = "Nhân viên tiếp nhận";
                break;
            default:
                roleVN = "người dùng";
                break;
        }

        String loginUrl = "http://localhost:8080/authenticate/login";

        String body = "Kính gửi " + roleVN + " " + user.getFullname() + ",\n\n"
                + "Chúng tôi rất tiếc phải thông báo rằng tài khoản đăng ký của bạn đã **KHÔNG ĐƯỢC PHÊ DUYỆT** trên hệ thống HospitalCare.\n\n"
                + "👉 **Lý do từ chối:** " + reason + "\n\n"
                + "Vui lòng kiểm tra lại thông tin và đăng ký lại nếu cần thiết, hoặc liên hệ bộ phận hỗ trợ để biết thêm chi tiết.\n\n"
                + "Nếu đây là sự nhầm lẫn, vui lòng phản hồi email này để chúng tôi xem xét lại yêu cầu của bạn.\n\n"
                + "🔗 Đăng nhập hoặc đăng ký lại tại: " + loginUrl + "\n\n"
                + "Trân trọng,\n"
                + "Đội ngũ HospitalCare";

        sendMessageEmailToUser(user.getEmail(), subject, body);
    }




}

