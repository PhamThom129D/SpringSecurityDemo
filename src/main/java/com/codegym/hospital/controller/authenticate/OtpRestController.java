package com.codegym.hospital.controller.authenticate;


import com.codegym.hospital.service.implement.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
public class OtpRestController {
    @Autowired
    private OtpService otpService;

    @PostMapping("/send")
    public ResponseEntity<?> sendOtp(@RequestParam String phonenumber){
        otpService.sendOtp(phonenumber);
        return ResponseEntity.ok("OTP sent");
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(@RequestParam String phonenumber, @RequestParam String otp_code){
        boolean isValid = otpService.verifyOtp(phonenumber,otp_code);
        if(isValid){
            return ResponseEntity.ok("OTP verified");
        }else {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }
    }

    @GetMapping("/home")
    public String getHome(){
        return "home";
    }
}
