package com.codegym.hospital.controller.authenticate;


import com.codegym.hospital.model.User;
import com.codegym.hospital.service.IUserService;
import com.codegym.hospital.service.implement.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authenticate")
public class AuthenticateController {

    @Autowired
    private IUserService userService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "authenticate/login";
    }
    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute("user") User user, Model model) {
        User loggedInUser = userService.loginUser(user);
        if (loggedInUser != null) {
            model.addAttribute("user", loggedInUser);
            return "home";
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
        if(userService.isPhoneNumberExist(user.getPhonenumber()) != null){
         model.addAttribute("error","Số điện thoại đã tồn tại");
         return "authenticate/register";
        }else{
            switch (user.getRole().getName().toLowerCase()) {
                case "patient" :
                    user.setStatus("active");
                    model.addAttribute("messageLogin","Đăng ký thành công");
                    break;
                case "doctor":
                case "receptionist" :
                    model.addAttribute("messageLogin","Đăng ký thành công. Chờ duyệt!");
                    user.setStatus("pending");
                    break;
                default:
                    model.addAttribute("error","Vai trò không hợp lệ");
                    return "authenticate/register";
            }
            userService.registerUser(user);
            return "authenticate/login";
        }
    }
}
