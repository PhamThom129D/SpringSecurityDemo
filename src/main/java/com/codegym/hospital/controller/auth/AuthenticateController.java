package com.codegym.hospital.controller.auth;


import com.codegym.hospital.model.user.User;
import com.codegym.hospital.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/authenticate")
public class AuthenticateController {

    @Autowired
    private IUserService userService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "authenticate/login";
    }@GetMapping("/otp")
    public String showLoginOTPForm(Model model) {
        model.addAttribute("user", new User());
        return "authenticate/otp";
    }
    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute("user") User user, HttpSession session, Model model) {
        User loggedInUser = userService.loginUser(user);
        if (loggedInUser != null) {
            session.setAttribute("loggedInUser", loggedInUser);
            return "admin/home";
        }else {
            model.addAttribute("error", "Số điện thoại hoặc mật khẩu không đúng");
            return "authenticate/login";
        }

    }
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "authenticate/register";
    }

    @PostMapping("/register")
    public String processRegisterForm(@ModelAttribute("user") User user, Model model) {
        String phone = user.getPhonenumber();
        String email = user.getEmail();

        if (userService.isPhoneNumberExist(phone) != null) {
            model.addAttribute("errorPhone", "Số điện thoại đã tồn tại");
            return "authenticate/register";
        }

        if (userService.isEmailExist(email) != null) {
            model.addAttribute("errorEmail", "Email đã tồn tại");
            return "authenticate/register";
        }

        String message = userService.registerUser(user);
        if (message == null) {
            model.addAttribute("error", "Vai trò không hợp lệ!");
            return "authenticate/register";
        }

        model.addAttribute("messageLogin", message);
        return "authenticate/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        session.invalidate();
        model.addAttribute("messageLogin","Đăng xuất thành công!");
        return "redirect:/authenticate/login";
    }

}
