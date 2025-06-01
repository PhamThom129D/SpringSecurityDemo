package com.codegym.hospital.controller;

import com.codegym.hospital.model.user.User;
import com.codegym.hospital.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/accountInfo")
public class AccountController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private BCryptPasswordEncoder passEncoder;
    @GetMapping("/adminInfo")
    public String showAdminInfo(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        model.addAttribute("roleDisplay", roleService.getRoleDisplayNames());
        model.addAttribute("user", user);
        return "admin/account";
    }
    @PostMapping("/adminInfo")
    public String updateAdminInfo(HttpSession session,@ModelAttribute User user, RedirectAttributes redirectAttributes) throws IOException {
        userService.createUserWithDetail(user, user.getAvatarFile());
        Optional<User> user1 = userService.getUserById(user.getId());
        redirectAttributes.addFlashAttribute("success", "Cập nhật thông tin người dùng thành công!");
        session.setAttribute("loggedInUser", user1.get());
        return "redirect:/accountInfo/adminInfo";
    }
    @PostMapping("/changePass")
    public String changePass(HttpSession session, @RequestParam("newPasswordModal") String newPass,@RequestParam("currentPasswordModal") String oldPass, RedirectAttributes redirectAttributes) throws IOException {
        User user = (User) session.getAttribute("loggedInUser");
        if (passEncoder.matches(oldPass, user.getPassword())) {
            user.setPassword(passEncoder.encode(newPass));
            userService.saveUser(user);
            redirectAttributes.addFlashAttribute("success", "Cập nhật mật khẩu thành công!");
        }else {
            redirectAttributes.addFlashAttribute("showChangePassModal", true);
            redirectAttributes.addFlashAttribute("error", "Sai mật khẩu , vui lòng nhập lại !");
        }
        return "redirect:/accountInfo/adminInfo";
    }
    @PostMapping("/toggleStatus")
    public String toggleUserStatus(HttpSession session,RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            if ("active".equals(user.getStatus())) {
                user.setStatus("inactive");
                redirectAttributes.addFlashAttribute("messageLogin", "Khoá tài khoản thành công!");
            }
            userService.saveUser(user);
        }
        return "redirect:/authenticate/login";
    }
}
